pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean compile'
      }
    }
    stage('Tests') {
      failFast true
      parallel {
        stage('Unit') {
          steps {
            sh 'mvn test'
          }
          post {
            always {
              junit 'target/surefire-reports/*.xml'
            }
          }
        }
        stage('Functional') {
          steps {
            sh 'mvn verify -DskipUTs=true'
          }
         post {
            always {
              junit 'target/failsafe-reports/*.xml'
            }
          }
        }
      }
    }
    stage('Static Code Analysis') {
      steps {
        sh 'mvn sonar:sonar -Dsonar.host.url=$SONARQUBE_HOST'
      }
    }
    stage('Deploy to Dev') {
      when {
        branch 'develop'
      }
      steps {
        sh 'oc login $OPENSHIFT_HOST --username $OPENSHIFT_CREDS_USR --password $OPENSHIFT_CREDS_PSW'
        sh 'oc project three-pt-dev'
        sh 'oc start-build threept-dev'
      }
    }
  }
  tools {
    maven 'Maven 3.5.4'
    jdk 'JDK8'
  }
  environment {
    SONARQUBE_HOST = "http://localhost:9000"
    OPENSHIFT_HOST = "https://mp-dev-cnap-master.bmwgroup.net"
    OPENSHIFT_CREDS = credentials('threept-openshift')
  }
}
