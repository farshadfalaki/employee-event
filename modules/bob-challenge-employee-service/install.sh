#!/bin/bash
echo '******Building the entire project******'
mvn clean package -DskipTests

echo '******Building docker files******'
docker build -t employee .

echo '******Going to run network via docker-compose******'
docker-compose -f ../../docker-compose-common.yml -f docker-compose.yml up
