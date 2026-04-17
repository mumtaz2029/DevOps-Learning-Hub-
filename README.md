# DevOps Learning Portal

This is a Jenkins freestyle project built and deployed in an automated way using Jenkins, Maven, Apache Tomcat 9, and AWS S3. The application is pulled from GitHub, packaged as a WAR file, uploaded as an artifact to S3, and deployed to Tomcat through a Jenkins post-build flow.

Alongside the deployment workflow, the project itself is a public-facing DevOps Learning Portal that presents core topics, interview preparation, hands-on labs, and project-based learning in a clean multi-page UI.

## Project Highlights

- Jenkins freestyle job for build and deployment automation
- Maven-based Spring Boot 2.7 application packaged as a WAR
- Remote deployment to Apache Tomcat 9
- Artifact publishing to AWS S3
- IAM role-based AWS access from EC2
- Public-facing DevOps learning platform UI

## Tech Stack

- Java 21
- Maven
- Spring Boot 2.7
- Thymeleaf
- Jenkins
- Apache Tomcat 9
- AWS S3
- IAM Role
- Amazon Linux EC2

## What The Project Does

The project combines two ideas in one repository:

1. A real Jenkins-based CI/CD workflow for a Java WAR application
2. A DevOps Learning Portal that users can open in the browser after deployment

The application includes:

- Core DevOps topic pages
- Interview preparation content
- Hands-on lab ideas
- Project-based learning sections
- Public resource and revision content

## CI/CD Workflow

The automated deployment flow is:

1. Jenkins pulls the source code from GitHub
2. Maven runs `clean package`
3. The WAR file is generated
4. Jenkins uploads the WAR artifact to AWS S3
5. Jenkins deploys the WAR to Apache Tomcat 9
6. The application becomes available through the Tomcat server

## Visual Walkthrough

These screenshots show both the portal UI and the Jenkins automation flow.

### Live DevOps Learning Portal

![Live portal homepage](src/main/resources/static/images/showcase/portal-live.png)

### Jenkins Plugin Installation

This step shows the `Deploy to container` plugin being prepared for Tomcat deployment.

![Jenkins plugin installation](src/main/resources/static/images/showcase/jenkins-plugin-install.png)

### Jenkins S3 Artifact Publishing

This screenshot shows the post-build configuration used to publish the generated WAR artifact to S3.

![Jenkins S3 post-build action](src/main/resources/static/images/showcase/jenkins-s3-postbuild.png)

### Jenkins Build Verification

This view confirms successful job execution and helps demonstrate the CI/CD flow clearly in the repository.

![Jenkins build status](src/main/resources/static/images/showcase/jenkins-build-status.png)

## Application Structure

- Home page for the DevOps Learning Portal
- Separate topic pages for the 8 core DevOps topics
- Interview preparation section
- Hands-on labs section
- Project learning section
- Health API endpoint for deployment verification

## Build The Project

Run this inside the project directory:

```bash
mvn clean package
```

Expected output:

```bash
target/project4-devops-app.war
```

## Run Locally

You can also run it locally for testing:

```bash
mvn spring-boot:run
```

Then open:

```text
http://localhost:8080/
```

## Jenkins Freestyle Job Setup

Create a freestyle project in Jenkins and configure:

- Source Code Management: `Git`
- Repository URL: your GitHub repository URL
- Branch: `*/main`
- GitHub credentials for repository access

Under `Build`:

- Select `Invoke top-level Maven targets`
- Goals:

```text
clean package
```

Under `Post-build Actions`:

1. `Publish artifacts to S3 Bucket`
2. `Deploy war/ear to a container`

Use:

```text
**/*.war
```

for the WAR artifact pattern.

## AWS S3 Artifact Storage

To store build artifacts in AWS:

1. Create an S3 bucket
2. Create an IAM role for EC2
3. Attach S3 access to the IAM role
4. Attach that IAM role to the EC2 instance

This avoids storing AWS access keys directly in Jenkins and lets the EC2 instance access S3 securely through the instance role.

For practice environments, `AmazonS3FullAccess` can be used. In real projects, a bucket-specific policy is better.

## EC2 And Tomcat Deployment

This project is designed to run on an Amazon Linux EC2 instance with Tomcat 9.

Typical setup:

1. Install Java 17, Maven, Git, Jenkins, and Tomcat 9
2. Change Tomcat from port `8080` to `9191` to avoid conflict with Jenkins
3. Open port `9191` in the EC2 security group
4. Configure `tomcat-users.xml` with `manager-script`
5. Allow remote manager access through the Tomcat `context.xml` files
6. Use Jenkins to deploy the WAR automatically

Expected deployed URL:

```text
http://<your-ec2-public-ip>:9191/project4-devops-app/
```

## Jenkins Plugins Used

- `Deploy to container`
- `S3 publisher`

## Application Endpoints

- Home page: `/project4-devops-app/`
- Health endpoint: `/project4-devops-app/api/health`

## Why This Project Is Useful

This repository is a good DevOps portfolio project because it demonstrates:

- Java WAR packaging
- Jenkins freestyle automation
- Tomcat deployment
- AWS S3 artifact storage
- IAM role-based access
- EC2-based deployment setup
- A real browser-based application instead of a backend-only demo

## Compatibility Note

This project uses Spring Boot 2.7 so it is compatible with Apache Tomcat 9.

## Repository Link

```text
https://github.com/mumtaz2029/DevOps-Learning-Hub-
```
