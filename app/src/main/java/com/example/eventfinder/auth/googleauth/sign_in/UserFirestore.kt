package com.example.eventfinder.auth.googleauth.sign_in

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

fun userFirestore(
    userData: UserData,
    onComplete: ((isSuccess: Boolean, errorMessage: String?) -> Unit)
){
    val db = FirebaseFirestore.getInstance()
    val userMap = hashMapOf(
        "userID" to userData.userId,
        "username" to userData.username,
        "profilePictureUrl" to userData.profilePictureUrl,
        "userEmail" to userData.userEmail,
        "userPhoneNumber" to userData.userPhoneNumber
    )

    db.collection("users").document(userData.userId)
        .set(userMap)
        .addOnSuccessListener {
            onComplete(true,null)
        }
        .addOnFailureListener {e->
            onComplete(false, e.message ?: "Erro a Salvar Dados na Firestore")
        }
}