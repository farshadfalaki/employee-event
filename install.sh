#!/bin/bash
echo '******Building the employee project******'
cd ./modules/bob-challenge-employee-service
mvn clean package -DskipTests
echo '******Building employee docker ******'
docker build -t employee .
echo '******Building the event  project******'
cd ../../modules/bob-challenge-event-service
mvn clean package -DskipTests
echo '******Building employee docker ******'
docker build -t event .
cd ../..
echo '******Going to run employee and event apps via docker-compose******'
docker-compose -f docker-compose-common.yml  -f ./modules/bob-challenge-employee-service/docker-compose.yml -f ./modules/bob-challenge-event-service/docker-compose.yml up
