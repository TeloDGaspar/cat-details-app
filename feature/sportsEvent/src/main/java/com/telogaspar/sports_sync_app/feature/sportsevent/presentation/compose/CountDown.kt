package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.CountDownTimer
import kotlinx.coroutines.launch

@Composable
fun CountDown(
    modifier: Modifier = Modifier,
    timeStamp: Long,
    color: Color,
    style: TextStyle,
    textAlign: TextAlign,
    updateInterval: Long = 1
) {
    val countDownTimer = remember { CountDownTimer(timeStamp, updateInterval) }
    val remainingTime by countDownTimer.remainingTime.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        launch {
            countDownTimer.start()
        }
    }
    val text =
        if (remainingTime <= 0) "Event already started" else remainingTime.formatUnixTimeToHHMMSS()
    Text(modifier = modifier, text = text, color = color, style = style, textAlign = textAlign)
}

private fun Long.formatUnixTimeToHHMMSS(): String {
    val hours = (this / 3600)
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}