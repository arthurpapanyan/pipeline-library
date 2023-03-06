def call(Map pipelineParams) {

    pipeline {
        agent any
        // stages {
        //     stage('checkout git') {
        //         steps {
        //             git branch: pipelineParams.branch, credentialsId: 'GitCredentials', url: pipelineParams.scmUrl
        //         }
        //     }

        //     stage('build') {
        //         steps {
        //             sh 'mvn clean package -DskipTests=true'
        //         }
        //     }

        //     stage ('test') {
        //         steps {
        //             parallel (
        //                 "unit tests": { sh 'mvn test' },
        //                 "integration tests": { sh 'mvn integration-test' }
        //             )
        //         }
        //     }

        //     stage('deploy developmentServer'){
        //         steps {
        //             deploy(pipelineParams.developmentServer, pipelineParams.serverPort)
        //         }
        //     }

        //     stage('deploy staging'){
        //         steps {
        //             deploy(pipelineParams.stagingServer, pipelineParams.serverPort)
        //         }
        //     }

        //     stage('deploy production'){
        //         steps {
        //             deploy(pipelineParams.productionServer, pipelineParams.serverPort)
        //         }
        //     }
        // }
        stages{
              stage('Build') {
                steps {
                    script{
                        println(pipelineParams)
                        sh(pipelineParams.buildCommand)
                    }
                }
            }
            stage('Test') {
                steps {
                    script{
                        println(pipelineParams)
                        sh(pipelineParams.testCommand)
                    }
                }
            }
            stage('Deploy'){
              when{
                  expression{
                    pipelineParams.deployTo
                  }
              }
              steps{
                script{
                  echo("make deploy ENVIRONMENT=${pipelineParams.testCommand} HELM_VERSION=${CHART_VERSION}")
                  
                }
              }
            }
        }
        post {
            failure {
                mail to: pipelineParams.email, subject: 'Pipeline failed', body: "${env.BUILD_URL}"
            }
        }
    }
}