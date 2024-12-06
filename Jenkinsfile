pipeline {
    

    agent {
        ///docker { image 'budtmo/docker-android' }
        docker { image 'cimg/android:2024.11.1' }
       //docker { image 'amrka/android-emulator:pixelC_tablet_playstore-latest' } 
        //mouaffak1/dockeragent  budtmo/docker-android
    }


    stages {
        // stage('Clean Workspace') {
        //     steps {
        //         echo 'Cleaning workspace...'
        //         sh 'fastlane android run_clean'
        //     }
        // }

        // stage('Build APK') {
        //     steps {
        //         echo 'Building APK...'
        //        sh 'fastlane android build_apk ' // or assembleRelease based on your requirements
        //      //  sh './gradlew assembleDebug' // or assembleRelease based on your requirements
        //     }
        // }

       // stage('Setup Emulator') {
       //      steps {
       //          // Create the emulator if not already created
       //          sh '''
       //          #if [ ! -d "$ANDROID_SDK_ROOT/avd/jenkins_avd.avd" ]; then
       //            #sdkmanager --list
       //            sdkmanager --install   "system-images;android-35;google_apis;x86_64"
       //             #kvm-ok
       //            avdmanager create avd -n jenkins_avd -k "system-images;android-35;google_apis;x86_64" -d "pixel"
       //             avdmanager list avd
       //          #fi
       //          '''
       //      }
       //  }

        stage('Start Emulator') {
            steps {
                sh '''
               adb connect 192.168.100.240:5555
                adb devices
               # adb wait-for-device
                #adb shell input keyevent 82
                '''
            }
        }
        // stage('Start Emulator') {
        //     steps {
        //         sh '''
        //         emulator -avd jenkins_avd -no-snapshot -no-audio -no-window -gpu swiftshader_indirect 
        //         # Wait for the emulator to boot
        //         #!/bin/bash
        // adb wait-for-device
        // BOOT_COMPLETED=""
        //         while [[ "$BOOT_COMPLETED" != "1" ]]; do
        //       BOOT_COMPLETED=$(adb shell getprop sys.boot_completed | tr -d '\r')
        //       sleep 1
        //     done
        //  echo "Emulator is ready!"

        //        # adb wait-for-device
        //         #adb shell input keyevent 82
        //         '''
        //     }
        // }
        
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
