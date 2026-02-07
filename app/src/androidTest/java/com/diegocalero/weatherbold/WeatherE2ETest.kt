package com.diegocalero.weatherbold

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class WeatherE2ETest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchLocationAndExpandForecastDay() {
        composeRule.onNodeWithText("Weather Bold").assertIsDisplayed()

        composeRule.waitUntilAtLeastOneExists(hasTestTag("search_text_field"), 5_000)

        composeRule.onNodeWithTag("search_text_field").performTextInput("bog")

        composeRule.waitUntilAtLeastOneExists(hasTestTag("location_item"), 10_000)
        composeRule.onAllNodesWithTag("location_item").onFirst().performClick()

        composeRule.waitUntilAtLeastOneExists(hasTestTag("forecast_day_accordion"), 15_000)
        composeRule.onAllNodesWithTag("forecast_day_accordion").onFirst().performClick()

        composeRule.waitUntil(10_000) {
            composeRule.onAllNodes(hasTestTag("accordion_expanded_content"), useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onAllNodes(hasTestTag("accordion_expanded_content"), useUnmergedTree = true)
            .onFirst().assertIsDisplayed()
    }
}
