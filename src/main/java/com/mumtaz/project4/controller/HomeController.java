package com.mumtaz.project4.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "DevOps Learning Portal");
        model.addAttribute("subtitle", "A public learning portal for DevOps fundamentals, interview preparation, hands-on labs, and project-based practice.");
        model.addAttribute("heroTag", "Learn. Practice. Deploy.");
        model.addAttribute("learningPaths", List.of("Beginner Path", "Interview Prep", "Hands-on Labs", "Project Track"));
        model.addAttribute("stats", List.of(
                Map.of("label", "Core Modules", "value", "10+"),
                Map.of("label", "Interview Questions", "value", "75+"),
                Map.of("label", "Hands-on Labs", "value", "18"),
                Map.of("label", "Project Guides", "value", "04")
        ));
        model.addAttribute("topics", List.of(
                Map.of("title", "Linux Essentials", "tag", "Beginner", "description", "Master commands, permissions, package management, processes, and shell scripting basics."),
                Map.of("title", "Git and GitHub", "tag", "Core", "description", "Understand branching, pull requests, merges, rebasing, and collaboration workflows."),
                Map.of("title", "Maven and Build Tools", "tag", "Build", "description", "Learn project structure, dependencies, lifecycle goals, packaging, and WAR/JAR generation."),
                Map.of("title", "Jenkins and CI/CD", "tag", "Automation", "description", "Build pipelines for source checkout, testing, packaging, Docker image creation, and deployment."),
                Map.of("title", "Docker and Containers", "tag", "Containers", "description", "Explore images, containers, Dockerfiles, networking, volumes, and container lifecycle management."),
                Map.of("title", "Kubernetes Basics", "tag", "Orchestration", "description", "Study pods, deployments, services, config maps, secrets, and rollout strategies."),
                Map.of("title", "Cloud Fundamentals", "tag", "Cloud", "description", "Compare AWS and Azure basics, VM deployment, storage, networking, IAM, and scaling concepts."),
                Map.of("title", "Monitoring and Logging", "tag", "Observability", "description", "Learn how metrics, alerts, dashboards, and logs improve reliability and incident response.")
        ));
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
        model.addAttribute("resources", List.of(
                "Command cheat sheets for Linux, Git, Docker, and Kubernetes",
                "Step-by-step deployment notes for Amazon Linux and Tomcat",
                "Topic roadmaps for beginner, intermediate, and interview preparation",
                "Quick revision cards for important DevOps concepts"
        ));
        model.addAttribute("lastUpdated", LocalDateTime.now());
        return "index";
    }

    @GetMapping("/api/health")
    @ResponseBody
    public Map<String, Object> health() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "UP");
        response.put("application", "project-4-tomcat-war");
        response.put("deploymentTarget", "Apache Tomcat");
        response.put("recommendedOs", "Amazon Linux");
        return response;
    }
}
