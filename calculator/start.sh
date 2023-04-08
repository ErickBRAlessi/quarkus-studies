#!/bin/bash
cd calculator
docker-compose -f ./docker-compose.yml config
docker-compose -f ./docker-compose.yml up
cd ..