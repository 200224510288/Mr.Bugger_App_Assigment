package com.example.mrbugger_app.CommonSections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


// Reusable categorizing bar component
@Composable
fun SectionsText(
    title: String,
    onShowMoreClick: () -> Unit,
    showMoreText: String = "Show more",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = onShowMoreClick,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = showMoreText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.offset(x = 8.dp),
            )
        }
    }
}