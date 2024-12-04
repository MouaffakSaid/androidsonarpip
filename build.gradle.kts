

// Top-level build file where you can add configuration options common to all sub-projects/modules.


/*
buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("org.jacoco:org.jacoco.core:0.8.12")
    }
}
*/


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
     id("org.sonarqube") version "5.1.0.4882"

}



sonar  {
    properties {
        property( "sonar.projectKey", "androidtest")
        property( "sonar.projectName", "androidtest")
        property("sonar.host.url", "http://192.168.100.240:9000")
        property("sonar.login", "sqp_f19e595aa4fe5ad463ee2abef2eb0cbde84aa4d9")
        property ("sonar.java.coveragePlugin", "jacoco")
        property ("sonar.coverage.jacoco.xmlReportPaths", "${rootProject.projectDir}/app/build/reports/jacoco/JacocoDebugCodeCoverage/JacocoDebugCodeCoverage.xml")
    }
}
