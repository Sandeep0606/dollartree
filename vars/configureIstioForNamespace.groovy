/**
 * This library will configure Istio for specific Namespace
 * @param config
 * @return
 */
import static Constants.*
def call(Map config) {

    // Incoming Params

    def namespace = config.namespace
    def injectMode = config.injectMode
    def enableMTLS = config.enableMTLS
    def enableTelemetry = config.enableTelemetry


	stage('Configure Istio') {
        script {
            def mtlsOperation = enableMTLS == 'yes' ? 'apply': 'delete'
            def telemetryOperation = enableTelemetry == 'yes' ? 'apply': 'delete'
                sh """
                        gcloud config set account "${ADMIN_SERVICE_ACCOUNT}"
                    	gcloud container clusters get-credentials "${PROJECT}-gke" --region "${REGION}" --project "${PROJECT}"
                        kubectl label namespace "${namespace}" istio-injection="${injectMode}"
                        cd istio/base
    				"""
            try {
                sh """
                    kubectl ${mtlsOperation} -f peerAuthentication.yaml -n "${namespace}"
                    """
            }catch (Exception e) {
                echo 'Exception occurred: ' + e.toString()
            }
            try {
                sh """
                    kubectl ${telemetryOperation} -f telemetry.yaml -n "${namespace}"
                    """
            }catch (Exception e) {
                echo 'Exception occurred: ' + e.toString()
            }
            }


    }
}