/**
 * This library will perform unit test
 * @param config
 * @return
 */

def call(Map config) {

    // Incoming Params
	def extraArgs = config.extraArgs
	stage('Test') {
	dir("${env.WORKSPACE}"){
	 withEnv(["JAVA_HOME=${tool 'jdk-17'}"]) {
	      sh """
				chmod 777 gradlew
			  ./gradlew clean test ${extraArgs}
			"""
		}
	 } 
    }
}
