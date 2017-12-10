pipeline {
    agent any

    stages {
        stage ('Build Stage') {
            steps {
                sh 'mvn clean install -Dmaven.test.skip=true'
            }

        }

        stage ('Deploy Stage') {
             steps {
                 sh '/home/valverde/scripts/jenkins/buschecker.sh'
             }
        }
    }
}