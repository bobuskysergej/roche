## Products application

This is an application that exposes endpoints to retrieve and create products. It uses h2 in memory database for the
storage layer.


## Tests
To run the test navigate to the root folder and run:  `mvn clean test` 


## Launching the app
To launch the app run: `mvn spring-boot:run`

The application will start locally on the port 8080. 

Once application has booted up it's ready to process incoming http requests. To see what endpoints it exposes 
navigate to: `http://localhost:8080//swagger-ui.html`

