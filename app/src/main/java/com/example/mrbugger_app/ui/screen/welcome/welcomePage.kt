package com.example.mrbugger_app.ui.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mrbugger_app.R
import com.example.mrbugger_app.ui.components.ActionButton
import com.example.mrbugger_app.ui.theme.MrBurgerTheme
import com.example.mrbugger_app.ui.theme.PrimaryYellowDark
import com.example.mrbugger_app.ui.theme.PrimaryYellowLight
import kotlinx.coroutines.delay

@Composable

fun welcomePage(
    modifier: Modifier = Modifier,
    navController: NavController

){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0f to PrimaryYellowDark,
                    0.6f to PrimaryYellowLight,
                    1f to Color.White
                )
            )
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.chicken_burger),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().padding(top = 55.dp),
            contentScale = ContentScale.Crop,

        )

        // "Mr. Burger" Text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 114.dp)

        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 36.sp)) {
                        append("Mr. ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 48.sp,
                            shadow = Shadow(
                                color = Color.Black,
                                blurRadius = 8f
                            )
                        )
                    ) {
                        append("Burger")
                    }
                },
                modifier = Modifier.align(Alignment.Center).padding(bottom = 30.dp),
                textAlign = TextAlign.Center,
                color = Color.Black // Main text color
            )
        }

    }
    // Navigate to Login Page after a delay
    LaunchedEffect(Unit) {
        delay(3000L) // Wait for 3 seconds
        navController.navigate("login_screen") {
            popUpTo("welcome_screen") { inclusive = true }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewWelcomePage() {
//    MrBugger_AppTheme {
//        welcomePage()
//    }
//}