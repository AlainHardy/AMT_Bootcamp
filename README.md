# AMT-Bootcamp
This application has been made for the course of AMT.  It is possible to do simple things with a list of cars. All the cars are displayed in a list. You can add, edit or delete a thing car. It is also possible to generate randomly a number of cars.

## Setup
You can simply install the environment with docker and docker-compose. It will mount a mysql, phpmyadmin and a glassfish server.
Go to the **_docker/topology_amt/_** folder. It contains the **_docker-compose.yml_** file. Run the following command in the folder :
```
docker-compose up --build
```
If you want to do a new installation of the whole thing and it is not the first time you run, use :
```
docker-compose down
```
When all machines have been mounted, you can access the app. If you used the default docker machine, the ip address should be http://192.168.99.100:8080/Bootcamp-1.0-SNAPSHOT/

## About the machines
### MySQL
login : __root__ / __adminpw__
port : __3306__
### phpmyadmin
login : __root__ / __adminpw__
port : __6060__
### Glassfish
login : __admin__ /  __glassfish__ (
Admin console port : __4848__
Application port : __8080__



## How to use
### What is a car ?
In the app a car is simply defined by 4 attributes : the brand, the color, the horsepower and the price.
For the brand and color, you can input whatever you want while it is not longer than 64 characters but for the horsepower and price, integer are expected. And yeah, no float for the price. Really, what car has cents in the price !?

### Available screens
There is 4 screens in the app :
  * __Home__ :  Nothing special to do here. Just the welcome page.
  * __List__ : Where all the cars are shown. From here you can edit and delete cars.
  * __Car__ : The form used to insert cars. Also used to edit cars.
  * __Configuration__ :  Where it is possible to generate a large number of cars.

