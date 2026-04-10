package com.mumtaz.project4.controller;

import java.time.LocalDateTime;
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
        model.addAttribute("title", "Project 4 - Tomcat Deployment Demo");
        model.addAttribute("subtitle", "WAR-based Spring Boot application prepared for Apache Tomcat on Amazon Linux EC2.");
        model.addAttribute("buildTool", "Maven");
        model.addAttribute("deployTarget", "Apache Tomcat 10");
        model.addAttribute("runtime", "Java 17");
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
