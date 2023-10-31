package com.example.eventfinder.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventfinder.R
import com.example.eventfinder.ui.theme.GreenLigth
import com.example.eventfinder.ui.theme.WhiteLigth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController
) {
    var mail by remember { mutableStateOf(TextFieldValue(""))
    }
    var password by remember { mutableStateOf("")
    }
    var name by remember { mutableStateOf("")
    }
    var number by remember { mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .background(GreenLigth)
            .fillMaxSize()

    ) {

        // Right Corner Circle
        Canvas(
            modifier = Modifier
                .offset(x = 20.dp, y = 30.dp),
            onDraw = {
                drawCircle(
                    color = WhiteLigth,
                    center = Offset(350.dp.toPx(),20.dp.toPx()),
                    radius = 200.dp.toPx()

                )
            }
        )

        //Middle Canvas Rectangle
        Canvas(modifier = Modifier
            .offset(x = 35.dp, y = 280.dp),

            onDraw = {
                drawRoundRect(
                    color = WhiteLigth,
                    cornerRadius = CornerRadius(40f,30f),
                    size = Size( 1200f,  1410f ),

                    )
            }
        )

        Text( modifier = Modifier
            .offset(x=140.dp, y=700.dp),
            text = "Use other Method",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                color = WhiteLigth
            )
        )

        // Lines Between "Use Other Methods"
        Canvas(
            modifier = Modifier
                .offset(x = 80.dp, y = 705.dp),
            onDraw = {
                drawLine(
                    color = Color.White,
                    start = Offset(0f,60f),
                    end = Offset(900f,60f),
                    strokeWidth = 2f

                )
            }
        )

        //Input Box

        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 0.dp, y = 300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Name
            TextField(value = name, onValueChange = {name = it},
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
            TextField(value = mail, onValueChange = {mail = it},
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
            TextField(value = number, onValueChange = {number = it},
                label = { Text(text = "PhoneNumber") },
                placeholder = { Text(text = "Enter Your Email") },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    placeholderColor = Color.Gray,
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = GreenLigth,

                    )
            )

            Spacer(modifier = Modifier.height(16.dp))
            //Password
            TextField(value = password, onValueChange = {password = it},
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter Your Email") },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    placeholderColor = Color.Gray,
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = GreenLigth
                )
            )
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                modifier = Modifier
                    .size(250.dp,45.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(GreenLigth)){
                Text(
                    text = "Register",
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontSize = 20.sp
                    )
                )

            }
            Spacer(modifier = Modifier.height(15.dp))




        }

        Text( modifier = Modifier
            .offset(x = 20.dp, y = 70.dp),
            text = "SignUp",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,

                )
        )
        Text( modifier = Modifier
            .offset(x = 20.dp, y = 125.dp),
            text = "Here's your first\nstep with us",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        )

        // Sign in Method With Google
        Button( modifier = Modifier
            .offset(x =170.dp, y = 730.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.mipmap.google_sign_in) ,
                    contentDescription = "Google Button"
                )
                Text(
                    text = "Sign-In",
                    style = TextStyle(
                        color = Color.White
                    )
                )
            }

        }

        //Horizontal Rectangle
        Canvas(
            modifier = Modifier
                .offset(x = 40.dp, y = 180.dp)
                .size(600.dp, 5.dp),
            onDraw = {
                drawRoundRect(
                    color = WhiteLigth,
                    cornerRadius = CornerRadius(x = 100f, y = 50f)

                )
            }
        )
        //Vertical Rectangle
        Canvas(
            modifier = Modifier
                .offset(x = 405.dp, y = 235.dp)
                .size(10.dp, 600.dp),
            onDraw = {
                drawRoundRect(
                    color = WhiteLigth,
                    cornerRadius = CornerRadius(x = 100f, y = 50f)

                )
            }
        )

        //Redirect Button for SignUp
        Button( modifier = Modifier
            .offset(x =345.dp, y = 750.dp),
            onClick = { navController.navigate("sign_in") },
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Image(
                modifier = Modifier
                    .offset(x = 0.dp, y = 0.dp)
                    .size(60.dp, 60.dp),
                painter = painterResource(id = R.mipmap.back) ,
                contentDescription = "SignUp Page Button",
                alignment = Alignment.CenterStart
            )



        }

        Image(
            modifier = Modifier
                .offset(x = 200.dp, y = 0.dp)
                .size(200.dp, 200.dp),
            painter = painterResource(id = R.mipmap.logo),
            contentDescription = "Logo Button"
        )

    }
}
