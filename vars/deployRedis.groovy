/**
 * This library will deploy with redis-sentinel using helm chart
 * @param config
 * @return
 */
import static Constants.*
def call(Map config) {

    // Incoming Params

    def chartVersion = config.chartVersion
    def namespace= config.namespace


	stage('Deploy redis-sentinel') {
        script {
                sh """
                        gcloud config set account "${ADMIN_SERVICE_ACCOUNT}"
                    	gcloud container clusters get-credentials "${PROJECT}-gke" --region "${REGION}" --project "${PROJECT}"
                        cd Redis-sentinel-setup
                    	kubectl apply -f ssd-storageclass.yaml -n "${namespace}"
                    	kubectl apply -f redispvc.yaml -n "${namespace}"
    					if ! helm status redis-sentinel -n "${namespace}" > /dev/null 2>&1; then
                            helm install redis-sentinel oci://registry-1.docker.io/bitnamicharts/redis --version "${chartVersion}" -f values.yaml -n "${namespace}"
                        else
                            helm upgrade redis-sentinel oci://registry-1.docker.io/bitnamicharts/redis --version "${chartVersion}" -f values.yaml -n "${namespace}"
                        fi
    					"""
            }


    }
}