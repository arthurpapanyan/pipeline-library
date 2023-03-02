def call(body) {
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                echo 'Hello Inner'
            }
        }
    }
}

}