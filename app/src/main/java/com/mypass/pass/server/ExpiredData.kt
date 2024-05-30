package com.mypass.pass.server

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun expiredStudentData(dataList: List<StudentData>,onDelete:(String)->Unit) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    userId ?: return
    val database = Firebase.database
    val storage = Firebase.storage
    val imageRef = userId.let { storage.getReference().child(userId).child("images") }
    val myRef =
        userId.let { database.getReference().child("users").child(it).child("StudentPass") }

    val currentDate = Date()
    runBlocking {
        dataList.forEach { data ->
            val expiredDate = data.expiryDate

            if (expiredDate.isNotEmpty()) {
                val isExpired = async(Dispatchers.Default) {
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                    val parsedExpiredDate = dateFormat.parse(expiredDate)
                    currentDate.after(parsedExpiredDate)
                }.await()
                if (isExpired) {
                    myRef.removeValue().await()
                    imageRef.delete().await()
                    onDelete("Pass Expired apply a new One")
                }
            }
        }
    }
}
fun expiredUserData(dataList: List<UserData>,onDelete:(String)->Unit) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    userId ?: return
    val database = Firebase.database
    val storage = Firebase.storage
    val imageRef = userId.let { storage.getReference().child(userId).child("images") }
    val myRef =
        userId.let { database.getReference().child("users").child(it).child("GeneralPass") }

    val currentDate = Date()
    runBlocking {
        dataList.forEach { data ->
            val expiredDate = data.expiryDate

            if (expiredDate.isNotEmpty()) {
                val isExpired = async(Dispatchers.Default) {
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                    val parsedExpiredDate = dateFormat.parse(expiredDate)
                    currentDate.after(parsedExpiredDate)
                }.await()

                if (isExpired) {
                    myRef.removeValue().await()
                    imageRef.delete().await()
                    onDelete("Pass Expired apply a new One")
                }
            }
        }
    }
}
