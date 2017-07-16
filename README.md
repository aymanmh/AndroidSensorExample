# AndroidSensor example
This is a sample experiment project that uses the accelerometer and magnetic field sensors to detect when the phone is rotated roughly 90 degress to the left or right

###### Logic:
- Register the listeners for the two sensors
- check if the screen is truned on, if not. then ignore
- if screen is on, get the sensors reading and determine if the device is rotated lef/right
- print a log message and count the number of times its was rotated and the azimuth (can be used in determining your direction)



###### Notes:
- This works as a background service, there is nothing in the UI
- The code also enables bluetooth and pairs with the PC, but doesn't use it yet.
- The usefull logic is inside SensorActivity.java