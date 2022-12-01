package com.example.wheel_of_fortune.ui

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.wheel_of_fortune.data.wordArray
import com.example.wheel_of_fortune.data.categoryArray
import kotlinx.coroutines.flow.update

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
    lateinit var guessCategory: String

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

    fun checkUserGuess() {
        if (guessWord.contains(userGuess, ignoreCase = true) && !_uiState.value.shownWord.contains(
                userGuess,
                ignoreCase = true
            )
        ) {
            val letter_indexes = arrayListOf<Int>()
            for (i in guessWord.indices) {
                if (userGuess.first() == guessWord[i])
                    letter_indexes.add(i)
            }
            for (i in letter_indexes.indices) {
                _uiState.update { currentState ->
                    currentState.copy(
                        shownWord = _uiState.value.shownWord.substring(
                            0,
                            letter_indexes[i]
                        ) + userGuess + _uiState.value.shownWord.substring(letter_indexes[i] + 1)
                    )
                }
            }
            _uiState.update { currentState -> currentState.copy(points = _uiState.value.points + 100 * letter_indexes.size) }
        } else {
            _uiState.update { currentState ->
                currentState.copy(lives = _uiState.value.lives - 1)
            }
        }
        updateUserGuess("")
    }
}

