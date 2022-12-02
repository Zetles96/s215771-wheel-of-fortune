package com.example.wheel_of_fortune.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wheel_of_fortune.R
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Main screen component, and holds all the sub components and passes the viewModel down to the components
 * @param modifier
 * @param gameViewModel
 */
@Preview
@Composable
fun GameScreen(modifier: Modifier = Modifier, gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PlayerInfo(life = gameUiState.lives, points = gameUiState.points, modifier = Modifier)
        if (gameUiState.isSpinFace) {
            SpinTheWheelButton(
                spinButtonClick = { gameViewModel.spinTheWheel() },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            )
        }
        Status(
            modifier = Modifier,
            guessCategory = gameViewModel.guessCategory,
            shownWord = gameUiState.shownWord,
            pointsFromWheel = gameUiState.wheelPoints
        )
        if (!gameUiState.isSpinFace) {
            GuessAndSubmitLetter(
                onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
                userGuess = gameViewModel.userGuess,
                onKeyboardDone = { gameViewModel.checkUserGuess() },
                submitGuess = { gameViewModel.checkUserGuess() },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
    if (gameUiState.isGameOver) {

        FinishedGame(
            score = gameUiState.points,
            isGameWon = gameUiState.isGameWon,
            onPlayAgain = { gameViewModel.resetGame() })
    }
}

/**
 * composable for the player information at the top of the screen
 * @param life the players life
 * @param points the players current points
 * @param modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerInfo(life: Int, points: Int, modifier: Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp),
    ) {
        Text(
            text = stringResource(R.string.life, life), fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(R.string.points, points),
            fontSize = 18.sp,
        )
    }
}

/**
 * Composable button for spinning the wheel
 * @param spinButtonClick lambda expression for what happens when spinning the wheel
 * @param modifier
 */
@Composable
fun SpinTheWheelButton(spinButtonClick: () -> Unit, modifier: Modifier) {
    Button(onClick = spinButtonClick) {
        Text(text = stringResource(R.string.spin_button_text))
    }
}

/**
 * Composable for showing the game status, like the nonguessed word, the category, and the points
 * you will get if you guess correctly
 * @param shownWord
 * @param guessCategory
 * @param pointsFromWheel
 * @param modifier
 */
@Composable
fun Status(shownWord: String, guessCategory: String, pointsFromWheel: Int, modifier: Modifier) {
    Column(modifier = Modifier) {
        if (pointsFromWheel > -1) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                text = stringResource(R.string.pointsFromWheel, pointsFromWheel)
            )
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                text = stringResource(R.string.bankrupt)
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            text = guessCategory
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center), text = shownWord
        )
    }
}

/**
 * Composable for taking an input from the user and submitting it
 * @param onUserGuessChanged handles state for the input in textbox
 * @param onKeyboardDone also method for submitting the guess
 * @param submitGuess method for submitting the guess
 * @param modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuessAndSubmitLetter(
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    submitGuess: () -> Unit,
    userGuess: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = userGuess.uppercase(),
            maxLines = 1,
            onValueChange = onUserGuessChanged,
            label = { Text(text = stringResource(R.string.input)) },
            placeholder = { Text(text = stringResource(R.string.writeLetter)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Characters
            ),
            keyboardActions = KeyboardActions(onDone = { onKeyboardDone() }),
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            onClick = submitGuess
        ) {
            Text(text = stringResource(R.string.submit))
        }
    }

}

/**
 * Alert dialog for when the game is finished, where you can play again or finish the game, and shows
 * final score and whether you have won
 * @param score players score
 * @param isGameWon whether the player won or lost
 * @param onPlayAgain method for if the player wants to play again
 */
@Composable
private fun FinishedGame(
    score: Int,
    isGameWon: Boolean,
    onPlayAgain: () -> Unit, modifier: Modifier = Modifier,
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(onDismissRequest = {},
        title = {
            if (isGameWon) {
                Text(text = stringResource(R.string.wongame))
            } else {
                Text(text = stringResource(R.string.lostgame))
            }
        },
        text = { Text(text = stringResource(R.string.thismanypoints, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = {
                activity.finish()
            }) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onPlayAgain()
            }) {
                Text(text = stringResource(R.string.playAgain))
            }
        })
}
