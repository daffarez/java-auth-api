package com.example.javaauthapi.controller.v1;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("v1/api/health")
public class HealthController {

    @GetMapping("/check")
    public ResponseEntity<String> healthCheck(HttpServletRequest request) {
        return ResponseEntity.ok("Status: OK");
    }
}
