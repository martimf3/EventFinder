package com.example.eventfinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eventfinder.ui.theme.BlueLitgh
import com.example.eventfinder.ui.theme.WhiteLigth
import com.example.eventfinder.ui.theme.GreenLigth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTeste() {




    Box(
        modifier = Modifier
            .background(WhiteLigth)
            .fillMaxSize()

    ){

        FloatingActionButton(
            modifier = Modifier
                .size(40.dp, 40.dp)
                .scale(0.8f, 0.8f)
                .offset(x = 430.dp, y = 880.dp),
            onClick = {

            },
            shape = CircleShape,
            containerColor = WhiteLigth,
            contentColor = Color.Black

        ) {

            Icon(Icons.Filled.DateRange, "Floating action button.")
        }
        FloatingActionButton(
            modifier = Modifier
                .size(40.dp, 40.dp)
                .scale(0.6f, 0.6f)
                .offset(x = 575.dp, y = 1100.dp),
            onClick = {

            },
            shape = CircleShape,
            containerColor = WhiteLigth,
            contentColor = Color.Black

        ) {

            Icon(Icons.Filled.Search, "Floating action button.")
        }

    }















}











@Preview
@Composable
fun HomeScreenTestePreview() {
    HomeScreenTeste()
}