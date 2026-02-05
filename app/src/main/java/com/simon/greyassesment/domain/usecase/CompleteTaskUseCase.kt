package com.simon.greyassesment.domain.usecase

import com.simon.greyassesment.domain.repository.LearningRepository
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(
    private val repository: LearningRepository
) {
    suspend operator fun invoke(taskId: String) {
        repository.completeTask(taskId)
    }
}