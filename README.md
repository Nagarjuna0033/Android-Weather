# Weather Application

## Overview
This is a weather application built using Jetpack Compose, Hilt, and Coil. The application allows users to search for weather information by location and toggle between dark and light themes.

## Features
- Search for weather information by location.
- Display current weather details.
- Toggle between dark and light themes.

## Tech Stack
- **Kotlin**: Programming language used.
- **Jetpack Compose**: For building the UI.
- **Hilt**: For dependency injection.
- **Coil**: For image loading.
- **Shared Preferences**: For storing user preferences (dark/light theme).

## Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/yourusername/weather-application.git
    cd weather-application
    ```

2. **Open the project** in Android Studio.

3. **Build the project**:
    - Ensure you have the latest version of Android Studio.
    - Sync the project with Gradle files.

4. **Run the application** on an emulator or a physical device.

## Usage
1. **Search for Weather**:
    - Enter a location in the search bar and get the current weather details for that location.

2. **Toggle Theme**:
    - Navigate to the settings screen.
    - Use the switch to toggle between dark and light themes. The theme preference will be saved and applied throughout the app.

## Project Structure
The project follows the MVVM (Model-View-ViewModel) architecture pattern:

