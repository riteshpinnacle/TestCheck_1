package com.ssccgl.pinnacle.testcheck_1


import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(viewModel: MainViewModel = viewModel()) {
    val data by viewModel.data.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()
//    val saveAnswerResponse by viewModel.saveAnswerResponse.observeAsState()

    val details = data.flatMap { it.details }

    var currentQuestionId by remember { mutableStateOf(1) } // Start with the first question_id
    var selectedOption by remember { mutableStateOf("") } // To keep track of the selected option
//    val context = LocalContext.current

//    Timer state
    var remainingTime by remember {mutableStateOf(3600L)}
    var timerStarted by remember { mutableStateOf(false) } // Track if the timer has started





//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("API Data") }
//            )
//        }
//    ) { paddingValues ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//            if (error != null) {
//                Text(
//                    text = error ?: "Unknown error",
//                    color = MaterialTheme.colorScheme.error,
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.padding(16.dp)
//                )
////            } else if(details.isEmpty()){
////                CircularProgressIndicator(
////                )
//            }else {
//
//
//                val currentQuestion = details.find { it.question_id == currentQuestionId }
//
//                if (currentQuestion != null) {
//                    LazyColumn(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp),
//                        verticalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        item {
//                            HtmlText(html = currentQuestion.question)
//                            Spacer(modifier = Modifier.height(16.dp))
////                            HtmlText(currentQuestion.toString())
//
//                            OptionItem(
//                                option = currentQuestion.option1,
//                                optionValue = "a",
//                                selectedOption = selectedOption,
//                                onSelectOption = { selectedOption = it
////                                    viewModel.saveAnswer(
////                                        paperId = currentQuestion.question_id,
////                                        option = /*selectedOption*/  "a",
////                                        subject = currentQuestion.subject_id,
////                                        currentPaperId = currentQuestionId
////                                    )
//                                }
//                            )
//                            OptionItem(
//                                option = currentQuestion.option2,
//                                optionValue = "b",
//                                selectedOption = selectedOption,
//                                onSelectOption = { selectedOption = it
////                                    viewModel.saveAnswer(
////                                        paperId = currentQuestion.question_id,
////                                        option = /*selectedOption*/ "b",
////                                        subject = currentQuestion.subject_id,
////                                        currentPaperId = currentQuestionId
////                                    )
//                                }
//                            )
//                            OptionItem(
//                                option = currentQuestion.option3,
//                                optionValue = "c",
//                                selectedOption = selectedOption,
//                                onSelectOption = { selectedOption = it
////                                    viewModel.saveAnswer(
////                                        paperId = currentQuestion.question_id,
////                                        option = /*selectedOption*/ "c",
////                                        subject = currentQuestion.subject_id,
////                                        currentPaperId = currentQuestionId
////                                    )
//                                }
//                            )
//                            OptionItem(
//                                option = currentQuestion.option4,
//                                optionValue = "d",
//                                selectedOption = selectedOption,
//                                onSelectOption = { selectedOption = it
////                                    viewModel.saveAnswer(
////                                        paperId = currentQuestion.question_id,
////                                        option = /*selectedOption*/ "d",
////                                        subject = currentQuestion.subject_id,
////                                        currentPaperId = currentQuestionId
////                                    )
//                                }
//                            )
//
//                            Spacer(modifier = Modifier.height(16.dp))
//                        }
//
//                        item {
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                if (currentQuestionId > 1) {
//                                    Button(
//                                        onClick = {
//                                            currentQuestionId--
//                                        },
//                                    ) {
//                                        Text("Previous")
//                                    }
//                                }
//
//                                if (currentQuestionId < details.maxOf { it.question_id }) {
//                                    Button(
//                                        onClick = {
//                                            viewModel.saveAnswer(
//                                                paperId = currentQuestion.question_id,
//                                                option = selectedOption.ifEmpty { "" },
//                                                subject = currentQuestion.subject_id,
//                                                currentPaperId = currentQuestionId
//                                            )
//                                            currentQuestionId++
//                                            selectedOption = "" // Reset selected option for next question
//                                        },
//                                    ) {
//                                        Text("Save and Next")
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    Text(
//                        text = "Questions are loading...",
//                        style = MaterialTheme.typography.bodyLarge,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//            }
//        }
//    }

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
            } else {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    val currentQuestion = details.find { it.question_id == currentQuestionId }

                    //  Start the timer
                    if (currentQuestion != null && !timerStarted){
                        LaunchedEffect(Unit) {
                            while (remainingTime > 0) {
                                delay(1000L)
                                remainingTime--
                            }
                        }
                    }


                    // Display the timer
                    Text(
                        text = formatTime(remainingTime),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally)  // Corrected alignment
                    )


                    if (currentQuestion != null) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            item {
                                HtmlText(html = currentQuestion.question)
                                Spacer(modifier = Modifier.height(16.dp))

                                OptionItem(
                                    option = currentQuestion.option1,
                                    optionValue = "a",
                                    selectedOption = selectedOption,
                                    onSelectOption = { selectedOption = it }
                                )
                                OptionItem(
                                    option = currentQuestion.option2,
                                    optionValue = "b",
                                    selectedOption = selectedOption,
                                    onSelectOption = { selectedOption = it }
                                )
                                OptionItem(
                                    option = currentQuestion.option3,
                                    optionValue = "c",
                                    selectedOption = selectedOption,
                                    onSelectOption = { selectedOption = it }
                                )
                                OptionItem(
                                    option = currentQuestion.option4,
                                    optionValue = "d",
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
                                                viewModel.saveAnswer(
                                                    paperId = currentQuestion.question_id,
                                                    option = selectedOption.ifEmpty { "" },
                                                    subject = currentQuestion.subject_id,
                                                    currentPaperId = currentQuestionId,
                                                    remainingTime = formatTime(remainingTime)
                                                )
                                                currentQuestionId++
                                                selectedOption = "" // Reset selected option for next question
                                            },
                                        ) {
                                            Text("Save and Next")
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "Questions are loading...",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally)  // Corrected alignment
                        )
                    }
                }
            }
        }
    }


}

@Composable
fun OptionItem(option: String, optionValue: String, selectedOption: String, onSelectOption: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        RadioButton(
            selected = selectedOption == optionValue,
            onClick = { onSelectOption(optionValue) }
        )
        HtmlText(html = option)
    }
}

fun formatTime(seconds: Long): String {
    val hours = TimeUnit.SECONDS.toHours(seconds)
    val minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}