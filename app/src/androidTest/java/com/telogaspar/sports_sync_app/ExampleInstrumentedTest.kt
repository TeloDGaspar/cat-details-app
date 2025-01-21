package com.telogaspar.sports_sync_app

import androidx.compose.ui.test.DeviceConfigurationOverride
import androidx.compose.ui.test.ForcedSize
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.telogaspar.sports_sync_app.feature.sportsevent.data.di.FeatureModuleRemote
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose.SportScreen
import com.telogaspar.sports_sync_app.ui.theme.SportssyncappTheme
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(FeatureModuleRemote::class)
class ExampleInstrumentedTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @BindValue
    lateinit var sportListRepository: SportListRepository

    @Before
    fun init() {
        sportListRepository =
            FakeSportListRepository()
        hiltRule.inject()
        composeTestRule.waitForIdle()
    }

    @Test
    fun testOnSmallScreen() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.ForcedSize(DpSize(480.dp, 800.dp))
            ) {
                SportssyncappTheme {
                    SportScreen()
                }
            }
        }
        assertSportScreenIsDisplayed()
    }

    @Test
    fun testOnMediumScreen() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.ForcedSize(DpSize(1280.dp, 800.dp))
            ) {
                SportssyncappTheme {
                    SportScreen()
                }
            }
        }
        assertSportScreenIsDisplayed()
    }

    @Test
    fun testOnLargeScreen() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.ForcedSize(DpSize(1920.dp, 1080.dp))
            ) {
                SportssyncappTheme {
                    SportScreen()
                }
            }
        }
        assertSportScreenIsDisplayed()
    }

    private fun assertSportScreenIsDisplayed() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("sport_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("sport_item_Soccer").assertIsDisplayed()
        composeTestRule.onNodeWithTag("sport_item_Basketball").assertIsDisplayed()
        composeTestRule.onNodeWithTag("sport_favorite_Soccer").assertIsDisplayed()
        composeTestRule.onNodeWithTag("sport_favorite_Basketball").assertIsDisplayed()

        composeTestRule.onNodeWithTag("arrow_Soccer").assertIsDisplayed().performClick()
        composeTestRule.onNodeWithTag("arrow_Basketball").assertIsDisplayed().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("event_item_Soccer-Match").assertIsDisplayed()
        composeTestRule.onNodeWithTag("event_item_Basketball-Match").assertIsDisplayed()

        composeTestRule.onNodeWithTag("event_name_Soccer-Match").assertIsDisplayed()
        composeTestRule.onNodeWithTag("event_name_Basketball-Match").assertIsDisplayed()

        composeTestRule.onNodeWithTag("event_time_Soccer-Match").assertIsDisplayed()
        composeTestRule.onNodeWithTag("event_time_Basketball-Match").assertIsDisplayed()

        composeTestRule.onNodeWithTag("sport_favorite_Soccer").assertIsDisplayed()
        composeTestRule.onNodeWithTag("sport_favorite_Basketball").assertIsDisplayed()
    }
}