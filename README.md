# Connect-4 Web Server #

This project has been developed with Spring Boot using Gradle as the build tool.

### What is this repository for? ###

* A web server running a basic version of Connect 4 with volatile data store.
* Version 0.1.0

#### What functionality is available? ####

* Single player against random bot
* Redis cache while game is active, cleaned up after it's finished

### How do I get set up? ###

#### Dependencies ####
* Java 8
* Redis (using default "localhost" and port 6379, should later be moved to application.properties)

#### Testing ####

* For testing `./gradlew test`
* For debug testing `./gradlew test --debug`

#### Development ####

* For running the server `./gradlew bootRun`, if used together with `./gradlew build --continuous` changes to source files will trigger a compilation and reload the server.

#### API ####

##### Starting a game #####

Send a GET request to `http://localhost:8080/start/?size=SIZE_DEFAULT`, it should return a json object with the necessary game information, similar to:

```
#!javascript

{
  "id": "2f6aeb74-9928-41e4-b408-447365cad323",
  "boardSize": "SIZE_DEFAULT",
  "discs": [
    [ "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY" ],
    [ "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY" ],
    [ "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY" ],
    [ "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY" ],
    [ "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY" ],
    [ "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY" ],
    [ "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY", "EMPTY" ]
  ],
  "lastPlayer": "EMPTY",
  "gameState": "ACTIVE"
}
```

##### Inputting a move #####

Send a PUT request to `http://localhost:8080/play/{id}` where id is the one received from the first request, in this case `2f6aeb74-9928-41e4-b408-447365cad323`.

The body of the request should contain the chosen player, `PLAYER_ONE` or `PLAYER_TWO` as well as the column chosen.


```
#!javascript

{
    "disc": "PLAYER_ONE",
    "column": 0
}
```


### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines