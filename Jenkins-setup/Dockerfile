FROM jenkins/jenkins:lts

USER root

RUN apt-get update && apt-get install -y lsb-release apt-transport-https ca-certificates gnupg wget buildah
RUN wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | apt-key add - &&\
	echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | tee /etc/apt/sources.list.d/adoptium.list && apt-get update &&\
	apt-get install temurin-17-jdk -y

RUN curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key --keyring /usr/share/keyrings/cloud.google.gpg add - &&\
 echo "deb [signed-by=/usr/share/keyrings/cloud.google.gpg] http://packages.cloud.google.com/apt cloud-sdk main" | tee /etc/apt/sources.list.d/google-cloud-sdk.list && apt-get update -y && apt-get install google-cloud-cli  google-cloud-cli-gke-gcloud-auth-plugin -y
 
 
RUN curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add - &&\
   echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" | tee -a /etc/apt/sources.list.d/kubernetes.list && apt-get update -y && apt-get install -y kubectl
 
RUN wget https://get.helm.sh/helm-v3.12.0-linux-amd64.tar.gz && tar xvf helm-v3.12.0-linux-amd64.tar.gz && mv linux-amd64/helm /usr/local/bin && rm  helm-v3.12.0-linux-amd64.tar.gz && rm -rf linux-amd64

USER jenkins

COPY key.json key.json
COPY admin-key.json admin-key.json