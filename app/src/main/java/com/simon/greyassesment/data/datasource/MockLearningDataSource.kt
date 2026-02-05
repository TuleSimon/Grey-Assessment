package com.simon.greyassesment.data.datasource

import com.simon.greyassesment.R
import com.simon.greyassesment.domain.model.Badge
import com.simon.greyassesment.domain.model.Course
import com.simon.greyassesment.domain.model.Path
import com.simon.greyassesment.domain.model.Task
import com.simon.greyassesment.domain.model.Topic
import com.simon.greyassesment.domain.model.User

object MockLearningDataSource {

    val currentUser = User(
        id = "user_1",
        firstName = "Tule",
        lastName = "Simon",
        streakDays = 3
    )

    private val reactBadge = Badge(
        id = "badge_react",
        name = "React Master",
        icon = R.drawable.badge_completed,
        description = "Completed Learn React path",
        earnedAt = null
    )

    private val gitBadge = Badge(
        id = "badge_git",
        name = "Git Guru",
        icon = R.drawable.badge_completed,
        description = "Completed Git & Version Control path",
        earnedAt = System.currentTimeMillis() - 86400000
    )

    private val programmingBasicsBadge = Badge(
        id = "badge_programming",
        name = "Programming Basics",
        icon = R.drawable.badge_completed,
        description = "Completed Programming Basics path",
        earnedAt = System.currentTimeMillis() - 172800000
    )

    private val mobileUIBadge = Badge(
        id = "badge_mobile_ui",
        name = "Mobile UI Expert",
        icon = R.drawable.badge_not_started,
        description = "Completed Core Mobile UI Build path",
        earnedAt = null
    )

    private val deviceFeaturesBadge = Badge(
        id = "badge_device",
        name = "Device Master",
        icon = R.drawable.badge_not_started,
        description = "Completed Access Device Features path",
        earnedAt = null
    )

    private val navigationBadge = Badge(
        id = "badge_navigation",
        name = "Navigation Pro",
        icon = R.drawable.badge_not_started,
        description = "Completed Navigation and Forms path",
        earnedAt = null
    )

    private val stateManagementBadge = Badge(
        id = "badge_state",
        name = "State Wizard",
        icon = R.drawable.badge_not_started,
        description = "Completed State Management path",
        earnedAt = null
    )


    private val programmingBasicsPath = Path(
        id = "path_programming",
        title = "Programming Basics",
        description = "Learn the fundamentals of programming",
        icon = R.drawable.badge_completed,
        badge = programmingBasicsBadge,
        orderIndex = 0,
        isUnlocked = true,
        topics = listOf(
            Topic(
                id = "topic_variables",
                title = "Variables and Data Types",
                orderIndex = 0,
                tasks = listOf(
                    Task("task_1", "Introduction to variables", isCompleted = true, orderIndex = 0),
                    Task("task_2", "Working with strings", isCompleted = true, orderIndex = 1),
                    Task("task_3", "Numbers and operators", isCompleted = true, orderIndex = 2)
                )
            ),
            Topic(
                id = "topic_control",
                title = "Control Flow",
                orderIndex = 1,
                tasks = listOf(
                    Task("task_4", "If statements", isCompleted = true, orderIndex = 0),
                    Task("task_5", "Loops", isCompleted = true, orderIndex = 1)
                )
            )
        )
    )

    private val gitPath = Path(
        id = "path_git",
        title = "Git & Version Control",
        description = "Master version control with Git",
        icon = R.drawable.badge_completed,
        badge = gitBadge,
        orderIndex = 1,
        isUnlocked = true,
        topics = listOf(
            Topic(
                id = "topic_git_basics",
                title = "Git Basics",
                orderIndex = 0,
                tasks = listOf(
                    Task("task_6", "Initialize a repository", isCompleted = true, orderIndex = 0),
                    Task("task_7", "Commit changes", isCompleted = true, orderIndex = 1),
                    Task("task_8", "Push to remote", isCompleted = true, orderIndex = 2)
                )
            ),
            Topic(
                id = "topic_branching",
                title = "Branching",
                orderIndex = 1,
                tasks = listOf(
                    Task("task_9", "Create branches", isCompleted = true, orderIndex = 0),
                    Task("task_10", "Merge branches", isCompleted = true, orderIndex = 1)
                )
            )
        )
    )

