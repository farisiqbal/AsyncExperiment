package com.example.asyncload.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.asyncload.experiment.provider.CustomerUserProvider
import com.example.asyncload.experiment.provider.ExperimentationProvider
import com.example.asyncload.ui.theme.AsyncLoadTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var customerUserProvider: CustomerUserProvider
    @Inject
    lateinit var experimentationProvider: ExperimentationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
        setContent {
            val state1 by customerUserProvider.data.collectAsState()
//            val state2 by anotherDataProvider.data.collectAsState()
            AsyncLoadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("we're just loading the data here. $state1", { goToNextScreen() })
                }
            }
        }
    }

    private fun loadData() = lifecycleScope.launch {
        experimentationProvider.init()
        customerUserProvider.fetchData()
    }

    private fun goToNextScreen() {
        startActivity(Intent(this, NextActivity::class.java))
    }
}

@Composable
fun Greeting(
    name: String,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Hello, $name",
            modifier = modifier
        )
        if (onClick != null) {
            Button(onClick = { onClick() }) {
                Text(text = "Go to next screen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AsyncLoadTheme {
        Greeting("Android")
    }
}