# Project 4 - DevOps Learning Portal

This project is a Maven-based Spring Boot application packaged as a `.war` file so it can be deployed to an external Apache Tomcat server.

It now presents a modern DevOps Learning Portal with topic-wise learning sections, interview preparation, hands-on labs, project-based learning, and public-facing revision resources, while still keeping the WAR deployment flow for Apache Tomcat on Amazon Linux EC2.

## Tech Stack

- Java 17
- Maven
- Spring Boot 2.7
- Apache Tomcat 9
- Thymeleaf
- Amazon Linux EC2

## Project Goal

This repository is meant to demonstrate:

- WAR artifact creation with Maven
- Deployment to external Tomcat instead of embedded Tomcat
- A public-facing DevOps learning platform built with Spring Boot and Thymeleaf
- EC2-friendly deployment steps for a DevOps portfolio project

## Portal Sections

- Core DevOps topics
- Interview preparation
- Hands-on labs
- Project-based learning
- Revision resources and roadmaps

## Build The WAR

Run this inside the project directory:

```bash
mvn clean package
```

Expected artifact:

```bash
target/project4-devops-app.war
```

## Local Run Option

Even though this project is prepared for external Tomcat deployment, it still includes a `main` method. You can run it locally for testing if needed:

```bash
mvn spring-boot:run
```

## Deploy On Amazon Linux EC2

These steps assume:

- You created an EC2 instance
- You installed Java 17
- You installed Maven
- You installed Apache Tomcat 9
- Port `8080` is allowed in the EC2 security group

### 1. Install dependencies

Amazon Linux 2023 example:

```bash
sudo dnf update -y
sudo dnf install -y java-17-amazon-corretto-devel git maven tar
```

### 2. Install Tomcat 9

```bash
cd /opt
sudo curl -O https://downloads.apache.org/tomcat/tomcat-9/v9.0.117/bin/apache-tomcat-9.0.117.tar.gz
sudo tar -xzf apache-tomcat-9.0.117.tar.gz
sudo mv apache-tomcat-9.0.117 tomcat
sudo chmod +x /opt/tomcat/bin/*.sh
```

### 3. Download the repository

```bash
git clone https://github.com/mumtaz2029/Project--4.git
cd Project--4
```

If you prefer ZIP download:

- Download the ZIP from GitHub
- Unzip it on the EC2 instance
- Open the extracted project directory

### 4. Build the WAR file

```bash
mvn clean package
```

### 5. Copy the WAR to Tomcat

```bash
sudo cp target/project4-devops-app.war /opt/tomcat/webapps/
```

### 6. Start Tomcat

```bash
sudo /opt/tomcat/bin/startup.sh
```

### 7. Open the application

```text
http://<your-ec2-public-ip>:8080/project4-devops-app/
```

## Application Endpoints

- Home page: `/project4-devops-app/`
- Health endpoint: `/project4-devops-app/api/health`

## Deployment Options

This repository supports three simple deployment flows depending on how you want to use Tomcat.

### Option 1. Download ZIP From GitHub And Upload The WAR

This is the easiest option for someone who just wants a ready WAR file from the repository.

1. Open the repository on GitHub.
2. Click `Code` and download the ZIP file.
3. Unzip the downloaded file on your machine or server.
4. Open the `deployable/` folder.
5. Use the WAR file named `project4-devops-app.war`.

If you are using Tomcat Manager:

1. Open the Tomcat Manager page.
2. In the `WAR file to deploy` section, click `Choose File`.
3. Select `deployable/project4-devops-app.war`.
4. Click `Deploy`.

### Option 2. Deploy Using Tomcat Manager Upload

If you want to build the WAR yourself and then upload it in the Tomcat web interface:

```bash
git clone https://github.com/mumtaz2029/Project--4.git
cd Project--4
mvn clean package
```

After the build finishes:

1. Open Tomcat Manager.
2. In the `WAR file to deploy` section, click `Choose File`.
3. Select `target/project4-devops-app.war`.
4. Click `Deploy`.

### Option 3. Copy The WAR Into The Tomcat webapps Folder

If you want to deploy directly on the server without using the Tomcat Manager page:

