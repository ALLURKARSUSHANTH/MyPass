package com.mypass.pass.server
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mypass.pass.screenui.FormModel

@SuppressLint("ObsoleteSdkInt")
@Composable
fun ImageUploadFunction(formModel: FormModel, onImageSelected: (Uri) -> Unit) {
    val context = LocalContext.current
    val getContent =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                formModel.selectedImageUri = uri
                onImageSelected(uri)
            }
        }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (formModel.selectedImageUri != null) {
            val bitmap: Bitmap? = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    formModel.selectedImageUri
                )
            } else {
                val source =
                    ImageDecoder.createSource(context.contentResolver, formModel.selectedImageUri!!)
                ImageDecoder.decodeBitmap(source)
            }
            bitmap?.asImageBitmap()?.let { imageBitmap ->
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black)
                        .size(100.dp),
                    contentScale = ContentScale.Crop,
                    bitmap = imageBitmap,
                    contentDescription = null
                )
            }
        } else {
            Image(painter = painterResource(id = android.R.drawable.ic_menu_camera),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black)
                    .size(100.dp)
                    .padding(10.dp)
                    .clickable { getContent.launch("image/*") }
            )
        }
    }
}
    fun uploadImageToFirebase(imageUri: Uri) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val storageRef = Firebase.storage.reference
        val imagesRef = uid?.let { storageRef.child(it).child("images/") }
        imagesRef?.putFile(imageUri)?.addOnSuccessListener {
            Log.d("Success:","Uploaded Image Successfully")
        }
            ?.addOnFailureListener { e->
             Log.e("Firebase Error : ", "Error uploading image ${e.message}")
        }
    }

@Preview(showBackground = true)
@Composable
fun ImageUploadScreenPreview() {
    Surface {
        ImageUploadFunction(formModel = FormModel()) {

        }
    }
}
