package com.simon.greyassesment.domain.usecase

import app.cash.turbine.test
import com.simon.greyassesment.domain.model.Badge
import com.simon.greyassesment.domain.model.Course
import com.simon.greyassesment.domain.model.Path
import com.simon.greyassesment.domain.model.Task
import com.simon.greyassesment.domain.model.Topic
import com.simon.greyassesment.domain.repository.LearningRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetLearningPathUseCaseTest {

    private lateinit var repository: LearningRepository
    private lateinit var useCase: GetLearningPathUseCase

    private val testBadge = Badge(
        id = "badge_1",
        name = "Test Badge",
        description = "Test Description",
        icon = 0,
        earnedAt = null
    )

    private val testTask = Task(
        id = "task_1",
        title = "Test Task",
        isCompleted = false,
        orderIndex = 0
    )

    private val testTopic = Topic(
        id = "topic_1",
        title = "Test Topic",
        tasks = listOf(testTask),
        orderIndex = 0
    )

    private val testPath = Path(
        id = "path_1",
        title = "Test Path",
        description = "Test Description",
        icon = 0,
        topics = listOf(testTopic),
        badge = testBadge,
        orderIndex = 0,
        isUnlocked = true
    )

    private val testCourse = Course(
        id = "course_1",
        title = "Test Course",
        description = "Test Description",
        icon = 0,
        paths = listOf(testPath)
    )

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetLearningPathUseCase(repository)
    }

    @Test
    fun `getActiveCourse returns active course from repository`() = runTest {
        every { repository.getActiveCourse() } returns flowOf(testCourse)

        useCase.getActiveCourse().test {
            val course = awaitItem()

            assertEquals(testCourse, course)
            assertEquals("course_1", course?.id)

            awaitComplete()
        }
    }

    @Test
    fun `getActiveCourse returns null when no active course`() = runTest {
        every { repository.getActiveCourse() } returns flowOf(null)

        useCase.getActiveCourse().test {
            val course = awaitItem()

            assertNull(course)

            awaitComplete()
        }
    }

    @Test
    fun `getPathsForCourse returns paths from repository`() = runTest {
        every { repository.getPathsForCourse("course_1") } returns flowOf(listOf(testPath))

        useCase.getPathsForCourse("course_1").test {
            val paths = awaitItem()

            assertEquals(1, paths.size)
            assertEquals(testPath, paths[0])

            awaitComplete()
        }
    }

    @Test
    fun `getPathsForCourse returns empty list for unknown course`() = runTest {
        every { repository.getPathsForCourse("unknown") } returns flowOf(emptyList())

        useCase.getPathsForCourse("unknown").test {
            val paths = awaitItem()

            assertEquals(0, paths.size)

            awaitComplete()
        }
    }

    @Test
    fun `getCourseById returns course from repository`() = runTest {
        every { repository.getCourseById("course_1") } returns flowOf(testCourse)

        useCase.getCourseById("course_1").test {
            val course = awaitItem()

            assertEquals(testCourse, course)
            assertEquals("course_1", course?.id)

            awaitComplete()
        }
    }

    @Test
    fun `getCourseById returns null for unknown course`() = runTest {
        every { repository.getCourseById("unknown") } returns flowOf(null)

        useCase.getCourseById("unknown").test {
            val course = awaitItem()

            assertNull(course)

            awaitComplete()
        }
    }
}