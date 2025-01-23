package com.example.mrbugger_app.ui.screen.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mrbugger_app.R
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.ui.screen.PlainTextVisualTransformation
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.ExtraYellowLight
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight
import com.example.mrbugger_app.ui.theme.SecondaryColor
import com.example.mrbugger_app.ui.theme.TextColor

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.backgorud),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Glass Background Box
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = if (isLandscape) 220.dp else 32.dp,
                    vertical = if (isLandscape) 15.dp else 20.dp
                )
                .padding(top = if (isLandscape) 2.dp else 150.dp)
                .padding(bottom = if (isLandscape) 2.dp else 50.dp)
                .background(
                    color = Color.White.copy(alpha = 1f), // transparent white
                    shape = RoundedCornerShape(24.dp) // Rounded corners
                )
                .clip(RoundedCornerShape(24.dp))
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()

            ) {
                // Top-left Mr. Burger Text
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(if (isLandscape) 16.dp else 12.dp)
                        .offset(x = (-15).dp, y = (-16).dp) // X and Y offsets
                        .padding(start = 4.dp, top = 4.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = AnnotatedString.Builder().apply {
                            // Style for "Mr."
                            pushStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                                    fontWeight = MaterialTheme.typography.displayLarge.fontWeight,
                                    fontSize = 22.sp
                                )
                            )
                            append("Mr.")
                            pop()

                            // Style for "Burger"
                            pushStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                                    fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                                    fontSize = 22.sp
                                )
                            )
                            append("Burger")
                            pop()
                        }.toAnnotatedString(),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))
                // Logo
                Image(
                    painter = painterResource(R.drawable.logo2),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(160.dp)
//                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Welcome Text
                Text(
                    text = "Welcome",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Email TextField
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = BackgroundColor,
                        unfocusedLabelColor = TextColor
                    ),
                    shape = RoundedCornerShape(16.dp),
                            leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Email Icon",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )

                Spacer(modifier = Modifier.height(26.dp))

                // Password TextField
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = BackgroundColor,
                        unfocusedLabelColor = TextColor
                    ),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(26.dp))

                // Login Button
                Button(
                    onClick = { navController.navigate(Screen.Home.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 30.dp)
                        .clip(CircleShape),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                // Signup TextButton
                TextButton(onClick = { navController.navigate(Screen.Singup.route)  }) {
                    Text(text = "Don't Have an Account? Sign Up", color = TextColor, fontSize = 18.sp)
                }
            }
        }
    }
}
