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
                script {
                    try {
                        sh 'mvn clean test -DsuiteXmlFile=testng-parallel-browsers.xml'
                    } catch (Exception e) {
                        echo "Test execution completed with failures: ${e.message}"
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Publish Reports') {  // <--- Added this stage wrapper
            steps {
                script {
                    junit allowEmptyResults: true,
                         testResults: '**/target/surefire-reports/*.xml'

                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'test-output/ExtentReports',
                        reportFiles: 'TestReport_*.html',
                        reportName: 'Extent Report',
                        reportTitles: 'Selenium Test Report'
                    ])

                    archiveArtifacts artifacts: 'test-output/screenshots/**/*.png',
                                   allowEmptyArchive: true,
                                   fingerprint: true

                    archiveArtifacts artifacts: 'test-output/ExtentReports/**/*.html',
                                   allowEmptyArchive: true,
                                   fingerprint: true
                }
            }
        }
    }

    post {
        always {
            echo 'Test execution completed'

            emailext (
                subject: "Test Report: ${currentBuild.fullDisplayName}",
                body: """
                    <p>Build Status: ${currentBuild.result}</p>
                    <p>Check console output at: ${BUILD_URL}</p>
                    <p>View Extent Report at: ${BUILD_URL}Extent_Report/</p>
                """,
                to: 'shabari13@gmail.com',
                mimeType: 'text/html',
                attachmentsPattern: 'test-output/ExtentReports/*.html'
            )
        }
        success {
            echo '✓ Tests passed successfully'
        }
        failure {
            echo '✗ Tests failed'
        }
        unstable {
            echo '⚠ Tests completed with failures'
        }
    }
}