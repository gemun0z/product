# Product API

### To run locally
___
- Run app with gradlew
```
 ./gradlew bootRun
```

### To access swagger
___

- Go to http://localhost:8080/swagger-ui/

### To access health status
___

- Go to http://localhost:8080/actuator/health

#Architecture
For the development of the API, a hexagonal architecture was chosen, with adapters and ports, to keep coupling to a minimum.

