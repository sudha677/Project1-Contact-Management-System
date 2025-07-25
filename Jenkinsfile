pipeline {
    agent any

    tools {
        maven 'Maven_3.9'
        jdk 'JDK_21'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/sudha677/Project1-Contact-Management-System', branch: 'main'
                echo 'Code checked out from GitHub repository.'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests via TestNG suite file...'
                bat 'mvn clean test -DsuiteXmlFile=ECommerceSystemOriginal/testng.xml'
            }
        }

    }

    post {
        always {
            echo 'Publishing Extent Report...'
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output/ContactManagementSuite',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report'
            ])
        }
    }
}
