package com.example.wheel_of_fortune.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.wheel_of_fortune.data.wordArray
import com.example.wheel_of_fortune.data.categoryArray

class GameViewModel : ViewModel() {
    // State of the game ui
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    fun updateUserGuess(guessedWord: String) {
        val textOnlyRegex = Regex("[a-zA-Z]*")
        if (guessedWord.matches(textOnlyRegex) && guessedWord.length <= 1)
            userGuess = guessedWord.uppercase()
    }

    // State of words to guess and shown word
    private lateinit var guessWord: String
    private lateinit var guessCategory: String

    init {
        resetGame()
    }

    private fun resetGame() {
        _uiState.value = GameUiState(shownWord = pickWordAndGenerateUnguessedWord())
    }


    // picks a random word and then returns a string filled with * that is the same length as the word
    private fun pickWordAndGenerateUnguessedWord(): String {
        val random = (0 until wordArray.size).random()
        guessWord = wordArray[random].uppercase()
        guessCategory = categoryArray[random]
        return "*".repeat(guessWord.length)
    }
}
