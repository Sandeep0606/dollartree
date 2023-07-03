/**
 * This library will build and tag image
 * @param config
 * @return
 */

import static Constants.*
def call(Map config) {

    // Incoming Params
    def serviceName = config.serviceName
    def branchName = config.branchName
	stage('Image Build') {
	 withEnv(["JAVA_HOME=${tool 'jdk-17'}"]) {
	       script {
                    SHA = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
                    def IMAGE_TAG_WITH_PREFIX = "${serviceName}:${branchName}-${SHA}_${BUILD_NUMBER}"
                    sh """
                        gcloud auth activate-service-account ${SERVICE_ACCOUNT} --key-file=/key.json
                        gcloud auth print-access-token | buildah login -u oauth2accesstoken --password-stdin https://${REPO_LOCATION}
                        buildah bud -f Dockerfile -t ${REPO_LOCATION}/${PROJECT}/${REPO_NAME}/${REPO_SUFIX}/${IMAGE_TAG_WITH_PREFIX}
                        buildah push ${REPO_LOCATION}/${PROJECT}/${REPO_NAME}/${REPO_SUFIX}/${IMAGE_TAG_WITH_PREFIX}
                    """
                }
	  }
    }
}
