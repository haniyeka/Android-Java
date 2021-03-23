Program # 2

Name: Haniye Kashgarani

Cosc 5730 

Description: (how to run the program, phone/emulator screen size, android version ie 10)
Tip calculator program; This program supports SDK version 16 to 30. I tried running the program both in PIXEL XL emulator with API 30 and Samsung S7 with Android version 8. When running the program there is one TextBox for amount of bill and one for the percentage of tip we want to calculate. If we want to split the total amount there is a swith that enables the textbox for entering number of people. Also, we have two rounding option, one rounds the total bill and one rounds only the tip. At the end, by pressing Calculate button you can see that one Dialog with the information will pop up.

Anything that doesn't work:
Everything works.

# Graded: 46/50 #

* Do not pad numbers that are short (i.e. 45.0) or round numbers beyond 2 decimal places **(-1 point)**

Remember that is app is intended to be used as a tip calculator for an arbitrary user. Although you are aware of how the app was made and how data types act within your program, this may cause confusion for the person using your app (especially when the number of digits after the decimal is very large).

* No way to return to no rounding once a rounding method is selected without reloading the app **(-1 point)**

This can be especially annoying if the user unintentionally selects a rounding method.

* Your round total bill does not update the tip value properly **(-2 points)**

For example: $48.86 with 20% tip = $48.86 + $9.77 tip (should round down from 9.772), so prior to rounding we have $58.63. To round this to nearest dollar (i.e. to $59)
we need to add $0.37, which will then be added to the tip such that $9.77 + $0.37 = $10.14 is the new tip. This extra has to be accounted for in the tip!

One thing to note: If the device has auto-rotate enabled, it is possible for the app to not display as intended if the phone is flipped horizontally. While I do not intend to dock points for this sort of thing, you should know that it is possible to lock the screen orientation to ensure that the layout always displays correctly.

For example, if you want to lock your screen to display vertically (portrait mode), you can simply add the following to the androidManifest.xml:

```
android:screenOrientation="portrait"
```

and the following prior to "super.onCreate" in your MainActivity's onCreate function:

```
this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
```

Your app appears to work just fine in either orientation, but I thought I should mention this (given that many other student's apps did not work in both orientations). There are plenty of other options [here](https://developer.android.com/guide/topics/manifest/activity-element) that you can play around with as well (should you feel the urge to do so). :thumbsup:
