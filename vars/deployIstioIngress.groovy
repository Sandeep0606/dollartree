/**
 * This library will deploy Istio Ingress using helm chart
 * @param config
 * @return
 */
import static Constants.*
def call(Map config) {

    // Incoming Params

    def chartVersion = config.chartVersion


	stage('Deploy Istio Ingress') {
        script {
                sh """
                        gcloud config set account "${ADMIN_SERVICE_ACCOUNT}"
                    	gcloud container clusters get-credentials "${PROJECT}-gke" --region "${REGION}" --project "${PROJECT}"
                        cd istio/ingress                     
                        helm repo add istio https://istio-release.storage.googleapis.com/charts
                        helm repo update
    					if !  helm status istio-ingress -n istio-ingress > /dev/null 2>&1; then
    					    kubectl create namespace istio-ingress
    					    helm install istio-ingress istio/gateway --version "${chartVersion}" -n istio-ingress -f values.yaml
                        else
                            helm upgrade istio-ingress istio/gateway --version "${chartVersion}" -n istio-ingress -f values.yaml
                        fi
    					"""
            }


    }
}