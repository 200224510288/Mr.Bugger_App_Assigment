package com.example.mrbugger_app.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mrbugger_app.R
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
                .padding(horizontal = 32.dp, vertical = 20.dp)
                .padding(top = 150.dp, bottom = 50.dp)
                .background(
                    color = Color.White.copy(alpha = 1f),
                    shape = RoundedCornerShape(24.dp)
                )
                .clip(RoundedCornerShape(24.dp))
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                    shape = RoundedCornerShape(16.dp)
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
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(26.dp))

                // Login Button
                Button(
                    onClick = { /* Handle Login */ },
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
                TextButton(onClick = { /* Handle Signup navigation */ }) {
                    Text(text = "Don't Have an Account? Sign Up", color = TextColor, fontSize = 18.sp)
                }
            }
        }
    }
}
