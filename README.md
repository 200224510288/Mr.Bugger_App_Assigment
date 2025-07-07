# 🍔 Burger Delivery App

> A feature-rich Android food delivery application built with modern Android development stack, featuring real-time location tracking, offline capabilities, and seamless ordering experience.

[![Android](https://img.shields.io/badge/Android-API%2021+-3DDC84?style=for-the-badge&logo=android)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-7F52FF?style=for-the-badge&logo=kotlin)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5-4285F4?style=for-the-badge&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase)](https://firebase.google.com/)
[![Google Maps](https://img.shields.io/badge/Google%20Maps-4285F4?style=for-the-badge&logo=googlemaps)](https://developers.google.com/maps)

## 📱 App Overview

A comprehensive burger delivery application that combines modern Android development practices with essential food delivery features. Built with Jetpack Compose for a native Android experience, featuring offline-first architecture and real-time location services.

## ✨ Key Features

### 🔐 **Authentication & Security**
- **Firebase Authentication** with multiple sign-in methods
- **Biometric Authentication** (fingerprint support)
- **Secure Session Management**

### 🌐 **Network & Data Management**
- **Offline-First Architecture** with seamless sync
- **GitHub JSON API Integration** for dynamic content
- **Local Storage** with Room database for data persistence
- **Real-time Data Synchronization**

### 🗺️ **Location Services**
- **Google Maps Integration** for address selection
- **GPS Location Tracking** for delivery updates
- **Location-based Restaurant Discovery**
- **Real-time Delivery Tracking**

### 📱 **Device Integration**
- **Camera Access** for profile pictures and food photos
- **Fingerprint Authentication** for secure login
- **Ambient Light Sensor** for adaptive UI themes
- **Location Sensor** for automatic address detection

### 🛒 **Shopping Experience**
- **Interactive Cart System** with real-time updates
- **Order Confirmation** with detailed summaries
- **Push Notifications** for order status updates
- **Slideshow** for featured items and promotions

### 🎨 **User Interface**
- **Jetpack Compose** for modern, declarative UI
- **Material Design 3** components
- **Bottom Navigation** for easy app navigation
- **Responsive Design** for various screen sizes

### 🏗️ **Architecture**
- **MVVM Pattern** with ViewModel for state management
- **Repository Pattern** for data abstraction
- **Dependency Injection** with Hilt
- **Clean Architecture** principles

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Architecture** | MVVM + Repository Pattern |
| **Database** | Room (Local) + Firebase Firestore |
| **Authentication** | Firebase Auth + Biometric |
| **Maps** | Google Maps SDK |
| **Networking** | Retrofit + OkHttp |
| **State Management** | ViewModel + LiveData/StateFlow |
| **Dependency Injection** | Hilt |
| **Image Loading** | Coil |
| **Push Notifications** | Firebase Cloud Messaging |

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 21+
- Kotlin 1.9+
- Google Maps API Key
- Firebase project setup

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/burger-delivery-app.git
   cd burger-delivery-app
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository

3. **Configure API Keys**
   
   Create `local.properties` file in root directory:
   ```properties
   # Google Maps API Key
   MAPS_API_KEY=your_google_maps_api_key_here
   
   # GitHub API Configuration
   GITHUB_BASE_URL=https://api.github.com/
   GITHUB_TOKEN=your_github_token_here
   ```

4. **Firebase Setup**
   - Create a Firebase project
   - Add your Android app to Firebase
   - Download `google-services.json`
   - Place it in `app/` directory

5. **Build and Run**
   ```bash
   ./gradlew build
   ```
   Or use Android Studio's build button

## 📁 Project Structure

```
burger-delivery-app/
├── 📁 app/
│   ├── 📁 src/main/java/com/yourpackage/
│   │   ├── 📁 data/              # Data layer
│   │   │   ├── 📁 local/         # Room database
│   │   │   ├── 📁 remote/        # API services
│   │   │   └── 📁 repository/    # Repository implementations
│   │   ├── 📁 domain/            # Business logic
│   │   │   ├── 📁 model/         # Domain models
│   │   │   ├── 📁 repository/    # Repository interfaces
│   │   │   └── 📁 usecase/       # Use cases
│   │   ├── 📁 presentation/      # UI layer
│   │   │   ├── 📁 ui/            # Composables
│   │   │   ├── 📁 viewmodel/     # ViewModels
│   │   │   └── 📁 navigation/    # Navigation
│   │   ├── 📁 di/                # Dependency injection
│   │   └── 📁 utils/             # Utility classes
│   └── 📁 src/main/res/          # Resources
├── 📄 build.gradle.kts
└── 📄 README.md
```

## 🔧 Key Components

### Authentication Flow
```kotlin
@Composable
fun AuthenticationScreen(
    authViewModel: AuthViewModel,
    onAuthSuccess: () -> Unit
) {
    // Firebase Auth + Biometric integration
}
```

### Location Services
```kotlin
@Composable
fun LocationPickerScreen(
    locationViewModel: LocationViewModel,
    onLocationSelected: (LatLng) -> Unit
) {
    // Google Maps integration with location selection
}
```

### Cart Management
```kotlin
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onCheckout: () -> Unit
) {
    // Real-time cart updates with local persistence
}
```

## 📱 Screenshots

| Home Screen | Menu | Cart | Order Tracking |
|-------------|------|------|----------------|
| ![Home](screenshots/home.png) | ![Menu](screenshots/menu.png) | ![Cart](screenshots/cart.png) | ![Tracking](screenshots/tracking.png) |

## 🎯 Features Breakdown

### 🔄 **Offline Capabilities**
- **Local Data Caching** with Room database
- **Offline Order Queue** for poor connectivity
- **Automatic Sync** when connection restored
- **Offline Maps** for location services

### 🔔 **Push Notifications**
- **Order Status Updates** (confirmed, preparing, delivered)
- **Promotional Offers** and discounts
- **Delivery Tracking** notifications
- **Custom Notification Actions**

### 📊 **State Management**
- **ViewModel Architecture** for UI state
- **Repository Pattern** for data management
- **LiveData/StateFlow** for reactive updates
- **Compose State** for UI reactivity

### 🛡️ **Security Features**
- **Biometric Authentication** (fingerprint)
- **Secure Token Storage** with encrypted preferences
- **Network Security** with certificate pinning
- **Data Encryption** for sensitive information

## 🔧 Configuration

### Firebase Configuration
1. Enable Authentication methods in Firebase Console
2. Set up Cloud Firestore database
3. Configure Firebase Cloud Messaging
4. Add SHA fingerprints for release builds

### Google Maps Setup
1. Enable Maps SDK for Android
2. Enable Places API
3. Configure API key restrictions
4. Add billing account

## 🧪 Testing

```bash
# Run unit tests
./gradlew test

# Run instrumentation tests
./gradlew connectedAndroidTest

# Run specific test
./gradlew test --tests="com.yourpackage.ExampleUnitTest"
```

## 🚀 Deployment

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### Play Store Upload
```bash
./gradlew bundleRelease
```

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Development Guidelines
- Follow Kotlin coding conventions
- Use meaningful commit messages
- Write unit tests for new features
- Update documentation as needed
- Follow Material Design guidelines

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [Android Developers](https://developer.android.com/) for excellent documentation
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for modern UI toolkit
- [Firebase](https://firebase.google.com/) for backend services
- [Google Maps Platform](https://developers.google.com/maps) for location services

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/your-username/burger-delivery-app/issues)
- **Discussions**: [GitHub Discussions](https://github.com/your-username/burger-delivery-app/discussions)
- **Email**: alokasilva2021@gmail.com

---

<div align="center">
  <p>Built with ❤️ using Android & Jetpack Compose</p>
  <p>⭐ Star this repo if you find it helpful!</p>
</div>
