pipeline {
    

    agent {
        ///docker { image 'budtmo/docker-android' }
        docker { image 'cimg/android:2024.11.1' }
       //docker { image 'amrka/android-emulator:pixelC_tablet_playstore-latest' } 
        //mouaffak1/dockeragent  budtmo/docker-android
    }

 environment {

     APK_OUTPUT_PATH = "app/build/outputs/apk/release/app-release-unsigned.apk"
 }
    stages {
        stage('Clean Workspace') {
            steps {
                echo 'Cleaning workspace...'
                sh 'fastlane android run_clean'
            }
        }

       

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

        stage('Connect To Emulator') {
            steps {
                sh '''
               adb connect 192.168.100.240:5555
                adb devices
               
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
                //withSonarQubeEnv('SonarQube') { // Ensure the correct SonarQube instance is configured in Jenkins
                    sh 'fastlane android run_sonar'
                }
            }
      
     stage('Build APK') {
            steps {
                echo 'Building APK...'
               sh 'fastlane android build_apk ' // or assembleRelease based on your requirements
             //  sh './gradlew assembleDebug' // or assembleRelease based on your requirements
            }
        
         post {
               success {
                  archiveArtifacts artifacts: "${APK_OUTPUT_PATH}", allowEmptyArchive: false
             }
          }
      }

   stage('Push APK to Artifactory') {
            steps {
                script {
                    // Rename APK to include a timestamp
                    def timestamp = new Date().format("yyyyMMddHHmmss")
                    def originalApk = 'app/build/outputs/apk/release/app-release.apk'
                    def renamedApk = "app/build/outputs/apk/release/unittest-${timestamp}.apk"

                    sh """
                    mv ${originalApk} ${renamedApk}
                    """

                    // Use Jenkins credentials for secure username and password
                    withCredentials([usernamePassword(credentialsId: 'artifactory-creds', usernameVariable: 'ARTIFACTORY_USER', passwordVariable: 'ARTIFACTORY_PASSWORD')]) {
                        sh """
                        curl -u${ARTIFACTORY_USER}:${ARTIFACTORY_PASSWORD} -T ${renamedApk} \
                        "http://192.168.100.240:8082/artifactory/android-unittest/"
                        """
                    }
                }
            }
        }
        

    }
        
    
    post {
        always {
            echo 'Cleaning up workspace...'
           // sh './gradlew clean'
        }
        success {
            echo 'Build and analysis completed successfully!'
        }
        failure {
            echo 'Build or analysis failed!'
        }
    }
}
