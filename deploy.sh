#!/bin/bash

echo "==== 🚀 Deploying to EC2 ===="

# EC2로 SSH 접속하여 아래 명령 실행
ssh -o StrictHostKeyChecking=no -i outline-keypair.pem ubuntu@13.124.229.252 << 'EOF'
  cd ~/app

  echo "📦 Pull latest code"
  git pull origin main

  echo "⚙️ Build Docker image"
  ./gradlew clean build

  echo "🐳 Restart Docker Compose"
  docker-compose down
  docker-compose up -d --build
EOF
