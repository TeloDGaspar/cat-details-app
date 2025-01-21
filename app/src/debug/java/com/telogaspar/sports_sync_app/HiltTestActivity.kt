/**
 * Copyright (c), BMW Critical TechWorks. All rights reserved.
 */
package com.telogaspar.sports_sync_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose.SportScreen
import com.telogaspar.sports_sync_app.ui.theme.SportssyncappTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity to enable RSE ui tests.
 * Necessary because ui tests fail if the application to test is the default launcher.
 */
@AndroidEntryPoint
class HiltTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}