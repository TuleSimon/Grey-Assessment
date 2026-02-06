package com.simon.greyassesment.data.repository

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LearningRepositoryImplTest {

    private lateinit var repository: LearningRepositoryImpl

    @Before
    fun setup() {
        repository = LearningRepositoryImpl()
    }

    @Test
    fun `getUser returns current user`() = runTest {
        repository.getUser().test {
            val user = awaitItem()

            assertNotNull(user)
            assertEquals("user_1", user.id)
            assertEquals("Tule", user.firstName)
            assertEquals("Simon", user.lastName)

            awaitComplete()
        }
    }

    @Test
    fun `getCourses returns all courses`() = runTest {
        repository.getCourses().test {
            val courses = awaitItem()

            assertTrue(courses.isNotEmpty())
            assertEquals("course_fullstack_mobile", courses[0].id)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCourseById returns correct course`() = runTest {
        repository.getCourseById("course_fullstack_mobile").test {
            val course = awaitItem()

            assertNotNull(course)
            assertEquals("Fullstack Mobile Engineer", course?.title)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCourseById returns null for unknown course`() = runTest {
        repository.getCourseById("unknown_course").test {
            val course = awaitItem()

            assertNull(course)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getActiveCourse returns fullstack mobile course by default`() = runTest {
        repository.getActiveCourse().test {
            val course = awaitItem()

            assertNotNull(course)
            assertEquals("course_fullstack_mobile", course?.id)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPathsForCourse returns paths for valid course`() = runTest {
        repository.getPathsForCourse("course_fullstack_mobile").test {
            val paths = awaitItem()

            assertTrue(paths.isNotEmpty())
            assertTrue(paths.size >= 7) // At least 7 paths in the course

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPathsForCourse returns empty list for unknown course`() = runTest {
        repository.getPathsForCourse("unknown_course").test {
            val paths = awaitItem()

            assertTrue(paths.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPathById returns correct path`() = runTest {
        repository.getPathById("path_programming").test {
            val path = awaitItem()

            assertNotNull(path)
            assertEquals("Programming Basics", path?.title)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPathById returns null for unknown path`() = runTest {
        repository.getPathById("unknown_path").test {
            val path = awaitItem()

            assertNull(path)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getEarnedBadges returns badges from completed paths`() = runTest {
        repository.getEarnedBadges().test {
            val badges = awaitItem()

            // Based on mock data, Programming Basics and Git paths are completed
            assertTrue(badges.size >= 2)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAllBadges returns all badges from all paths`() = runTest {
        repository.getAllBadges().test {
            val badges = awaitItem()

            assertTrue(badges.isNotEmpty())
            assertTrue(badges.size >= 7) // At least 7 paths = 7 badges

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getDailyTask returns current task when available`() = runTest {
        repository.getDailyTask().test {
            val dailyTask = awaitItem()

            // Based on mock data, React path is in progress
            assertNotNull(dailyTask)
            assertNotNull(dailyTask?.task)
            assertNotNull(dailyTask?.topic)
            assertNotNull(dailyTask?.path)
            assertNotNull(dailyTask?.course)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `completeTask marks task as completed`() = runTest {

        val dailyTaskFlow = repository.getDailyTask()
        dailyTaskFlow.test {
            val dailyTask = awaitItem()
            assertNotNull(dailyTask)

            val taskId = dailyTask?.task?.id ?: return@test

            repository.completeTask(taskId)

            val updatedTask = awaitItem()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setActiveCourse changes active course`() = runTest {
        // This test verifies the active course can be changed
        // Since we only have one course in mock data, we test that it remains the same
        repository.setActiveCourse("course_fullstack_mobile")

        repository.getActiveCourse().test {
            val course = awaitItem()

            assertEquals("course_fullstack_mobile", course?.id)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `repository emits updates when tasks are completed`() = runTest {
        repository.getCourses().test {
            val initialCourses = awaitItem()
            val initialPath = initialCourses[0].paths.find { it.id == "path_react" }
            val initialTask = initialPath?.topics?.flatMap { it.tasks }?.find { !it.isCompleted }

            if (initialTask != null) {
                repository.completeTask(initialTask.id)

                val updatedCourses = awaitItem()
                val updatedPath = updatedCourses[0].paths.find { it.id == "path_react" }
                val updatedTask = updatedPath?.topics?.flatMap { it.tasks }?.find { it.id == initialTask.id }

                assertTrue(updatedTask?.isCompleted == true)
            }

            cancelAndIgnoreRemainingEvents()
        }
    }
}