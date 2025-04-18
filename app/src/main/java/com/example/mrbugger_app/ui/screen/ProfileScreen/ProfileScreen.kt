package com.example.mrbugger_app.ui.screen.ProfileScreen

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import coil.compose.rememberAsyncImagePainter
import com.example.mrbugger_app.AuthViewModel.AuthViewModel
import com.example.mrbugger_app.CommonSections.ScreenWithBottonNavBar
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.R
import com.example.mrbugger_app.Screen
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.UserProfileViewModel
import com.example.mrbugger_app.ui.theme.BackgroundColor
import com.example.mrbugger_app.ui.theme.Shapes
import com.example.mrbugger_app.ui.theme.TextColor
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, userProfileViewModel: UserProfileViewModel,authViewModel: AuthViewModel, cartViewModel: CartViewModel) {
    // adding variables for edit mode
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


    // Image Picker
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri // Set selected image URI
    }

    Scaffold(
        topBar = {
            TopBarSection(navController = navController, cartViewModel = cartViewModel)
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
                .background(MaterialTheme.colorScheme.background)
                .imePadding()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Profile Image (getting image by gallery)
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
                        contentDescription = stringResource(R.string.profile_image),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.profile2),
                        contentDescription = "Profile Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = username,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,

            )
            Spacer(modifier = Modifier.height(20.dp))
               // main user details part
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
                        onValueChange = { username = it },
                        keyboardType = KeyboardType.Text
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    UserInfoField(
                        label = "Password",
                        value = password,
                        isEditMode = isEditMode,
                        onValueChange = { password = it },
                        keyboardType = KeyboardType.Text)
                    Spacer(modifier = Modifier.height(15.dp))
                    UserInfoField(
                        label = "E-mail",
                        value = email,
                        isEditMode = isEditMode,
                        onValueChange = { email = it },
                        keyboardType = KeyboardType.Text)
                    Spacer(modifier = Modifier.height(15.dp))
                    UserInfoField(
                        label = "Contact",
                        value = contact,
                        isEditMode = isEditMode,
                        onValueChange = { contact = it },
                        keyboardType = KeyboardType.Phone)
                    Spacer(modifier = Modifier.height(15.dp))
                    UserInfoField(
                        label = "Address",
                        value = address,
                        isEditMode = isEditMode,
                        onValueChange = { address = it },
                        keyboardType = KeyboardType.Text)
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
                    Text(text = "Logout", style = MaterialTheme.typography.titleLarge)
                }
            }

            Spacer(modifier = Modifier.height(65.dp))
        }

        ScreenWithBottonNavBar(navController = navController, cartViewModel = cartViewModel)    }
}


@Composable
fun UserInfoField(label: String, value: String, isEditMode: Boolean, onValueChange: (String) -> Unit,keyboardType: KeyboardType = KeyboardType.Text) {
    var text by remember { mutableStateOf(value) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = Shapes.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
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
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onValueChange(text) }),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                shape = Shapes.medium,
                modifier = Modifier.background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)))

        } else {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
