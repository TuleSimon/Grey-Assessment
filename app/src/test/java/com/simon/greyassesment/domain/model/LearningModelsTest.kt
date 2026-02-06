package com.simon.greyassesment.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UserTest {

    @Test
    fun `initials returns uppercase first letters of first and last name`() {
        val user = User(
            id = "1",
            firstName = "John",
            lastName = "Doe",
            streakDays = 5
        )

        assertEquals("JD", user.initials)
    }

    @Test
    fun `initials handles empty names gracefully`() {
        val user = User(
            id = "1",
            firstName = "",
            lastName = "",
            streakDays = 0
        )

        assertEquals("", user.initials)
    }

    @Test
    fun `displayName returns firstName`() {
        val user = User(
            id = "1",
            firstName = "Simon",
            lastName = "Tule",
            streakDays = 3
        )

        assertEquals("Simon", user.displayName)
    }
}

class BadgeTest {

    @Test
    fun `isEarned returns true when earnedAt is not null`() {
        val badge = Badge(
            id = "badge_1",
            name = "Test Badge",
            description = "Test description",
            icon = 0,
            earnedAt = System.currentTimeMillis()
        )

        assertTrue(badge.isEarned)
    }

    @Test
    fun `isEarned returns false when earnedAt is null`() {
        val badge = Badge(
            id = "badge_1",
            name = "Test Badge",
            description = "Test description",
            icon = 0,
            earnedAt = null
        )

        assertFalse(badge.isEarned)
    }
}

class TaskTest {

    @Test
    fun `task default isCompleted is false`() {
        val task = Task(
            id = "task_1",
            title = "Test Task",
            orderIndex = 0
        )

        assertFalse(task.isCompleted)
    }

    @Test
    fun `task with isCompleted true`() {
        val task = Task(
            id = "task_1",
            title = "Test Task",
            isCompleted = true,
            orderIndex = 0
        )

        assertTrue(task.isCompleted)
    }
}

class TopicTest {

    @Test
    fun `totalTasks returns correct count`() {
        val topic = createTopicWithTasks(completed = 2, incomplete = 3)

        assertEquals(5, topic.totalTasks)
    }

    @Test
    fun `completedTasks returns correct count`() {
        val topic = createTopicWithTasks(completed = 3, incomplete = 2)

        assertEquals(3, topic.completedTasks)
    }

    @Test
    fun `isCompleted returns true when all tasks are completed`() {
        val topic = createTopicWithTasks(completed = 3, incomplete = 0)

        assertTrue(topic.isCompleted)
    }

    @Test
    fun `isCompleted returns false when some tasks are incomplete`() {
        val topic = createTopicWithTasks(completed = 2, incomplete = 1)

        assertFalse(topic.isCompleted)
    }

    @Test
    fun `isCompleted returns false when topic has no tasks`() {
        val topic = Topic(
            id = "topic_1",
            title = "Empty Topic",
            tasks = emptyList(),
            orderIndex = 0
        )

        assertFalse(topic.isCompleted)
    }

    @Test
    fun `progress returns correct percentage`() {
        val topic = createTopicWithTasks(completed = 2, incomplete = 2)

        assertEquals(0.5f, topic.progress, 0.001f)
    }

    @Test
    fun `progress returns 0 for empty topic`() {
        val topic = Topic(
            id = "topic_1",
            title = "Empty Topic",
            tasks = emptyList(),
            orderIndex = 0
        )

        assertEquals(0f, topic.progress, 0.001f)
    }

    @Test
    fun `currentTask returns first incomplete task`() {
        val tasks = listOf(
            Task("1", "Task 1", isCompleted = true, orderIndex = 0),
            Task("2", "Task 2", isCompleted = true, orderIndex = 1),
            Task("3", "Task 3", isCompleted = false, orderIndex = 2),
            Task("4", "Task 4", isCompleted = false, orderIndex = 3)
        )
        val topic = Topic(
            id = "topic_1",
            title = "Test Topic",
            tasks = tasks,
            orderIndex = 0
        )

        assertEquals("3", topic.currentTask?.id)
    }

    @Test
    fun `currentTask returns null when all tasks are completed`() {
        val topic = createTopicWithTasks(completed = 3, incomplete = 0)

        assertNull(topic.currentTask)
    }

    private fun createTopicWithTasks(completed: Int, incomplete: Int): Topic {
        val tasks = (0 until completed).map { i ->
            Task("completed_$i", "Completed Task $i", isCompleted = true, orderIndex = i)
        } + (0 until incomplete).map { i ->
            Task("incomplete_$i", "Incomplete Task $i", isCompleted = false, orderIndex = completed + i)
        }

        return Topic(
            id = "topic_1",
            title = "Test Topic",
            tasks = tasks,
            orderIndex = 0
        )
    }
}

class PathTest {

    @Test
    fun `totalTopics returns correct count`() {
        val path = createPathWithTopics(completedTopics = 2, incompleteTopics = 3)

        assertEquals(5, path.totalTopics)
    }

    @Test
    fun `completedTopics returns correct count`() {
        val path = createPathWithTopics(completedTopics = 2, incompleteTopics = 3)

        assertEquals(2, path.completedTopics)
    }

    @Test
    fun `progress calculates based on all tasks across topics`() {
        val topics = listOf(
            createTopic("1", completedTasks = 2, totalTasks = 4),
            createTopic("2", completedTasks = 3, totalTasks = 4)
        )
        val path = createPath(topics = topics, isUnlocked = true)

        // 5 completed out of 8 total = 0.625
        assertEquals(0.625f, path.progress, 0.001f)
    }

