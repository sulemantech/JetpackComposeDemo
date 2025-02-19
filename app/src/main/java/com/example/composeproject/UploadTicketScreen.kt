package com.example.composeproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun UploadTicketScreen(navController: NavController) {
    val context = LocalContext.current
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    var selectedFileName by remember { mutableStateOf<String?>(null) }
    // Camera launcher
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                selectedImageBitmap = bitmap
                selectedFileUri = null
                selectedFileName = null
            }
        }

    // Gallery launcher
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedFileUri = it
                selectedFileName = getFileName(context, it)
                selectedImageBitmap = loadBitmapFromUri(context, it)
            }
        }

    // Document picker
    val documentLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedFileUri = it
                selectedFileName = getFileName(context, it)
                selectedImageBitmap = null
            }
        }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(790.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.DarkGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Abbrechen",
                    color = Color(0xFF10E0D7),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .clickable { navController.popBackStack() }
                )

                Spacer(modifier = Modifier.height(27.dp))

                Text(
                    text = "Ticketupload",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Wähle ein Foto deines Tickets, mache ein neues oder lade ein PDF des Tickets hoch.",
                    color = Color.White,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(58.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Divider(
                        color = Color(0xFF00796B),
                        thickness = 1.dp,
                        modifier = Modifier.width(140.dp)
                    )
                }

                Spacer(modifier = Modifier.height(34.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        selectedImageBitmap != null -> {
                            Image(
                                bitmap = selectedImageBitmap!!.asImageBitmap(),
                                contentDescription = "Selected Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        selectedFileUri != null -> {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_document),
                                    contentDescription = "Document Icon",
                                    tint = Color.White,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Selected File: $selectedFileName",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        else -> {
                            Image(
                                painter = painterResource(id = R.drawable.upload_ticket),
                                contentDescription = "Upload Ticket Icon",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(147.dp, 39.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    UploadOptionButton("Kamera", R.drawable.ic_camera) { cameraLauncher.launch() }
                    UploadOptionButton("Bild", R.drawable.ic_gallery) { galleryLauncher.launch("image/*") }
                    UploadOptionButton("Dokument", R.drawable.ic_document) { documentLauncher.launch(
                        arrayOf("*/*").toString()
                    ) }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Hochladen", color = Color.Black, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun UploadOptionButton(text: String, iconRes: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(115.dp)
            .height(40.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color.White)
        }
    }
}

fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Get File Name from URI
fun getFileName(context: Context, uri: Uri): String {
    var name = "Unknown File"
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cursor.moveToFirst() && nameIndex >= 0) {
            name = cursor.getString(nameIndex)
        }
    }
    return name
}
@Preview
@Composable
fun PreviewUploadTicketBottomSheet() {
    UploadTicketScreen(navController = NavController(LocalContext.current))
}
