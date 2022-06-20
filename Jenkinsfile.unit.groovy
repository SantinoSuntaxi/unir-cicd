pipeline {
    agent {
        label 'docker'
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building stage!'
                sh 'make build'
            }
        }
        stage('Unit tests') {
            steps {
                sh 'make test-unit'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }
        stage('Api tests') {
            steps {
                sh 'make test-api'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }
        stage('e2e tests') {
            steps {
                sh 'make test-e2e'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }
       
    }
    post {
        always {
            junit 'results/*_result.xml'
            cleanWs()
        }
        success {  
             echo "El nombre del trabajo es: ${JOB_NAME}"
             echo "El numero de ejecucion es: ${EXECUTOR_NUMBER}"
             
         }  
        
        
    }
}
