package com.simon.greyassesment.domain.usecase

import com.simon.greyassesment.domain.repository.LearningRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CompleteTaskUseCaseTest {

    private lateinit var repository: LearningRepository
    private lateinit var useCase: CompleteTaskUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = CompleteTaskUseCase(repository)
    }

    @Test
    fun `invoke calls repository completeTask with correct taskId`() = runTest {
        val taskId = "task_123"
        coEvery { repository.completeTask(taskId) } just runs

        useCase(taskId)

        coVerify(exactly = 1) { repository.completeTask(taskId) }
    }

    @Test
    fun `invoke can be called multiple times with different taskIds`() = runTest {
        coEvery { repository.completeTask(any()) } just runs

        useCase("task_1")
        useCase("task_2")
        useCase("task_3")

        coVerify(exactly = 1) { repository.completeTask("task_1") }
        coVerify(exactly = 1) { repository.completeTask("task_2") }
        coVerify(exactly = 1) { repository.completeTask("task_3") }
    }
}