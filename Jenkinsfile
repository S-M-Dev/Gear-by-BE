pipeline {
    agent any

    stages {
        stage('Build application') {
            steps {
                sh "mvn clean package"
            }
        }

        stage('Build container'){
            steps {
                sh 'sudo docker rm --force gear'
                sh 'sudo docker build --no-cache --tag=gear-be:latest .'
            }
        }
        
        stage('Deploy') {
            steps {
                sh 'sudo docker run -d --network="host" --name gear gear-be:latest'
            }
        }
        
    }
}
