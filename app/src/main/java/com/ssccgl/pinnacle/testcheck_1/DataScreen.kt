package com.ssccgl.pinnacle.testcheck_1


import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(viewModel: MainViewModel = viewModel()) {
    val data by viewModel.data.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()

    val details = data.flatMap { it.details }

    var currentQuestionId by remember { mutableStateOf(1) } // Start with the first question_id
    var selectedOption by remember { mutableStateOf("") } // To keep track of the selected option
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("API Data") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (error != null) {
                Text(
                    text = error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            } else if(details.isEmpty()){
                CircularProgressIndicator(
                )
            }else {
                val currentQuestion = details.find { it.question_id == currentQuestionId }

                if (currentQuestion != null) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        item {
                            HtmlText(html = currentQuestion.question)
                            Spacer(modifier = Modifier.height(16.dp))
//                            HtmlText(currentQuestion.toString())

                            OptionItem(
                                option = currentQuestion.option1,
                                selectedOption = selectedOption,
                                onSelectOption = { selectedOption = it }
                            )
                            OptionItem(
                                option = currentQuestion.option2,
                                selectedOption = selectedOption,
                                onSelectOption = { selectedOption = it }
                            )
                            OptionItem(
                                option = currentQuestion.option3,
                                selectedOption = selectedOption,
                                onSelectOption = { selectedOption = it }
                            )
                            OptionItem(
                                option = currentQuestion.option4,
                                selectedOption = selectedOption,
                                onSelectOption = { selectedOption = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                if (currentQuestionId > 1) {
                                    Button(
                                        onClick = {
                                            currentQuestionId--
                                        },
                                    ) {
                                        Text("Previous")
                                    }
                                }

                                if (currentQuestionId < details.maxOf { it.question_id }) {
                                    Button(
                                        onClick = {
                                            currentQuestionId++
                                        },
                                    ) {
                                        Text("Next")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Questions are loading...",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun OptionItem(option: String, selectedOption: String, onSelectOption: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        RadioButton(
            selected = selectedOption == option,
            onClick = { onSelectOption(option) }
        )
        HtmlText(html = option)
    }
}