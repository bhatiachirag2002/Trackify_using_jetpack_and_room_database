# 💰 Trackify - Budget Tracker

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0+-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack--Compose-UI--Toolkit-4285F4?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Room](https://img.shields.io/badge/Room-Database-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/training/data-storage/room)
[![MVVM](https://img.shields.io/badge/Architecture-MVVM-orange?style=for-the-badge)](https://developer.android.com/topic/libraries/architecture/viewmodel)

**Trackify** is a modern, powerful, and intuitive Budget Tracker application built with **Jetpack Compose**. It helps users manage their finances effectively by tracking income and expenses, providing monthly insights, and visualizing spending habits through detailed statistics—all stored securely on your device using **Room Database**.

---


## ✨ Features

- 📊 **Real-time Balance Tracking**: Stay updated with your available balance instantly as you record transactions.
- 💳 **In-depth Financial Management**: Seamlessly add, edit, and delete income (Credit) and expense (Debit) records.
- 📅 **Monthly Summaries**: Automatically calculates total income and expenses for the current month.
- 📈 **Visual Statistics**: View your spending patterns through clean, interactive charts and graphs.
- 🗄️ **Secure Local Storage**: Powered by **Room Database**, ensuring your financial data never leaves your device.
- 🎨 **Modern & Responsive UI**: Fully built with **Jetpack Compose** featuring smooth animations and a premium look.
- 🌓 **Dynamic Theming**: Support for custom primary colors and themes to match your style.

---

## 🛠️ Project Structure

The project follows a clean **MVVM (Model-View-ViewModel)** architecture:

```text
lib/com.bhatia.trackify/
├── db/              # Room Database configuration (AppDatabase, DAOs)
├── model/           # Data models (Transaction, Balance, etc.)
├── repository/      # Data handling and abstraction layer
├── ui/              # UI layer (Compose Screens and Themes)
│   ├── theme/       # App typography, colors, and design system
│   └── view/        # Compose Screens (Dashboard, Home, Stats, etc.)
├── viewmodel/       # State Management (AppViewModel)
└── util/            # Helpers, formatters, and shared components
```

---

## ⚙️ Installation & Setup

### Prerequisites
- Android Studio Ladybug (or higher)
- Kotlin 1.9.0+
- Android Device/Emulator (API 24+)

### Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/bhatiachirag2002/Trackify_using_jetpack_and_room_database.git
   cd Trackify_using_jetpack_and_room_database
   ```

2. **Open in Android Studio**
   - Wait for Gradle to sync dependencies.

3. **Room Setup**
   - The app uses KSP for Room. Ensure `ksp` is enabled in your Android Studio plugins if needed (it usually handles itself via Gradle).

4. **Run the App**
   - Select your device and click **Run**.

---

## 🔌 Tech Stack

- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (100% Kotlin-based UI)
- **Database**: [Room](https://developer.android.com/training/data-storage/room) for persistent storage
- **Navigation**: [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- **Architecture**: MVVM with Repository Pattern
- **State Management**: [StateFlow & ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- **Asynchronous Tasks**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- **Dependency Processing**: [KSP (Kotlin Symbol Processing)](https://kotlinlang.org/docs/ksp-overview.html)

---

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

---

## 👨‍💻 Developed By
**Chirag Bhatia**  
Feel free to reach out for collaborations or feedback!

---
*If you find this project useful, give it a ⭐ on GitHub!*
