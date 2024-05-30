package com.mypass.pass.server


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


fun uploadStudentData(studentData: StudentData, onComplete:(Boolean)->Unit): Boolean {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val database = Firebase.database
    val myRef = userId?.let { database.getReference().child("users").child(it).child("StudentPass") }
        myRef?.get()?.addOnSuccessListener { snapshot ->
            if(snapshot.exists()){
                Log.d("Data Exists:","Data already exists in GeneralPass, skipping upload")
                onComplete(true)
            }
            else{
                myRef.push().setValue(studentData)
                    .addOnSuccessListener {
                        Log.d("Success : ","Successfully uploaded student data")
                        onComplete(true)
                    }
                    .addOnSuccessListener {
                        Log.e("Failure : ","Failed to upload student data")
                        onComplete(true)
                    }
            }
        }
    return true
}

fun uploadUserData(userData: UserData, onComplete:(Boolean)->Unit): Boolean {
    val database = Firebase.database
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val myRef = userId?.let { database.getReference().child("users").child(it).child("GeneralPass") }
    myRef?.get()?.addOnSuccessListener { snapshot ->
        if(snapshot.exists()){
            Log.d("Data Exists:","Data already exists in GeneralPass, skipping upload")
            onComplete(true)
        }
        else{
            myRef.push().setValue(userData)
                .addOnSuccessListener {
                    Log.d("Success : ","Successfully uploaded student data")
                    onComplete(true)
                }
                .addOnFailureListener {
                    Log.e("Failure : ","Failed to upload student data")
                    onComplete(true)
                }
        }
    }
    return true

}
