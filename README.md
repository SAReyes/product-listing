# product-listing
Small REST example using java 10 + Spring boot 2.1.x with webflux

# Running the application locally
## Pre-requirement
Clone the repository
```
$ git clone https://github.com/SAReyes/product-listing.git
$ cd product-listing
```
## Using docker
### Requirements
* Docker installed on the machine. Tested using `Docker Engine - Comunity v18.09.0`
### Commands
```
$ docker-compose up -d
```
This will pull a mysql container and wait for it to be reachable before starting the application.
## Using pure java
### Requirements
* Java 10. Tested using `jvm:10.0.2`
### Comands
Unix:
```
$ ./gradlew bootRun
```
Windows:
```
$ gradlew.bat bootRun
```
The application will try to connect to a mysql database running locally with the credentials `root:root`. To use other
credentials use the following variables:
* `DB_USER` - database user
* `DB_PWD` - database password

The host is not configurable as this is only supposed to be run locally
