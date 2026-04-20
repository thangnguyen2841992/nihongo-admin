package com.nihongo.admin.service;

import com.nihongo.admin.model.UserDTO;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface AdminService {
    UserDTO extractUsername(String token);

    List<UserDTO> getAllUser();
}
