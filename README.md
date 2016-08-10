# RaspberryDoorOpener - Work in progress

### Raspberry door opener -  The idea
Some houses and apartments are equipped with automatic door openers. The door opener switch connects a circuit which powers
an electromagnet that moves part of the lock and thus allows the door to be opened.
So why not replace that switch with a relay and open the door with an HTTP-Request?

## Webserver
A jetty webserver will run on the raspberry with a small sqlite-database to allow for authentication

## Relay
When an authorized user sends the command to open the door, the webserver triggers a small script on the raspberry that sets a GPIO-Pin, triggering the relay. 
