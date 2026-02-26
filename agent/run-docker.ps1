# Run the ai-agent container (PowerShell)
# Usage: .\run-docker.ps1

docker run -p 8080:8080 `
  -e ANTHROPIC_API_KEY `
  -e GITHUB_TOKEN `
  ai-agent
