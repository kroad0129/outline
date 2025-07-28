#!/bin/bash

# 1. SSH 키 생성
echo "$PRIVATE_KEY" > outline-key.pem
chmod 600 outline-key.pem

# 2. EC2 접속 및 배포 명령 실행
ssh -o StrictHostKeyChecking=no -i outline-key.pem $USER@$HOST << 'EOF'
  cd ~/outline

  # 최신 코드 가져오기
  git pull origin main

  # Gradle 빌드
  ./gradlew build -x test

  # Docker 재시작
  sudo docker-compose down
  sudo docker-compose up -d --build

  exit
EOF

# 3. 사용한 키 삭제
rm -f outline-key.pem
