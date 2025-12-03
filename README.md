# HydroMealApp
An Android App tracking water and meal intake

App Overview: The Hydration Tracker is a comprehensive Android application designed to help users monitor their daily water intake (in milliliters) and meal calorie (in kilocalories) consumption with intuitive logging, visual statistics, guidelines, and smart reminders with real-time visual feedback through interactive charts and comprehensive statistics. This will lead users to maintain healthy hydration and nutritional habits. 

Purpose: Water and meal tracking are essential for maintaining a balanced lifestyle. This app simplifies the process by providing:
Easy-to-use logging interface for quick entry of consumed water and meals
Visual statistics showing daily progress toward health goals
Smart reminders at optimal times throughout the day
Evidence-based guidelines from health authorities
Data persistence to ensure no information is lost
Target Users:
Fitness enthusiasts
Health-conscious individuals
Users tracking water and caloric intake
People to monitor daily water and meal intake
Everyone who drinks and eats meals

Key Features
Log Tab - Data Entry:
Water Logging: Log water intake in milliliters with a single tap
Meal Logging: Track calorie consumption from meals
Input Validation: Prevents invalid entries with helpful error messages
Edit Functionality: Modify any logged entry at any time
Delete Functionality: Remove incorrect entries seamlessly
Text-to-Speech Feedback: Audio confirmation when entries are logged
Entry Validation:
Requires numeric input: passed
Prevents zero or negative values: passed
Shows clear error messages for invalid input: passed
Success confirmation after logging: passed
Statistics Tab – plot:
Side-by-Side Charts: Water and meal intakes displayed simultaneously
Progress Bars: Visual representation of daily consumption
Real-Time Totals: Instant calculation of daily amounts
Dynamic Updates: Statistics refresh as new entries are added
Period Selection: View data Daily, Weekly, and Monthly basis
Entry History: Complete list of all logged entries with edit/delete options
Privacy Notice: Clear data protection information displayed
Visual Elements:
Water intake bar (green color #4CAF50)
Meal intake bar (blue color #2196F3)
Real-time total calculations
Progress bars scaled to 3000ml and 3000kcal, respectively
Guidelines Tab - Health Information:
Daily Water Intake Table: Recommended water consumption by age and gender
Daily Calorie Intake Table: Recommended calories by age and gender
Comprehensive Data: Based on official health guidelines
Age Groups Covered: Children (4-8 years); Adolescents (9-18 years); Young Adults (19-30 years); 
Older Adults (50+ years)
External Data Sources: Links to authoritative sources: 
WaterLogic Water Intake Chart
2015-2020 Dietary Guidelines for Americans
Reminders Tab - Smart Scheduling:
Water Reminders: 5 reminders per day (6 AM, 10 AM, 2 PM, 6 PM, 10 PM)
Meal Reminders: 3 reminders per day (7 AM, 12 PM, 7 PM)
Push Notifications: System notifications with configurable titles and messages
Recurring Schedule: Automatic daily repetition
Test Function: Test notifications without waiting for a scheduled time
Background Operation: Reminders work even when the app is closed

Reminder Schedule:
Water Reminders (Every 4 hours):
├── 6:00 AM - Morning hydration
├── 10:00 AM - Mid-morning hydration
├── 2:00 PM - Afternoon hydration
├── 6:00 PM - Evening hydration
└── 10:00 PM - Night hydration

Meal Reminders:
├── 7:00 AM - Breakfast reminder
├── 12:00 PM - Lunch reminder
└── 7:00 PM - Dinner reminder

Privacy & Security
Local Storage Only: All data stored locally on device
No Cloud Sync: Data never sent to external servers
No Data Collection: No personal information collected
User Control: Full ability to view, edit, delete entries
Clear Privacy Notice: Prominently displayed in Stats tab
Transparent Data Policy: No hidden data practices

Screenshots:
<img width="1080" height="2424" alt="01_LogTab" src="https://github.com/user-attachments/assets/36a28c32-5715-4cba-b106-887b5a724f7a" />
<img width="1080" height="2424" alt="02_StatsTab Privacy" src="https://github.com/user-attachments/assets/a0ef4240-47da-4bbb-b8e4-4e40f4c29ffd" />
<img width="1080" height="2424" alt="03_GuideTabSources" src="https://github.com/user-attachments/assets/17226b6d-4565-40c0-9e9b-34ec70963258" />
<img width="1080" height="2424" alt="04_RemindTab" src="https://github.com/user-attachments/assets/5c46e330-6480-4df4-9eda-8935e94635ba" />
<img width="1080" height="2424" alt="05_logTabKeyboard" src="https://github.com/user-attachments/assets/6885c80f-e3f6-427e-a559-57c8cd91613b" />
<img width="1080" height="2424" alt="06_LogTabInput" src="https://github.com/user-attachments/assets/72251881-4933-434d-ac91-15795b2e664b" />
<img width="1080" height="2424" alt="07_LogTabInputSucess" src="https://github.com/user-attachments/assets/02005c1c-c592-4e34-b342-ad6717394650" />
<img width="1080" height="2424" alt="08_StatsTabPlotEntry" src="https://github.com/user-attachments/assets/8af28dbf-e0f5-4515-9cf7-df6aef1135f7" />
<img width="1080" height="2424" alt="09_mealLogSucess" src="https://github.com/user-attachments/assets/f6dc5e43-b14e-42b8-b05f-0b13b3db9477" />
<img width="1080" height="2424" alt="10_mealPlot" src="https://github.com/user-attachments/assets/9c8ac3ac-1e20-4e97-a461-1122efd56185" />
<img width="1080" height="2424" alt="11_waterInput0" src="https://github.com/user-attachments/assets/a60fb124-dfd6-4b94-a22f-baef0f117dcf" />
<img width="1080" height="2424" alt="12_waterInputNegative" src="https://github.com/user-attachments/assets/42416450-ac8c-4622-bbe3-b4a25eaaa335" />
<img width="1080" height="2424" alt="13_waterInputChar" src="https://github.com/user-attachments/assets/1ac35b4d-4f63-4d3b-a55d-df7edd5f6ad5" />
<img width="1080" height="2424" alt="14_waterInputEmpty" src="https://github.com/user-attachments/assets/945b1bae-1ed0-4b73-b700-992cd6d3f1d2" />
<img width="1080" height="2424" alt="15_mealInputNegative" src="https://github.com/user-attachments/assets/deae21c8-58cb-4c47-997a-24cb7b3dae22" />
<img width="1080" height="2424" alt="16_mealInput0" src="https://github.com/user-attachments/assets/6a16549f-2fe5-429c-9776-638863435823" />
<img width="1080" height="2424" alt="17_mealInpuEmpty" src="https://github.com/user-attachments/assets/58748845-ae24-4517-ae3f-64d8f13b816a" />
<img width="1080" height="2424" alt="18__RemidTestNotification" src="https://github.com/user-attachments/assets/82e656c8-461b-4e29-8e54-8cac84868857" />
<img width="1080" height="2424" alt="19_LogTabEditEntry" src="https://github.com/user-attachments/assets/631a1aeb-5e34-4804-be7b-2c7953d1a25f" />
<img width="1080" height="2424" alt="20_LogTabDel" src="https://github.com/user-attachments/assets/7dfe407b-0053-44cb-a6fc-9d33b9dc45b5" />
<img width="1080" height="2424" alt="21_StatsTabPlots" src="https://github.com/user-attachments/assets/1adf1f85-94af-4425-b4b4-e3cb4cda1006" />

Video: https://drive.google.com/file/d/1QXY0LtmzVHYu-bF8Rp72bAlYyXT9evKM/view?usp=sharing

APKs: uploaded on Blackboard
https://drive.google.com/file/d/1ItJss6zGxDdmeq5BkbEj5sc7VrK-MWmZ/view?usp=sharing
https://drive.google.com/file/d/1ItJss6zGxDdmeq5BkbEj5sc7VrK-MWmZ/view?usp=sharing

Data Flow
User Input (Log Tab)
    ↓
Validation & Feedback
    ↓
Room Database (Local Storage)
    ↓
Statistics Display (Stats Tab)
    ↓
Visual Feedback (Charts)

Technology Stack
Frontend
Jetpack Compose: Modern UI framework for Android
Material Design 3: Latest Material Design guidelines
Kotlin: Modern, expressive programming language
Backend & Data
Room Database: Local SQLite database with Kotlin coroutines
LiveData/StateFlow: Reactive data binding
MVVM Architecture: Model-View-ViewModel pattern
System Integration
AlarmManager: Scheduling daily reminders
BroadcastReceiver: Handling alarm events
NotificationManager: Push notifications
TextToSpeech: Audio feedback for entries
Build System
Gradle 8.13.1: Build automation
Kotlin 2.0.21: Language compiler
Android SDK 34: Target platform

Libraries & Dependencies
// Core Android
androidx.core:core-ktx:1.12.0
androidx.lifecycle:lifecycle-runtime-ktx:2.6.2
androidx.activity:activity-compose:1.8.0

// Compose & UI
androidx.compose.ui:ui
androidx.compose.material3:material3:1.1.1
androidx.compose.foundation:foundation:1.5.4

// Room Database
androidx.room:room-runtime:2.6.1
androidx.room:room-ktx:2.6.1

// ViewModel
androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2

Installation
Prerequisites
Android Studio 2024.1.1 or higher
Java Development Kit (JDK) 11 or higher
Android SDK API Level 24 or higher
16GB RAM
Steps
1.	Clone the Repository
git clone https://github.com/kson115/HydrationTracker.git
cd HydrationTracker
2.	Open in Android Studio
o	File → Open → Select HydrationTracker folder
3.	Sync Gradle
o	Android Studio will prompt "Gradle Sync"
o	Click "Sync Now"
o	Wait for completion (2-5 minutes)
4.	Configure Emulator/Device
o	Tools → Device Manager
o	Create virtual device (API 24+) 
o	Enable USB Debugging on device
5.	Build & Run
o	Select device from dropdown
o	Click Run (green play button)
o	App launches automatically

Build from Command Line
// Navigate to project
cd HydrationTracker

// Clean build
./gradlew clean

// Build APK
./gradlew build

// Run on connected device/emulator
./gradlew installDebug

Usage
First Launch
1.	Grant Permissions
o	Allow POST_NOTIFICATIONS (Android 13+)
o	Grant access to reminders
2.	Review Guidelines
o	Open "Guide" tab to see health recommendations
o	Note your age group's daily targets
Daily Usage Workflow
Morning:
Open "Log" tab
Enter water consumed (e.g., 250ml)
Click "Add Water"
Hear TTS confirmation
Log breakfast calories if tracking meals
Throughout Day:
Receive reminder notifications every 4 hours
Log additional water intake
View running totals in "Stats" tab
Watch progress bars fill
Evening:
Review daily statistics in "Stats" tab
Check privacy notice
Plan next day's hydration goals

Example Entries
Morning (6:00 AM):
- Water: 250ml (after waking up)
- Breakfast: 400kcal

Mid-Morning (10:00 AM):
- Water: 250ml
- Coffee: 50kcal

Afternoon (2:00 PM):
- Water: 250ml
- Lunch: 600kcal

Evening (6:00 PM):
- Water: 250ml

Night (10:00 PM):
- Water: 250ml

Daily Total: 1250ml water, 1050kcal meals

Project Structure
HydrationTracker/
│
├── app/
│   ├── src/main/
│   │   ├── java/com/example/hydrationtracker/
│   │   │   ├── MainActivity.kt         // Main activity, UI composition
│   │   │   ├── LogEntry.kt             // Data entity (Room)
│   │   │   ├── LogDao.kt               // Database access object
│   │   │   ├── TrackerDatabase.kt      // Room database setup
│   │   │   ├── TrackerViewModel.kt     // MVVM ViewModel
│   │   │   └── ReminderReceiver.kt     // Broadcast receiver for alarms
│   │   │
│   │   └── res/
│   │       ├── values/
│   │       │   ├── strings.xml         // UI strings
│   │       │   ├── colors.xml          // Color definitions
│   │       │   └── themes.xml          // Theme resources
│   │       ├── drawable/               // App icons
│   │       └── mipmap/                 // Launcher icons
│   │
│   └── AndroidManifest.xml             // App permissions & components
│
├── gradle/
│   └── libs.versions.toml              // Dependency versions
│
├── build.gradle.kts                    // Project build config
├── settings.gradle.kts                 // Gradle settings
├── local.properties                    // Local SDK configuration
├── .gitignore                          // Git ignore rules
│
└── README.md                           // This file


Permissions
Required Permissions
<!-- AndroidManifest.xml -->

<!-- Internet for external links -->
<uses-permission android:name="android.permission.INTERNET" />

<!-- Post notifications on Android 13+ -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<!-- Schedule exact alarms for reminders -->
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />


Permission Handling
POST_NOTIFICATIONS: Requested at runtime on Android 13+
INTERNET: Required for opening external links
SCHEDULE_EXACT_ALARM: Required for daily reminders
All permissions handled gracefully; app works if denied

Architecture
Design Pattern: MVVM (Model-View-ViewModel)

┌───────────────────────────────────
│            UI Layer (Compose)            
│  ┌────────────────────────────────   
│  │  Composable Functions                
│  │  - TestContent()                     
│  │  - LogTab()                          
│  │  - StatsTab()                        
│  │  - GuideTab()                        
│  │  - ReminderTab()                     
│  └────────────────────────────────   
└────────────────┬──────────────────
                 │ observes
                 ▼
┌────────────────────────────────────
│       ViewModel Layer                    
│  ┌─────────────────────────────────   
│  │  TrackerViewModel                    
│  │  - addLog()                          
│  │  - deleteLog()                      
│  │  - todayLogs (State)                 
│  └──────────────────────────────────   
└────────────────┬────────────────────
                 │ uses
                 ▼
┌────────────────────────────────────
│       Data Layer (Room)                  
│  ┌─────────────────────────────────   
│  │  TrackerDatabase                     
│  │  - logDao()                          
│  └─────────────────────────────────   
│  ┌─────────────────────────────────   
│  │  LogDao (Queries)                    
│  │  - insertLog()                       
│  │  - getLogsByDate()                   
│  │  - deleteLog()                       
│  └──────────────────────────────────   
│  ┌──────────────────────────────────   
│  │  SQLite Local Database               
│  │  - logs table                        
│  └──────────────────────────────────   
└─────────────────────────────────────

Data Flow
User Interaction (Button Click)
    ↓
ViewModel.addLog(type, amount)
    ↓
LogDao.insertLog(LogEntry)
    ↓
Room SQLite Database
    ↓
StateFlow emits updated data
    ↓
UI recomposes with new data
    ↓
User sees updated entries & charts

Threading Model
Main Thread: UI composition and interactions
Coroutine (Background): Database operations
AlarmManager: Separate process for reminders

Acknowledgments: 
This App was created with communication with AI.
Development & Technologies
•	Google Android Team - Android Framework, Jetpack libraries, and Material Design
•	JetBrains - Kotlin programming language and development tools
•	Google Fonts - Typography and design resources
Libraries & Frameworks
•	Jetpack Compose - Modern declarative UI framework
•	Room Database - Type-safe local database with Kotlin coroutines
•	Lifecycle Components - ViewModel and LiveData for state management
•	Material Design 3 - Comprehensive design system and components
Open Source Resources
•	Stack Overflow Community - Android development solutions
•	GitHub - Version control and project hosting
•	Android Developers Documentation - Official Android references

Acceptance Tests (AT)
1. Build and Installation: Test ID: AT-01
Requirement: The app should build and install successfully on a real Android device or emulator.
Test Steps:
Open the project in Android Studio
Connect an Android device (API 24+) or start an emulator
Run the app using "Run > Run 'app'"
Verify the app installs without errors
Result:
Build completes successfully with no errors
App icon appears on device/emulator home screen
App launches without crashing
Status: PASSED

2. Permissions Handling: Test ID: AT-02
Requirement: The app handled required permissions gracefully, prompted when needed, and continued to work appropriately if denied.
Test Steps:
POST_NOTIFICATIONS Permission
First, launch the app on the Android 13+ device
Allow or deny POST_NOTIFICATIONS permission when prompted
Try to set a reminder
Verify reminders still work or show an appropriate message
Result:
Permission prompt appears on Android 13+
If allowed: Reminders show notifications
If denied: App continues to work, reminders run silently
No crash occurs
Internet Permission
Verify AndroidManifest.xml includes INTERNET permission
Click "Water Intake Chart" or "Dietary Guidelines" buttons
Verify webpages load successfully
Result: Webpages open in the browser without errors
Status: PASSED

3. Core Features - No Crashes: Test ID: AT-03
Requirement: The core features should function as described without crashes.
Test Steps:
Log Water Intake
Open "Log" tab
Enter "250" in Water field
Click "Add Water"
Verify entry appears in "Today's Entries"
Result:
•	Entry added successfully
•	TTS speaks "Added 250 milliliters of water"
•	No crash occurs
•	Entry visible in list
Log Meal
1.	Enter "500" in Meal field
2.	Click "Add Meal"
3.	Verify entry appears in list
Expected Result:
•	Entry added successfully
•	TTS speaks "Added 500 kilocalories"
•	Entry visible in list
View Statistics
Go to "Stats" tab
Verify water and meal charts display side by side
Check total amounts are calculated correctly
Result:
Charts render without crashing
Totals match logged entries
Both charts visible simultaneously
View Guidelines
Go to "Guide" tab
Scroll through tables
Click external links
Result:
Tables display with proper formatting
Header row visible (Group (Age), Male, Female)
Links open external webpages
Edit Entries
In Log tab, click "Edit" on an entry
Change the amount
Click "Save"Result:
Entry updates successfully
Old entry deleted, new value added
Toast message: "Entry updated successfully"
Delete Entries
In Log tab, click "Del" on an entry
Verify entry disappears
Result:
Entry removed from list
Toast message: "Entry deleted"
Statistics update accordingly
View Reminders
Go to "Remind" tab
Verify schedule displays (6AM, 10AM, 2PM, 6PM, 10PM for water; 7AM, 12PM, 7PM for meals)
Click "Test Notification"
Result:
Schedule displays correctly
Test notification appears
No crashes
Status: PASSED

4. Input Validation and Error Handling: Test ID: AT-04
Requirement: The app should provide basic feedback for invalid inputs.
Test Steps:
Empty Input
Leave Water field blank
Click "Add Water"
Result:
Toast message: "Please enter a valid number"
Entry not added
No crash
Invalid Input (Letters)
Enter "abc" in Water field
Click "Add Water"
Result:
Toast message: "Please enter a valid number"
Entry not added
No crash
Zero or Negative Numbers
Enter "0" in Water field
Click "Add Water"
Result:
Toast message: "Please enter a number greater than 0"
Entry not added
Invalid Edit
Try to edit with invalid number
Click "Save"
Result:
Toast message: "Please enter a valid number"
Entry not updated
Status: PASSED

5. Data Persistence: Test ID: AT-05
Requirement: User data should persist after closing and reopening the app.
Test Steps:
Add several water and meal entries (e.g., 250ml water, 500kcal meal)
Note the totals in Stats tab
Close the app completely
Wait 5 seconds
Reopen the app
Check Log and Stats tabs
Result:
All entries still visible
Statistics totals match previous session
Data persists across app restarts
Database file exists at: /data/data/com.example.hydrationtracker/databases/tracker.db
Status: PASSED

6. Background Tasks and Alarms: Test ID: AT-06
Requirement: Background tasks should run within acceptable time and not freeze the UI.
Test Steps:
Reminders Schedule
Close the app
Wait for a scheduled reminder time (6AM, 10AM, 2PM, 6PM, 10PM, 7AM, 12PM, 7PM)
Verify notification appears at scheduled time
Result:
Notification appears at correct time
Does not block UI
App responds normally when opened
UI Responsiveness
Add 10+ entries rapidly
Switch between tabs
Scroll through entries
Result:
UI responds immediately
No freezing or lag
All transitions smooth
Status: PASSED

7. Privacy and Data Protection: Test ID: AT-07
Requirement: Privacy or permission notices should be displayed clearly and not block normal usage.
Test Steps:
Privacy Notice Visibility
1.	Open "Stats" tab
2.	Scroll down to view Privacy Notice section
Result:
Privacy notice clearly displayed in yellow highlighted card
Text readable and complete: 
"Data Protection:"
"This app stores your daily hydration and meal data locally on your device..."
"No personal information is collected or shared."
Privacy Notice Does Not Block Usage
Verify all app features work with privacy notice present
Add entries
View stats
Switch tabs
Result:
Privacy notice does not block any functionality
User can still use app normally
Notice is informational only
Status: PASSED

8. Text-to-Speech (AI-assisted TTS) Functionality: Test ID: AT-08
The TTS function was incorporated instead of a food image recognition because it was not only simpler to code, but also provides input confirmation and is very valuable to the disabled. The added code is shown below. Nevertheless, a main reason was that I didn’t want to subscribe to any AI yet for an API key. Google vision API seemed to be free, but it identifies food items without estimating calories, which is useless. This App needs calorie information.

import android.speech.tts.TextToSpeech
import java.util.Locale

private var tts: TextToSpeech? = null
…..
    tts = TextToSpeech(this) { status ->
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
        }
    }
          override fun onDestroy() {
    tts?.stop()
    tts?.shutdown()
    super.onDestroy()}

Requirement: Text-to-Speech should provide audio feedback for logging actions.
Test Steps:
Enable device sound/speaker
Add a water entry (e.g., 250ml)
Listen for TTS audio
Result:
TTS speaks: "Added 250 milliliters of water"
Clear and audible
Supports meal entries as well
Status: PASSED

9. UI/UX Standards: Test ID: AT-09
Requirement: App should follow Material Design 3 and provide good user experience.
Test Steps:
Verify navigation with 4 bottom tabs (Log, Stats, Guide, Remind)
Check colors and typography
Verify proper spacing and layout
Test on different screen sizes (if available)
Result:
Bottom navigation tabs always visible
Consistent Material Design 3 styling
Proper color scheme (blues, greens for data)
Readable fonts and proper spacing
Responsive to different screen orientations
Status: PASSED

Summary
Test ID	  Test Name	                    Status	            Notes
AT-01	    Build and Installation	     PASSED	          Builds successfully on API 24+
AT-02	    Permissions Handling	       PASSED	          Graceful handling of all permissions
AT-03	    Core Features	               PASSED	          All features work without crashes
AT-04	    Input Validation	           PASSED	          All invalid inputs handled with messages
AT-05	    Data Persistence	           PASSED	          Data survives app restart
AT-06	    Background Tasks	           PASSED	          Reminders work, UI not frozen
AT-07	    Privacy Notice	             PASSED	          Clear, visible, non-blocking
AT-08	    Text-to-Speech	             PASSED	          Audio feedback works correctly
AT-09	    UI/UX Standards	             PASSED	          Material Design 3 compliant

Final decision: ALL TESTS PASSED
The Hydration Tracker app meets all acceptance test requirements and is ready for submission.

Testing Environment
•	Minimum API Level: 24 (Android 7.0)
•	Target API Level: 34 (Android 14)
•	Testing Devices: Android Emulator + Real Devices

Test Date: December 1, 2025

License
This project is created for educational purposes as part of a school assignment.
License: Educational Use Only - December 2, 2025
Terms of Use: Free to use for learning purposes; Modify for personal or educational use; Share with instructors/classmates; Not for commercial distribution; Not for resale or commercial profit; Respect original author attribution

Contact & Support
Developer: Kay Son
Project: HydrationTracker
Email: 
School: University of Arkansas at Little Rock
Created: November 24, 2025

Version History
v1.0.0 - Initial Release:
Core logging functionality (water & meals)
Statistics with side-by-side charts
Health guidelines tables
Daily reminder scheduling
Input validation with error messages
Text-to-Speech feedback
Privacy notice and data protection
MVVM architecture with Room database
Jetpack Compose UI

Last Updated: December 2, 2025

