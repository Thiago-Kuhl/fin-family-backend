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
        sh 'scp -i /home/jenkins/keys/fin-family.pem build/libs/finfamily-0.0.1-SNAPSHOT.war ubuntu@172.31.88.177:/tmp/apis.war'
        echo 'Changing .war file permisions and deploying file on Tomcat'
        sh 'ssh -i /home/jenkins/keys/fin-family.pem ubuntu@172.31.88.177 \'sh /home/ubuntu/scripts/deploy.sh\' '
      }
    }

  }
}