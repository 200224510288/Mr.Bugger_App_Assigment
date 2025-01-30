package com.example.mrbugger_app.ui.screen.signup

import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mrbugger_app.AuthViewModel.AuthState
import com.example.mrbugger_app.AuthViewModel.AuthViewModel
import com.example.mrbugger_app.R
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.TextColor

@Composable
fun signupPage(navController: NavController,authViewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var contact by remember{ mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    var passwordVisible by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Image Picker
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri // Set selected image URI
    }

//user sign up with firebase
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }

    }

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
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 1f), // transparent white
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
                                )
                            )
                            append("Burger")
                            pop()
                        }.toAnnotatedString(),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Profile Image
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (profileImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(profileImageUri),
                            contentDescription = "Profile Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Default Profile Icon",
                            tint = Color.White,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Welcome Text
                Text(
                    text = stringResource(R.string.create_your_account),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(32.dp))

                // UserName TextField
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("UserName") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(16.dp)
                        )
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                    textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground
                    ),
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Person Icon",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )

                Spacer(modifier = Modifier.height(26.dp))

                // Email TextField
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(16.dp)
                        )
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                    textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground
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

                // Contact TextField
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(16.dp)
                        )
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                    textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground
                    ),
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "Lock Icon",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )

                Spacer(modifier = Modifier.height(26.dp))

                // Location TextField
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(16.dp)
                        )
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                    textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground
                    ),
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.AddLocationAlt,
                            contentDescription = "Location Icon",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )

                Spacer(modifier = Modifier.height(26.dp))

                // SignUp Button
                Button(
                    onClick = { authViewModel.signup(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 7.dp, horizontal = 50.dp)
                        .height(60.dp)
                        .clip(CircleShape),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    enabled = authState.value != AuthState.Loading // Disable button when loading
                ) {
                    Text(
                        text = "Sign Up",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 28.dp)
                    )
                }


                Spacer(modifier = Modifier.height(22.dp))

                // Signup TextButton
                TextButton(onClick = {navController.navigate(Screen.Login.route) }) {
                    Text(text = "Already Have an Account? Login", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
