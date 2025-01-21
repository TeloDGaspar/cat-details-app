# SportsSync

This project is designed to synchronize sports events, providing users with a streamlined way to stay updated on their favorite sports. Users can select their favorite sports and view countdowns to upcoming events in real time.

# Project Features

Event Synchronization: Fetches a list of sports events from an API endpoint.
Organized Display: Events are grouped by sport type in a scrollable, user-friendly list.
Favorites Management: Users can mark events as favorites and filter events to view only their selected favorites.
Real-Time Countdown: Displays a live countdown timer for each event.
Collapsible Views: Allows users to collapse or expand event groups by sport type.
Error Handling: Displays appropriate messages when no events are available or if there’s an API error.
Favorite Storage: Favorites can be stored in session or a local database for persistence.
Unit Tests: Ensures functionality like filtering and countdown updates works as expected.
UI Tests: Verifies the app’s appearance and behavior on different devices and screen sizes

# Technologies Used

Android Framework: Built using Android SDK with support for SDK 21 and above.
Programming Language: Kotlin.
UI: Jetpack Compose for building the user interface.
Architecture: Follows the MVVM (Model-View-ViewModel) architecture pattern and Clean Architecture principles.
API Integration: Fetches data from a API endpoint with Retrofit.
Dependency Injection: Uses Hilt for dependency injection.
DataStore: Stores favorites in a local preferences file using DataStore.

# Multi Module Architecture

The project is organized into multiple modules by feature, ensuring a clear separation of concerns, maintainability and scalability. Each feature has its own module, and common/shared code is placed in separate modules.

app: The main application module that depends on other feature modules and the core module.
core: Contains shared code such as base classes, utilities, and common dependencies.
feature-sportsevent: Contains all the code related to the sports event feature, including domain, presentation, and data layers.
