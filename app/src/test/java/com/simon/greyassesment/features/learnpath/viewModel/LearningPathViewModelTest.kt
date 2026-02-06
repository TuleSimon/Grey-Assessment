package com.simon.greyassesment.features.learnpath.viewModel

import app.cash.turbine.test
import com.simon.greyassesment.domain.model.Badge
import com.simon.greyassesment.domain.model.Course
import com.simon.greyassesment.domain.model.Path
import com.simon.greyassesment.domain.model.Task
import com.simon.greyassesment.domain.model.Topic
import com.simon.greyassesment.domain.usecase.GetLearningPathUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LearningPathViewModelTest {

    private lateinit var getLearningPathUseCase: GetLearningPathUseCase
    private val testDispatcher = StandardTestDispatcher()

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

    private val testPath1 = Path(
        id = "path_1",
        title = "Path 1",
        description = "Test Description",
        icon = 0,
        topics = listOf(testTopic),
        badge = testBadge,
        orderIndex = 0,
        isUnlocked = true
    )

    private val testPath2 = Path(
        id = "path_2",
        title = "Path 2",
        description = "Test Description",
        icon = 0,
        topics = listOf(testTopic),
        badge = testBadge.copy(id = "badge_2"),
        orderIndex = 1,
        isUnlocked = false
    )

    private val testCourse = Course(
        id = "course_1",
        title = "Test Course",
        description = "Test Description",
        icon = 0,
        paths = listOf(testPath1, testPath2)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getLearningPathUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() = runTest {
        every { getLearningPathUseCase.getCourseById(any()) } returns flowOf(testCourse)

        val viewModel = LearningPathViewModel(getLearningPathUseCase)

        assertTrue(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `loadCourse updates state with course data`() = runTest {
        every { getLearningPathUseCase.getCourseById("course_1") } returns flowOf(testCourse)

        val viewModel = LearningPathViewModel(getLearningPathUseCase)
        viewModel.loadCourse("course_1")

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertFalse(state.isLoading)
            assertNotNull(state.course)
            assertEquals("course_1", state.course?.id)
            assertEquals("Test Course", state.course?.title)
            assertEquals(2, state.paths.size)
            assertNull(state.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadCourse calls use case with correct courseId`() = runTest {
        every { getLearningPathUseCase.getCourseById("course_1") } returns flowOf(testCourse)

        val viewModel = LearningPathViewModel(getLearningPathUseCase)
        viewModel.loadCourse("course_1")

        testDispatcher.scheduler.advanceUntilIdle()

        verify { getLearningPathUseCase.getCourseById("course_1") }
    }

    @Test
    fun `loadCourse handles null course gracefully`() = runTest {
        every { getLearningPathUseCase.getCourseById("unknown") } returns flowOf(null)

        val viewModel = LearningPathViewModel(getLearningPathUseCase)
        viewModel.loadCourse("unknown")

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertFalse(state.isLoading)
            assertNull(state.course)
            assertTrue(state.paths.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `paths are extracted from course`() = runTest {
        val course = Course(
            id = "course_1",
            title = "Test Course",
            description = "Test Description",
            icon = 0,
            paths = listOf(testPath1, testPath2)
        )
        every { getLearningPathUseCase.getCourseById("course_1") } returns flowOf(course)

        val viewModel = LearningPathViewModel(getLearningPathUseCase)
        viewModel.loadCourse("course_1")

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(2, state.paths.size)
            assertEquals("path_1", state.paths[0].id)
            assertEquals("path_2", state.paths[1].id)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loading different courses updates state correctly`() = runTest {
        val course1 = testCourse
        val course2 = testCourse.copy(
            id = "course_2",
            title = "Second Course",
            paths = listOf(testPath1)
        )

        every { getLearningPathUseCase.getCourseById("course_1") } returns flowOf(course1)
        every { getLearningPathUseCase.getCourseById("course_2") } returns flowOf(course2)

        val viewModel = LearningPathViewModel(getLearningPathUseCase)

        viewModel.loadCourse("course_1")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("course_1", viewModel.uiState.value.course?.id)
        assertEquals(2, viewModel.uiState.value.paths.size)

        viewModel.loadCourse("course_2")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals("course_2", state.course?.id)
            assertEquals("Second Course", state.course?.title)
            assertEquals(1, state.paths.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state contains correct path count`() = runTest {
        val paths = listOf(
            testPath1,
            testPath2,
            testPath1.copy(id = "path_3", title = "Path 3"),
            testPath1.copy(id = "path_4", title = "Path 4")
        )
        val course = testCourse.copy(paths = paths)
        every { getLearningPathUseCase.getCourseById("course_1") } returns flowOf(course)

        val viewModel = LearningPathViewModel(getLearningPathUseCase)
        viewModel.loadCourse("course_1")

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(4, state.paths.size)

            cancelAndIgnoreRemainingEvents()
        }
    }
}