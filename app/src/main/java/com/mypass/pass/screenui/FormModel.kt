    package com.mypass.pass.screenui

    import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

    class FormModel: ViewModel() {
        var name by mutableStateOf(TextFieldValue(""))
        var parent by mutableStateOf(TextFieldValue(""))
        var email by mutableStateOf(TextFieldValue(""))
        var phoneNumber by mutableStateOf(TextFieldValue(""))
        var address by mutableStateOf(TextFieldValue(""))
        var dob by mutableStateOf(TextFieldValue(""))
        var aadharNo by mutableStateOf(TextFieldValue(""))
        var isStudent by mutableStateOf(false)
        var collegeName by mutableStateOf(TextFieldValue(""))
        var collegeAddress by mutableStateOf(TextFieldValue(""))
        var instituteCode by mutableStateOf(TextFieldValue(""))
        var rollNo by mutableStateOf(TextFieldValue(""))
        var uploadSuccess by mutableStateOf(false)
        var uploadFailure by mutableStateOf(false)
        var selectedImageUri by mutableStateOf(null as Uri?)
        val selectedPassType = mutableStateOf("")
        val selectedValidity = mutableStateOf("")
        var nameHasError by mutableStateOf(false)
        var nameError by mutableStateOf("")
        var phoneHasError by mutableStateOf(false)
        var phoneError by mutableStateOf("")
        var aadharHasError by mutableStateOf(false)
        var aadharError by mutableStateOf("")
        var rollHasError by mutableStateOf(false)
        var rollError by mutableStateOf("")
        var dobHasError by mutableStateOf(false)
        var dobError by mutableStateOf("")
        var parentHasError by mutableStateOf(false)
        var parentError by mutableStateOf("")
        var addressHasError by mutableStateOf(false)
        private var addressError by mutableStateOf("")
        var clgNameHasError by mutableStateOf(false)
        var clgNameError by mutableStateOf("")
        private var clgaddressHasError by mutableStateOf(false)
        private var clgaddressError by mutableStateOf("")
        var instHasError by mutableStateOf(false)
        var instError by mutableStateOf("")
        private val typeOptions = listOf(
            "Ordinary", "Metro-express"
        )
        private val selectedOption2 = mutableStateOf<String?>(null)
        private val passOptions = listOf(
            "Day-Pass", "Monthly", "Quarterly", "Half-Yearly", "Yearly"
        )
        private val selectedOption = mutableStateOf<String?>(null)
        private val  genderOptions= listOf("Male", "Female")
        val selectedGender = mutableStateOf("")
        private val  genderselected = mutableStateOf<String?>(null)

        @Composable
        fun ApplicationFormField(
            label: String,
            value: TextFieldValue,
            onValueChange: (TextFieldValue) -> Unit,
            isError: Boolean
        ) {
            Column(
                modifier = Modifier,
                // .padding(6.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = label, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = value, onValueChange = onValueChange,
                    isError = isError,
                    maxLines = 1,
                    modifier = Modifier.width(180.dp)
                )
            }
        }

        @Composable
        fun Validity(onValiditySelected: (String) -> Unit) {
            var expanded by remember { mutableStateOf(false) }
            Card(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier) {
                    TextButton(onClick = { expanded = !expanded }) {
                        Text(text = "Validity :")
                    }
                    AnimatedVisibility(expanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            passOptions.forEach { text ->
                                Text(
                                    text = text,
                                    modifier = Modifier.padding(16.dp)
                                )
                                RadioButton(selected = selectedOption.value == text,
                                    onClick = {
                                        selectedOption.value = text
                                        onValiditySelected(text)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        @Composable
        fun PassType(onPassTypeSelected: (String) -> Unit) {
            var expanded by remember { mutableStateOf(false) }
            Card(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier) {
                    TextButton(onClick = { expanded = !expanded }) {
                        Text(text = "Pass Type:")
                    }
                    AnimatedVisibility(expanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            typeOptions.forEach { text ->
                                Text(
                                    text = text,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    RadioButton(selected = selectedOption2.value == text,
                                        onClick = {
                                            selectedOption2.value = text
                                            onPassTypeSelected(text)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        @Composable
        fun SelectGender(onGenderSelected:(String)-> Unit) {
            var expanded by remember { mutableStateOf(false) }
            Card(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier) {
                    TextButton(onClick = { expanded = !expanded }) {
                        Text(text = "Gender :")
                    }
                    AnimatedVisibility(expanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            genderOptions.forEach { text ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    RadioButton(selected = genderselected.value == text,
                                        onClick = {
                                            genderselected.value = text
                                            onGenderSelected(text)
                                            expanded = false
                                        }
                                    )
                                    Text(
                                        text = text,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
        @Composable
        fun validTill(): String {
            val currentDate = LocalDateTime.now()
            val validTill = when (selectedValidity.value) {
                "Day-Pass" -> currentDate.plusDays(1)
                "Monthly" -> currentDate.plusMonths(1)
                "Quarterly" -> currentDate.plusMonths(3)
                "Half-Yearly" -> currentDate.plusMonths(6)
                "Yearly" -> currentDate.plusMonths(12)
                else -> currentDate
            }
            val formattedDate = validTill.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
            Card(modifier = Modifier.padding(5.dp)) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Valid Till : $formattedDate", modifier = Modifier
                            .padding(16.dp),
                    )
                }
            }
            return formattedDate.toString()
        }

        fun passAmount(validity: String, passType: String): Float {
            return when (validity to passType) {
                "Day-Pass" to "Ordinary" -> 100f
                "Day-Pass" to "Metro-express" -> 120f
                "Monthly" to "Ordinary" -> 500f
                "Monthly" to "Metro-express" -> 1200f
                "Quarterly" to "Ordinary" -> 800f
                "Quarterly" to "Metro-express" -> 1500f
                "Half-Yearly" to "Ordinary" -> 1600f
                "Half-Yearly" to "Metro-express" -> 2100f
                "Yearly" to "Ordinary" -> 2800f
                "Yearly" to "Metro-express" -> 3300f
                else -> 0f
            }
        }

        fun formValidation() {
            when {
                name.text.isEmpty() -> {
                    nameHasError = true
                    nameError = "This Field is Required"
                }

                name.text.length < 3 -> {
                    nameHasError = true
                    nameError = "This Field should atleast be 3 characters long"
                }
            }
            when {
                parent.text.isEmpty() -> {
                    parentHasError = true
                    parentError = "This Field is Required"
                }

                parent.text.length < 3 -> {
                    parentHasError = true
                    parentError = "This Field should atleast be 3 characters long"
                }

                parent.text.length < 3 -> {
                    addressHasError = true
                    addressError = "This Field should atleast be 3 characters long"
                }
            }
            when {
                address.text.isEmpty() -> {
                    addressHasError = true
                    addressError = "This Field is Required"
                }
            }
            when {
                collegeName.text.isEmpty() -> {
                    clgNameHasError = true
                    clgNameError = "This Field is Required"
                }

                collegeName.text.length < 3 -> {
                    clgNameHasError = true
                    clgNameError = "This Field should atleast be 3 characters long"
                }

                collegeAddress.text.isEmpty() -> {
                    clgaddressHasError = true
                    clgaddressError = "This Field is Required"
                }
            }
            when {
                instituteCode.text.isEmpty() -> {
                    instHasError = true
                    instError = "This Field is Required"
                }

                instituteCode.text.isEmpty() -> {
                    instHasError = true
                    instError = "This Field is Required"
                }
            }
            when {
                aadharNo.text.isEmpty() -> {
                    aadharHasError = true
                    aadharError = "This Field is Required"
                }

                !aadharNo.text.all { it.isDigit() } -> {
                    aadharHasError = true
                    aadharError = "Aadhar Number should contain digits"
                }

                aadharNo.text.length != 12 -> {
                    aadharHasError = true
                    aadharError = "Aadhar Number should be 12 digits long"
                }
            }
            when {
                !rollNo.text.all { it.isDigit() } -> {
                    rollHasError = true
                    rollError = "Roll Number should contain digits"
                }

                rollNo.text.isEmpty() -> {
                    rollHasError = true
                    rollError = "This Field is Required"
                }
            }
            when {

                dob.text.isEmpty() -> {
                    dobHasError = true
                    dobError = "This Field is Required"
                }

                !dob.text.all { it.isDigit() } -> {
                    dobHasError = true
                    dobError = "Please Enter a valid Date"
                }
            }
            when {
                phoneNumber.text.isEmpty() -> {
                    phoneHasError = true
                    phoneError = "This Field is Required"
                }

                !phoneNumber.text.all { it.isDigit() } -> {
                    phoneHasError = true
                    phoneError = "Phone Number should contain only digits"
                }

                phoneNumber.text.length != 10 -> {
                    phoneHasError = true
                    phoneError = "Phone Number should be 10 digits long"
                }
            }

        }

    }