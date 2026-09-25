# ListyCity

An Android city-list application written in Java. It supports adding, editing, and deleting cities, with Firebase Firestore used to persist and synchronize the list.

## What it demonstrates

- Android activities, fragments, a custom list adapter, and form dialogs.
- Firestore reads and writes, including a snapshot listener for updates.
- A small data model for cities and provinces.

## Run locally

Open the project in Android Studio and configure a Firebase project for the app's Firestore integration. Build and run the `app` module on an emulator or Android device. A Firebase configuration is required; the repository does not include credentials.

## Context

This began as an Android coursework exercise. The Firestore integration was implemented with AI assistance, as noted in the source code.
