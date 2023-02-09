# RealtyFeedSDK-Android

This is RealtyFeedSDK for Android apps.

# Setup
## Step 1. Add RealtyFeedSDK to your project
There are two ways to add the RealtyFeedSDK into your project:
* A) Add RealtyFeedSDK from the gradle
```kotlin
dependencies {
        implementation 'com.github.realtyna:RealtyFeedSDK-Android:0.0.1'
}
```

* B) Download the RealtyFeedSDK latest release from Github and add it as local library 

## Step 2. Add the RealtyFeedSDK Initialization Code
Add the RealtyFeedSDK initialization code to App.kt.

```kotlin
class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        RealtyFeedSDK.initial("YOUR-API-KEY", "YOUR-RAPID-API-KEY")
    }
}
```

Make sure to import the RealtyFeedSDK header:
```kotlin
import com.realtyna.realtyfeedsdk.RealtyFeedSDK
import com.realtyna.realtyfeedsdk.API
```

```kotlin
RealtyFeedSDK.initial("YOUR-API-KEY", "YOUR-RAPID-API-KEY")
```
YOUR-API-KEY with your RealtyFeedSDK API Key.
YOUR-RAPID-API-KEY with your Rapid API Key.

Now you can call RealtyFeedSDK APIs anywhere of your project,

## Get Listings list
```kotlin
    API.instance.getListings { result, error ->
        Log.d("Done", result)
    }
```

## Get Property detail
```kotlin
    API.instance.getProperty("P_5dba1fb94aa4055b9f29691f") { result, error ->
        Log.d("Done", result)
    }
```

