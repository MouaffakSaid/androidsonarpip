pipeline {
    agent any


    stages {
        stage('Clean Workspace') {
            steps {
                echo 'Cleaning workspace...'
                sh 'fastlane android run_clean'
            }
        }

        stage('Build APK') {
            steps {
                echo 'Building APK...'
                sh 'fastlane android build_apk ' // or assembleRelease based on your requirements
            }
        }

       
        
        stage('Run Code Coverage') {
            steps {
                echo 'Running Code Coverage Analysis...'

                sh 'fastlane android run_jacoco'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                withSonarQubeEnv('SonarQube') { // Ensure the correct SonarQube instance is configured in Jenkins
                    sh 'fastlane android run_sonar'
                }
            }
        }

stage('Stop Emulator') {
            steps {
                sh 'adb -s emulator-5554 emu kill'
            }
        }
            }
    
    post {
        always {
            echo 'Cleaning up workspace...'
            sh './gradlew clean'
        }
        success {
            echo 'Build and analysis completed successfully!'
        }
        failure {
            echo 'Build or analysis failed!'
        }
    }
}
