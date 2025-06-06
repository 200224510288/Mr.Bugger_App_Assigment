package com.example.mrbugger_app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mrbugger_app.model.ThemeViewModel

@Composable
fun AmbientLightCard(
    themeViewModel: ThemeViewModel,
    adaptiveColor: Color = MaterialTheme.colorScheme.primary
) {
    val isEnabled by themeViewModel.isAmbientLightEnabled.collectAsState()
    val currentLevel by themeViewModel.currentLightLevel.collectAsState()
    val threshold by themeViewModel.darkModeThreshold.collectAsState()
    val showPermissionDialog by themeViewModel.showPermissionDialog.collectAsState()
    val hasLightSensor = themeViewModel.hasAmbientLightSensor()

    // Permission dialog
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = {
                themeViewModel.onPermissionDialogResponse(false)
            },
            title = {
                Text("Enable Ambient Light Sensor")
            },
            text = {
                Text("Allow the app to use your device's ambient light sensor to automatically adjust the theme based on surrounding light conditions?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        themeViewModel.onPermissionDialogResponse(true)
                    }
                ) {
                    Text("Allow")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        themeViewModel.onPermissionDialogResponse(false)
                    }
                ) {
                    Text("Deny")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = adaptiveColor.copy(alpha = 0.1f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {

                    Text(
                        "Ambient Light Sensor",
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (!hasLightSensor) {
                        Text(
                            "Not supported on this device",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }

                Switch(
                    checked = isEnabled,
                    onCheckedChange = { enable ->
                        if (enable && !isEnabled) {
                            // Request permission before enabling
                            themeViewModel.requestAmbientLightPermission()
                        } else {
                            // Disable directly
                            themeViewModel.enableAmbientLight(enable)
                        }
                    },
                    enabled = hasLightSensor,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = adaptiveColor,
                        checkedTrackColor = adaptiveColor.copy(alpha = 0.5f)
                    )
                )
            }

            if (isEnabled && hasLightSensor) {
                Spacer(modifier = Modifier.height(12.dp))

                // Current light level display
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Current Light Level:",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "${"%.1f".format(currentLevel)} lx",
                        fontWeight = FontWeight.Medium,
                        color = adaptiveColor
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${"%.0f".format(threshold)} lx",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        if (currentLevel < threshold) "Dark Mode" else "Light Mode",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (currentLevel < threshold) Color(0xFF4CAF50) else Color(0xFFFF9800)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Info text
                Text(
                    "Theme will automatically switch when light level goes below the threshold",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    lineHeight = 16.sp
                )
            }
        }
    }
}