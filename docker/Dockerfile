FROM azul/zulu-openjdk:11-latest

RUN apt update
RUN apt install -y git

RUN git clone https://github.com/axhoust/swapi-search-cli.git /usr/local/swapi-search-cli

WORKDIR /usr/local/swapi-search-cli

RUN ./gradlew init

RUN ./gradlew jar
