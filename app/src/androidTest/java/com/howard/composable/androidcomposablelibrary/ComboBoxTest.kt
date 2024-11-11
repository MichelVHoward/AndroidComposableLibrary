package com.howard.composable.androidcomposablelibrary

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.howard.composable.androidcomposablelibrary.ui.theme.AndroidComposableLibraryTheme
import com.howard.composable.composelibrary.dropdown.ComboBox
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

enum class TrackingInterval(
    val interval: Long,
    val description: String,

    ) {
    SHORT(10000, "Short 10 s"), MIDDLE(30000, "Middle 30 s"), LONG(60000, "Long 1 min")
}

@RunWith(AndroidJUnit4::class)
class ComboBoxTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun callWith() {
        var capturedState = TrackingInterval.SHORT // Default initial state
        composeTestRule.setContent {
            AndroidComposableLibraryTheme {
                val map = TrackingInterval.entries.associate { tr -> tr.description to tr }
                ComboBox(
                    map = map,
                    initialKey = TrackingInterval.LONG.description,
                    label = "Tracking Interval"
                ) {
                    capturedState = it
                }
            }
        }
        composeTestRule.onRoot().printToLog("TAG")

        composeTestRule.onNodeWithTag("EditIcon").performClick()

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("DropdownMenu").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Item2").performClick()
        composeTestRule.onNodeWithTag("DropdownMenu").assertIsNotDisplayed()
        assert(capturedState == TrackingInterval.MIDDLE)
    }
}