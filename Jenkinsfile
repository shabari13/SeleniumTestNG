pipeline {
    agent any
    
    tools {
        maven 'Maven3.9.12'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Test') {
            steps {
                /*sh 'mvn clean test'*/
                sh 'mvn clean test -DsuiteXmlFile=testng-parallel-browsers.xml'
            }
        }
    }
    
    post {
        always {
            step([$class: 'Publisher', 
                  reportFilenamePattern: '**/target/surefire-reports/testng-results.xml'])
            junit '**/target/surefire-reports/*.xml'
        }
    }
}