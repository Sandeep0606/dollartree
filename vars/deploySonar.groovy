/**
 * This library will deploy with SonarQube using helm chart
 * @param config
 * @return
 */
import static Constants.*
def call(Map config) {

    // Incoming Params

    def chartVersion = config.chartVersion
    def namespace= config.namespace


	stage('Deploy SonarQube') {
        script {
                sh """
                        gcloud config set account "${ADMIN_SERVICE_ACCOUNT}"
                    	gcloud container clusters get-credentials "${PROJECT}-gke" --region "${REGION}" --project "${PROJECT}"
                        cd sonar-setup
                    	kubectl apply -f sonarpvc.yaml -n "${namespace}"
    					if ! helm status sonarqube -n "${namespace}" > /dev/null 2>&1; then
                            helm repo add sonarqube https://SonarSource.github.io/helm-chart-sonarqube
                            helm repo update
                            helm install sonarqube sonarqube/sonarqube --version "${chartVersion}" -f values.yaml -n "${namespace}"
                        else
                            helm upgrade sonarqube sonarqube/sonarqube --version "${chartVersion}" -f values.yaml -n "${namespace}"
                        fi
    					"""
            }


    }
}