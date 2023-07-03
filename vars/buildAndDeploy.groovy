/**
 * This library will perform build and deploy of Microservices
 * @param config
 * @return
 */

def call(Map config) {

    // Incoming Params
    def serviceName = config.serviceName
	def branchName = config.branchName
    def sonarProjectKey = config.sonarProjectKey
    def releaseName = config.releaseName
    def namespace = config.namespace
    def appSecret = config.appSecret
    def configName = config.configName
    def extraDeployArgs = config.extraDeployArgs
    def serviceType = config.serviceType
    def enableMtls = config.enableMtls
    def enableAutoscaling = config.enableAutoscaling
    def createServiceAccount = config.createServiceAccount

	scmCheckout()
    gradleCleanBuild extraArgs: ""
    sonarScan sonarProjectKey: sonarProjectKey
    imageBuild serviceName: serviceName  , branchName: branchName
    helmDeploy serviceName: serviceName , branchName: branchName , releaseName: releaseName, namespace: namespace, appSecret: appSecret, extraDeployArgs: extraDeployArgs, serviceType: serviceType, enableMtls: enableMtls, enableAutoscaling: enableAutoscaling, createServiceAccount:createServiceAccount, configName:configName


}
