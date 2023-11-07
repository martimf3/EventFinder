package com.example.eventfinder.ui.screens

import android.util.Log

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.eventfinder.*
import com.example.eventfinder.auth.googleauth.sign_in.UserData
import com.example.eventfinder.ui.theme.BlueLitgh
import com.example.eventfinder.ui.theme.GreenLigth
import com.example.eventfinder.ui.theme.WhiteLigth
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
    val keyword = currentUser?.displayName.toString()

    var userData by remember { mutableStateOf<UserData?>(null) }
    var confirmationText by remember { mutableStateOf("") }
    var showOptions by remember { mutableStateOf(false) }



    LaunchedEffect(Unit) {
        val userDoc = db.collection("users").document(userID).get().await()
        userData = userDoc.toObject(UserData::class.java)
    }

    Box(
        modifier = Modifier
            .background(WhiteLigth)
            .fillMaxWidth()

    )
    Canvas(
        modifier = Modifier,
        onDraw = {
            drawRoundRect(
                color = BlueLitgh,
                cornerRadius = CornerRadius(0f, 30f),
                size = Size(1500f, 900f)
            )
        }
    )
    userData?.let { user ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Profile",
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(15.dp))
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


            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Name:",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            )
            
            Spacer(modifier = Modifier.height(10.dp))

            user.username?.let { username ->
                Text(
                    text = username,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,

                )

            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Email:",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            user.userEmail?.let { email ->
                Text(
                    text = email,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,


                    )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Phone Number:",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            user.userPhoneNumber?.let { phone ->
                if(phone.isNotBlank()){
                    Text(
                        text = phone,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )

                }else{
                    Text(
                        text = "N/A",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )

                }


            }


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Settings:",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            )

            IconButton(onClick = { showOptions = true},
                modifier = Modifier.size(50.dp)) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")

                }



            }
            
        }

    if (showOptions) {
        AlertDialog(

            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = { showOptions = false },
            containerColor = WhiteLigth,
            title = { Text(text = "Settings") },
            confirmButton = {
                IconButton(onClick = { showOptions = false},
                    modifier = Modifier.scale(2f)){
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = Color.Black )
                }

            },
            text = {
                // Options Inside Dialog
                userData?.let { user ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
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

                        Button(
                            onClick = { navController.navigate("update") },
                            colors = ButtonDefaults.buttonColors(GreenLigth)) {
                            Text(text = "Update",
                                style = TextStyle(
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 20.sp
                                )
                            )

                        }


                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { onSignOut() },
                            colors = ButtonDefaults.buttonColors(GreenLigth)) {
                            Text(text = "Sign Out",
                                style = TextStyle(
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 20.sp
                                )
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Sign Out")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = confirmationText,
                            onValueChange = { confirmationText = it },
                            label = {
                                Text(
                                    "Type '$keyword' for Profile Elimination",
                                    textAlign = TextAlign.Center
                                )
                            },
                            modifier = Modifier
                                .size(350.dp, 55.dp)

                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {
                            if (confirmationText == keyword) {
                                db.collection("users").document(userID)
                                    .delete()
                                    .addOnSuccessListener {
                                        Log.d("ProfileDeletion", "User data deleted from Firestore")
                                        firebaseAuth.currentUser?.delete()
                                        navController.navigate("sign_in")
                                    }
                            }
                        },
                            colors = ButtonDefaults.buttonColors(Color.Red.copy(alpha = 0.5f))) {
                            Text(text = "Delete Profile")
                        }


                    }

                }


            },

        )
    }


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