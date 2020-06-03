pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'echo \'First Step: Generate .war file\''
        sh 'ls'
        sh './gradlew build -x test'
        sh 'echo \'Validating file generation: \''
        sh 'ls build/libs/'
      }
    }

    stage('Deploy') {
      steps {
        sh 'scp -i /var/lib/jenkins/keys/fin-family-backend-new.pem build/libs/finfamily-0.0.1-SNAPSHOT.war ubuntu@54.160.85.5:/tmp/teste.war'
      }
    }

  }
}