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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun UploadTicketScreen(navController: NavController) {
    val context = LocalContext.current
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    var selectedFileName by remember { mutableStateOf<String?>(null) }

    val isUploadEnabled = selectedImageBitmap != null || selectedFileUri != null

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                selectedImageBitmap = bitmap
                selectedFileUri = null
                selectedFileName = null
            }
        }


    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedFileUri = it
                selectedFileName = getFileName(context, it)
                selectedImageBitmap = loadBitmapFromUri(context, it)
            }
        }

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
            .background(color = colorResource(id = R.color.image_background))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(790.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(colorResource(id =R.color.btnColor ))
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
                    text = stringResource(id = R.string.Abbrechen),
                    color = Color(0xFF10E0D7),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .clickable { navController.popBackStack() }
                )

                Spacer(modifier = Modifier.height(27.dp))

                Text(
                    text = stringResource(id = R.string.Ticketupload),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (selectedImageBitmap == null && selectedFileUri == null) {
                    Text(
                        text = stringResource(id = R.string.wahle_ein_foto),
                        color = Color.White,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )
                }

                Spacer(modifier = Modifier.height(58.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                    .padding(top = 30.dp),// Margin from text to Divider
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageBitmap == null && selectedFileUri == null) {
                        Divider(
                            color = Color(0xFF00796B),
                            thickness = 1.dp,
                            modifier = Modifier.width(140.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp)) // Margin from Divider to Image Box

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorResource(id =R.color.btnColor )),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        selectedImageBitmap != null -> {
                            Image(
                                bitmap = selectedImageBitmap!!.asImageBitmap(),
                                contentDescription = stringResource(id = R.string.select_image),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        selectedFileUri != null -> {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_document),
                                    contentDescription = stringResource(id = R.string.select_Document),
                                    tint = Color.White,
                                    modifier = Modifier.size(48.dp)
                                )

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
                                contentDescription = stringResource(id = R.string.Upload_Ticket_Icon),
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
                    UploadOptionButton( text = stringResource(id = R.string.kamera), R.drawable.ic_camera, 115.dp) { cameraLauncher.launch() }
                    UploadOptionButton( text = stringResource(id = R.string.bild), R.drawable.ic_gallery, 95.dp) { galleryLauncher.launch("image/*") } // Reduced width
                    UploadOptionButton(text = stringResource(id = R.string.Dokument), R.drawable.ic_document, 130.dp) { documentLauncher.launch(arrayOf("*/*").toString()) }

            }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { if (isUploadEnabled) navController.navigate("search_screen") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isUploadEnabled)  colorResource(id = R.color.blue) else Color(0xFF00796B) // Dark Blue when enabled, same when disabled
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                ) {
                    Text(text = "Hochladen", color = Color.Black, fontSize = 16.sp)
                }



                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun UploadOptionButton(text: String, iconRes: Int, width: Dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 19.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = Color.Black,
                spotColor = Color.Black
            )
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(id =R.color.btnColor ))
            .clickable { onClick() }
            .width(width)
            .height(45.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
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

