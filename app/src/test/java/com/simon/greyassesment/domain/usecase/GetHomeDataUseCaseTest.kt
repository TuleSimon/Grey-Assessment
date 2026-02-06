package com.simon.greyassesment.domain.usecase

import app.cash.turbine.test
import com.simon.greyassesment.domain.model.Badge
import com.simon.greyassesment.domain.model.Course
import com.simon.greyassesment.domain.model.DailyTask
import com.simon.greyassesment.domain.model.Path
import com.simon.greyassesment.domain.model.Task
import com.simon.greyassesment.domain.model.Topic
import com.simon.greyassesment.domain.model.User
import com.simon.greyassesment.domain.repository.LearningRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetHomeDataUseCaseTest {

    private lateinit var repository: LearningRepository
    private lateinit var useCase: GetHomeDataUseCase

    private val testUser = User(
        id = "user_1",
        firstName = "Test",
        lastName = "User",
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

    @Before
    fun setup() {
        repository = mockk()
    }

    @Test
    fun `invoke returns combined home data from repository`() = runTest {
        every { repository.getUser() } returns flowOf(testUser)
        every { repository.getDailyTask() } returns flowOf(testDailyTask)
        every { repository.getActiveCourse() } returns flowOf(testCourse)
        every { repository.getEarnedBadges() } returns flowOf(listOf(testBadge))

        useCase = GetHomeDataUseCase(repository)

        useCase().test {
            val homeData = awaitItem()

            assertEquals(testUser, homeData.user)
            assertEquals(testDailyTask, homeData.dailyTask)
            assertNotNull(homeData.activeLearningPath)
            assertEquals(testCourse, homeData.activeLearningPath?.course)
            assertEquals(1, homeData.earnedBadges.size)

            awaitComplete()
        }
    }

    @Test
    fun `invoke returns null activeLearningPath when no active course`() = runTest {
        every { repository.getUser() } returns flowOf(testUser)
        every { repository.getDailyTask() } returns flowOf(null)
        every { repository.getActiveCourse() } returns flowOf(null)
        every { repository.getEarnedBadges() } returns flowOf(emptyList())

        useCase = GetHomeDataUseCase(repository)

        useCase().test {
            val homeData = awaitItem()

            assertEquals(testUser, homeData.user)
            assertNull(homeData.dailyTask)
            assertNull(homeData.activeLearningPath)
            assertEquals(0, homeData.earnedBadges.size)

            awaitComplete()
        }
    }

    @Test
    fun `invoke returns null activeLearningPath when course has no current path`() = runTest {
        val completedTask = testTask.copy(isCompleted = true)
        val completedTopic = testTopic.copy(tasks = listOf(completedTask))
        val completedPath = testPath.copy(topics = listOf(completedTopic))
        val completedCourse = testCourse.copy(paths = listOf(completedPath))

        every { repository.getUser() } returns flowOf(testUser)
        every { repository.getDailyTask() } returns flowOf(null)
        every { repository.getActiveCourse() } returns flowOf(completedCourse)
        every { repository.getEarnedBadges() } returns flowOf(emptyList())

        useCase = GetHomeDataUseCase(repository)

        useCase().test {
            val homeData = awaitItem()

            // When all paths are completed, currentPath will be null
            // which means activeLearningPath should be null
            assertNull(homeData.activeLearningPath)

            awaitComplete()
        }
    }
}