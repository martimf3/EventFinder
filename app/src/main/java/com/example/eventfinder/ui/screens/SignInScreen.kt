package com.example.eventfinder.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eventfinder.auth.googleauth.sign_in.SignInState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavController,
    state: SignInState,
    onSignInClick: () ->Unit
) {
    var mail by remember { mutableStateOf(TextFieldValue(""))
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError){
        state.signInError?.let{error->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    
    //Sign In Email Account /// Testing
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Email
        Text(text = "Email")
        TextField(value = mail, onValueChange = {mail = it},
            label = { Text(text = "Email")},
            placeholder = { Text(text = "Enter Your Email")})
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Pass")
        Spacer(modifier = Modifier.height(20.dp))
        //Password

        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Login ")
            
        }
    }
    
    Spacer(modifier = Modifier.height(16.dp))

    
    // Sign In Google Account 
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center

    ){
        Button(onClick = onSignInClick,
                modifier = Modifier
                    )


        {
            Text(text="Google Sing-In")
        }
    }
}