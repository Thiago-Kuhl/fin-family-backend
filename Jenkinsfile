pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        echo 'First Step: Generate .war file'
        sh './gradlew build -x test'
        echo 'Validating file generation: '
        sh 'ls build/libs/'
      }
    }

  }
}