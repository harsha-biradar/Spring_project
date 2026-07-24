pipeline {

    agent any

    parameters {
        choice(
            name: 'ACTION',
            choices: ['DEPLOY', 'REMOVE'],
            description: 'Choose whether to deploy or remove containers'
        )
    }

    tools {
        maven 'maven'
    }

    stages {

        stage('Build JAR') {
            when {
                expression {
                    params.ACTION == 'DEPLOY'
                }
            }

            steps {
                echo "Building Spring Boot Application..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy Application') {
            when {
                expression {
                    params.ACTION == 'DEPLOY'
                }
            }

            steps {
                echo "Deploying Containers..."
                sh 'docker compose up --build -d'
            }
        }

        stage('Remove Application') {
            when {
                expression {
                    params.ACTION == 'REMOVE'
                }
            }

            steps {
                echo "Removing Containers..."
                sh 'docker compose down'
                sh 'docker image prune -af'
            }
        }

    }

    post {

        success {
            echo 'Pipeline executed successfully.'
        }

        failure {
            echo 'Pipeline execution failed.'
        }

        always {
            echo 'Pipeline completed.'
        }

    }

}