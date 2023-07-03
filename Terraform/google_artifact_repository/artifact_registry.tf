variable "project_id" {
  description = "project id"
}

variable "region" {
  description = "region"
}

provider "google" {
  project = var.project_id
  region  = var.region
  access_token = ""
}

# IMAGE ARTIFACT REPOSITORY
resource "google_artifact_registry_repository" "image-repository" {
  location      = "us-east1"
  repository_id = "image"
  description   = "Image repository to hold all docker images for dollar tree application"
  format        = "DOCKER"
}

# CHART ARTIFACT REPOSITORY
resource "google_artifact_registry_repository" "chart-repository" {
  location      = "us-east1"
  repository_id = "chart"
  description   = "Chart repository to hold all helm chart for dollar tree application"
  format        = "DOCKER"
}
