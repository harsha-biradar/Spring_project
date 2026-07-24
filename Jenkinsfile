pipeline {
    agent any

    environment {
        // Change 'your-dockerhub-username' to your actual Docker Hub username
        DOCKER_HUB_USER = 'harshaa2475'
        APP_NAME        = 'spring-boot-app'
        IMAGE_TAG       = "${env.BUILD_NUMBER}"
        IMAGE_NAME      = "${DOCKER_HUB_USER}/${APP_NAME}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Pulling latest code from GitHub...'
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker image: ${IMAGE_NAME}:${IMAGE_TAG}"
                    sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} -t ${IMAGE_NAME}:latest ."
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    echo 'Logging into Docker Hub and pushing images...'
                    withCredentials([usernamePassword(
                        credentialsId: 'docker-hub-credentials', 
                        usernameVariable: 'DOCKER_USER', 
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                        sh "echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin"
                        sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
                        sh "docker push ${IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Deploy / Run Container') {
            steps {
                script {
                    echo 'Cleaning up old container if running...'
                    sh "docker stop ${APP_NAME} || true"
                    sh "docker rm ${APP_NAME} || true"

                    echo 'Starting new container...'
                    sh "docker run -d --name ${APP_NAME} -p 8082:8082 ${IMAGE_NAME}:latest"
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished. Cleaning up local unused Docker images...'
            sh "docker image prune -f"
        }
        success {
            echo 'Successfully built, pushed, and deployed the application!'
        }
        failure {
            echo 'Pipeline failed. Please check the logs above.'
        }
    }
}
