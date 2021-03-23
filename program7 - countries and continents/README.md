# Program # 1

Name:  Haniye Kashgarani

Cosc 5735 

Description:  (how to run the program, phone/emulator screen size, android version ie 10.0)

I ran my app in Samsung Galaxy S6 Egde and Pixel 3 emulator. Minimum SDK version to run this application is 15. 
In this application you have list of continents in the Navigation Drawer view. After selecting one continent you will see list of countries in the main screen and Navigation Drawer view will be closed. By clicking on each country you will see one Toast message at the button of screen. Also, you can add countries by using FAB button. This button is only enabled when you choose one continent. 

Anything that doesn't work: I think everything works. 

# Graded: 50/50 #

* Everything works as expected

However, some things worth mentioning:

-Rather then start with no continent and tell a user to pick one before adding a country, just select a continent (for example Africa) by default.

-In your XML, extend the layout width and height of your text view to that of the parent. In this manner, you do not need to press exactly on the text of the name of a country to toast the information, but rather anywhere on the card in your CardView. It makes your app easier to use (you need to assume the user does not know specifically how the listeners work).

These are not worth any points, just design considerations for the future.



