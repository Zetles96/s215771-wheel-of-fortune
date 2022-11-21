package com.example.wheel_of_fortune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
        WelcomeMessage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )
        SpinTheWheelButton(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center))
    }

}

@Composable
fun WelcomeMessage(modifier: Modifier) {
    Text(
        text = stringResource(R.string.welcome_to_the_wheel_of_fortune),
        modifier = modifier.wrapContentWidth(Alignment.CenterHorizontally)
    )
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
@Composable
fun SubmitLetterButton(modifier: Modifier) {
    Button(onClick = { /*TODO*/ }) {
        Text(text = stringResource(R.string.spin_button_text))
    }
}