    private val reactPath = Path(
        id = "path_react",
        title = "Learn React",
        description = "Build modern UIs with React",
        icon = R.drawable.badge_in_progress,
        badge = reactBadge,
        orderIndex = 2,
        isUnlocked = true,
        topics = listOf(
            Topic(
                id = "topic_react_basics",
                title = "React Basics",
                orderIndex = 0,
                tasks = listOf(
                    Task("task_11", "Create your first component", isCompleted = true, orderIndex = 0),
                    Task("task_12", "Understanding JSX", isCompleted = true, orderIndex = 1),
                    Task("task_13", "Props and state", isCompleted = true, orderIndex = 2)
                )
            ),
            Topic(
                id = "topic_component_lifecycle",
                title = "Component lifecycle",
                orderIndex = 1,
                tasks = listOf(
                    Task("task_14", "Build a login screen in React", isCompleted = false, orderIndex = 0),
                    Task("task_15", "Handle user events", isCompleted = false, orderIndex = 1),
                    Task("task_16", "useEffect hook", isCompleted = false, orderIndex = 2)
                )
            ),
            Topic(
                id = "topic_hooks",
                title = "React Hooks",
                orderIndex = 2,
                tasks = listOf(
                    Task("task_17", "useState deep dive", isCompleted = false, orderIndex = 0),
                    Task("task_18", "Custom hooks", isCompleted = false, orderIndex = 1)
                )
            )
        )
    )

    private val mobileUIPath = Path(
        id = "path_mobile_ui",
        title = "Core Mobile UI Build",
        description = "Build beautiful mobile interfaces",
        icon = R.drawable.badge_not_started,
        badge = mobileUIBadge,
        orderIndex = 3,
        isUnlocked = false,
        topics = listOf(
            Topic(
                id = "topic_layouts",
                title = "Layouts and Containers",
                orderIndex = 0,
                tasks = listOf(
                    Task("task_19", "Row and Column layouts", isCompleted = false, orderIndex = 0),
                    Task("task_20", "Box and Stack", isCompleted = false, orderIndex = 1)
                )
            )
        )
    )

    private val deviceFeaturesPath = Path(
        id = "path_device",
        title = "Access Device Features",
        description = "Use camera, GPS, and more",
        icon = R.drawable.badge_not_started,
        badge = deviceFeaturesBadge,
        orderIndex = 4,
        isUnlocked = false,
        topics = listOf(
            Topic(
                id = "topic_camera",
                title = "Camera Access",
                orderIndex = 0,
                tasks = listOf(
                    Task("task_21", "Take photos", isCompleted = false, orderIndex = 0),
                    Task("task_22", "Access gallery", isCompleted = false, orderIndex = 1)
                )
            )
        )
    )

    private val navigationPath = Path(
        id = "path_navigation",
        title = "Navigation and Forms",
        description = "Build multi-screen apps with forms",
        icon = R.drawable.badge_not_started,
        badge = navigationBadge,
        orderIndex = 5,
        isUnlocked = false,
        topics = listOf(
            Topic(
                id = "topic_nav",
                title = "Navigation Basics",
                orderIndex = 0,
                tasks = listOf(
                    Task("task_23", "Setup navigation", isCompleted = false, orderIndex = 0),
                    Task("task_24", "Pass data between screens", isCompleted = false, orderIndex = 1)
                )
            )
        )
    )

    private val stateManagementPath = Path(
        id = "path_state",
        title = "State Management",
        description = "Manage app state effectively",
        icon = R.drawable.badge_not_started,
        badge = stateManagementBadge,
        orderIndex = 6,
        isUnlocked = false,
        topics = listOf(
            Topic(
                id = "topic_state",
                title = "State Patterns",
                orderIndex = 0,
                tasks = listOf(
                    Task("task_25", "Local vs Global state", isCompleted = false, orderIndex = 0),
                    Task("task_26", "Redux basics", isCompleted = false, orderIndex = 1)
                )
            )
        )
    )

    val fullstackMobileCourse = Course(
        id = "course_fullstack_mobile",
        title = "Fullstack Mobile Engineer",
        description = "Become a complete mobile developer",
        icon = R.drawable.badge_in_progress,
        paths = listOf(
            programmingBasicsPath,
            gitPath,
            reactPath,
            mobileUIPath,
            deviceFeaturesPath,
            navigationPath,
            stateManagementPath
        )
    )

    val allCourses = listOf(fullstackMobileCourse)

    val allBadges = listOf(
        programmingBasicsBadge,
        gitBadge,
        reactBadge,
        mobileUIBadge,
        deviceFeaturesBadge,
        navigationBadge,
        stateManagementBadge
    )

    val earnedBadges = allBadges.filter { it.isEarned }
}