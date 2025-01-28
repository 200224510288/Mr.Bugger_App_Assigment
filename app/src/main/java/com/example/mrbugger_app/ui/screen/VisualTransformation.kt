package com.example.mrbugger_app.ui.screen

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PlainTextVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            text, // Pass the text directly
            OffsetMapping.Identity // Use Identity for no offset mapping changes
        )
    }
}
