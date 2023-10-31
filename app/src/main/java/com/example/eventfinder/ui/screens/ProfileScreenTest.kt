package com.example.eventfinder.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.eventfinder.auth.googleauth.sign_in.UserData
import com.example.eventfinder.ui.theme.GreenLigth
import com.example.eventfinder.ui.theme.WhiteLigth
import com.example.eventfinder.auth.googleauth.sign_in.GoogleAuthUiClient
import kotlinx.coroutines.launch

@Composable
fun ProfileScreenTest(
    navController: NavController,
    userData: com.example.eventfinder.auth.googleauth.sign_in.UserData?,
    onSignOut: () -> Unit
) {

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteLigth),
    ){
        Column (
            modifier = Modifier
                .fillMaxSize(),
        ){


        }
    }


    
}







@Preview
@Composable
fun ProfileScreenTestPreview() {



    
}