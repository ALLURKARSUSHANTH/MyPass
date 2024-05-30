package com.mypass.pass.screenui
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypass.pass.server.ImageUploadFunction
import com.mypass.pass.server.StudentData
import com.mypass.pass.server.UserData
import com.mypass.pass.server.uploadImageToFirebase
import com.mypass.pass.server.uploadStudentData
import com.mypass.pass.server.uploadUserData

@Composable
fun ApplicationFormContents(formModel: FormModel =viewModel()) {
    var validTill by remember {mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var paymentSuccess by remember { mutableStateOf(false) }
    var paymentInitiated by remember { mutableStateOf(false) }
    val formFields = listOf(
        "Full Name" to formModel.name,
        "Parent/Guardian" to formModel.parent,
        "Phone Number" to formModel.phoneNumber,
        "Address" to formModel.address,
        "D.O.B" to formModel.dob,
        "AadharNumber" to formModel.aadharNo,
        "College Name" to formModel.collegeName,
        "College Address" to formModel.collegeAddress,
        "Institute Code" to formModel.instituteCode,
        "RollNo/AdmissionNo" to formModel.rollNo
    )
    val amount= formModel.passAmount(
        formModel.selectedValidity.value,
        formModel.selectedPassType.value
    )
    val discountAmount= (amount*0.7).toFloat()
    Column(
        modifier = Modifier
            .padding(top = 45.dp), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        ImageUploadFunction(formModel = FormModel()) { uri ->
            formModel.selectedImageUri = uri
        }
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            userScrollEnabled = true,
            columns = GridCells.Fixed(2)
        ) {
            items(formFields) { (label, value) ->
                when (label) {
                    "Full Name" -> {
                        formModel.ApplicationFormField(
                            label = label,
                            value = value,
                            onValueChange = { newValue -> formModel.name = newValue },
                            isError = formModel.nameHasError,
                        )
                        if (formModel.nameHasError) {
                            Text(
                                modifier = Modifier.padding(top = 80.dp),
                                text = formModel.nameError,
                                color = Color.Red
                            )
                        }
                    }

                    "Parent/Guardian" -> {
                        formModel.ApplicationFormField(
                            label = label,
                            value = value,
                            onValueChange = { newValue -> formModel.parent = newValue },
                            isError = formModel.parentHasError
                        )
                        if (formModel.parentHasError) {
                            Text(
                                modifier = Modifier.padding(top = 80.dp),
                                text = formModel.parentError,
                                color = Color.Red
                            )
                        }
                    }

                    "Phone Number" -> {
                        formModel.ApplicationFormField(
                            label = label,
                            value = value,
                            onValueChange = { newValue -> formModel.phoneNumber = newValue },
                            isError = formModel.phoneHasError
                        )
                        if (formModel.phoneHasError) {
                            Text(
                                modifier = Modifier.padding(top = 80.dp),
                                text = formModel.phoneError,
                                color = Color.Red
                            )
                        }
                    }

                    "Address" -> {
                        formModel.ApplicationFormField(
                            label = label,
                            value = value,
                            onValueChange = { newValue -> formModel.address = newValue },
                            isError = formModel.addressHasError
                        )
                        if (formModel.addressHasError) {
                            Text(
                                modifier = Modifier.padding(top = 80.dp),
                                text = formModel.nameError,
                                color = Color.Red
                            )
                        }
                    }

                    "D.O.B" -> {
                        formModel.ApplicationFormField(
                            label = label,
                            value = value,
                            onValueChange = { newValue -> formModel.dob = newValue },
                            isError = formModel.dobHasError
                        )
                        if (formModel.dobHasError) {
                            Text(
                                modifier = Modifier.padding(top = 80.dp),
                                text = formModel.dobError,
                                color = Color.Red
                            )
                        }
                    }

                    "AadharNumber" -> {
                        formModel.ApplicationFormField(
                            label = label,
                            value = value,
                            onValueChange = { newValue -> formModel.aadharNo = newValue },
                            isError = formModel.aadharHasError
                        )
                        if (formModel.aadharHasError) {
                            Text(
                                modifier = Modifier.padding(top = 80.dp),
                                text = formModel.aadharError,
                                color = Color.Red
                            )
                        }
                    }
                }
            }
            item {
                formModel.SelectGender {
                    formModel.selectedGender.value=it
                }
            }
            item {
                formModel.PassType {
                    formModel.selectedPassType.value = it
                }
            }
            item {
                formModel.Validity {
                    formModel.selectedValidity.value = it
                }
            }
            item {
                validTill = formModel.validTill()
            }
            item {
                Card(modifier = Modifier.padding(8.dp)) {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextButton(onClick = { expanded = !expanded }) {
                            Text(text = "Are you a Student?")
                        }
                        AnimatedVisibility(expanded) {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(checked = formModel.isStudent,
                                    onCheckedChange = { isChecked ->
                                        formModel.isStudent = isChecked
                                        expanded= false
                                    }
                                )
                                Text(text = "Yes")
                            }
                        }
                    }
                }
            }
            item {
                Card(modifier = Modifier.padding(8.dp)) {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ) {
                        if (formModel.isStudent) {
                            Text(text = "AMOUNT (INR) : $discountAmount",modifier = Modifier
                                .padding(16.dp)
                                )
                        }
                        else {
                            Text(
                                text = "AMOUNT (INR) : $amount",
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
            if (formModel.isStudent) {
                item {
                    formModel.ApplicationFormField(
                        label = "College Name",
                        value = formModel.collegeName,
                        onValueChange = { newValue -> formModel.collegeName = newValue },
                        isError = formModel.clgNameHasError)
                    if (formModel.clgNameHasError) {
                        Text(
                            modifier = Modifier.padding(top=80.dp),
                            text = formModel.clgNameError,
                            color = Color.Red
                        )
                    }

                }

                item {
                    formModel.ApplicationFormField(
                        label = "College Address",
                        value = formModel.collegeAddress,
                        onValueChange = { newValue -> formModel.collegeAddress = newValue },
                        isError = formModel.clgNameHasError)
                    if (formModel.clgNameHasError) {
                        Text(
                            modifier = Modifier.padding(top = 80.dp),
                            text = formModel.clgNameError,
                            color = Color.Red
                        )
                    }

                }

                item {
                    formModel.ApplicationFormField(
                        label = "Institution Code",
                        value = formModel.instituteCode,
                        onValueChange = { newValue -> formModel.instituteCode = newValue },
                        isError = formModel.instHasError)
                    if (formModel.instHasError) {
                        Text(
                            modifier = Modifier.padding(top = 80.dp),
                            text = formModel.instError,
                            color = Color.Red
                        )
                    }

                }
                item {
                    formModel.ApplicationFormField(
                        label = "Roll No",
                        value = formModel.rollNo,
                        onValueChange = { newValue -> formModel.rollNo = newValue },
                        isError = formModel.rollHasError)
                    if (formModel.rollHasError) {
                        Text(
                            modifier = Modifier.padding(top = 80.dp),
                            text = formModel.rollError,
                            color = Color.Red
                        )
                    }

                }
            }
            item {

            }
            item{
                Button(onClick = {
                    formModel.formValidation()
                    if (formModel.selectedImageUri != null) {
                        paymentInitiated = true
                    }
                    else if (formModel.name.text.isEmpty() || formModel.parent.text.isEmpty() || formModel.phoneNumber.text.isEmpty() ||
                            formModel.address.text.isEmpty() || formModel.dob.text.isEmpty() || formModel.aadharNo.text.isEmpty()) {
                        formModel.uploadFailure=true
                        }
                }) {
                    Text(text = "Pay")
                }
            }
        }
        if (paymentInitiated) {
            AlertDialog(onDismissRequest = { paymentInitiated = false },
                title = { Text(text = "Payment") },
                text = {
                    if (formModel.isStudent) {
                        Text(text = " Pay $discountAmount")
                    } else {
                        Text(text = "Pay $amount")
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (formModel.isStudent) {
                            uploadImageToFirebase(formModel.selectedImageUri!!)
                            val studentData = StudentData(
                                name = formModel.name.text,
                                parent = formModel.parent.text,
                                phoneNumber = formModel.phoneNumber.text,
                                address = formModel.address.text,
                                dob = formModel.dob.text,
                                gender = formModel.selectedGender.value,
                                aadharNo = formModel.aadharNo.text,
                                collegeName = formModel.collegeName.text,
                                collegeAddress = formModel.collegeAddress.text,
                                instituteCode = formModel.instituteCode.text,
                                rollNo = formModel.rollNo.text,
                                validity = formModel.selectedValidity.value,
                                type = formModel.selectedPassType.value,
                                amount= discountAmount,
                                expiryDate = validTill
                            )
                            formModel.uploadSuccess =
                                uploadStudentData(studentData) { success ->
                                    if (success) {
                                        formModel.uploadSuccess=true
                                    } else {
                                        formModel.uploadFailure = true
                                    }                                }
                        } else {
                            uploadImageToFirebase(formModel.selectedImageUri!!)
                            val userData = UserData(
                                name = formModel.name.text,
                                parent = formModel.parent.text,
                                phoneNumber = formModel.phoneNumber.text,
                                address = formModel.address.text,
                                dob = formModel.dob.text,
                                gender = formModel.selectedGender.value,
                                aadharNo = formModel.aadharNo.text,
                                validity = formModel.selectedValidity.value,
                                type = formModel.selectedPassType.value,
                                amount= amount,
                                expiryDate = validTill
                            )
                            formModel.uploadSuccess = uploadUserData(userData) { success ->
                                if (success) {
                                    formModel.uploadSuccess=true

                                } else {
                                    formModel.uploadFailure = true
                                }                            }
                        }
                        paymentInitiated = false
                        paymentSuccess = true
                    }) {
                        Text(text = "Confirm Payment")
                    }
                },
                dismissButton = {
                    Button(onClick = { paymentInitiated=false }) {
                        Text(text = "Cancel")
                    }
                })
        }
            if (paymentSuccess) {
                AlertDialog(onDismissRequest = { paymentSuccess = false },
                    title = { Text(text = "Payment Success") },
                    text= {
                        Column(modifier = Modifier
                            .padding(8.dp)) {
                            Text(text = "Paid Successfully")
                            Spacer(Modifier.height(8.dp))
                            Text(text = "Your Pass will be ready in a few minutes")
                        }
                    },
                    confirmButton = {
                        Button(onClick = { paymentSuccess = false }) {
                            Text(text = "ok")
                        }
                    })
            formModel.name = TextFieldValue("")
            formModel.parent = TextFieldValue("")
            formModel.email = TextFieldValue("")
            formModel.phoneNumber = TextFieldValue("")
            formModel.address = TextFieldValue("")
            formModel.dob = TextFieldValue("")
            formModel.aadharNo = TextFieldValue("")
            formModel.collegeName = TextFieldValue("")
            formModel.collegeAddress = TextFieldValue("")
            formModel.instituteCode = TextFieldValue("")
            formModel.rollNo = TextFieldValue("")
        } else if (formModel.uploadFailure) {
            AlertDialog(onDismissRequest = { formModel.uploadFailure = false },
                title = { Text(text = "Failure") },
                text = {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Text(text = "There might be some Error !")
                        Spacer(Modifier.height(8.dp))
                        Text(text = "Check Your details and try again")
                    }
                },
                confirmButton = {
                    Button(onClick = { formModel.uploadFailure = false }) {
                        Text(text = "Ok")
                    }
                })
        }

    }
}




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ApplicationForm() {
    Scaffold(
        modifier = Modifier.padding(20.dp)
            ,
        content = {
            Column(modifier=Modifier
                .fillMaxSize()
            ) {
                ApplicationFormContents()
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ApplicationPreview() {
    ApplicationForm()
}

