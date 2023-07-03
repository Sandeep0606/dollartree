/**
 * This library will deploy Application with helm chart
 * @param config
 * @return
 */
import static Constants.*
def call(Map config) {

    // Incoming Params
    def serviceName = config.serviceName
    def branchName = config.branchName

    def releaseName = config.releaseName
    def namespace= config.namespace
    def appSecret= config.appSecret
    def configName= config.configName
    def SHA = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
    def IMAGE_NAME = "${REPO_LOCATION}/${PROJECT}/${REPO_NAME}/${REPO_SUFIX}/${serviceName}"
    def IMAGE_TAG = config.imageTag ?: "${branchName}-${SHA}_${BUILD_NUMBER}"
    def extraDeployArgs = config.extraDeployArgs
    def serviceType = config.serviceType
    def enableMtls = config.enableMtls
    def enableAutoscaling = config.enableAutoscaling
    def createServiceAccount = config.createServiceAccount

	stage('Helm Deploy') {
        withCredentials([usernamePassword(credentialsId: 'github_pat', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            script {
                sh """
                    gcloud config set account ${ADMIN_SERVICE_ACCOUNT}
					gcloud container clusters get-credentials "${PROJECT}-gke" --region "${REGION}" --project "${PROJECT}"
                    rm -rf ${LIBRARY_REPO_BASE}
                    git clone -b ${LIBRARY_REPO_BRANCH} https://${USERNAME}:${PASSWORD}@github.com/Family-Dollar/${LIBRARY_REPO_BASE}.git
					if ! helm status "${releaseName}" -n "${namespace}" > /dev/null 2>&1; then
                        helm install "${releaseName}" ${LIBRARY_REPO_BASE}/${CHART_PATH} --set image.repository="${IMAGE_NAME}",image.tag="${IMAGE_TAG}",imagePullSecrets[0].name="${IMAGE_PULL_SECRET}",secretStore.name="${appSecret}",config.name="${configName}",extraDeployArgs[0]='${extraDeployArgs}',service.type="${serviceType}",mtls.enabled=true,autoscaling.enabled="${enableAutoscaling}",serviceAccount.create=true -n "${namespace}"
                    else
                        helm upgrade "${releaseName}" ${LIBRARY_REPO_BASE}/${CHART_PATH} --set image.repository="${IMAGE_NAME}",image.tag="${IMAGE_TAG}",secretStore.name="${appSecret}",extraDeployArgs[0]=${extraDeployArgs} -n "${namespace}"
                    fi
					"""
            }
        }

    }
}