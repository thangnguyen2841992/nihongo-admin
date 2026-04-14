package com.nihongo.admin.service;

import com.nihongo.admin.repository.UserClient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserClient userClient;

    public AdminServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public String getName() {
        return userClient.getName();
    }
}
