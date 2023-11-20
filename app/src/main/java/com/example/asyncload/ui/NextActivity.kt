package com.example.asyncload.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.asyncload.experiment.ExperimentA
import com.example.asyncload.experiment.ExperimentTracker
import com.example.asyncload.experiment.ExperimentTrackerExample
import com.example.asyncload.ui.theme.Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NextActivity : ComponentActivity() {

    @Inject
    lateinit var experimentA: ExperimentA
    @Inject
    lateinit var experimentTracker: ExperimentTrackerExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by experimentA.decide().collectAsState("")
            Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(
                        "Getting the data: $state",
                        onClick = { track() }
                    )
                }
            }
        }
    }

    private fun track() {
        lifecycleScope.launch { experimentTracker.track() }
    }

    /**
    private fun display(state: String): String {
        when (state) {
            "BAU" -> {
                // show variant 1
            }
            "VARIANT" -> {
                // show variant 2
            }
            else -> {
                // show default/loading
            }
        }
        return "MY_DECISION"
    }
    */
}