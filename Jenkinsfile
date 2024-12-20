pipeline {
    agent any
    tools {
        jdk 'JDK 17'
        maven 'Maven'
    }
    environment {
        REPO_URL = 'https://github.com/xenonlouis/Gestion_biblioth-que.git'
    }
    stages {
        stage('Clean workspace') {
            steps {
                cleanWs()
            }
        }
        stage('Checkout from GitHub') {
            steps {
                git branch: 'master',
                    url: REPO_URL
            }
        }
        stage('Build') {
            steps {
                powershell 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                powershell 'mvn test'
            }
        }
        stage('Quality Analysis') {
            steps {
                // Removing the 'credentialsId' to allow anonymous access
                withSonarQubeEnv(installationName: 'SonarQube') {
                    powershell 'mvn sonar:sonar'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Déploiement simulé réussi'
            }
        }
    }
    post {
        success {
            mail(
                to: 'adnaneidili@gmail.com',
                subject: 'Build Success',
                body: 'Le build a été complété avec succès.'
            )
        }
        failure {
            mail(
                to: 'adnaneidili@gmail.com',
                subject: 'Build Failed',
                body: 'Le build a échoué.'
            )
        }
    }
}
