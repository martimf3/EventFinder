package com.example.eventfinder.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eventfinder.auth.googleauth.sign_in.UserData
import com.example.eventfinder.ui.theme.GreenLigth
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfile(
    navController: NavController,
    db:FirebaseFirestore,
    userData: UserData,
    onProfileUpdated: (Boolean) -> Unit

) {


    var userName by remember  { mutableStateOf("")}
    var userEmail by remember { mutableStateOf("")}
    var userPhone by remember { mutableStateOf("")}


    // Carregar dados do Utilizador
    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = 0.dp, y = 300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Name
        TextField(
            value = userName, onValueChange = { userName = it },
            label = { Text(text = "Name") },
            placeholder = { Text(text = "Enter Your Name") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                placeholderColor = Color.Gray,
                containerColor = Color.Transparent,
                focusedIndicatorColor = GreenLigth,

                )
        )
        Spacer(modifier = Modifier.height(16.dp))
        //Email
        TextField(
            value = userEmail, onValueChange = { userEmail = it },
            label = { Text(text = "Email") },
            placeholder = { Text(text = "Enter Your Email") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                placeholderColor = Color.Gray,
                containerColor = Color.Transparent,
                focusedIndicatorColor = GreenLigth,

                )
        )
        Spacer(modifier = Modifier.height(16.dp))
        //PhoneNumber
        TextField(
            value = userPhone, onValueChange = { userPhone = it },
            label = { Text(text = "Phone Number") },
            placeholder = { Text(text = "Enter Your Phone Number") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                placeholderColor = Color.Gray,
                containerColor = Color.Transparent,
                focusedIndicatorColor = GreenLigth,

                )
        )

        Spacer(modifier = Modifier.height(16.dp))
    }


    Button(onClick = {
        val updateUser = mutableMapOf<String, Any>()

        if(userName.isNotEmpty() && userName!=userData.username){
            updateUser["userName"] = userName
        }
        if(userEmail.isNotEmpty() && userEmail!=userData.userEmail){
            updateUser["userEmail"] = userEmail
        }
        if(userPhone.isNotEmpty() && userPhone!=userData.userPhoneNumber){
            updateUser["userPhoneNumber"] = userPhone
        }

        db.collection("users").document(userData.userId).update(updateUser)
            .addOnSuccessListener {
                onProfileUpdated(true)
            }
            .addOnFailureListener { e ->
                onProfileUpdated(false)
            }

    }) {
        Text(text = "Update Profile")
    }


}