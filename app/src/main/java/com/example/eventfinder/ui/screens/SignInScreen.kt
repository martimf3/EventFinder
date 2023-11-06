package com.example.eventfinder.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventfinder.R
import com.example.eventfinder.auth.googleauth.sign_in.SignInState
import com.example.eventfinder.ui.theme.GreenLigth
import com.example.eventfinder.ui.theme.WhiteLigth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavController,
    state: SignInState,
    onSignInClick: () ->Unit
) {
    var mail by remember { mutableStateOf(TextFieldValue(""))
    }
    var password by remember { mutableStateOf("")
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


    //Design
    Box(
        modifier = Modifier
            .background(WhiteLigth)
            .fillMaxSize()

    ) {

        // Right Corner Circle
        Canvas(
            modifier = Modifier
                .offset(x = 20.dp, y = 30.dp),
            onDraw = {
                drawCircle(
                    color = GreenLigth,
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
                    size = Size( 900f,  1100f ),

                    )
            }
        )

        Text( modifier = Modifier
            .offset(x=150.dp, y=700.dp),
            text = "Use other Method",
            style = TextStyle(
                fontFamily = FontFamily.Serif
            )
        )

        // Lines Between "Use Other Methods"
        Canvas(
            modifier = Modifier
                .offset(x = 80.dp, y = 705.dp),
            onDraw = {
                drawLine(
                    color = Color.Black,
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
                .offset(x = 0.dp, y = 330.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Email
            TextField(value = mail, onValueChange = {mail = it},
                label = { Text(text = "Email")},
                placeholder = { Text(text = "Enter Your Email")},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    placeholderColor = Color.Gray,
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = GreenLigth,

                    )
            )
            Spacer(modifier = Modifier.height(40.dp))
            //Password
            TextField(value = password, onValueChange = {password = it},
                label = { Text(text = "Password")},
                placeholder = { Text(text = "Enter Your Password")},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    placeholderColor = Color.Gray,
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = GreenLigth
                )
            )
            Spacer(modifier = Modifier.height(80.dp))

            Button(
                modifier = Modifier
                    .size(250.dp,45.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(GreenLigth)){
                Text(
                    text = "Login",
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontSize = 20.sp
                    )
                )

            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "New User? Register Now",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = GreenLigth
                )
            )


        }

        //Title
        Text( modifier = Modifier
            .offset(x = 20.dp, y = 70.dp),
            text = "SignIn",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,

                )
        )
        //Subtitle
        Text( modifier = Modifier
            .offset(x = 20.dp, y = 125.dp),
            text = "For exciting events\nwith EventFinder",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        )

        // Sign in Method With Google
        Button( modifier = Modifier
            .offset(x =170.dp, y = 730.dp),
            onClick = (onSignInClick),
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
                        color = Color.Black
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
                    color = GreenLigth,
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
                    color = GreenLigth,
                    cornerRadius = CornerRadius(x = 100f, y = 50f)

                )
            }
        )

        //Floating Button

        FloatingActionButton(
            onClick = { navController.navigate("sign_up")},
            modifier = Modifier
                .offset(x=365.dp, y = 755.dp)
                .size(60.dp, 60.dp),
            shape = CircleShape,
            containerColor = GreenLigth

        ) {
            Image(
                modifier = Modifier
                    .offset(x = 0.dp, y = 0.dp)
                    .size(60.dp, 60.dp),
                painter = painterResource(id = R.mipmap.plus_icon) ,
                contentDescription = "SignUp Page Button",
                alignment = Alignment.CenterStart
            )

        }



        /*
        //Redirect Button for SignUp
        Button( modifier = Modifier
            .offset(x =345.dp, y = 750.dp),
            onClick = {navController.navigate("sign_up")},
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Image(
                modifier = Modifier
                    .offset(x = 0.dp, y = 0.dp)
                    .size(60.dp, 60.dp),
                painter = painterResource(id = R.mipmap.plus_icon) ,
                contentDescription = "SignUp Page Button",
                alignment = Alignment.CenterStart
            )

        }
         */


        //Logo Image
        Image(
            modifier = Modifier
                .offset(x = 200.dp, y = 0.dp)
                .size(200.dp, 200.dp),
            painter = painterResource(id = R.mipmap.logo),
            contentDescription = "Logo Button"
        )

    }


}

