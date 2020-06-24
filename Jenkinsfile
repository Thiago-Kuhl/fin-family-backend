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
        echo 'Enviando .war para a fin-family-backend-01'
        sh 'scp -o StrictHostKeyChecking=no  -i /var/lib/jenkins/keys/fin-family-backend-new.pem build/libs/finfamily-0.0.1-SNAPSHOT.war ubuntu@34.231.187.221:/tmp/ROOT.war'
        echo 'Realizando deploy do .war na fin-family-01'
        sh 'ssh -o StrictHostKeyChecking=no  -i /var/lib/jenkins/keys/fin-family-backend-new.pem ubuntu@34.231.187.221 \'/home/ubuntu/scripts/deploy.sh\''
        echo 'Enviando .war para a fin-family-backend-02'
        sh 'scp -o StrictHostKeyChecking=no  -i /var/lib/jenkins/keys/fin-family-backend-new.pem build/libs/finfamily-0.0.1-SNAPSHOT.war ubuntu@34.237.168.116:/tmp/ROOT.war'
        echo 'Realizando deploy do .war na fin-family-02'
        sh 'ssh -o StrictHostKeyChecking=no  -i /var/lib/jenkins/keys/fin-family-backend-new.pem ubuntu@34.237.168.116 \'/home/ubuntu/scripts/deploy.sh\''
      }
    }

  }
}
