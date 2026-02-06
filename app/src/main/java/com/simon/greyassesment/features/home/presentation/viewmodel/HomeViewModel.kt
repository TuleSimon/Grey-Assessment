package com.simon.greyassesment.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simon.greyassesment.domain.model.ActiveLearningPathSummary
import com.simon.greyassesment.domain.model.Badge
import com.simon.greyassesment.domain.model.DailyTask
import com.simon.greyassesment.domain.model.User
import com.simon.greyassesment.domain.usecase.CompleteTaskUseCase
import com.simon.greyassesment.domain.usecase.GetHomeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val greeting: String = "",
    val dailyTask: DailyTask? = null,
    val activeLearningPath: ActiveLearningPathSummary? = null,
    val earnedBadges: List<Badge> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        getHomeDataUseCase()
            .onEach { homeData ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        user = homeData.user,
                        greeting = getGreeting(homeData.user.displayName),
                        dailyTask = homeData.dailyTask,
                        activeLearningPath = homeData.activeLearningPath,
                        earnedBadges = homeData.earnedBadges,
                        error = null
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onDailyTaskClick() {
        // Navigate to task detail
    }

    fun onViewFullPathClick() {
        // Navigate to learning path screen
    }

    fun completeCurrentTask() {
        val taskId = _uiState.value.dailyTask?.task?.id ?: return
        viewModelScope.launch {
            completeTaskUseCase(taskId)
        }
    }

    private fun getGreeting(name: String): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val timeOfDay = when {
            hour < 12 -> "morning"
            hour < 17 -> "afternoon"
            else -> "evening"
        }
        return "Good $timeOfDay $name!"
    }
}