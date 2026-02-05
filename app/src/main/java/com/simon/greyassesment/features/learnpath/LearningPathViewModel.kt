package com.simon.greyassesment.features.learnpath

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simon.greyassesment.domain.model.Course
import com.simon.greyassesment.domain.model.Path
import com.simon.greyassesment.domain.usecase.GetLearningPathUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class LearningPathUiState(
    val isLoading: Boolean = true,
    val course: Course? = null,
    val paths: List<Path> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class LearningPathViewModel @Inject constructor(
    private val getLearningPathUseCase: GetLearningPathUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LearningPathUiState())
    val uiState: StateFlow<LearningPathUiState> = _uiState.asStateFlow()

    init {
        loadActiveCourse()
    }

    private fun loadActiveCourse() {
        getLearningPathUseCase.getActiveCourse()
            .onEach { course ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        course = course,
                        paths = course?.paths ?: emptyList(),
                        error = null
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadCourse(courseId: String) {
        getLearningPathUseCase.getCourseById(courseId)
            .onEach { course ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        course = course,
                        paths = course?.paths ?: emptyList(),
                        error = null
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}