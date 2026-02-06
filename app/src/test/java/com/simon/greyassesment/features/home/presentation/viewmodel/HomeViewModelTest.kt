package com.simon.greyassesment.features.home.presentation.viewmodel

import app.cash.turbine.test
import com.simon.greyassesment.domain.model.ActiveLearningPathSummary
import com.simon.greyassesment.domain.model.Badge
import com.simon.greyassesment.domain.model.Course
import com.simon.greyassesment.domain.model.DailyTask
import com.simon.greyassesment.domain.model.Path
import com.simon.greyassesment.domain.model.Task
import com.simon.greyassesment.domain.model.Topic
import com.simon.greyassesment.domain.model.User
import com.simon.greyassesment.domain.usecase.GetHomeDataUseCase
import com.simon.greyassesment.domain.usecase.HomeData
import io.mockk.every
import io.mockk.mockk
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
class HomeViewModelTest {

    private lateinit var getHomeDataUseCase: GetHomeDataUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val testUser = User(
        id = "user_1",
        firstName = "John",
        lastName = "Doe",
        streakDays = 5
    )

    private val testBadge = Badge(
        id = "badge_1",
        name = "Test Badge",
        description = "Test Description",
        icon = 0,
        earnedAt = System.currentTimeMillis()
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

    private val testDailyTask = DailyTask(
        task = testTask,
        topic = testTopic,
        path = testPath,
        course = testCourse
    )

    private val testActiveLearningPath = ActiveLearningPathSummary(
        course = testCourse,
        currentPath = testPath,
        currentTopic = testTopic
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getHomeDataUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() = runTest {
        val homeData = HomeData(
            user = testUser,
            dailyTask = testDailyTask,
            activeLearningPath = testActiveLearningPath,
            earnedBadges = listOf(testBadge)
        )
        every { getHomeDataUseCase() } returns flowOf(homeData)

        val viewModel = HomeViewModel(getHomeDataUseCase)

        // Initial state before data loads
        assertTrue(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `loadHomeData updates state with user data`() = runTest {
        val homeData = HomeData(
            user = testUser,
            dailyTask = testDailyTask,
            activeLearningPath = testActiveLearningPath,
            earnedBadges = listOf(testBadge)
        )
        every { getHomeDataUseCase() } returns flowOf(homeData)

        val viewModel = HomeViewModel(getHomeDataUseCase)

        // Advance until idle to let the ViewModel process the data
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertFalse(state.isLoading)
            assertEquals(testUser, state.user)
            assertEquals(testDailyTask, state.dailyTask)
            assertEquals(testActiveLearningPath, state.activeLearningPath)
            assertEquals(1, state.earnedBadges.size)
            assertNull(state.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `greeting contains user display name`() = runTest {
        val homeData = HomeData(
            user = testUser,
            dailyTask = null,
            activeLearningPath = null,
            earnedBadges = emptyList()
        )
        every { getHomeDataUseCase() } returns flowOf(homeData)

        val viewModel = HomeViewModel(getHomeDataUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertTrue(state.greeting.contains("John"))
            assertTrue(state.greeting.startsWith("Good"))

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state handles null daily task`() = runTest {
        val homeData = HomeData(
            user = testUser,
            dailyTask = null,
            activeLearningPath = testActiveLearningPath,
            earnedBadges = emptyList()
        )
        every { getHomeDataUseCase() } returns flowOf(homeData)

        val viewModel = HomeViewModel(getHomeDataUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertNull(state.dailyTask)
            assertNotNull(state.activeLearningPath)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state handles null active learning path`() = runTest {
        val homeData = HomeData(
            user = testUser,
            dailyTask = testDailyTask,
            activeLearningPath = null,
            earnedBadges = emptyList()
        )
        every { getHomeDataUseCase() } returns flowOf(homeData)

        val viewModel = HomeViewModel(getHomeDataUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertNotNull(state.dailyTask)
            assertNull(state.activeLearningPath)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state contains earned badges`() = runTest {
        val badges = listOf(
            testBadge,
            testBadge.copy(id = "badge_2", name = "Badge 2"),
            testBadge.copy(id = "badge_3", name = "Badge 3")
        )
        val homeData = HomeData(
            user = testUser,
            dailyTask = null,
            activeLearningPath = null,
            earnedBadges = badges
        )
        every { getHomeDataUseCase() } returns flowOf(homeData)

        val viewModel = HomeViewModel(getHomeDataUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(3, state.earnedBadges.size)

            cancelAndIgnoreRemainingEvents()
        }
    }
}