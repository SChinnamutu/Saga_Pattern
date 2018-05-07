node ('jenkins-slave') {
  def app_name = 'goomo-card-vault-service'
  def registry_url = 'https://dr.goomo.team:5000'
  def registry_creds = 'dr_goomo_team'
  def app_env = 'goomo-dev'

  def tag
  def image

  stage('Checkout') {
    checkout scm
    sshagent (credentials: ['goomo-bitbucket']){
      sh 'git submodule update --init'
    }

    tag = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
  }

  stage('Build') {
    echo "Build"
    docker.withRegistry("${registry_url}","${registry_creds}") {
      image = docker.build("${app_name}:${tag}")
    }
  }

  stage('Unit Tests') {
      }

  if(!isOnDevelop()) {
    return;
  }
  stage('Promote') {
    docker.withRegistry("${registry_url}","${registry_creds}"){
      docker.build("${app_name}:${tag}").push("${tag}")
    }
  }

  stage('Dev Deployment')
  {
        git credentialsId: 'ciserver-bitbucket-ssh', url: 'ssh://git@bitbucket.goomo.team:7999/in/dcos-deployment.git'
        echo "starting app deployment on dev-environment"
        sh("/bin/sh service_deploy.sh ${app_name} ${tag} ${app_env}")
      
        //sh "curl -i -H \"Content-Type: application/json\" -X PUT -d '{\"version\":\"${tag}\",\"update\": \"1\"}' http://ansible.goomo.team:5050/api/v1.0/app/update/${app_name}"
   }     

  stage('Dev Tests') {
    echo "Test Dev"
  }

  stage('Promote') {
    echo "Push artifact"
  }

 
}

def isOnDevelop(){
  return !env.BRANCH_NAME || env.BRANCH_NAME == 'develop';
}
/*
node ('jenkins-slave-bot') { 

 stage('Checkout') {
                echo 'Checkout'
                git credentialsId: 'ciserver-bitbucket-ssh', url: 'ssh://git@bitbucket.goomo.team:7999/bot/ui_regress.git'
                sh 'git submodule update --init'
   }
   stage('Build') {
                echo 'Building..'
                sh 'ant build'
        }
       
  
  stage('Holidays Backend Service') {
              echo 'Running Hermes Domestic scripts .....'
              sh 'ant runhermesdomapi || true'
        }
        
*/        
//   stage('Reports') {
//                echo 'Generating reports'
//                junit '**/*.xml'
//                archiveArtifacts artifacts: '**', fingerprint: true
//                step([$class: 'Publisher'])
//                publishHTML (target: [allowMissing: false,alwaysLinkToLastBuild: false,keepAll: true,reportDir: 'reports',reportFiles: 'Sample-report.html',reportName: "Extent Reports"])
//        }

//}
