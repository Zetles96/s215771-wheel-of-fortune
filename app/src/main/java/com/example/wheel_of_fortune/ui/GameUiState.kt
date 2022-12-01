package com.example.wheel_of_fortune.ui

/**
 * Data class thingy
 */
data class GameUiState(
    val shownWord: String = "",
    val points: Int = 0,
    val lives: Int = 5,
    val wheelPoints: Int = 0,
    val isGameOver: Boolean = false,
    val isGameWon: Boolean = false,
    val isSpinFace: Boolean = true,
)
