pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'harshaa2475/spring-project'
        DOCKER_HUB_CREDS = 'docker-hub-credentials'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/harsha-biradar/Spring_project.git'
            }
        }
stage('Build Application') {
    steps {
        // Force executable permissions on mvnw for the Jenkins workspace
        sh 'chmod +x mvnw'
        sh './mvnw clean package -DskipTests'
    }
}

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the docker image tagged with the build number and latest
                    dockerImage = docker.build("${DOCKER_IMAGE}:${BUILD_NUMBER}")
                    dockerImage.tag('latest')
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    // Log in to Docker Hub using the Jenkins credentials ID and push
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_HUB_CREDS}") {
                        dockerImage.push("${BUILD_NUMBER}")
                        dockerImage.push('latest')
                    }
                }
            }
        }

        stage('Deploy Container') {
            steps {
                script {
                    // Stop and remove old container if running, then run the newly pushed image
                    sh '''
                        docker stop spring-app || true
                        docker rm spring-app || true
                        docker run -d --name spring-app -p 8082:8080 harshaa2475/spring-project:latest
                    '''
                }
            }
        }
    }

    post {
        always {
            // Clean up workspace to save disk space on EC2
            cleanWs()
        }
    }
}
