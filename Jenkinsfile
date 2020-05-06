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

    stage('deploy') {
      steps {
        echo 'Deploying .war file'
        sh 'ssh -i /home/jenkins/keys/fin-family.pem ubuntu@172.31.88.177 \'sh ls\' '
        echo 'Changing .war file permisions and deploying file on Tomcat'
        sh 'ssh -i /home/jenkins/keys/fin-family.pem ubuntu@172.31.88.177 \'sh /home/ubuntu/deploy.sh\' '
      }
    }

  }
}