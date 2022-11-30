package com.example.wheel_of_fortune.ui

/**
 * Data class thingy
 */
data class GameUiState(
    var currentWord: String = " ",
    var points: Int = 0,
    var lives: Int = 5,
    var isGameOver: Boolean = false,
)
