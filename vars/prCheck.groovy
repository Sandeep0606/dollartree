/**
 * This library will perform tests like unit test and sonar static code analysis
 * @param config
 * @return
 */

def call(Map config) {

    // Incoming Params
    def sonarProjectKey = config.sonarProjectKey

	scmCheckout()
    gradleCleanBuild extraArgs: ""
    sonarScan sonarProjectKey: sonarProjectKey
}