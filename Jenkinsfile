pipeline {
    agent any
    tools {
            jdk 'JDK 17'
            maven 'Maven'
    }
    environment {
            REPO_URL = 'https://github.com/xenonlouis/Gestion_biblioth-que.git'
            SONARQUBE_CREDENTIALS_ID = 'sonar'
            GITHUB_CREDENTIALS_ID = 'Github_Token'
    }
    stages {
         stage('clean work-space') {
                    steps {
                        cleanWs()
                    }
         }
         stage('Checkout from github') {
                     steps {
                         git branch: 'master',
                         credentialsId: GITHUB_CREDENTIALS_ID,
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
                withSonarQubeEnv(installationName: 'SonarQube' , credentialsId: SONARQUBE_CREDENTIALS_ID) {
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
