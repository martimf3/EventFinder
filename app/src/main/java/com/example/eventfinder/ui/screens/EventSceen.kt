package com.example.eventfinder.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventfinder.data.api.ticketMaster.GetEvents

import com.example.eventfinder.data.models.EventData

@Composable
fun EventListCard(events: List<EventData>) {

    @Composable
    fun EventCard(event: EventData) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = event.name, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = event.type, style =TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = event.url, style =TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(navController: NavController) {
    var events by remember { mutableStateOf(emptyList<EventData>()) }

    // Chame sua função GetEvents para buscar os dados e atualizar a lista "events"
    GetEvents { eventData ->
        if (eventData != null) {
            // Atualize a lista de eventos com os dados obtidos
            events = eventData
        } else {
            println("Failed to retrieve event data.")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de Eventos") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Adicione o espaçamento aqui
        ) {
            EventListCard(events = events)
        }
    }
}

