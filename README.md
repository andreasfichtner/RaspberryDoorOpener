# RaspberryDoorOpener - [Work in progress]

Note: Right now, the jetty server will not actually trigger the relay, it will only display a message to confirm your login.

## Introduction
### What?
Some houses and apartments are equipped with automatic door openers. The door opener switch connects a circuit which powers
an electromagnet that moves part of the lock and thus allows the door to be opened.
So why not replace that switch with a relay and open the door from your browser*?
This small project contains software for a raspberry pi and hardware instructions that enable us to do just that. 

*Actually, there are many good reasons against this, so please only do this if you know what you are doing. This is a just for fun project and you should not expect the authentication to be bullet proof. Enable https in jetty. Also, your raspberry pi should not be reachable from outside your home wifi and have strong passwords for all remote users.

### Why?
Because we can. And because forgetting ones smartphone, notebook and keys at home is very unlikely.

### How?
A jetty webserver running on the raspberry will trigger a relay to close the door opener circuit.
A sqlite-database is used for user authentication. To open the door, either point your browser to raspberrypi:8080 and fill in your data or send an http post with username and password fields.

## Setup
1. Setup your hardware as described in [hardwaresetup.md](https://github.com/retterdesapok/RaspberryDoorOpener/blob/master/hardwaresetup.md "hardwaresetup.md")
2. Start the web server  
To run a quick test, check out this repo on your raspberry and type 

        mvn jetty:run
in your terminal.
To run it more permanently (see my note above), build it as a war file 
        
        mvn package
and run it in a jetty server.
3. Create a user
    - Open raspberrypi:8080 (or any URL you might have chosen) and submit an empty form. This will create an empty sql database with a user table. For obvious reasons, no default user is created.
    - Think of a password and generate its md5 hash.
    - Edit the database via command line or any tool you like:
    
            insert into user (username, passwordhash, failedlogincount, remaininglogins) VALUES ('Yourname', 'yourmd5hash', 0, 99);
    This creates a user with username Yourname, your chosen password and 99 remaining logins.
4. Go to the login form again and use your newly created credentials to open your door.
