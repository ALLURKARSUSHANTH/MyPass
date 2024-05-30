package com.mypass.pass.screenui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypass.pass.server.StudentData
import com.mypass.pass.server.UserData
import com.mypass.pass.server.UserDataViewModel
import com.mypass.pass.server.expiredStudentData
import com.mypass.pass.server.expiredUserData

@Composable
fun DisplayStudentDataInCard(dataList: List<StudentData>,userDataViewModel: UserDataViewModel) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        dataList.forEach { data ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    Text(
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
                        text = "Your Details: "
                    )
                    Text(
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
                        text = "Student Pass"
                    )
                    userDataViewModel.imageRetrieve()
                    userDataViewModel.RetrievedImage(userDataViewModel.image)
                    Row {
                        Column {
                            Text(text = "Name: ${data.name}")
                            Text(text = "Parent : ${data.parent}")
                            Text(text = "Phone Number: ${data.phoneNumber}")
                            Text(text = "Address: ${data.address}")
                            Text(text = "Date of Birth: ${data.dob}")
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(text = "Validity Type: ${data.validity}")
                    Text(text = "Valid Till: ${data.expiryDate}")
                    Text(text = "Category: ${data.type}")
                    Text(text = "Amount Paid: ${data.amount}")
                }
            }

        }
    }
}
@Composable
fun DisplayUserDataInCard(dataList: List<UserData>,userDataViewModel: UserDataViewModel) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        dataList.forEach { data ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    Text(modifier = Modifier, fontWeight = FontWeight.Bold, text = "Your Details: ")
                    Text(modifier = Modifier, fontWeight = FontWeight.Bold, text = "General Pass")
                    userDataViewModel.imageRetrieve()
                    userDataViewModel.RetrievedImage(userDataViewModel.image)
                    Row {
                        Column {
                            Text(text = "Name: ${data.name}")
                            Text(text = "Parent : ${data.parent}")
                            Text(text = "Phone Number: ${data.phoneNumber}")
                            Text(text = "Address: ${data.address}")
                            Text(text = "Date of Birth: ${data.dob}")
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(text = "Validity Type: ${data.validity}")
                    Text(text = "Valid Till: ${data.expiryDate}")
                    Text(text = "Category: ${data.type}")
                    Text(text = "Amount Paid: ${data.amount}")
                }
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PassData(userDataViewModel: UserDataViewModel = viewModel()) {
    var onDeleteMessage by remember { mutableStateOf("") }
    Scaffold (modifier = Modifier
        .padding(15.dp),
        content = {
            Column(
                modifier = Modifier
                    .padding(top = 45.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LaunchedEffect(Unit) {
                    userDataViewModel.retrieveUserData()
                    userDataViewModel.retrieveStudentData { studentData ->
                        userDataViewModel.studentDataList = studentData
                    }
                }

                expiredUserData(dataList = userDataViewModel.userDataList) { message ->
                    onDeleteMessage = message
                }
                expiredStudentData(dataList = userDataViewModel.studentDataList) { message ->
                    onDeleteMessage = message
                }
                Card(modifier = Modifier.padding(8.dp)) {
                    var expanded by remember { mutableStateOf(false) }
                    Column(modifier = Modifier) {
                        TextButton(onClick = { expanded = !expanded }) {
                            Text(text = "Student Pass :")
                        }
                        AnimatedVisibility(expanded) {
                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (userDataViewModel.studentDataList.isEmpty()) {

                                    Text("Your Pass will Appear here 👇")

                                } else {
                                    DisplayStudentDataInCard(
                                        dataList = userDataViewModel.studentDataList,
                                        userDataViewModel
                                    )
                                }
                            }
                        }
                    }
                }
                Card(modifier = Modifier.padding(8.dp)) {
                    var expanded by remember { mutableStateOf(false) }
                    Column(modifier = Modifier) {
                        TextButton(onClick = { expanded = !expanded }) {
                            Text(text = "General Pass :")
                        }
                        AnimatedVisibility(expanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                if (userDataViewModel.userDataList.isEmpty()) {
                                    Column(
                                        modifier = Modifier,
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("Your Pass will Appear here 👇")
                                    }
                                } else {
                                    DisplayUserDataInCard(
                                        dataList = userDataViewModel.userDataList,
                                        userDataViewModel
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPassData() {
    PassData()
}