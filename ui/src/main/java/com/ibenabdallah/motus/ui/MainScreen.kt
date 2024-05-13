package com.ibenabdallah.motus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ibenabdallah.motus.domain.model.Letter
import com.ibenabdallah.motus.domain.model.Status
import com.ibenabdallah.motus.ui.theme.BookStoreTheme
import java.util.regex.Pattern

@Composable
fun AppScreen() {

    val viewModel = hiltViewModel<MotusViewModel>()

    LaunchedEffect(key1 = true) {
        viewModel.fetch()
    }


    val state = remember { viewModel.state }.collectAsState()
    val score = remember { viewModel.score }.collectAsState()
    val jeuState = remember { viewModel.jeuState }.collectAsState()
    val currentEssays = remember { viewModel.currentEssays }.collectAsState()


    UIStateView(state) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            Column(
                Modifier
                    .padding(24.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = "Vous avez resolue ${score.value} mot(s) !")
            }

            Column(Modifier.align(alignment = Alignment.CenterHorizontally)) {
                currentEssays.value.forEach {
                    UiLetter(letters = it)
                }
            }

            if (jeuState.value == null) {
                UiTextView(Modifier.align(alignment = Alignment.CenterHorizontally)) {
                    viewModel.validate(it)
                }
            }


            jeuState.value?.let {
                UiJeuStateView(
                    jeuState = it,
                    reset = { viewModel.reset() },
                    next = { viewModel.next() }
                )
            }


            UiInfoView()
        }
    }
}

@Composable
fun UiTextView(modifier: Modifier, onClick: (String) -> Unit) {
    val pattern = Pattern.compile("^[A-Za-z]+$")
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = "")) }
    var buttonEnabled by remember { mutableStateOf(false) }
    val maxChar = 6
    Row(modifier = modifier.padding(top = 24.dp)) {
        TextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                val matcher = pattern.matcher(newValue.text)
                if (matcher.matches() || newValue.text.isEmpty()) {
                    if (newValue.text.length <= maxChar) {
                        textFieldValue = newValue
                    }
                    buttonEnabled = textFieldValue.text.length == maxChar
                }
            },
            modifier = Modifier.width(200.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )
        Button(
            onClick = {
                onClick(textFieldValue.text)
                textFieldValue = TextFieldValue(text = "")
                buttonEnabled = false
            },
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(start = 8.dp),
            enabled = buttonEnabled
        ) {
            Text(text = "Submit")
        }
    }
}

@Composable
fun UiLetter(letter: Letter, position: Int = 0) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .width(50.dp)
            .background(color = getColorLetter(letter.status))
            .border(width = 1.dp, color = Color.White)
    ) {

        if (letter.status != Status.None || position == 0) {
            Text(
                text = letter.letter.toString(),
                modifier = Modifier.align(alignment = Alignment.Center),
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.Black,
                )
            )
        }
    }
}

@Composable
fun UiLetter(letters: List<Letter>, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        letters.forEachIndexed { index, letter -> UiLetter(letter, index) }
    }
}

@Composable
fun UiInfoView(modifier: Modifier = Modifier) {

    Column(modifier = modifier.padding(top = 24.dp)) {
        Row {
            UiLetter(Letter('A', Status.Correct))
            Text(
                text = "La lettre est bien placée",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
        }
        Row {
            UiLetter(Letter('A', Status.WrongPlace))
            Text(
                text = "La lettre est présente mais mal placée",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
        }
        Row {
            UiLetter(Letter('A', Status.Wrong))
            Text(
                text = "La lettre n'est pas présente dans le mot",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
        }
    }

}

@Composable
fun UiJeuStateView(jeuState: JeuState, reset: () -> Unit, next: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(16.dp)
    ) {
        when (jeuState) {
            JeuState.Failure -> UIStateView(
                text = "Vous avez perdu cette partie !",
                textButton = "Rejouer",
                color = Color.Red,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) { reset() }

            is JeuState.Success -> UIStateView(
                text = "Vous avez gagné cette partie !",
                textButton = "Suivant",
                color = Color.Green,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) { next() }
        }
    }
}

@Composable
fun UIStateView(
    text: String,
    textButton: String,
    color: Color,
    modifier: Modifier,
    reset: () -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = text,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            color = color
        )

        Button(
            onClick = { reset() },
            modifier = Modifier
                .padding(8.dp)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(text = textButton)
        }
    }
}

@Composable
fun getColorLetter(status: Status) = when (status) {
    Status.Correct -> Color.Green
    Status.Wrong -> Color.Red
    Status.WrongPlace -> Color.Yellow
    Status.None -> Color.LightGray
}


@Preview(showBackground = true)
@Composable
fun UiLetterPreview() {
    BookStoreTheme {
        val letter = Letter('A', Status.Correct)
        UiLetter(letter)
    }
}

@Preview(showBackground = true)
@Composable
fun UiLettersPreview() {
    BookStoreTheme {
        //ABACAS
        val letter1 = Letter('A', Status.Correct)
        val letter2 = Letter('B', Status.Wrong)
        val letter3 = Letter('A', Status.WrongPlace)
        val letter4 = Letter('C', Status.Correct)
        val letter5 = Letter('A', Status.WrongPlace)
        val letter6 = Letter('S', Status.Wrong)

        val letters = listOf(letter1, letter2, letter3, letter4, letter5, letter6)
        UiLetter(letters)
    }
}

@Preview(showBackground = true)
@Composable
fun UiTextViewPreview() {
    BookStoreTheme {
        UiTextView(modifier = Modifier) {}
    }
}

@Preview(showBackground = true)
@Composable
fun UiInfoViewPreview() {
    BookStoreTheme {
        UiInfoView()
    }
}

@Preview(showBackground = true)
@Composable
fun UiJeuStateViewPreview_Failure() {
    BookStoreTheme {
        UiJeuStateView(JeuState.Failure, {}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun UiJeuStateViewPreview_Success() {
    BookStoreTheme {
        UiJeuStateView(JeuState.Success, {}, {})
    }
}