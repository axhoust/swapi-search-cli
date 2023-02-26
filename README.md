# Starwars API Example

This repo is an example of how to connect to the [Socket.io](https://socket.io/docs/v4)
wrapped version of the [Star Wars API](https://swapi.dev/documentation#search).

## Pre-requisites

## Building the App

    ./gradlew init
    
    ./gradlew jar
    
    java -jar build/libs/swapi-search-cli-1.0-SNAPSHOT.jar

## Running the App

The app assumes that your SocketIO backend is running on the default URI of http://localhost:3000. If not,
the URI can be specified on the command line:

    java -jar build/libs/swapi-search-cli-1.0-SNAPSHOT.jar "<your URI>"

