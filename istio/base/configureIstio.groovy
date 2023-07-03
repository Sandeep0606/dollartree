library identifier: 'dollar-tree-pipeline-library@infra', changelog: false, retriever: modernSCM(
        [$class       : 'GitSCMSource',
         remote       : 'https://github.com/Family-Dollar/FD-platform-build.git',
         credentialsId: 'github_pat']) _

properties([
  parameters([
    string(defaultValue: '', description: 'Namespace to enable Istio sidecar', name: 'NAMESPACE', trim: true),
    choice(defaultValue: 'enabled', choices: ['enabled', 'disabled'], description: 'Enable or disable Istio sidecar injection', name: 'INJECT_MODE'),
    choice(defaultValue: 'yes', choices: ['yes', 'no'], description: 'Enable MTLS ', name: 'ENABLE_MTLS'),
    choice(defaultValue: 'no', choices: ['no', 'yes'], description: 'Enable Telemetry ', name: 'ENABLE_TELEMETRY')
    ])
])
node {
    scmCheckout()
    configureIstioForNamespace chartVersion: params.CHART_VERSION, injectMode: params.INJECT_MODE, enableMTLS: params.ENABLE_MTLS, enableTelemetry: params.ENABLE_TELEMETRY
}