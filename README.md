# Tip Calculator App

This is a simple Tip Calculator app built using Jetpack Compose in Android. The app allows users to calculate the total bill per person by splitting the bill among a specified number of people and adding a tip percentage.

## Features

- **Enter Bill Amount:** Users can input the total bill amount.
- **Split Bill:** The bill can be split among a specified number of people.
- **Tip Calculation:** The app calculates the tip based on a percentage selected using a slider.
- **Real-Time Updates:** The total per person is updated in real-time as the tip percentage or number of people to split among is changed.

## Screenshots

![Screenshot 1](https://github.com/KushagraPatni/TipCalculatorApp/blob/main/TipCalculatorAppScreenshot.jpg)


## Screens

### Main Screen

The main screen displays a header with the total amount per person and a form for entering the bill amount, splitting the bill, and selecting the tip percentage.

## Components

### `MainActivity`

The main entry point of the app. It sets the content view and calls the `MyApp` composable, which loads the main content of the app.

### `MyApp`

This composable sets up the app's theme using `TipCalculatorAppTheme` and displays the content passed to it.

### `TopHeader`

This composable displays the total amount per person at the top of the screen. The default value is $134.00, but this is dynamically updated based on user input.

### `BillForm`

This composable handles the input form for entering the bill amount, selecting the number of people to split the bill among, and adjusting the tip percentage. It contains:
- **InputField:** For entering the total bill amount.
- **Split Among Row:** For adjusting the number of people splitting the bill.
- **Tip Row:** For displaying the calculated tip amount.
- **Slider:** For adjusting the tip percentage.

### Utility Functions

- **`calculateTotalTip(totalBill: Double, tipPercentage: Int): Double`**
  - Calculates the total tip amount based on the bill amount and tip percentage.

- **`calculateTotalPerPerson(totalBill: Double, splitBy: Int, tipPercentage: Int): Double`**
  - Calculates the total amount per person after splitting the bill and adding the tip.

## How to Run

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on an Android emulator or physical device.

## Future Enhancements

- Add support for different currencies.
- Implement a history feature to save past calculations.
- Improve the UI with more customization options.


