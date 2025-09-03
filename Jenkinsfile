pipeline {
    agent any

         tools {
             maven 'maven-3.9'
             jdk 'JDK19'
         }

    stages {
        stage('Build (no tests)') {
            steps {
                echo "Running mvn clean install -DskipTests"
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo "Running mvn test"
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }
}