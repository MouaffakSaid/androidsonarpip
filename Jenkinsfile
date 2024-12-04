pipeline {
    agent any


    stages {
        stage('Clean Workspace') {
            steps {
                echo 'Cleaning workspace...'
                sh './gradlew clean'
            }
        }

        stage('Build APK') {
            steps {
                echo 'Building APK...'
                sh './gradlew assembleDebug' // or assembleRelease based on your requirements
            }
        }

  stage('Start Emulator') {
            steps {
                echo 'Starting Android Emulator...'
                androidEmulator avdName: 'Test-AVD', targetABI: 'x86_64', density: 240, resolution: '480x800', locale: 'en_US'
                timeout(time: 5, unit: 'MINUTES') {
                    sh 'adb wait-for-device'
                }
            }
        }
        
        stage('Run Code Coverage') {
            steps {
                echo 'Running Code Coverage Analysis...'

                sh './gradlew jacocoDebugCodeCoverage'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                withSonarQubeEnv('SonarQube') { // Ensure the correct SonarQube instance is configured in Jenkins
                    sh './gradlew sonar'
                }
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
