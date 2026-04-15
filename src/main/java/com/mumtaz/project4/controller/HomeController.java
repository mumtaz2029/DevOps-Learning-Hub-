package com.mumtaz.project4.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        List<Map<String, Object>> topics = buildTopics();
        model.addAttribute("title", "DevOps Learning Portal");
        model.addAttribute("subtitle", "A public learning portal for DevOps fundamentals, interview preparation, hands-on labs, and project-based practice.");
        model.addAttribute("heroTag", "Learn. Practice. Deploy.");
        model.addAttribute("learningPaths", List.of("Beginner Path", "Interview Prep", "Hands-on Labs", "Project Track"));
        model.addAttribute("stats", List.of(
                Map.of("label", "Core Modules", "value", "08"),
                Map.of("label", "Interview Questions", "value", "75+"),
                Map.of("label", "Hands-on Labs", "value", "18"),
                Map.of("label", "Project Guides", "value", "04")
        ));
        model.addAttribute("topics", topics);
        model.addAttribute("interviewTracks", List.of(
                Map.of("title", "Foundational Questions", "items", List.of(
                        "What is DevOps and how is it different from Agile?",
                        "Explain CI, CD, and continuous deployment with examples.",
                        "What is infrastructure as code and why is it important?"
                )),
                Map.of("title", "Tool-based Questions", "items", List.of(
                        "How does Jenkins trigger pipeline stages after a Git push?",
                        "What is the difference between a Docker image and a container?",
                        "Why do we use Kubernetes deployments instead of individual pods?"
                )),
                Map.of("title", "Scenario-based Questions", "items", List.of(
                        "A build passes locally but fails in Jenkins. How would you debug it?",
                        "A production container keeps restarting. What checks would you perform first?",
                        "How would you plan zero-downtime deployment for a Tomcat-hosted app?"
                ))
        ));
        model.addAttribute("labs", List.of(
                Map.of("title", "Build a WAR with Maven", "level", "Intermediate"),
                Map.of("title", "Deploy on Tomcat 9", "level", "Practical"),
                Map.of("title", "Create a Jenkins Pipeline", "level", "Intermediate"),
                Map.of("title", "Containerize with Docker", "level", "Practical")
        ));
        model.addAttribute("projects", List.of(
                Map.of("title", "P1 - CI/CD Java Application", "description", "A Java application demonstrating Jenkins, Maven, Docker, and automated build pipelines."),
                Map.of("title", "P2 - Dockerized Web App", "description", "A web application packaged into Docker to show portable deployment across environments."),
                Map.of("title", "P3 - Azure Infrastructure Deployment", "description", "Cloud infrastructure provisioning on Azure with VM, networking, and deployment concepts."),
                Map.of("title", "P4 - Tomcat Deployable WAR", "description", "A Spring Boot WAR deployed on Apache Tomcat to demonstrate external app server deployment.")
        ));
        model.addAttribute("visuals", List.of(
                Map.of("title", "Live Learning Portal", "caption", "The deployed public-facing DevOps Learning Portal running on Tomcat.", "image", "/images/showcase/portal-live.png"),
                Map.of("title", "Jenkins Plugin Setup", "caption", "Deploy-to-container plugin installation used for automated Tomcat deployment.", "image", "/images/showcase/jenkins-plugin-install.png"),
                Map.of("title", "Artifact Publishing", "caption", "Post-build action configuration for publishing WAR artifacts to S3.", "image", "/images/showcase/jenkins-s3-postbuild.png"),
                Map.of("title", "Build Verification", "caption", "Jenkins job status screen confirming successful builds and pipeline flow.", "image", "/images/showcase/jenkins-build-status.png")
        ));
        model.addAttribute("resources", List.of(
                "Command cheat sheets for Linux, Git, Docker, and Kubernetes",
                "Step-by-step deployment notes for Amazon Linux, S3, Jenkins, and Tomcat",
                "Topic roadmaps for beginner, intermediate, and interview preparation",
                "Quick revision cards for important DevOps concepts"
        ));
        model.addAttribute("lastUpdated", LocalDateTime.now());
        return "index";
    }

    @GetMapping("/topics/{slug}")
    public String topicPage(@PathVariable String slug, Model model) {
        Map<String, Object> topic = findTopic(slug);
        if (topic == null) {
            return "redirect:/";
        }

        model.addAttribute("siteTitle", "DevOps Learning Portal");
        model.addAttribute("topic", topic);
        model.addAttribute("relatedTopics", buildTopics().stream()
                .filter(item -> !item.get("slug").equals(slug))
                .limit(4)
                .toList());
        model.addAttribute("lastUpdated", LocalDateTime.now());
        return "topic";
    }

    @GetMapping("/api/health")
    @ResponseBody
    public Map<String, Object> health() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "UP");
        response.put("application", "devops-learning-portal");
        response.put("deploymentTarget", "Apache Tomcat");
        response.put("recommendedOs", "Amazon Linux");
        return response;
    }

    private Map<String, Object> findTopic(String slug) {
        return buildTopics().stream()
                .filter(topic -> topic.get("slug").equals(slug))
                .findFirst()
                .orElse(null);
    }

    private List<Map<String, Object>> buildTopics() {
        return List.of(
                createTopic(
                        "linux-essentials",
                        "Linux Essentials",
                        "Beginner",
                        "Master commands, permissions, package management, processes, and shell scripting basics.",
                        "Linux is the backbone of modern DevOps environments because most servers, containers, and cloud workloads run on Linux-based operating systems. Start with command line usage, file navigation, permissions, services, package management, and shell scripting because these skills support everyday administration and automation work.",
                        List.of("File system navigation and file permissions", "Package installation and process management", "User management, services, and shell scripting"),
                        List.of("Why is Linux preferred for servers and DevOps workflows?", "What is the difference between chmod 755 and 644?", "How do you check memory, CPU, and running services on Linux?"),
                        List.of("Use ls, cd, pwd, cp, and mv confidently", "Understand systemctl, ps, top, and log inspection", "Write simple Bash scripts for repetitive admin tasks"),
                        "Practice one command group at a time, then apply it on EC2 or a Linux VM until it feels natural."
                ),
                createTopic(
                        "git-and-github",
                        "Git and GitHub",
                        "Core",
                        "Understand branching, pull requests, merges, rebasing, and collaboration workflows.",
                        "Git and GitHub are essential for version control, collaboration, rollback, branching, and CI/CD integration in modern software delivery pipelines. This topic covers repository creation, commits, branches, merging, pull requests, conflict resolution, and how teams collaborate through GitHub workflows.",
                        List.of("Commits, branches, merges, and rebasing", "Remote repositories, pull requests, and reviews", "Branching strategy for team collaboration"),
                        List.of("What is the difference between git fetch and git pull?", "Why do teams use pull requests?", "How do you resolve merge conflicts safely?"),
                        List.of("Create clean commit history with meaningful messages", "Understand origin, upstream, branch checkout, and branch protection", "Use GitHub as a source for CI/CD triggers"),
                        "Treat Git as both a code history tool and a collaboration tool, not just a backup system."
                ),
                createTopic(
                        "maven-and-build-tools",
                        "Maven and Build Tools",
                        "Build",
                        "Learn project structure, dependencies, lifecycle goals, packaging, and WAR/JAR generation.",
                        "Maven automates dependency management, packaging, and the application build lifecycle, which makes Java application delivery consistent and repeatable. In DevOps, Maven is important because it standardizes how code is compiled, tested, packaged, and prepared for deployment into JAR or WAR artifacts.",
                        List.of("Project structure and pom.xml essentials", "Lifecycle goals such as clean, compile, test, package, and install", "Dependency management and plugin configuration"),
                        List.of("What happens when you run mvn clean package?", "What is the role of pom.xml in a Maven project?", "How does Maven help in CI/CD pipelines?"),
                        List.of("Know the standard Maven directory structure", "Understand how dependencies are downloaded and reused", "Build WAR files for Tomcat deployment and JAR files for standalone apps"),
                        "Link each Maven command to a real pipeline stage so you remember why it matters in delivery automation."
                ),
                createTopic(
                        "jenkins-and-cicd",
                        "Jenkins and CI/CD",
                        "Automation",
                        "Build pipelines for source checkout, testing, packaging, Docker image creation, and deployment.",
                        "Jenkins helps automate the build, test, packaging, and deployment flow so changes move faster and more reliably from source control to target environments. This topic explains how Jenkins jobs, pipelines, plugins, credentials, webhooks, and post-build actions work together in a DevOps workflow.",
                        List.of("Freestyle jobs and pipeline concepts", "Credentials, tools configuration, and webhooks", "Build, test, artifact storage, and deployment stages"),
                        List.of("What is the difference between CI and CD?", "How does Jenkins integrate with GitHub and Tomcat?", "Why are credentials and environment separation important in Jenkins?"),
                        List.of("Configure tools like JDK and Maven in Jenkins", "Use Jenkins plugins for deployment and artifact publishing", "Understand job execution logs and failure troubleshooting"),
                        "Think of Jenkins as the orchestrator that connects source code, build tools, artifact storage, and deployment targets."
                ),
                createTopic(
                        "docker-and-containers",
                        "Docker and Containers",
                        "Containers",
                        "Explore images, containers, Dockerfiles, networking, volumes, and container lifecycle management.",
                        "Docker packages applications and their dependencies into portable containers so they behave consistently across development, testing, and production environments. This topic covers Docker images, Dockerfiles, containers, networking, volumes, and how containerization improves reliability and deployment consistency.",
                        List.of("Images, containers, Dockerfiles, and layers", "Container networking, ports, and volumes", "Build, run, inspect, and manage containers"),
                        List.of("What is the difference between an image and a container?", "Why are containers useful in DevOps?", "How do you expose an application port using Docker?"),
                        List.of("Write simple Dockerfiles for Java and web applications", "Understand mapping ports and persisting data with volumes", "Use Docker to reduce environment mismatch issues"),
                        "Containers become much easier when you connect each command to the lifecycle: build, run, inspect, stop, and remove."
                ),
                createTopic(
                        "kubernetes-basics",
                        "Kubernetes Basics",
                        "Orchestration",
                        "Study pods, deployments, services, config maps, secrets, and rollout strategies.",
                        "Kubernetes helps manage containerized applications at scale by handling scheduling, self-healing, service exposure, configuration, and rollout strategies. A DevOps learner should understand pods, deployments, replica sets, services, config maps, secrets, and how Kubernetes abstracts infrastructure complexity.",
                        List.of("Pods, deployments, replica sets, and services", "Config maps, secrets, namespaces, and scaling", "Rolling updates, rollback, and service discovery"),
                        List.of("What is a pod in Kubernetes?", "Why do we use deployments?", "How does a service expose an application?"),
                        List.of("Read basic Kubernetes YAML files with confidence", "Understand why Kubernetes is used after containerization", "Learn scaling and rollout concepts through deployments"),
                        "Start with architecture concepts before deep commands, because Kubernetes becomes easier once the moving parts make sense together."
                ),
                createTopic(
                        "cloud-fundamentals",
                        "Cloud Fundamentals",
                        "Cloud",
                        "Compare AWS and Azure basics, VM deployment, storage, networking, IAM, and scaling concepts.",
                        "Cloud platforms like AWS and Azure provide elastic infrastructure, managed services, and networking building blocks that modern DevOps teams use every day. This topic introduces compute, storage, networking, IAM, scaling, and how cloud services support application hosting and automation workflows.",
                        List.of("Compute services, virtual machines, and storage", "Networking basics including VPCs, subnets, and security groups", "IAM, scalability, and shared responsibility"),
                        List.of("What is the role of IAM in AWS?", "What is the purpose of a security group?", "How does cloud scalability support DevOps?"),
                        List.of("Understand the difference between compute, storage, and networking services", "Use EC2 or Azure VMs as practical learning platforms", "Connect deployment, security, and automation concepts together"),
                        "Anchor cloud learning around one real deployment flow so services feel connected instead of isolated."
                ),
                createTopic(
                        "monitoring-and-logging",
                        "Monitoring and Logging",
                        "Observability",
                        "Learn how metrics, alerts, dashboards, and logs improve reliability and incident response.",
                        "Monitoring and logging help teams understand system health, troubleshoot incidents, measure reliability, and improve performance over time. This topic explains metrics, dashboards, logs, alerts, and how observability helps operations teams react quickly and prevent repeated failures.",
                        List.of("Metrics, logs, traces, and dashboards", "Alerting, thresholds, and incident response", "Application logs, server logs, and build/deployment logs"),
                        List.of("Why is monitoring important after deployment?", "What is the difference between logging and monitoring?", "How do alerts help reduce downtime?"),
                        List.of("Read system and application logs with purpose", "Understand how dashboards reveal trends and incidents", "Use observability to improve troubleshooting and reliability"),
                        "A good DevOps workflow does not stop at deployment; it continues through monitoring, alerts, and continuous improvement."
                )
        );
    }

    private Map<String, Object> createTopic(
            String slug,
            String title,
            String tag,
            String description,
            String overview,
            List<String> concepts,
            List<String> interviewQuestions,
            List<String> takeaways,
            String studyTip) {
        Map<String, Object> topic = new LinkedHashMap<>();
        topic.put("slug", slug);
        topic.put("title", title);
        topic.put("tag", tag);
        topic.put("description", description);
        topic.put("overview", overview);
        topic.put("concepts", concepts);
        topic.put("interviewQuestions", interviewQuestions);
        topic.put("takeaways", takeaways);
        topic.put("studyTip", studyTip);
        return topic;
    }
}
