package com.simon.greyassesment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.simon.greyassesment.features.learnpath.screens.BadgeState
import com.simon.greyassesment.features.learnpath.screens.LearningPathScreen
import com.simon.greyassesment.features.learnpath.screens.LevelNode
import com.simon.greyassesment.ui.theme.GreyAssesmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreyAssesmentTheme {
                val levels = remember {
                    listOf(
                        LevelNode(1, "Programming Basics", BadgeState.COMPLETED, R.drawable.badge_completed),
                        LevelNode(2, "Git & Version Control", BadgeState.COMPLETED,  R.drawable.badge_completed),
                        LevelNode(3, "Learn React", BadgeState.InProgress(0.5f), R.drawable.badge_in_progress),
                        LevelNode(4, "Core Mobile UI Build", BadgeState.LOCKED,  R.drawable.badge_not_started),
                        LevelNode(5, "Access Device Features", BadgeState.LOCKED,  R.drawable.badge_not_started),
                        LevelNode(6, "Navigations and Forms", BadgeState.LOCKED,  R.drawable.badge_not_started),
                        LevelNode(7, "State Management", BadgeState.LOCKED,  R.drawable.badge_not_started),
                    )
                }
                LearningPathScreen(levels)
            }
        }
    }
}