package com.simon.greyassesment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.simon.greyassesment.navigation.AppNavGraph
import com.simon.greyassesment.ui.theme.GreyAssesmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreyAssesmentTheme {
                AppNavGraph()
            }
        }
    }
}