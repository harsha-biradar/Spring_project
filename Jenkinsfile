pipeline {
    agent any

    parameters {
        choice(
            name: 'ACTION',
            choices: [
                '1. Build & Push Docker Image',
                '2. Deploy Database',
                '3. Deploy Application',
                '4. Remove Application',
                '5. Remove Database'
            ],
            description: 'Select the task to execute on the cluster or runner'
        )
    }

    environment {
        DOCKER_IMAGE = 'harshaa2475/spring-project'
        DOCKER_HUB_CRED = 'docker-hub-credentials'
    }

    stages {
        stage('CI - Build & Push Docker Image') {
            when {
                expression { params.ACTION == '1. Build & Push Docker Image' }
            }
            steps {
                script {
                    echo 'Cleaning workspace...'
                    cleanWs()
                    
                    echo "Building Docker Image ${DOCKER_IMAGE}:${BUILD_NUMBER}..."
                    sh "docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} ."
                    sh "docker tag ${DOCKER_IMAGE}:${BUILD_NUMBER} ${DOCKER_IMAGE}:latest"

                    withCredentials([usernamePassword(credentialsId: "${DOCKER_HUB_CRED}", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin"
                        sh "docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}"
                        sh "docker push ${DOCKER_IMAGE}:latest"
                    }
                }
            }
            post {
                always {
                    echo 'Cleaning local Docker images...'
                    sh "docker rmi ${DOCKER_IMAGE}:${BUILD_NUMBER} ${DOCKER_IMAGE}:latest || true"
                    sh "docker system prune -f"
                }
            }
        }

        stage('CD - Deploy Database') {
            when {
                expression { params.ACTION == '2. Deploy Database' }
            }
            steps {
                script {
                    echo 'Deploying MySQL StatefulSet into production namespace...'
                    sh 'kubectl apply -f mysql-statefulset.yaml'
                    sh 'kubectl rollout status statefulset/mysql -n production --timeout=120s'
                }
            }
        }

        stage('CD - Deploy Application') {
            when {
                expression { params.ACTION == '3. Deploy Application' }
            }
            steps {
                script {
                    echo 'Deploying Spring Boot App into production namespace...'
                    sh 'kubectl apply -f spring-app.yaml'
                    sh 'kubectl rollout status deployment/spring-app -n production --timeout=120s'
                }
            }
        }

        stage('CD - Remove Application') {
            when {
                expression { params.ACTION == '4. Remove Application' }
            }
            steps {
                script {
                    echo 'Removing Spring Boot App...'
                    sh 'kubectl delete -f spring-app.yaml --ignore-not-found=true'
                }
            }
        }

        stage('CD - Remove Database') {
            when {
                expression { params.ACTION == '5. Remove Database' }
            }
            steps {
                script {
                    echo 'Removing MySQL StatefulSet...'
                    sh 'kubectl delete -f mysql-statefulset.yaml --ignore-not-found=true'
                }
            }
        }
    }
}
