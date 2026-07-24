pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'harshaa2475/spring-project'
        DOCKER_HUB_CRED = 'docker-hub-credentials'
    }

    stages {
        stage('Prepare Build') {
            steps {
                echo 'Cleaning workspace and checking environment...'
                cleanWs()
                sh 'java -version'
                sh 'docker --version'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building image ${DOCKER_IMAGE}:${BUILD_NUMBER}..."
                    sh "docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} ."
                    sh "docker tag ${DOCKER_IMAGE}:${BUILD_NUMBER} ${DOCKER_IMAGE}:latest"
                }
            }
        }

        stage('Login & Push to DockerHub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_HUB_CRED}", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin"
                        sh "docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}"
                        sh "docker push ${DOCKER_IMAGE}:latest"
                    }
                }
            }
        }
    }

    post {
        always {
            stage('Clean System') {
                sh "docker rmi ${DOCKER_IMAGE}:${BUILD_NUMBER} ${DOCKER_IMAGE}:latest || true"
                sh "docker system prune -f"
            }
        }
    }
}
