/**
 * This library will perform a sonar scan for a given service name
 * @param config
 * @return
 */
import static Constants.*
def call(Map config) {


    // Incoming Params
    def sonarProjectKey = config.sonarProjectKey
	stage('Static Code Analysis') {
	 withEnv(["JAVA_HOME=${tool 'jdk-17'}"]) {
	      withCredentials([string(credentialsId: 'sonar-token', variable: 'TOKEN')]) {
			  catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
				  sh "./gradlew jacocoTestReport --stacktrace -Dorg.gradle.jvmargs=-Xmx512m sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.token=${TOKEN} -Dsonar.projectKey=${sonarProjectKey} -Dsonar.qualitygate.wait=true -Dsonar.qualitygate.timeout=300"
			  }
			 }
		  }
	  
    }
}
