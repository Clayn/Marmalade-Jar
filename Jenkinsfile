node {
   def mvnHome
  def jdk = tool name: 'JDK 11'
  env.JAVA_HOME = "${jdk}"
   stage('Preparation') { 
			checkout scm
            mvnHome = tool 'Maven'
            dir('Marmalade-Jar') {
                if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' clean"
            } else {
                bat(/"${mvnHome}\bin\mvn" clean/)
            }
            dir('Strawberry') {
                if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' clean"
            } else {
                bat(/"${mvnHome}\bin\mvn" clean/)
            }
        }
    }
    dir('Marmalade-Jar') { 
		stage('Build Jar') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -DskipTests compile"
            } else {
                bat(/"${mvnHome}\bin\mvn" -DskipTests compile/)
            }
        }
        stage('Test Jar') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore=true test"
            } else {
                bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore=true test/)
            }
        }
        stage('Install Jar') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -DskipTests install"
            } else {
                bat(/"${mvnHome}\bin\mvn" -DskipTests install/)
            }
        }
        stage('Jar Results') {
            junit allowEmptyResults: true, testResults: '**/TEST-*.xml'
        }
    }
    dir('Strawberry') { 
		stage('Build Strawberry') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -DskipTests compile"
            } else {
                bat(/"${mvnHome}\bin\mvn" -DskipTests compile/)
            }
        }
        stage('Test Strawberry') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore=true test"
            } else {
                bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore=true test/)
            }
        }
        stage('Install Strawberry') {
            if (isUnix()) {
                sh "'${mvnHome}/bin/mvn' -DskipTests install"
            } else {
                bat(/"${mvnHome}\bin\mvn" -DskipTests install/)
            }
        }
        stage('Strawberry Results') {
            junit allowEmptyResults: true, testResults: '**/TEST-*.xml'
        }
    }
}