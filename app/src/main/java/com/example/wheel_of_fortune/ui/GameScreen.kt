package com.example.wheel_of_fortune.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
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


@Preview
@Composable
fun GameScreen(modifier: Modifier = Modifier) {

    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        PlayerInfo(modifier = Modifier)
        SpinTheWheelButton(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )
        Status(modifier = Modifier)
        GuessAndSubmitLetter(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerInfo(modifier: Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp),
    ) {
        Text(
            text = stringResource(R.string.life, 5), fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(R.string.points, 0),
            fontSize = 18.sp,
        )
    }
}

@Composable
fun SpinTheWheelButton(modifier: Modifier) {
    Button(onClick = { /*TODO*/ }) {
        Text(text = stringResource(R.string.spin_button_text))
    }
}

@Composable
fun Status(modifier: Modifier) {
    Column(modifier = Modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            text = "Points from the wheel"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            text = "*************"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuessAndSubmitLetter(modifier: Modifier) {
    val textOnlyRegex = remember { Regex("[a-zA-Z]*") }
    Column(modifier = Modifier) {
        var text by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            maxLines = 1,
            onValueChange = {
                if (it.matches(textOnlyRegex) && it.length <= 1) {
                    text = it.uppercase()
                }
            },
            label = { Text(text = stringResource(R.string.input)) },
            placeholder = { Text(text = stringResource(R.string.writeLetter)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Characters
            ),
        )
        Button(modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center),
            onClick = { /*TODO*/ }) {
            Text(text = stringResource(R.string.submit))
        }
    }

}

@Composable
private fun FinishedGame(
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {
        },
        title = { Text(text = "something when the game is done") },
        text = { Text(text = "you got this many points") },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onPlayAgain()
                }
            ) {
                Text(text = stringResource(R.string.playAgain))
            }
        }
    )
}
