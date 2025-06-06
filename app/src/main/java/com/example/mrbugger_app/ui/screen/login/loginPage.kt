package com.example.mrbugger_app.ui.screen.login

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.mrbugger_app.AuthViewModel.AuthState
import com.example.mrbugger_app.AuthViewModel.AuthViewModel
import com.example.mrbugger_app.AuthViewModel.BiometricAuthStatus
import com.example.mrbugger_app.AuthViewModel.BiometricAuthenticator
import com.example.mrbugger_app.R
import com.example.mrbugger_app.Screen


@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val authState = authViewModel.authState.observeAsState()
    val hasPreviousLogin = authViewModel.hasPreviousLogin.observeAsState()
    val context = LocalContext.current

    // Safe casting with null check
    val fragmentActivity = context as? FragmentActivity

    // Initialize biometric authenticator only if we have a valid FragmentActivity
    val biometricAuthenticator = remember {
        if (fragmentActivity != null) BiometricAuthenticator(context) else null
    }

    // Check previous login status when screen loads
    LaunchedEffect(Unit) {
        authViewModel.checkPreviousLoginStatus(context)
    }

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
            contentDescription = stringResource(R.string.background_image),
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
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 1f),
                    shape = RoundedCornerShape(24.dp)
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
                        .offset(x = (-15).dp, y = (-16).dp)
                        .padding(start = 4.dp, top = 4.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = AnnotatedString.Builder().apply {
                            pushStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                                    fontWeight = MaterialTheme.typography.displayLarge.fontWeight,
                                )
                            )
                            append("Mr.")
                            pop()

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

                // Logo
                Image(
                    painter = painterResource(R.drawable.logo2),
                    contentDescription = "Logo",
                    modifier = Modifier.size(160.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Welcome Text
                Text(
                    text = stringResource(R.string.welcome),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Email TextField
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", style = MaterialTheme.typography.bodyLarge) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(16.dp)
                        )
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
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

                // Password TextField
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", style = MaterialTheme.typography.bodyLarge) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(16.dp)
                        )
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground
                    ),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Show biometric login option if all conditions are met - NOW POSITIONED AFTER INPUT FIELDS
                if (hasPreviousLogin.value == true &&
                    biometricAuthenticator != null &&
                    fragmentActivity != null &&
                    biometricAuthenticator.isBiometricAuthAvailable() == BiometricAuthStatus.READY) {

                    // Biometric Authentication Button with better alignment
                    OutlinedButton(
                        onClick = {
                            biometricAuthenticator.promptBiometricAuth(
                                title = "Biometric Login",
                                subTitle = "Use your fingerprint to sign in",
                                negativeButtonText = "Cancel",
                                fragmentActivity = fragmentActivity,
                                onSuccess = {
                                    authViewModel.biometricLogin()
                                },
                                onFailed = {
                                    Toast.makeText(context, "Biometric authentication failed", Toast.LENGTH_SHORT).show()
                                },
                                onError = { _, errorString ->
                                    Toast.makeText(context, errorString.toString(), Toast.LENGTH_SHORT).show()
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Fingerprint,
                                contentDescription = "Fingerprint",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Sign in with Fingerprint",
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Divider with proper alignment
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Divider(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                            thickness = 1.dp,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "OR",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Divider(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                            thickness = 1.dp,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Login Button with consistent styling
                Button(
                    onClick = { authViewModel.login(email, password, context) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    enabled = authState.value != AuthState.Loading
                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Signup TextButton
                TextButton(
                    onClick = { navController.navigate(Screen.Singup.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Don't Have an Account? Sign Up",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}