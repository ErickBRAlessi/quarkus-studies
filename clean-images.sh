#!/bin/bash
docker rm -f $(docker ps -qa)
docker images prune