```bash
git clone https://github.com/mumtaz2029/Project--4.git
cd Project--4
mvn clean package
sudo cp target/project4-devops-app.war /path/to/tomcat/webapps/
```

Then restart Tomcat.

Example:

```bash
sudo /path/to/tomcat/bin/shutdown.sh
sudo /path/to/tomcat/bin/startup.sh
```

If your Tomcat is installed in a custom location, replace `/path/to/tomcat/` with your real path.

## Jenkins CI/CD Deployment On EC2

This project can also be deployed through a Jenkins freestyle job using Maven for build and Tomcat 9 for deployment.

### Deployment Flow

1. Launch an Amazon Linux EC2 instance.
2. Connect to the server using MobaXterm.
3. Install Java 17, Maven, Git, Jenkins, and Tomcat 9.
4. Change the Tomcat port in `server.xml` to avoid conflict with Jenkins.
5. Open the Tomcat port in the EC2 security group.
6. Configure Tomcat Manager access and create Tomcat admin credentials.
7. Configure GitHub and Tomcat credentials in Jenkins.
8. Create a Jenkins job to pull the repository, build the WAR, and deploy it to Tomcat.

### Server Setup

Install required packages:

```bash
sudo dnf update -y
sudo dnf install -y java-17-amazon-corretto-devel git maven
```

Install and start Jenkins:

```bash
sudo systemctl enable jenkins
sudo systemctl start jenkins
sudo systemctl status jenkins
```

Tomcat can be installed manually and configured on a custom port such as `9191` so that Jenkins can continue using `8080`.

### Tomcat Configuration

Update the connector port in `server.xml`:

```xml
<Connector port="9191" protocol="HTTP/1.1"
```

Edit these files to allow Tomcat Manager access:

- `webapps/manager/META-INF/context.xml`
- `webapps/host-manager/META-INF/context.xml`

Edit `tomcat-users.xml` and add roles such as:

```xml
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<role rolename="admin-gui"/>
<user username="admin" password="your-password" roles="manager-gui,manager-script,admin-gui"/>
```

The `manager-script` role is important for Jenkins deployment.

### Jenkins Configuration

In Jenkins:

- Install the `Deploy to container` plugin
- Configure JDK under `Manage Jenkins` -> `Tools`
- Add GitHub credentials for the repository
- Add Tomcat credentials matching the user in `tomcat-users.xml`

### Jenkins Job Setup

Create a freestyle project and configure:

- Source Code Management: `Git`
- Repository URL: your GitHub repository URL
- Credentials: GitHub credentials
- Branch: `*/main`

Under `Build`:

- Select `Invoke top-level Maven targets`
- Goals:

```text
clean package
```

Under `Post-build Actions`:

- Select `Deploy war/ear to a container`
- WAR/EAR files:

```text
**/*.war
```

- Container: `Tomcat 9.x Remote`
- Tomcat URL:

```text
http://<your-ec2-public-ip>:9191
```

- Credentials: Tomcat credentials

### Build And Verify

Click `Build Now` in Jenkins.

If the build and deployment succeed, open:

```text
http://<your-ec2-public-ip>:9191/project4-devops-app/
```

This deployment flow demonstrates:

- GitHub integration with Jenkins
- Maven build automation
- WAR artifact generation
- Remote deployment to Tomcat 9
- End-to-end CI/CD for a Java web application

## Important Compatibility Note

This project now uses Spring Boot 2.7 so it is compatible with:

- Apache Tomcat 9

This is a better fit for environments where Tomcat 9 is already installed and you want a straightforward WAR deployment flow.

## Optional Restart Command

If you replace the WAR after the first deployment, restart Tomcat:

```bash
sudo pkill -f tomcat || true
sudo /opt/tomcat/bin/startup.sh
```

## Helper Script

A helper deployment script is included:

```bash
scripts/deploy-war.sh
```

Before using it, make sure:

- Tomcat is installed at `/opt/tomcat`
- Maven is installed
- The script has execute permission

Example:

```bash
chmod +x scripts/deploy-war.sh
./scripts/deploy-war.sh
```
