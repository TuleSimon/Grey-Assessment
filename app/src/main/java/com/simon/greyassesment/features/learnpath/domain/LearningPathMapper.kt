package com.simon.greyassesment.features.learnpath.domain

import com.simon.greyassesment.domain.model.Path
import com.simon.greyassesment.domain.model.PathStatus
import com.simon.greyassesment.features.learnpath.presentation.components.BadgeState
import com.simon.greyassesment.features.learnpath.presentation.components.LevelNode

object LearningPathMapper {

    fun mapPathToLevelNode(path: Path): LevelNode {
        return LevelNode(
            id = path.orderIndex,
            title = path.title,
            state = mapPathStatusToBadgeState(path),
            icon = path.icon,
            subTitle = path.currentTopic?.title,
            badge = path.badge
        )
    }

    fun mapPathsToLevelNodes(paths: List<Path>): List<LevelNode> {
        return paths.map { mapPathToLevelNode(it) }
    }

    private fun mapPathStatusToBadgeState(path: Path): BadgeState {
        return when (path.status) {
            PathStatus.COMPLETED -> BadgeState.COMPLETED
            PathStatus.IN_PROGRESS -> BadgeState.InProgress(path.progress)
            PathStatus.LOCKED -> BadgeState.LOCKED
        }
    }
}