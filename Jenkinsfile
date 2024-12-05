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
                sh 'fastlane android build_apk ' // or assembleRelease based on your requirements
            }
        }

        stage('Setup Emulator') {
            steps {
                // Create the emulator if not already created
                sh '''
             
                avdmanager create avd -n jenkins_avd -k "system-images;android-33;google_apis;x86_64" -d "pixel" --force
             
                '''
            }
        }
        stage('Start Emulator') {
            steps {
                sh '''
                /flutterdev/Android/sdk/tools/bin/emulator -avd jenkins_avd -no-snapshot -no-audio -no-window -gpu swiftshader_indirect &
                # Wait for the emulator to boot
                adb wait-for-device
                adb shell input keyevent 82
                '''
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
