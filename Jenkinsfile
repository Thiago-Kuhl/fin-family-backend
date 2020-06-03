pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'echo \'First Step: Generate .war file\''
        sh 'ls'
        sh 'sh \'./gradlew build -x test\''
        sh 'echo \'Validating file generation: \''
        sh '''        sh \'ls build/libs/\'
'''
      }
    }

  }
}