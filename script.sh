#!/bin/bash

# Stop script on error
set -e


echo "Updating system packages"
sudo apt update -y
sudo apt upgrade -y


echo "Installing Docker"
sudo apt install -y docker.io docker-compose-v2 git curl


echo "Starting Docker service"
sudo systemctl enable docker
sudo systemctl start docker


echo "Adding current user to docker group"
sudo usermod -aG docker $USER


echo "Moving to project directory"
cd ~/Fintech-Payment-Microservices-Platform-Java


echo "Stopping old containers"
sudo docker compose down || true


echo "Removing unused Docker data"
sudo docker system prune -f


echo "Building and starting services"
sudo docker compose up -d --build


echo "Running containers"
sudo docker ps


echo "Deployment completed successfully"
