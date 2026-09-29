package com.nihongo.admin.controller;

import com.nihongo.admin.service.AdminService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<String, Object> home(@AuthenticationPrincipal Jwt jwt) {
        String name = jwt.getClaimAsString("name");
        String email = jwt.getClaimAsString("email");
        return Map.of("name", name == null ? jwt.getSubject() : name,
                "email", email == null ? "" : email, "isLoggedIn", true);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(this.adminService.getAllUser(), HttpStatus.OK);
    }
}
