package com.example.hydrationtracker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Context
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import android.speech.tts.TextToSpeech
import java.util.Locale
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.ui.platform.LocalContext
import android.net.Uri

class MainActivity : ComponentActivity() {
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        scheduleReminders()

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US
            }
        }

        val database = TrackerDatabase.getInstance(this)
        val viewModel = ViewModelProvider(this, TrackerViewModelFactory(database.logDao())).get(TrackerViewModel::class.java)

        setContent {
            MaterialTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    TestContent(viewModel, tts)
                }
            }
        }
    }


    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("tracker_channel", "Tracker Reminders", NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    fun scheduleReminders() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val waterTimes = listOf(6, 10, 14, 18, 22)
        waterTimes.forEachIndexed { index, hour ->
            scheduleReminder(alarmManager, hour, 0, "Water Reminder", "Time to drink water!", index)
        }
        scheduleReminder(alarmManager, 7, 0, "Breakfast", "Time for breakfast!", 100)
        scheduleReminder(alarmManager, 12, 0, "Lunch", "Time for lunch!", 101)
        scheduleReminder(alarmManager, 19, 0, "Dinner", "Time for dinner!", 102)
    }

    private fun scheduleReminder(alarmManager: AlarmManager, hour: Int, minute: Int, title: String, message: String, requestCode: Int) {
        val intent = Intent(this, ReminderReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
        }
        val pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val calendar = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, hour)
            set(java.util.Calendar.MINUTE, minute)
            set(java.util.Calendar.SECOND, 0)
        }
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}

