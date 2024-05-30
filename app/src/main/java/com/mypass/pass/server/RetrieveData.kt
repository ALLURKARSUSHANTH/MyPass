package com.mypass.pass.server

//noinspection SuspiciousImport
import android.R
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class UserDataViewModel : ViewModel() {
    var studentDataList by mutableStateOf<List<StudentData>>(emptyList())
    var userDataList by mutableStateOf<List<UserData>>(emptyList())
    var image by mutableStateOf<Uri?>(null)

    fun imageRetrieve() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val storage = Firebase.storage
        userId?.let { uid ->
            val imageRef = storage.getReference().child(uid).child("images")
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                image = uri
            }.addOnFailureListener {
            }
        }
    }

    fun retrieveUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val database = Firebase.database
        val myRef =
            userId?.let { database.getReference().child("users").child(it).child("GeneralPass") }
        myRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<UserData>()
                for (dataSnapshot in snapshot.children) {
                    val userData = dataSnapshot.getValue(UserData::class.java)
                    userData?.let {
                        dataList.add(userData)
                    }
                }
                userDataList = dataList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Database Error :", "Database error occurred: ${error.message}")
            }
        })
    }

    fun retrieveStudentData(onDataReceived: (List<StudentData>) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val database = Firebase.database
        val myRef =
            userId?.let { database.getReference().child("users").child(it).child("StudentPass") }

        myRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<StudentData>()
                for (dataSnapshot in snapshot.children) {
                    val studentData = dataSnapshot.getValue(StudentData::class.java)
                    studentData?.let {
                        dataList.add(studentData)
                    }
                }
                onDataReceived(dataList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Database Error :", "Database error occurred: ${error.message}")
            }
        })
    }

    @Composable
    fun RetrievedImage(imageUri: Uri?) {
        Column(
            modifier = Modifier
                .padding(top = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = imageUri)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        placeholder(R.drawable.ic_menu_camera)
                        error(com.mypass.pass.R.drawable.ic_launcher_foreground)
                    }).build()
            )
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black)
                    .size(100.dp),
                contentScale = ContentScale.Crop,
                painter = painter,
                contentDescription = null
            )
        }
    }
}



