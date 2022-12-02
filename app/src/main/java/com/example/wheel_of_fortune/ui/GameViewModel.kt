package com.example.wheel_of_fortune.ui

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.wheel_of_fortune.data.wordArray
import com.example.wheel_of_fortune.data.categoryArray
import kotlinx.coroutines.flow.update

/**
 * GameViewModel class, that inherits from the viewmodel class, and basically has all the logic of
 * the game. and handles the state
 */
class GameViewModel : ViewModel() {
    // State of the game ui
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()


    // userguess variable for keyboard stuff
    var userGuess by mutableStateOf("")
        private set

    /**
     * updates the userguess and validates it with a regular expression to make sure it's a letter
     * and not an integer, and also makes sure that the length is only one character
     * @param guessedWord single character
     */
    fun updateUserGuess(guessedWord: String) {
        val textOnlyRegex = Regex("[a-zA-Z]*")
        if (guessedWord.matches(textOnlyRegex) && guessedWord.length <= 1)
            userGuess = guessedWord.uppercase()
    }

    // State of words to guess and shown word
    private lateinit var guessWord: String
    lateinit var guessCategory: String

    // what happens at the start of the game
    init {
        resetGame()
    }

    /**
     * resets the game state, at the start of the game and when you want to play again
     */
    fun resetGame() {
        _uiState.value = GameUiState(shownWord = pickWordAndGenerateUnguessedWord())
    }

    /**
     * picks a random word and then returns a string filled with * that is the same length as the word
     * @return string of stars for a word to guess
     */
    //
    private fun pickWordAndGenerateUnguessedWord(): String {
        val random = (wordArray.indices).random()
        guessWord = wordArray[random].uppercase()
        guessCategory = categoryArray[random]
        return "*".repeat(guessWord.length)
    }

    // wheel options array
    private val wheelOption = intArrayOf(-1, 100, 200, 300, 400, 500)

    /**
     * Spins the wheel, and set the state of points you get from the wheel
     */
    fun spinTheWheel() {
        _uiState.update { currentState ->
            currentState.copy(wheelPoints = wheelOption.random())
        }
        if (_uiState.value.wheelPoints == -1) {
            _uiState.update { currentState -> currentState.copy(points = 0) }
        } else {

            changeSpinFace()
        }
    }

    /**
     * changes the face between spinning and guessing
     */
    private fun changeSpinFace() {
        _uiState.update { currestState -> currestState.copy(isSpinFace = !_uiState.value.isSpinFace) }
    }

    /**
     * Check if the user guess is correct, and if the user guess is wrong, you loose a life, and if
     * i's right you gain points * how many questions was right, and replaces * with the letters that
     * are correctly guessed
     */
    fun checkUserGuess() {
        if (guessWord.contains(userGuess, ignoreCase = true) && !_uiState.value.shownWord.contains(
                userGuess,
                ignoreCase = true
            )
        ) {
            val letterIndexes = arrayListOf<Int>()
            for (i in guessWord.indices) {
                if (userGuess.first() == guessWord[i])
                    letterIndexes.add(i)
            }
            for (i in letterIndexes.indices) {
                _uiState.update { currentState ->
                    currentState.copy(
                        shownWord = _uiState.value.shownWord.substring(
                            0,
                            letterIndexes[i]
                        ) + userGuess + _uiState.value.shownWord.substring(letterIndexes[i] + 1)
                    )
                }
            }
            _uiState.update { currentState -> currentState.copy(points = _uiState.value.points + _uiState.value.wheelPoints * letterIndexes.size) }
            if (!_uiState.value.shownWord.contains("*")) {
                winGame()
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(lives = _uiState.value.lives - 1)
            }
            if (_uiState.value.lives == 0) {
                endGame()
            }

        }
        updateUserGuess("")
        changeSpinFace()
    }

    /**
     * Method that ends the game
     */
    private fun endGame() {
        _uiState.update { currentState ->
            currentState.copy(isGameOver = true)
        }
    }

    /**
     * Method that wins and end the game
     */
    private fun winGame() {
        _uiState.update { currentState ->
            currentState.copy(isGameWon = true)
        }
        endGame()

    }
}
