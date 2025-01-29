package com.example.mrbugger_app.ui.screen.ProfileScreen

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.mrbugger_app.AuthViewModel.AuthViewModel
import com.example.mrbugger_app.CommonSections.ScreenWithBottonNavBar
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.R
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.model.UserProfileViewModel
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.TextColor
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, userProfileViewModel: UserProfileViewModel,authViewModel: AuthViewModel) {
    var isEditMode by remember { mutableStateOf(false) }
    val userProfile = userProfileViewModel.userProfile.value

    var username by remember { mutableStateOf(userProfile.username) }
    var password by remember { mutableStateOf(userProfile.password) }
    var email by remember { mutableStateOf(userProfile.email) }
    var contact by remember { mutableStateOf(userProfile.contact) }
    var address by remember { mutableStateOf(userProfile.address) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBarSection(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .offset(y = -50.dp)
                    .clip(shape = CircleShape),
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.primary,

                onClick = {
                    if (isEditMode) {
                        coroutineScope.launch {
                            userProfileViewModel.updateUserData(
                                username,
                                password,
                                email,
                                contact,
                                address
                            )
                        }
                    }
                    isEditMode = !isEditMode
                }
            ) {
                androidx.compose.material3.Icon(
                    imageVector = if (isEditMode) Icons.Outlined.Check else Icons.Outlined.Edit,
                    contentDescription = if (isEditMode) "Save" else "Edit",
                )
            }
        },

        ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(R.drawable.profile2),
                contentDescription = "user",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Colonel Harland",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = if (isLandscape) 220.dp else 10.dp,
                        vertical = if (isLandscape) 15.dp else 10.dp
                    )
                    .padding(top = if (isLandscape) 2.dp else 10.dp)
                    .padding(bottom = if (isLandscape) 2.dp else 20.dp)
                    .background(
                        color = Color.LightGray.copy(alpha = 1f), // transparent white
                        shape = RoundedCornerShape(24.dp) // Rounded corners
                    )
                    .shadow(elevation = 8.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(12.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    UserInfoField(
                        label = "Username",
                        value = username,
                        isEditMode = isEditMode,
                        onValueChange = { username = it })
                    Spacer(modifier = Modifier.height(15.dp))
                    UserInfoField(
                        label = "Password",
                        value = password,
                        isEditMode = isEditMode,
                        onValueChange = { password = it })
                    Spacer(modifier = Modifier.height(15.dp))
                    UserInfoField(
                        label = "E-mail",
                        value = email,
                        isEditMode = isEditMode,
                        onValueChange = { email = it })
                    Spacer(modifier = Modifier.height(15.dp))
                    UserInfoField(
                        label = "Contact",
                        value = contact,
                        isEditMode = isEditMode,
                        onValueChange = { contact = it })
                    Spacer(modifier = Modifier.height(15.dp))
                    UserInfoField(
                        label = "Address",
                        value = address,
                        isEditMode = isEditMode,
                        onValueChange = { address = it })
                    Spacer(modifier = Modifier.height(30.dp))

                }

            }


            Spacer(modifier = Modifier.height(16.dp))

            // Row with Logout icon outside the button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon outside the button
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout Icon",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(30.dp)
                )
                Button(
                    onClick = { authViewModel.signout(navController) },
                    modifier = Modifier
                        .width(150.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Logout", fontSize = 22.sp)
                }
            }

            Spacer(modifier = Modifier.height(65.dp))
        }

        ScreenWithBottonNavBar(navController = navController)
    }
}


@Composable
fun UserInfoField(label: String, value: String, isEditMode: Boolean, onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        if (isEditMode) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    onValueChange(it)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onValueChange(text) }),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)))

        } else {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
