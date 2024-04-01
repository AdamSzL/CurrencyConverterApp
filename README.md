# Currency Converter Application

- - -

## Overview

**Currency Converter Application** was built entirely with Kotlin and Jetpack Compose. It was designed using latest Material 3 guildelines and follows the best practices of Android development. The application is fully responsive, it can be used on every device - a phone, a foldable or a tablet.
<br><br> **Currency Converter Application** provides a lot of different features. Users can see latest exchange rates between currencies, check historical exchange rates in form of a chart with selectable date ranges. Users can also add items to the watchlist with a certain condition that they would like to be notified as soon as it is fulfilled.
User data is cached locally (using DataStore), so then the data is visible to the user even if there is no internet connection.

### Screenshots
<img src="https://github.com/AdamSzL/CurrencyConverterApp/assets/59572422/06f60a90-d23d-40ce-82d8-8a30576dd6a9" alt="converter screen" width="200">
<img src="https://github.com/AdamSzL/CurrencyConverterApp/assets/59572422/41823a08-fd18-4998-8761-545a0bced112" alt="charts screen" width="200">
<img src="https://github.com/AdamSzL/CurrencyConverterApp/assets/59572422/638bd2cb-7b06-486d-8ded-acdfcd74b1dc" alt="watchlist items screen" width="200">
<img src="https://github.com/AdamSzL/CurrencyConverterApp/assets/59572422/756a3900-2aa0-490d-9b99-ba5ebe15d823" alt="watchlist add item screen" width="200">
<img src="https://github.com/AdamSzL/CurrencyConverterApp/assets/59572422/bf621bc6-d2b5-4201-aa3a-3099ba1b64fd" alt="watchlist items condition fulfilled" width="200">

### Built with
**[Jetpack Compose UI App Development Toolkit](https://developer.android.com/develop/ui/compose)**
#### External libraries
- [Retrofit](https://github.com/square/retrofit)
- [OkHttp](https://github.com/square/okhttp)
- [Hilt](https://dagger.dev/hilt/)
- [Vico](https://github.com/patrykandpatrick/vico) - used for drawing currency charts
- [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) - used for JSON and Protobuf serialization
#### Notable Android APIs
- Proto DataStore
- WorkManager
- SplashScreen API

- - -

## Functionality

### Converter

#### The Converter screen allows users to select a base currency, change the base currency value, add and delete (with undo) target currencies.

https://github.com/AdamSzL/CurrencyConverterApp/assets/59572422/70026b07-8ea1-47fe-aa9b-13179331d7a0

### Charts

#### The Charts screen lets the user select base and target currencies to be included in the chart, choose chart type (line, column) and select a recent time period of date range (with a date range picker).

https://github.com/AdamSzL/CurrencyConverterApp/assets/59572422/9e6a02f8-2d00-4e3c-9a65-d0069bf04ae4

### Watchlist

#### The Watchlist Screen (and watchlist add item screen) allows the user to add a watchlist item that will be used to track currency value and notify the user when a certain condition is met. Users can add, update and delete watchlist items.

