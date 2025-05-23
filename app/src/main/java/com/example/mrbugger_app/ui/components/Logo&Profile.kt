package com.example.mrbugger_app.ui.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mrbugger_app.model.UserProfileViewModel

@Composable
fun LogoAndCard(userProfileViewModel: UserProfileViewModel) {
    // Image Picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { userProfileViewModel.updateProfileImage(it) }
    }

    // Observe profile image states from UserProfileViewModel
    val profileImageBitmap by userProfileViewModel.profileImageBitmap
    val profileImageUri by userProfileViewModel.profileImageUri

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Column for text items
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = AnnotatedString.Builder().apply {
                    // Style for "Mr."
                    pushStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                            fontWeight = MaterialTheme.typography.displayLarge.fontWeight,
                            fontSize = 25.sp
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
                            fontSize = 25.sp
                        )
                    )
                    append("Burger")
                    pop()
                }.toAnnotatedString(),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        // Network Status Indicator (assuming it's defined elsewhere)
        NetworkStatusIndicator(
            modifier = Modifier.padding(end = 8.dp),
            showText = true
        )

        // Circular Profile Image
        ProfileSection(
            profileImageBitmap = profileImageBitmap,
            profileImageUri = profileImageUri,
            onImageClick = { imagePickerLauncher.launch("image/*") }
        )
    }
}

@Composable
fun ProfileSection(
    profileImageBitmap: Bitmap? = null,
    profileImageUri: Uri? = null,
    onImageClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Gray)
            .clickable { onImageClick() },
        contentAlignment = Alignment.Center
    ) {
        when {
            profileImageBitmap != null -> {
                Image(
                    painter = rememberAsyncImagePainter(profileImageBitmap),
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            profileImageUri != null -> {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUri),
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Default Profile Icon",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun LogoPagePreview() {
    // Mock UserProfileViewModel for preview (won't work in actual preview without proper setup)
    // LogoAndCard(userProfileViewModel = // Provide a mock or remove for actual implementation)
}