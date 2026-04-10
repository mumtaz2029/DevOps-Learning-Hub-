# Project 4 - Tomcat Deployable WAR Application

This project is a Maven-based Spring Boot application packaged as a `.war` file so it can be deployed to an external Apache Tomcat server.

It is designed for a DevOps-style deployment flow on an Amazon Linux EC2 instance where you install Java, Maven, and Tomcat, build the WAR, copy it to Tomcat, and expose the app on port `8080`.

## Tech Stack

- Java 17
- Maven
- Spring Boot 3
- Apache Tomcat 10
- Thymeleaf
- Amazon Linux EC2

## Project Goal

This repository is meant to demonstrate:

- WAR artifact creation with Maven
- Deployment to external Tomcat instead of embedded Tomcat
- EC2-friendly deployment steps for a DevOps portfolio project

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
- You installed Apache Tomcat 10
- Port `8080` is allowed in the EC2 security group

### 1. Install dependencies

Amazon Linux 2023 example:

```bash
sudo dnf update -y
sudo dnf install -y java-17-amazon-corretto-devel git maven tar
```

### 2. Install Tomcat 10

```bash
cd /opt
sudo curl -O https://downloads.apache.org/tomcat/tomcat-10/v10.1.39/bin/apache-tomcat-10.1.39.tar.gz
sudo tar -xzf apache-tomcat-10.1.39.tar.gz
sudo mv apache-tomcat-10.1.39 tomcat
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

## Important Compatibility Note

This project uses Spring Boot 3, so it should be deployed to:

- Apache Tomcat 10.1 or newer

Do not use Tomcat 9 for this project because Spring Boot 3 is based on `jakarta.*` packages.

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