    @Test
    fun `status returns COMPLETED when all topics are completed`() {
        val path = createPathWithTopics(completedTopics = 3, incompleteTopics = 0, isUnlocked = true)

        assertEquals(PathStatus.COMPLETED, path.status)
    }

    @Test
    fun `status returns IN_PROGRESS when unlocked and has progress`() {
        val topics = listOf(
            createTopic("1", completedTasks = 2, totalTasks = 4),
            createTopic("2", completedTasks = 0, totalTasks = 4)
        )
        val path = createPath(topics = topics, isUnlocked = true)

        assertEquals(PathStatus.IN_PROGRESS, path.status)
    }

    @Test
    fun `status returns LOCKED when not unlocked`() {
        val path = createPathWithTopics(completedTopics = 0, incompleteTopics = 3, isUnlocked = false)

        assertEquals(PathStatus.LOCKED, path.status)
    }

    @Test
    fun `currentTopic returns first incomplete topic`() {
        val topics = listOf(
            createTopic("1", completedTasks = 4, totalTasks = 4), // completed
            createTopic("2", completedTasks = 2, totalTasks = 4), // incomplete
            createTopic("3", completedTasks = 0, totalTasks = 4)  // incomplete
        )
        val path = createPath(topics = topics, isUnlocked = true)

        assertEquals("2", path.currentTopic?.id)
    }

    private fun createPathWithTopics(
        completedTopics: Int,
        incompleteTopics: Int,
        isUnlocked: Boolean = true
    ): Path {
        val topics = (0 until completedTopics).map { i ->
            createTopic("completed_$i", completedTasks = 3, totalTasks = 3)
        } + (0 until incompleteTopics).map { i ->
            createTopic("incomplete_$i", completedTasks = 0, totalTasks = 3)
        }

        return createPath(topics = topics, isUnlocked = isUnlocked)
    }

    private fun createTopic(id: String, completedTasks: Int, totalTasks: Int): Topic {
        val tasks = (0 until totalTasks).map { i ->
            Task(
                id = "${id}_task_$i",
                title = "Task $i",
                isCompleted = i < completedTasks,
                orderIndex = i
            )
        }
        return Topic(
            id = id,
            title = "Topic $id",
            tasks = tasks,
            orderIndex = 0
        )
    }

    private fun createPath(topics: List<Topic>, isUnlocked: Boolean): Path {
        return Path(
            id = "path_1",
            title = "Test Path",
            description = "Test Description",
            icon = 0,
            topics = topics,
            badge = Badge("badge_1", "Test Badge", "Description", 0, null),
            orderIndex = 0,
            isUnlocked = isUnlocked
        )
    }
}

class CourseTest {

    @Test
    fun `totalPaths returns correct count`() {
        val course = createCourseWithPaths(completedPaths = 2, incompletePaths = 3)

        assertEquals(5, course.totalPaths)
    }

    @Test
    fun `completedPaths returns correct count`() {
        val course = createCourseWithPaths(completedPaths = 2, incompletePaths = 3)

        assertEquals(2, course.completedPaths)
    }

    @Test
    fun `currentStage returns completedPaths plus 1`() {
        val course = createCourseWithPaths(completedPaths = 3, incompletePaths = 2)

        assertEquals(4, course.currentStage)
    }

    @Test
    fun `progress returns correct percentage`() {
        val course = createCourseWithPaths(completedPaths = 2, incompletePaths = 2)

        assertEquals(0.5f, course.progress, 0.001f)
    }

    @Test
    fun `currentPath returns first non-completed path`() {
        val paths = listOf(
            createPath("path_1", PathStatus.COMPLETED),
            createPath("path_2", PathStatus.COMPLETED),
            createPath("path_3", PathStatus.IN_PROGRESS),
            createPath("path_4", PathStatus.LOCKED)
        )
        val course = Course(
            id = "course_1",
            title = "Test Course",
            description = "Test Description",
            icon = 0,
            paths = paths
        )

        assertEquals("path_3", course.currentPath?.id)
    }

    @Test
    fun `earnedBadges returns badges from completed paths only`() {
        val paths = listOf(
            createPath("path_1", PathStatus.COMPLETED),
            createPath("path_2", PathStatus.COMPLETED),
            createPath("path_3", PathStatus.IN_PROGRESS)
        )
        val course = Course(
            id = "course_1",
            title = "Test Course",
            description = "Test Description",
            icon = 0,
            paths = paths
        )

        assertEquals(2, course.earnedBadges.size)
    }

    private fun createCourseWithPaths(completedPaths: Int, incompletePaths: Int): Course {
        val paths = (0 until completedPaths).map { i ->
            createPath("completed_$i", PathStatus.COMPLETED)
        } + (0 until incompletePaths).map { i ->
            createPath("incomplete_$i", PathStatus.IN_PROGRESS)
        }

        return Course(
            id = "course_1",
            title = "Test Course",
            description = "Test Description",
            icon = 0,
            paths = paths
        )
    }

    private fun createPath(id: String, status: PathStatus): Path {
        val (isUnlocked, completedTasks) = when (status) {
            PathStatus.COMPLETED -> true to 3
            PathStatus.IN_PROGRESS -> true to 1
            PathStatus.LOCKED -> false to 0
        }

        val tasks = (0 until 3).map { i ->
            Task("${id}_task_$i", "Task $i", isCompleted = i < completedTasks, orderIndex = i)
        }
        val topic = Topic("${id}_topic", "Topic", tasks, 0)

        return Path(
            id = id,
            title = "Path $id",
            description = "Description",
            icon = 0,
            topics = listOf(topic),
            badge = Badge("badge_$id", "Badge $id", "Description", 0,
                if (status == PathStatus.COMPLETED) System.currentTimeMillis() else null),
            orderIndex = 0,
            isUnlocked = isUnlocked
        )
    }
}