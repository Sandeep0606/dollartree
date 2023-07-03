/**
 * This library will perform unit test and build jar from source code
 * @param config
 * @return
 */

def call(Map config) {

    // Incoming Params
	def extraArgs = config.extraArgs
	stage('Test And Build') {
	dir("${env.WORKSPACE}"){
	 withEnv(["JAVA_HOME=${tool 'jdk-17'}"]) {
	      sh """
				chmod 777 gradlew
			  ./gradlew clean build ${extraArgs}
			"""
		}
	 } 
    }
}
