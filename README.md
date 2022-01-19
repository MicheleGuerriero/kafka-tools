# Kafka Tool

This repository provides kafka tool to interact with kafka **MSK with IAM role**: [Documentation](https://docs.aws.amazon.com/it_it/msk/latest/developerguide/create-serverless-cluster-client.html)

[Link GitHub Repository AWS](https://github.com/aws/aws-msk-iam-auth)

## What this repo contains

```text
src/main/java
docker/
  client.properties
  Dockerfile
README.md
create-image.sh
```

# About dev environment

This section provides docker image with Kafka utility that can be used with MSK 

## About the CLI

The CLI builds a Docker container image locally. This allows you to run a local command to create a topic producer and consumer

## Prerequisites

- **macOS**: [Install Docker Desktop](https://docs.docker.com/desktop/).
- **Linux/Ubuntu**: [Install Docker Compose](https://docs.docker.com/compose/install/) and [Install Docker Engine](https://docs.docker.com/engine/install/).
- **Windows**: Windows Subsystem for Linux (WSL) to run the bash based command `mwaa-local-env`. Please follow [Windows Subsystem for Linux Installation (WSL)](https://docs.docker.com/docker-for-windows/wsl/) and [Using Docker in WSL 2](https://code.visualstudio.com/blogs/2020/03/02/docker-in-wsl2), to get started.


### Step one: Building the Docker image

Build the Docker container image using the following command:

```bash
./create-image.sh
```

**Note**: it takes several minutes to build the Docker image locally.

### Step two: Execute login on aws

aws sso login

#### Step three: Run docker composer file

```bash
docker-compose -f ./docker-compose.yml up
```
#### Step four: Connect to ssh on container kafka-tool


#### Step five: Execute a kafka command

```bash
./kafka-topics.sh --bootstrap-server $BS --command-config client.properties --create --topic kafka-tool --partitions 1
```

# About code example

In the folder src/main/java there this class
- Producer
- Consumer
- Admin 
that implements the example in JAVA code with MSK