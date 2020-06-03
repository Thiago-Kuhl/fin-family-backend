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
        sh 'ssh -o -i /var/lib/jenkins/keys/fin-family-backend-new.pem ubuntu@54.160.85.5 ls'
      }
    }

  }
}