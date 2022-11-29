package com.example.wheel_of_fortune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.wheel_of_fortune.ui.theme.WheeloffortuneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WheeloffortuneTheme {
                // A surface container using the 'background' color from the theme
                WheelOfFortuneApp()
            }
        }
    }
}

@Preview
@Composable
fun WheelOfFortuneApp() {

    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        PlayerInfo(modifier = Modifier)
        SpinTheWheelButton(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )
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
    Column(modifier = Modifier) {
        Text(text = stringResource(R.string.life))
        Text(text = stringResource(R.string.points))
    }
}

@Composable
fun WheelPicture(modifier: Modifier) {

}

@Composable
fun SpinTheWheelButton(modifier: Modifier) {
    Button(onClick = { /*TODO*/ }) {
        Text(text = stringResource(R.string.spin_button_text))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuessAndSubmitLetter(modifier: Modifier) {
    val textOnlyRegex = remember { Regex("[A-Z]*") }
    Row(modifier = Modifier) {
        var text by remember { mutableStateOf("") }

        OutlinedTextField(
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
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(R.string.submit))
        }
    }

}
