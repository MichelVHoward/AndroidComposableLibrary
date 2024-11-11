package com.howard.composable.androidcomposablelibrary

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.tooling.preview.Preview
import com.howard.composable.androidcomposablelibrary.ui.theme.AndroidComposableLibraryTheme
import com.howard.composable.composelibrary.dropdown.ComboBox

enum class TrackingInterval(
    val interval: Long,
    val description: String,

    ) {
    SHORT(10000, "Short 10 s"),
    MIDDLE(30000, "Middle 30 s"),
    LONG(60000, "Long 1 min")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidComposableLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val map = TrackingInterval.entries.associate { tr -> tr.description to tr }
                        val state = remember { mutableStateOf(TrackingInterval.SHORT) }
                        Log.v("COMBO", state.value.name.toString())
                        ComboBox(
                            map = map,
                            initialKey = TrackingInterval.LONG.description,
                            label = "Tracing Interval"
                        ) {
                            state.value = it
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidComposableLibraryTheme {
        Greeting("Android")
    }
}