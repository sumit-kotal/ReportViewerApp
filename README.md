# Project Name

## Overview
This Android project integrates several key features, including **Google Sign-In**, **Firebase Cloud Messaging (FCM)** notifications, and API integration with local storage using Room Database. The project utilizes **Jetpack Compose** for the UI and **Koin** for dependency injection.

### Key Features:
- **API Integration**: Fetches a list of items from a remote API and displays them.
- **Local Storage**: Stores retrieved items in a **Room Database** for offline access.
- **Google Sign-In**: Allows users to log in with their Google account, and stores the credentials in **SharedPreferences**.
- **Firebase Notifications**: Uses Firebase Cloud Messaging to send notifications to users.

## Approach
The approach for building this app was as follows:
1. **API Integration**:
    - Used **Retrofit** for making HTTP calls to fetch a list of items from a provided API.
    - Parsed the data using **Gson** and saved it to a **Room Database** for offline storage.
    - Managed CRUD operations using **Room** and provided user interactions like updating and deleting items.

2. **UI and Navigation**:
    - **Jetpack Compose** was used for building the UI, ensuring a responsive and modern UI design.
    - **Navigation Compose** was utilized to handle navigation across the app’s different screens.

3. **Google Sign-In**:
    - Integrated **Google Sign-In** for user authentication.
    - On successful login, the user's Google details (ID, name, email, photo URL) are saved into **SharedPreferences** for persistence.
    - A **Toast** message is displayed on successful or failed sign-ins.

4. **Firebase Notifications**:
    - Integrated **Firebase Cloud Messaging** (FCM) to send notifications to users.
    - Notifications are sent based on specific triggers, though this feature is currently not fully functional in debug mode due to billing issues in the Google Cloud account.

## Tools Used
- **Retrofit** for API calls and Gson for parsing the responses.
- **Room** for local database management.
- **Koin** for dependency injection.
- **Jetpack Compose** for building the UI.
- **Google Sign-In API** for user authentication.
- **Firebase Cloud Messaging (FCM)** for sending push notifications.
- **SharedPreferences** for saving user data.

## Google Sign-In & Notifications
- **Google Sign-In** has been integrated successfully, but it is not working in the debug mode due to billing issues in the Google Cloud account. To get it working, the app needs to be registered and **google-services.json** must be properly configured in the Firebase console.
- **Firebase Notifications** are implemented, but they are also currently not functional in the debug mode due to the same billing-related issue. Ensure the Firebase project is properly configured for notifications in the Google Cloud Console and Firebase Console.

### How to configure Google Sign-In:
1. Register your app in the [Google Developer Console](https://console.developers.google.com/).
2. Enable **Google Sign-In API** and **Cloud Identity Platform**.
3. Create an **OAuth 2.0 Client ID** and configure it properly.
4. Download the **google-services.json** file and add it to the **app** folder of your Android project.
5. Ensure Firebase project setup is done in the [Firebase Console](https://console.firebase.google.com/).

**Note**: Since the app is currently not working in the debug mode, please verify your **billing account** and make sure **google-services.json** is correctly added to the app.

## Development Branch
To work with the latest changes, please **check out the `develop` branch**:

```bash
git checkout develop