/**
 * This library will deploy Istio using helm chart
 * @param config
 * @return
 */
import static Constants.*
def call(Map config) {

    // Incoming Params

    def chartVersion = config.chartVersion


	stage('Deploy Istio Base and Istio Daemon') {
        script {
                sh """
                        gcloud config set account "${ADMIN_SERVICE_ACCOUNT}"
                    	gcloud container clusters get-credentials "${PROJECT}-gke" --region "${REGION}" --project "${PROJECT}"
                        cd istio/base
                        helm repo add istio https://istio-release.storage.googleapis.com/charts
                        helm repo update
    					if ! helm status istio-base -n istio-system > /dev/null 2>&1; then
    					    kubectl create namespace istio-system
    					    helm install istio-base istio/base --version "${chartVersion}" -n istio-system --set defaultRevision=default -f values.yaml
                        else
                            helm upgrade istio-base istio/base --version "${chartVersion}" -n istio-system --set defaultRevision=default -f values.yaml
                        fi
                        if ! helm status istiod -n istio-system > /dev/null 2>&1; then
                            helm install istiod istio/istiod --version "${chartVersion}" -n istio-system
                        fi
    					"""
            }


    }
}