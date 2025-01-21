package com.telogaspar.sports_sync_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose.SportScreen
import com.telogaspar.sports_sync_app.ui.theme.SportssyncappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportssyncappTheme {
                SportScreen()
            }
        }
    }
}
