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
        sh 'scp -o StrictHostKeyChecking=no  -i /var/lib/jenkins/keys/fin-family-backend-new.pem build/libs/finfamily-0.0.1-SNAPSHOT.war ubuntu@34.231.187.221:/tmp/ROOT.war'
        sh 'echo \'Changing .war file permisions and deploying file on Tomcat\''
        sh 'ssh -i /var/lib/jenkins/keys/fin-family-backend-new.pem ubuntu@54.160.85.5 \'/home/ubuntu/scripts/deploy.sh\''
      }
    }

  }
}