@Composable
fun TestContent(viewModel: TrackerViewModel, tts: TextToSpeech?) {
    var waterAmount by remember { mutableStateOf("") }
    var mealAmount by remember { mutableStateOf("") }
    var tab by remember { mutableStateOf(0) }
    var editingId by remember { mutableStateOf<Int?>(null) }
    var editAmount by remember { mutableStateOf("") }
    var statsPeriod by remember { mutableStateOf("daily") }
    val logs by viewModel.todayLogs.collectAsState(emptyList())

    Scaffold(
        bottomBar = {10010
            BottomAppBar {
                Button(onClick = { tab = 0 }, modifier = Modifier.weight(1f)) { Text("Log") }
                Button(onClick = { tab = 1 }, modifier = Modifier.weight(1f)) { Text("Stats") }
                Button(onClick = { tab = 2 }, modifier = Modifier.weight(1f)) { Text("Guide") }
                Button(onClick = { tab = 3 }, modifier = Modifier.weight(1f)) { Text("Remind") }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
            when (tab) {
                0 -> LogTab(viewModel, logs, waterAmount, mealAmount, editingId, editAmount, tts, { waterAmount = it }, { mealAmount = it }, { editingId = it }, { editAmount = it })
                1 -> StatsTab(logs, statsPeriod, { statsPeriod = it })
                2 -> GuideTab()
                3 -> ReminderTab()
            }
        }
    }
}

@Composable
fun LogTab(viewModel: TrackerViewModel, logs: List<LogEntry>, waterAmount: String, mealAmount: String, editingId: Int?, editAmount: String, tts: TextToSpeech?, onWaterChange: (String) -> Unit, onMealChange: (String) -> Unit, onEditingIdChange: (Int?) -> Unit, onEditAmountChange: (String) -> Unit) {
    val context = LocalContext.current

    Text("Log Your Intake", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(16.dp))
    Text("Water (ml)")
    TextField(value = waterAmount, onValueChange = onWaterChange, modifier = Modifier.fillMaxWidth())
    Button(
        onClick = {
            if (waterAmount.isBlank()) {
                Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            } else {
                waterAmount.toIntOrNull()?.let {
                    if (it <= 0) {
                        Toast.makeText(context, "Please enter a number greater than 0", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.addLog("water", it)
                        tts?.speak("Added $it milliliters of water", TextToSpeech.QUEUE_FLUSH, null)
                        onWaterChange("")
                        Toast.makeText(context, "Water logged successfully", Toast.LENGTH_SHORT).show()
                    }
                } ?: run {
                    Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) { Text("Add Water") }

    Spacer(modifier = Modifier.height(16.dp))
    Text("Meal (kcal)")
    TextField(value = mealAmount, onValueChange = onMealChange, modifier = Modifier.fillMaxWidth())
    Button(
        onClick = {
            if (mealAmount.isBlank()) {
                Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            } else {
                mealAmount.toIntOrNull()?.let {
                    if (it <= 0) {
                        Toast.makeText(context, "Please enter a number greater than 0", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.addLog("meal", it)
                        tts?.speak("Added $it kilocalories", TextToSpeech.QUEUE_FLUSH, null)
                        onMealChange("")
                        Toast.makeText(context, "Meal logged successfully", Toast.LENGTH_SHORT).show()
                    }
                } ?: run {
                    Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) { Text("Add Meal") }

    Spacer(modifier = Modifier.height(16.dp))
    Text("Today's Entries:")
    if (editingId != null) {
        Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Edit Entry")
                TextField(value = editAmount, onValueChange = onEditAmountChange, modifier = Modifier.fillMaxWidth())
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            if (editAmount.isBlank()) {
                                Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                            } else {
                                editAmount.toIntOrNull()?.let {
                                    if (it <= 0) {
                                        Toast.makeText(context, "Please enter a number greater than 0", Toast.LENGTH_SHORT).show()
                                    } else {
                                        viewModel.deleteLog(editingId)
                                        viewModel.addLog("", it)
                                        onEditingIdChange(null)
                                        onEditAmountChange("")
                                        Toast.makeText(context, "Entry updated successfully", Toast.LENGTH_SHORT).show()
                                    }
                                } ?: run {
                                    Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) { Text("Save") }
                    Button(onClick = { onEditingIdChange(null); onEditAmountChange("") }, modifier = Modifier.weight(1f)) { Text("Cancel") }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    logs.forEach { log ->
        Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${log.type}: ${log.amount}")
                Row {
                    Button(onClick = { onEditingIdChange(log.id); onEditAmountChange(log.amount.toString()) }) { Text("Edit") }
                    Button(onClick = { viewModel.deleteLog(log.id); Toast.makeText(context, "Entry deleted", Toast.LENGTH_SHORT).show() }) { Text("Del") }
                }
            }
        }
    }
}
/*
@Composable
fun LogTab(viewModel: TrackerViewModel, logs: List<LogEntry>, waterAmount: String, mealAmount: String, editingId: Int?, editAmount: String, tts: TextToSpeech?, onWaterChange: (String) -> Unit, onMealChange: (String) -> Unit, onEditingIdChange: (Int?) -> Unit, onEditAmountChange: (String) -> Unit) {
    Text("Log Your Intake", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(16.dp))
    Text("Water (ml)")
    TextField(value = waterAmount, onValueChange = onWaterChange, modifier = Modifier.fillMaxWidth())
    Button(onClick = { waterAmount.toIntOrNull()?.let { viewModel.addLog("water", it); tts?.speak("Added $it ml water", TextToSpeech.QUEUE_FLUSH, null); onWaterChange("") } }, modifier = Modifier.fillMaxWidth()) { Text("Add Water") }
    Spacer(modifier = Modifier.height(16.dp))
    Text("Meal (kcal)")
    TextField(value = mealAmount, onValueChange = onMealChange, modifier = Modifier.fillMaxWidth())
    Button(onClick = { mealAmount.toIntOrNull()?.let { viewModel.addLog("meal", it); tts?.speak("Added $it kcal", TextToSpeech.QUEUE_FLUSH, null); onMealChange("") } }, modifier = Modifier.fillMaxWidth()) { Text("Add Meal") }
    Spacer(modifier = Modifier.height(16.dp))
    Text("Today's Entries:")
    if (editingId != null) {
        Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Edit Entry")
                TextField(value = editAmount, onValueChange = onEditAmountChange, modifier = Modifier.fillMaxWidth())
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { editAmount.toIntOrNull()?.let { viewModel.deleteLog(editingId); viewModel.addLog("", it); onEditingIdChange(null); onEditAmountChange("") } }, modifier = Modifier.weight(1f)) { Text("Save") }
                    Button(onClick = { onEditingIdChange(null); onEditAmountChange("") }, modifier = Modifier.weight(1f)) { Text("Cancel") }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    logs.forEach { log ->
        Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${log.type}: ${log.amount}")
                Row {
                    Button(onClick = { onEditingIdChange(log.id); onEditAmountChange(log.amount.toString()) }) { Text("Edit") }
                    Button(onClick = { viewModel.deleteLog(log.id) }) { Text("Del") }
                }
            }
        }
    }
}
*/

@Composable
fun StatsTab(logs: List<LogEntry>, statsPeriod: String, onPeriodChange: (String) -> Unit) {
    Text("Statistics", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { onPeriodChange("daily") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = if (statsPeriod == "daily") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)) { Text("Daily") }
        Button(onClick = { onPeriodChange("weekly") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = if (statsPeriod == "weekly") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)) { Text("Weekly") }
        Button(onClick = { onPeriodChange("monthly") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = if (statsPeriod == "monthly") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)) { Text("Monthly") }
    }
    Spacer(modifier = Modifier.height(16.dp))

    val water = logs.filter { it.type == "water" }.sumOf { it.amount }
    val meals = logs.filter { it.type == "meal" }.sumOf { it.amount }
    val waterBarHeight = ((water.toFloat() / 3000) * 150).dp.coerceAtMost(150.dp)
    val mealBarHeight = ((meals.toFloat() / 3000) * 150).dp.coerceAtMost(150.dp)

    // Side by side charts
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        // Water Chart
        Card(modifier = Modifier.weight(1f).padding(8.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Water Intake", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.height(150.dp), verticalArrangement = Arrangement.Bottom) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(waterBarHeight)
                            .background(Color(0xFF4CAF50))
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("$water ml", modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally), style = MaterialTheme.typography.labelSmall)
            }
        }

        // Meal Chart
        Card(modifier = Modifier.weight(1f).padding(8.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Meal Intake", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.height(150.dp), verticalArrangement = Arrangement.Bottom) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(mealBarHeight)
                            .background(Color(0xFF2196F3))
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("$meals kcal", modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally), style = MaterialTheme.typography.labelSmall)
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Privacy Notice
    Text("Privacy Notice", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Data Protection:", style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text("This app stores your daily hydration and meal data locally on your device. Your data is stored in a local database and is never sent to external servers. You can view, edit, or delete your entries anytime through the app interface. No personal information is collected or shared.", style = MaterialTheme.typography.bodySmall)
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    Text("All Entries:", style = MaterialTheme.typography.titleMedium)
    logs.forEach { log ->
        Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${log.type}: ${log.amount}")
            }
        }
    }
}

/*
@Composable
fun StatsTab(logs: List<LogEntry>, statsPeriod: String, onPeriodChange: (String) -> Unit) {
    Text("Statistics", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { onPeriodChange("daily") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = if (statsPeriod == "daily") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)) { Text("Daily") }
        Button(onClick = { onPeriodChange("weekly") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = if (statsPeriod == "weekly") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)) { Text("Weekly") }
        Button(onClick = { onPeriodChange("monthly") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = if (statsPeriod == "monthly") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)) { Text("Monthly") }
    }
    Spacer(modifier = Modifier.height(16.dp))
    val water = logs.filter { it.type == "water" }.sumOf { it.amount }
    val meals = logs.filter { it.type == "meal" }.sumOf { it.amount }
    val waterBarHeight = ((water.toFloat() / 3000) * 200).dp.coerceAtMost(200.dp)
    val mealBarHeight = ((meals.toFloat() / 3000) * 200).dp.coerceAtMost(200.dp)
    Text("Water Intake", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth().height(200.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = androidx.compose.ui.Alignment.Bottom) {
                Box(modifier = Modifier.weight(1f).height(waterBarHeight).background(Color(0xFF4CAF50)))
                Text("$water ml", modifier = Modifier.align(androidx.compose.ui.Alignment.CenterVertically))
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text("Meal Intake", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth().height(200.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = androidx.compose.ui.Alignment.Bottom) {
                Box(modifier = Modifier.weight(1f).height(mealBarHeight).background(Color(0xFF2196F3)))
                Text("$meals kcal", modifier = Modifier.align(androidx.compose.ui.Alignment.CenterVertically))
            }
        }
    }
}
*/

@Composable
fun GuideTab() {
    Text("Daily Guidelines", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(16.dp))

    Text("Daily Water Intake (ml)", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header Row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Group (Age)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.labelSmall)
                Text("Male", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
                Text("Female", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Data Rows
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Children (4-8)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("~1200", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("~1200", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Teens (9-18)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("1400-1900", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1400-1600", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Adults (19-30)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("~2600", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("~2100", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Older Adults (50+)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("2000-2500", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1600-2100", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Text("Daily Calorie Intake (kcal)", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header Row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Group (Age)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.labelSmall)
                Text("Male", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
                Text("Female", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Data Rows
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Children (4-8)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("1400-1600", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1200-1400", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Teens (9-18)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("2200-3200", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1600-2400", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Adults (19-30)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("2400-3000", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1800-2400", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Older Adults (50+)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("2000-2800", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1600-2200", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))
    Text("Sources", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(5.dp))
    val context = LocalContext.current
    Button(onClick = { val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.waterlogic.com/en-us/info-hub/water-intake-chart/")); context.startActivity(intent) }, modifier = Modifier.fillMaxWidth()) { Text("Water Intake Chart") }
    Button(onClick = { val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dietaryguidelines.gov/")); context.startActivity(intent) }, modifier = Modifier.fillMaxWidth()) { Text("Dietary Guidelines") }
}

/*
@Composable
fun GuideTab() {
    Text("Daily Guidelines", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(16.dp))

    Text("Daily Water Intake (ml)", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header Row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Group (Age)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.labelSmall)
                Text("Male", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
                Text("Female", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Data Rows
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Children (4-8)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("~1200", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("~1200", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Teens (9-18)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("1400-1900", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1400-1600", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Adults (19-30)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("~2600", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("~2100", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Older Adults (50+)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("2000-2500", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1600-2100", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Text("Daily Calorie Intake (kcal)", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header Row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Group (Age)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.labelSmall)
                Text("Male", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
                Text("Female", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Data Rows
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Children (4-8)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("1400-1600", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1200-1400", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Teens (9-18)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("2200-3200", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1600-2400", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Adults (19-30)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("2400-3000", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1800-2400", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Older Adults (50+)", modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodySmall)
                Text("2000-2800", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text("1600-2200", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    val context = LocalContext.current
    Button(onClick = { val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.waterlogic.com/en-us/info-hub/water-intake-chart/")); context.startActivity(intent) }, modifier = Modifier.fillMaxWidth()) { Text("Water Intake Chart") }
    Button(onClick = { val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dietaryguidelines.gov/")); context.startActivity(intent) }, modifier = Modifier.fillMaxWidth()) { Text("Dietary Guidelines") }
}
 */


@Composable
fun ReminderTab() {
    Text("Reminder Schedule", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(16.dp))
    Text("Water Reminders", style = MaterialTheme.typography.titleMedium)
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("6:00 AM")
            Text("10:00 AM")
            Text("2:00 PM")
            Text("6:00 PM")
            Text("10:00 PM")
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text("Meal Reminders", style = MaterialTheme.typography.titleMedium)
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("7:00 AM - Breakfast")
            Text("12:00 PM - Lunch")
            Text("7:00 PM - Dinner")
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    val context = LocalContext.current
    Button(onClick = { Toast.makeText(context, "Test notification!", Toast.LENGTH_LONG).show() }, modifier = Modifier.fillMaxWidth()) { Text("Test Notification") }

    Spacer(modifier = Modifier.height(8.dp))
    Text("Status: Reminders are scheduled and will run daily at the specified times.", style = MaterialTheme.typography.bodySmall)


}