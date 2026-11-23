# Android Connectivity SDK

A lightweight Java library for Android applications that provides network transport detection, network change monitoring, and internet connectivity validation.

Developed by **Feraud Hamid**.

## Features

- Detect active network transport: Wi-Fi, cellular, Ethernet, other, or none.
- Monitor default network transport changes automatically.
- Check whether Android has validated internet connectivity.
- Deliver network change notifications on the Android main thread.
- Support Android API 24 and later.

## Requirements

- Android API 24+
- Java 8-compatible Android project
- AndroidX-compatible development environment

## Installation

### Maven Central

**Publication pending.** Version `1.0.0` is being prepared for Maven Central.

Once published, the Gradle dependency will be:

```groovy
implementation 'io.github.matrix125:connectivity-sdk:1.0.0'
```

## Usage

### Detect the current network

```java
NetworkType type = NetworkDetector.getNetworkType(context);

if (type == NetworkType.WIFI) {
    // Connected through Wi-Fi.
}
```

### Check internet validation

```java
boolean validated =
        InternetConnectivity.isInternetAvailable(context);

if (validated) {
    // Android reports validated internet connectivity.
}
```

Android validation does not guarantee that every remote server is reachable.

### Monitor network changes

```java
NetworkMonitor monitor = new NetworkMonitor(
        context,
        networkType -> {
            // Handle the new network transport.
        }
);

// Call from the Android main thread.
monitor.start();

// Stop when monitoring is no longer required.
monitor.stop();
```

`NetworkMonitor` delivers callbacks on the main thread. The application must call `stop()` to unregister monitoring when it is no longer needed.

The current monitor reports transport changes; internet validation changes without a transport change may not trigger a callback.

## Permissions

The library declares:

```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Android's manifest merger normally includes this permission automatically when the library is added as a dependency.

## Project Structure

```text
ConnectivitySDK/
├── app/                 # Android demonstration application
├── network-android/     # Reusable Android library
├── gradle/              # Gradle wrapper and version catalog
├── LICENSE
└── README.md
```

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE).

Copyright (c) 2026 Feraud Hamid.

## Source Code

https://github.com/matrix125/android-connectivity-sdk

## Release Status

Version `1.0.0` is under preparation. The project is currently intended for learning and testing Android library publication through Maven Central.