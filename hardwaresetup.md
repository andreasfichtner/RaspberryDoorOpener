Disclaimer: Even though this works for my setup, I can not guarantee that following these steps will not leave you sitting in a fireball surrounded by pieces of what used to be your home. It is unlikely though.

This manual explains how to setup the hardware for my Raspberry Pi door opener project
=====================================================================================

The project
-----------
https://github.com/retterdesapok/RaspberryDoorOpener

What you need
-------------
- A RaspberryPi (I am using an old model B)
- A relay, e.g. http://www.sainsmart.com/arduino-pro-mini.html
- Three female to female jumper wires

Connecting the raspberry and the relay
--------------------------------------
The raspberry and the relay are best connected with jumper cables, althouh you could be brave and soldier stuff...
A reference on the raspberry pins can be found here:  
http://www.raspberrypi-spy.co.uk/2012/06/simple-guide-to-the-rpi-gpio-header-and-pins/

* Connect a raspberry 5V out pin (Pin 2 or Pin 4) to VCC
* Connect the raspberry GPIO 17 (Pin 11) to IN1 (or any other IN)
* Connect a raspberry ground pin (e.g. Pin 9) to GND

Done. To test your setup, you can switch the GPIO via command line. The relay I mentioned above has a built-in led that shows you the on/off state. You can also hear a clicking sound each time the relay switches. Instructions:
http://luketopia.net/2013/07/28/raspberry-pi-gpio-via-the-shell/

Connecting the door opener
--------------------------
The other side of the relay simply opens or closes a circuit, as does your door opener button. Look there for the cables that need to be connected. Sidenote: It is probably a good idea to keep the button working, so you might want to extend the wires from there to the relay. 
Connect one of them to the left and one to the center contact on the relay. This works for the mentioned relay, if it does not, try other combinations. Possible outcomes:
- Your door opens immediately: try again
- Your door does not open even though the relay is switched (led is on): try again
- Your door opens only if the relay is "on": good!

Done.
