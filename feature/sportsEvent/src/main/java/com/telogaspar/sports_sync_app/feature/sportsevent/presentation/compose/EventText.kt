package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun EventText(
    eventName: String,
    color: Color,
    style: TextStyle,
    textAlign: TextAlign
) {
    val splitStrings = eventName.split("-").map { it.trim() } // Trim to remove any extra spaces
    Text(
        text = buildAnnotatedString {
            append(splitStrings[0])
            append("  vs  ") // Add two spaces before and after "vs" for controlled spacing
            append(splitStrings[1])
        },
        style = style,
        textAlign = textAlign,
        color = color,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}