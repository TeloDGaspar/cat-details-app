package com.telogaspar.sports_sync_app

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose.SportScreen
import com.telogaspar.sports_sync_app.ui.theme.SportssyncappTheme
import dagger.hilt.android.HiltAndroidApp

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun testOnSmallScreen() {
        composeTestRule.setContent {
            val configuration = LocalConfiguration.current
            CompositionLocalProvider(
                LocalConfiguration provides configuration.apply {
                    screenWidthDp = 320 // Small screen width
                    screenHeightDp = 480 // Small screen height
                }
            ) {

                SportssyncappTheme {
                    SportScreen() // Your composable to test
                }
            }

            // Add assertions for small screen
            composeTestRule.onNodeWithTag("main_screen").assertIsDisplayed()
        }
    }

    /*@Test
    fun testOnLargeScreen() {
        composeTestRule.setContent {
            val configuration = LocalConfiguration.current
            CompositionLocalProvider(
                LocalConfiguration provides configuration.apply {
                    screenWidthDp = 1280 // Large screen width
                    screenHeightDp = 800 // Large screen height
                }
            ) {
                // Your composable to test
                composeTestRule.onNodeWithTag("main_screen").assertIsDisplayed()
            }
        }
    }*/

    @Test
    fun useAppContext() {
        composeTestRule.waitForIdle()
    }
}