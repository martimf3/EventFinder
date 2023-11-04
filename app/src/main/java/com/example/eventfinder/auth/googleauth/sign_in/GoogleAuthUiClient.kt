package com.example.eventfinder.auth.googleauth.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.example.eventfinder.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient (
    private val context: Context,
    private val oneTapClient: SignInClient
){
    private val auth  = Firebase.auth
    private val db = FirebaseFirestore.getInstance()

    suspend fun signIn(): IntentSender?{
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()

        }catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null

        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            val userId = user?.uid ?: ""
            val existingDoc = db.collection("users").document(userId).get().await()

            if (!existingDoc.exists()) {
                val userData = UserData(
                    userId = user?.uid ?: "",
                    username = user?.displayName ?: "",
                    profilePictureUrl = user?.photoUrl?.toString() ?: "",
                    userEmail = user?.email ?: "",
                    userPhoneNumber = user?.phoneNumber ?: "",

                )

                userFirestore(userData) { isSuccess, errorMessage ->
                    if (isSuccess) {
                        print("Profile saved Successfully")
                    } else {
                        print("Profile wasn't saved $errorMessage")

                    }
                }

                SignInResult(data = userData, errorMessage = null)
            }else{
                val  existingUserData = existingDoc.toObject(UserData::class.java)
                return if (existingUserData!= null){
                    SignInResult(data = existingUserData, errorMessage = null)
                }else{
                    SignInResult(data = null, errorMessage = "Error Retrieving data ")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e

            SignInResult(data = null, errorMessage = e.message)

            }
        }


    suspend fun signOut(){
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e

            Log.d("app", "Erro: ${e.message}")

        }
    }

    fun getSignedInUser(): UserData?= auth.currentUser?.run{
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString(),
            userEmail = email,
            userPhoneNumber = phoneNumber

        )
    }
    private fun buildSignInRequest(): BeginSignInRequest{
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

}