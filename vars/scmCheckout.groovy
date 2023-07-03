/**
 * This library will perform scm checkout for the specified microservice
 * @param config
 * @return
 */

def call(Map config) {
	stage('Scm checkout') {
		checkout scm
    }
}
