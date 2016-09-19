#Control RGB LED from java
<a href="http://www.youtube.com/watch?feature=player_embedded&v=dNsjPK-1Aoc
" target="_blank"><img src="http://img.youtube.com/vi/dNsjPK-1Aoc/0.jpg" 
alt="Video demostration" width="240" height="180" border="10" /></a>
<!-- This is the link for a video demostration http://www.youtube.com/watch?feature=player_embedded&v=dNsjPK-1Aoc-->

If you want to make this first you need to have a java ide, i recoment eclipse since is the one i use and you need the arduino ide from the harware poin you also need any kind of arduino with serial usb communication as many RGB LED's neopixel, i use <a href="https://www.adafruit.com/products/1426">this ones from adafruit </a>, a ~470 ohm resistor and last if you have too many LED's you may need 5v power supply for 32 LED's you dont need one.

<h1>First step: Libraries</h1>
Start with the neopixel Library for arduino if you know how to instal a library the donwload link is <a href="https://github.com/adafruit/Adafruit_NeoPixel/archive/master.zip">here</a> if not <a href="https://learn.adafruit.com/adafruit-neopixel-uberguide/arduino-library-installation">here </a>is a great tutorial
Now the RXTX libray for java is left, this one was very hard for my due to the otdated post for mac OS in this case follow <a href="this tutorial "></a>for others operation system check the <a href="http://rxtx.qbang.org/wiki/index.php/Installation"> oficial web instalation guide </a>
<h1>Second step: Hardware</h1>
This is very easy just follow this sketch 
<a href="https://learn.adafruit.com/adafruit-neopixel-uberguide/basic-connections
" target="_blank"><img src="https://cdn-learn.adafruit.com/assets/assets/000/030/892/original/leds_Wiring-Diagram.png?1456961114" 
alt="Adafruid sketch" width="300" height="180" border="10" /></a>
click on the picture for more info
<h1>Third step:Arduino</h1>
Dowload the arduino code <a href="ArduJavaLED.ino">here </a> oppen it modify the code if you have diferent amount of LED's and upload to the arduino
<h1>Forth step:Eclipse</h1>
Create a new project and follow <a href="http://rxtx.qbang.org/wiki/index.php/Using_RXTX_In_Eclipse">this</a> steps, now add this two .java files <a href=""></a><a href=""></a>to your src folder and change the package name 


