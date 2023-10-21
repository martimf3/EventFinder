package com.example.eventfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eventfinder.navigation.MainNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = MyApplication.applicationContext()
        super.onCreate(savedInstanceState)
        setContent {
            MainNavigation()

        }
    }
}

