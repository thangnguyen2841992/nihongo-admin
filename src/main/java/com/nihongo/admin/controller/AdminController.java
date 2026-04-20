package com.nihongo.admin.controller;

import com.nihongo.admin.service.AdminService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/home")
    public Map<String, Object> home(HttpServletRequest request) {

        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        Map<String, Object> response = new HashMap<>();

        if (token != null) {
            String name = adminService.extractUsername(token).getFullName();
            response.put("name", name);
            response.put("isLoggedIn", true);
        } else {
            response.put("isLoggedIn", false);
        }

        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(this.adminService.getAllUser(), HttpStatus.OK);
    }
}
