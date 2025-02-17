package com.example.composeproject

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.composeproject.database.TaskEntity
import com.example.composeproject.ui.theme.ComposeProjectTheme
import com.example.composeproject.viewmodel.ToDoViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        val savedTheme = runBlocking { ThemePreference.loadTheme(context).first() }

        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(savedTheme) }

            ComposeProjectTheme(darkTheme = isDarkTheme) {
                ToDoAppNavigation(
                    viewModel = viewModel(),
                    isDarkTheme = isDarkTheme,
                    onThemeChange = {
                        isDarkTheme = !isDarkTheme
                        lifecycleScope.launch {
                            ThemePreference.saveTheme(context, isDarkTheme)
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun SplashScreen(navController: NavController) {



    // Delay navigation for 2 seconds
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "App Icon",
                tint = Color.Blue,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "To-Do App",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

// 🗺 Navigation Setup
@Composable
fun ToDoAppNavigation(viewModel: ToDoViewModel, isDarkTheme: Boolean, onThemeChange: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("home") {
            ToDoScreen(navController, viewModel, isDarkTheme, onThemeChange)
        }
        composable(
            "details/{title}/{description}/{date}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val date = backStackEntry.arguments?.getString("date") ?: ""
            TaskDetailsScreen(title, description, date, navController)
        }
    }
}

// To-Do List Screen
@Composable
fun ToDoScreen(navController: NavController, viewModel: ToDoViewModel, isDarkTheme: Boolean,
               onThemeChange: () -> Unit) {
    val tasks by viewModel.tasks.collectAsState()
    var isDarkTheme by remember { mutableStateOf(false) }


    var taskTitle by remember { mutableStateOf(TextFieldValue("")) }
    var taskDescription by remember { mutableStateOf(TextFieldValue("")) }
    var taskDate by remember { mutableStateOf(TextFieldValue("")) }
    var reminderTime by remember { mutableStateOf(0L) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "To Do Task", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onThemeChange) {
            Text(if (isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode")
        }

        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            label = { Text("Enter Task Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            label = { Text("Enter Task Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = taskDate,
            onValueChange = { taskDate = it },
            label = { Text("Enter Task Date") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Button to open time picker
        Button(
            onClick = {
                showTimePicker(context) { minutes ->
                    reminderTime = minutes
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray, contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Set Reminder Time")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (taskTitle.text.isNotEmpty() && taskDescription.text.isNotEmpty() && taskDate.text.isNotEmpty()) {
                    viewModel.addTask(taskTitle.text, taskDescription.text, taskDate.text)

                    // Schedule reminder if a valid time is set
                    if (reminderTime > 0) {
                        viewModel.scheduleTaskReminder(taskTitle.text, reminderTime)
                        Toast.makeText(context, "Reminder set!", Toast.LENGTH_SHORT).show()
                    }

                    // Clear fields
                    taskTitle = TextFieldValue("")
                    taskDescription = TextFieldValue("")
                    taskDate = TextFieldValue("")
                } else {
                    Toast.makeText(context, "Please enter task details", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onClick = {
                        val encodedTitle = URLEncoder.encode(task.title, StandardCharsets.UTF_8.toString())
                        val encodedDescription = URLEncoder.encode(task.description, StandardCharsets.UTF_8.toString())
                        val encodedDate = URLEncoder.encode(task.date, StandardCharsets.UTF_8.toString())

                        navController.navigate("details/$encodedTitle/$encodedDescription/$encodedDate")
                    },
                    onRemove = { viewModel.removeTask(task) },
                    onEdit = { updatedTask -> viewModel.updateTask(updatedTask) }
                )
            }
        }
    }
}

// Time Picker function
fun showTimePicker(context: Context, onTimeSelected: (Long) -> Unit) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            val totalMinutes = ((selectedHour - hour) * 60) + (selectedMinute - minute)
            if (totalMinutes > 0) {
                onTimeSelected(totalMinutes.toLong())
            } else {
                Toast.makeText(context, "Invalid time selected!", Toast.LENGTH_SHORT).show()
            }
        },
        hour,
        minute,
        true
    ).show()
}

@Composable
fun TaskItem(task: TaskEntity, onClick: () -> Unit, onRemove: () -> Unit, onEdit: (TaskEntity) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    var newTitle by remember { mutableStateOf(TextFieldValue(task.title)) }
    var newDescription by remember { mutableStateOf(task.description?.let { TextFieldValue(it) }) }
    var newDate by remember { mutableStateOf(TextFieldValue(task.date)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, style = MaterialTheme.typography.titleLarge)
                task.description?.let { Text(text = it, style = MaterialTheme.typography.bodyMedium) }
                Text(text = task.date, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete Task", tint = Color.Red)
            }
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Task", tint = Color.Black)
            }
        }
    }

    // Show Edit Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Edit Task") },
            text = {
                Column {
                    TextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("Title") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    newDescription?.let {
                        TextField(
                            value = it,
                            onValueChange = { newDescription = it },
                            label = { Text("Description") }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = newDate,
                        onValueChange = { newDate = it },
                        label = { Text("Date") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updatedTask = task.copy(
                            title = newTitle.text,
                            description = newDescription?.text,
                            date = newDate.text
                        )
                        onEdit(updatedTask)
                        showDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false })
                {
                    Text("Cancel")
                }
            }
        )
    }
    // Show Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Task") },
            text = { Text("Are you sure you want to delete this task?") },
            confirmButton = {
                Button(
                    onClick = {
                        onRemove()
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

// 📄 Task Details Screen
@Composable
fun TaskDetailsScreen(title: String, description: String, date: String, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Task Details",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(text = "Title: $title", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Text(text = "Date: $date", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Text(text = "Description: $description", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text("Back to To-Do List")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewToDoApp() {
    val fakeViewModel: ToDoViewModel = viewModel(factory = FakeToDoViewModelFactory())
    ToDoAppNavigation(fakeViewModel, isDarkTheme = false, onThemeChange = {})
}


