# Employee-Event
 
#### How to run :
   - To run both of the applications, go to root of project and run install.sh
     - Make sure install.sh is executable
     - Make sure mvn command is in your path
     - By executing it, both of apps will be built and their docker images will be pushed in your local,
        then docker-compose will run both.
        
#### Service Endpoints:
 - Employee app will run at port 8080 on host, swagger url http://localhost:8080/swagger-ui.html
 - Event app will run at port 9090 on host, swagger url http://localhost:9090/swagger-ui.html   

#### Notes:
   - Event application is an idempotent consumer, because key of the messages is id of collections.
   - Mongodb is chosen as DB of Event application, because we just append the events and read them.   
   - Outbox transactional pattern is implemented in Employee Application in order to atomically persist
create, update and delete operations for employee and send them to message broker.
   - In Employee application, there are some fetch join queries to prevent N+1 problem.
   - There are three docker-compose configuration files. Each of applications have their own docker-compose 
      file and one common configuration file at root of project for common services(Kafka, db, ...) 
      If you want to run just one of applications like employee-service, go to the root of that module and run 
            install.sh of employee-service

