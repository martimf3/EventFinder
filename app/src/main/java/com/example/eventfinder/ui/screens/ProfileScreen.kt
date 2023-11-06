package com.example.eventfinder.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.eventfinder.*
import com.example.eventfinder.auth.googleauth.sign_in.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController:NavController,
    userData: UserData,
    db: FirebaseFirestore,
    onSignOut: () -> Unit
) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser
    val userID = currentUser?.uid.toString()
    val keyword = "DELETE"

    var userData by remember { mutableStateOf<UserData?>(null) }
    var confirmationText by remember { mutableStateOf("") }

    
    LaunchedEffect(Unit){
        val userDoc = db.collection("users").document(userID).get().await()
        userData = userDoc.toObject(UserData::class.java)
    }

    userData?.let {user ->
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        user.profilePictureUrl?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop

            )

        }


        Spacer(modifier = Modifier.height(16.dp))

        user.username?.let { username ->
            Text(
                text = username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        user.userEmail?.let { email ->
            Text(
                text = email,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,

                )

        }

        Spacer(modifier = Modifier.height(16.dp))

        user.userPhoneNumber?.let { phone ->
            Text(
                text = phone,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onSignOut() }) {
            Text(text = "Sign Out")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("update") }) {
            Text(text = "Update")

        }

    }
    }
    Button(onClick = {
        if (confirmationText.uppercase() == keyword) {
            db.collection("users").document(userID)
                .delete()
                .addOnSuccessListener {
                    Log.d("ProfileDeletion", "User data deleted from Firestore")
                    // Depois que os dados do Firestore forem excluídos com sucesso,
                    // então proceda com a exclusão do usuário do Firebase Authentication
                    firebaseAuth.currentUser?.delete()
                    navController.navigate("sign_in")
                }
        }
    }) {
        Text(text = "Delete Profile")
    }
    OutlinedTextField(
        value = confirmationText,
        onValueChange = { confirmationText = it },
        label = { Text("Type '$keyword' for Profile Elimination",
            textAlign = TextAlign.Center) },
        modifier = Modifier
            .size(250.dp, 75.dp)
            .offset(x = 80.dp, y = 650.dp)
    )
}

/*

// Your HomeScreen content
        if(userData?.profilePictureUrl !=null){
            AsyncImage(
                model = userData.profilePictureUrl ,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop

            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        if(userData?.username !=null){
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if(userData?.userEmail != null){
            Text(
                text = userData.userEmail,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,

                )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if(userData?.userPhoneNumber != null){
            Text(
                text = userData.userPhoneNumber,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
*/