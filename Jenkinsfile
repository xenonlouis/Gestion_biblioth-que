pipeline {
    agent any
    tools {
            jdk 'Java17'
            maven 'Maven3'
    }
    environment {
            REPO_URL = 'https://github.com/MINAWI0/GestionBibliotheque.git'
            SONARQUBE_CREDENTIALS_ID = 'sonar'
    }
    stages {
         stage('clean work-space') {
                    steps {
                        cleanWs()
                    }
         }
         stage('Checkout from github') {
                     steps {
                         git branch: 'main',
                         credentialsId: 'github',
                         url: REPO_URL
                     }
         }
        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Quality Analysis') {
            steps {
                withSonarQubeEnv(installationName: 'sonar' , credentialsId: SONARQUBE_CREDENTIALS_ID) {
                    bat 'mvn sonar:sonar'
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
                    to: 'minaouimh@gmail.com',
                    subject: 'Build Success',
                    body: 'Le build a été complété avec succès.'
                )
            }
            failure {
                mail(
                    to: 'minaouimh@gmail.com',
                    subject: 'Build Failed',
                    body: 'Le build a échoué.'
                )
            }
     }
}
