package com.example.mrbugger_app.ui.screen.ProfileScreen

import android.Manifest
import android.R.attr.contentDescription
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mrbugger_app.AuthViewModel.AuthViewModel
import com.example.mrbugger_app.CommonSections.ScreenWithBottonNavBar
import com.example.mrbugger_app.CommonSections.TopBarSection
import com.example.mrbugger_app.R
import com.example.mrbugger_app.model.CartViewModel
import com.example.mrbugger_app.model.ThemeViewModel
import com.example.mrbugger_app.model.UserProfileViewModel
import com.example.mrbugger_app.ui.theme.Shapes
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    userProfileViewModel: UserProfileViewModel,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    themeViewModel: ThemeViewModel
) {
    // Get theme state from ViewModel
    val isDarkMode by themeViewModel.isDarkMode

    // Rest of your existing code remains the same...
    var isEditMode by remember { mutableStateOf(false) }
    val userProfile = userProfileViewModel.userProfile.value
    val isLoading by userProfileViewModel.isLoading

    // Profile image states
    val profileImageUri by userProfileViewModel.profileImageUri
    val profileImageBitmap by userProfileViewModel.profileImageBitmap

    // User data states
    var username by remember { mutableStateOf(userProfile.username) }
    var password by remember { mutableStateOf(userProfile.password) }
    var email by remember { mutableStateOf(userProfile.email) }
    var contact by remember { mutableStateOf(userProfile.contact) }
    var address by remember { mutableStateOf(userProfile.address) }

    // Configuration and UI states
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Dialog states
    var showImageDialog by remember { mutableStateOf(false) }
    var showValidationError by remember { mutableStateOf(false) }
    var validationMessage by remember { mutableStateOf("") }

    // Image picker launchers (same as before)
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { userProfileViewModel.updateProfileImage(it) }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let { userProfileViewModel.updateProfileImage(it) }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        }
    }

    // Update local states when userProfile changes
    LaunchedEffect(userProfile) {
        username = userProfile.username
        password = userProfile.password
        email = userProfile.email
        contact = userProfile.contact
        address = userProfile.address
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
                        // Validate before saving
                        val (isValid, message) = userProfileViewModel.validateUserData()
                        if (isValid) {
                            coroutineScope.launch {
                                userProfileViewModel.updateUserData(
                                    username, password, email, contact, address
                                )
                            }
                            isEditMode = false
                        } else {
                            validationMessage = message
                            showValidationError = true
                        }
                    } else {
                        isEditMode = !isEditMode
                    }
                }
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    androidx.compose.material3.Icon(
                        imageVector = if (isEditMode) Icons.Outlined.Check else Icons.Outlined.Edit,
                        contentDescription = if (isEditMode) "Save" else "Edit"
                    )
                }
            }
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .imePadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Theme Toggle Section - Updated to use ViewModel
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = "Theme Icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (isDarkMode) "Dark Mode" else "Light Mode",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { themeViewModel.toggleTheme() }, // Use ViewModel method
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            // Profile Image Section with Edit
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .clickable { showImageDialog = true },
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        profileImageBitmap != null -> {
                            Image(
                                bitmap = profileImageBitmap!!.asImageBitmap(),
                                contentDescription = "Profile Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        profileImageUri != null -> {
                            Image(
                                painter = rememberAsyncImagePainter(profileImageUri),
                                contentDescription = stringResource(R.string.profile_image),
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        else -> {
                            Image(
                                painter = painterResource(R.drawable.profile2),
                                contentDescription = "Profile Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                //edit button
                FloatingActionButton(
                    onClick = { showImageDialog = true },
                    modifier = Modifier.size(36.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Edit Image",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = username,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Main user details part
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
                        keyboardType = KeyboardType.Password,
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    UserInfoField(
                        label = "E-mail",
                        value = email,
                        isEditMode = isEditMode,
                        onValueChange = { email = it },
                        keyboardType = KeyboardType.Email
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    UserInfoField(
                        label = "Contact",
                        value = contact,
                        isEditMode = isEditMode,
                        onValueChange = { contact = it },
                        keyboardType = KeyboardType.Phone
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    UserInfoField(
                        label = "Address",
                        value = address,
                        isEditMode = isEditMode,
                        onValueChange = { address = it },
                        keyboardType = KeyboardType.Text
                    )

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Additional action buttons (Clear Image & Reset)
            if (profileImageUri != null || profileImageBitmap != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { userProfileViewModel.clearProfileImage() },
                        modifier = Modifier.weight(1f)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.DeleteForever,
                            modifier = Modifier.size(18.dp),
                            contentDescription = "Clear Image",
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Clear Image",
                            color = MaterialTheme.colorScheme.onBackground
                        )                    }

                    OutlinedButton(
                        onClick = {
                            userProfileViewModel.resetToDefaults()
                            isEditMode = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.RestartAlt,
                            contentDescription = "Reset",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Reset", color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

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
                    modifier = Modifier.width(150.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Logout", style = MaterialTheme.typography.titleLarge)
                }
            }

            Spacer(modifier = Modifier.height(65.dp))
        }

        ScreenWithBottonNavBar(navController = navController, cartViewModel = cartViewModel)
    }

    // Image Selection Dialog
    if (showImageDialog) {
        ImageSelectionDialog(
            onGalleryClick = {
                galleryLauncher.launch("image/*")
                showImageDialog = false
            },
            onCameraClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) -> {
                        cameraLauncher.launch(null)
                    }
                    else -> {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
                showImageDialog = false
            },
            onDismiss = { showImageDialog = false }
        )
    }

    // Validation Error Dialog
    if (showValidationError) {
        AlertDialog(
            onDismissRequest = { showValidationError = false },
            title = {
                Text(
                    "Validation Error",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            text = {
                Text(
                    validationMessage,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            confirmButton = {
                TextButton(onClick = { showValidationError = false }) {
                    Text(
                        "OK",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        )
    }
}

@Composable
fun ImageSelectionDialog(
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Select Image Source",
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        text = {
            Text(
                "Choose how you want to select your profile image",
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = onGalleryClick) {
                    androidx.compose.material3.Icon(
                        Icons.Default.PhotoLibrary,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Gallery",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                TextButton(onClick = onCameraClick) {
                    androidx.compose.material3.Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Camera",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Cancel",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    )
}

// UserInfoField
@Composable
fun UserInfoField(
    label: String,
    value: String,
    isEditMode: Boolean,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    var text by remember { mutableStateOf(value) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Update text when value changes
    LaunchedEffect(value) {
        text = value
    }

    // Get appropriate icon for each field
    val fieldIcon = when (label) {
        "Username" -> Icons.Default.Person
        "Password" -> Icons.Default.Lock
        "E-mail" -> Icons.Default.Email
        "Contact" -> Icons.Default.Phone
        "Address" -> Icons.Default.LocationOn
        else -> Icons.Default.Info
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = Shapes.medium)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Icon + Label
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.4f)
        ) {
            androidx.compose.material3.Icon(
                imageVector = fieldIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        }

        // Right side - Input or Display
        Box(
            modifier = Modifier.weight(0.6f),
            contentAlignment = Alignment.CenterEnd
        ) {
            if (isEditMode) {
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onValueChange(it)
                    },
                    singleLine = true,
                    visualTransformation = if (isPassword && !passwordVisible)
                        PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = if (isPassword) {
                        {
                            IconButton(
                                onClick = { passwordVisible = !passwordVisible },
                                modifier = Modifier.size(24.dp)
                            ) {
                                androidx.compose.material3.Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide password"
                                    else "Show password",
                                    modifier = Modifier.size(18.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else null,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = keyboardType,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { onValueChange(text) }),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    ),
                    shape = Shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(8.dp)
                        )
                )
            } else {
                Text(
                    text = if (isPassword && text.isNotEmpty()) "•".repeat(8) else text,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}