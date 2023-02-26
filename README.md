# Starwars API Example

This repo is an example of how to connect to the [Socket.io](https://socket.io/docs/v4)
wrapped version of the [Star Wars API](https://swapi.dev/documentation#search).

This app uses Kotlin which requires a JDK to build. If you already have a JDK, or are ready to install one, then follow
the 'Building from Scratch' instructions. If you'd rather not install anything, I've included some instructions on how
you can build and run from Docker in the 'Build using Docker' section.

## Building from Scratch

### Pre-requisites

You will need a JDK installed, ideally version 11 which is what this was built and tested with. Azul OpenJDK 11 which
can be downloaded from [the Azul website](https://www.azul.com/downloads-new/?version=java-11-lts&package=jdk#zulu)

### Building the App

    ./gradlew init
    
    ./gradlew jar
    
    java -jar build/libs/swapi-search-cli-1.0-SNAPSHOT.jar

### Running the App

The app assumes that your SocketIO backend is running on the default URI of http://localhost:3000. If not,
the URI can be specified on the command line:

    java -jar build/libs/swapi-search-cli-1.0-SNAPSHOT.jar "<your URI>"

## Build using Docker

### Pre-requisites

You will need Docker installed.

### Building the Docker Image

A Dockerfile is included in this repo under docker/Dockerfile. It creates an image with a JDK and git installed, 
automatically pulls down the code in this repo, and builds the code. Later on we will interactively run the container
to use the app that it builds.

To build:

    cd docker
    docker build -t swapi-search-cli .

### Running the Apps

Because we have two docker containers (the app, plus the Star Wars API Socker.io server) we will need to set up a
network for them so that they can communicate.

    docker network create swapi-search-cli-net

Now run the server, note that we are giving the server a name and binding it to the network that we just created:

    docker run --name swapi-server --network swapi-search-cli-net -p 3000:3000 clonardo/socketio-backend

Finally, we can run our application interactively. Note that we are specifying the network and passing in the URL of
our server as a parameter:

    docker run  -i -t --name swapi-client --network swapi-search-cli-net swapi-search-cli /bin/bash -c "java -jar /usr/local/swapi-search-cli/build/libs/swapi-search-cli-1.0-SNAPSHOT.jar http://swapi-server:3000"

At this point you will be running the app as set out in the challenge.

### Cleanup

When you are done, you can close down the containers and remove the network:

    docker network rm swapi-search-cli-net
