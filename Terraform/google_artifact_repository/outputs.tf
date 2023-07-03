output "region" {
  value       = var.region
  description = "GCloud Region"
}

output "project_id" {
  value       = var.project_id
  description = "GCloud Project ID"
}

output "google_artifact_registry_image_repository" {
  value       = google_artifact_registry_repository.image-repository.name
  description = "Google Artifact repository for docker image repository"
}
output "google_artifact_registry_chart_repository" {
  value       = google_artifact_registry_repository.chart-repository.name
  description = "Google Artifact repository for helm repository"
}

