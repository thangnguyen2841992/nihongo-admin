package com.nihongo.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nihongo.admin.model.UserDTO;
import com.nihongo.admin.repository.UserClient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserClient userClient;

    public AdminServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDTO extractUsername(String token) {
        try {
            String[] parts = token.split("\\.");

            String payload = new String(Base64.getDecoder().decode(parts[1]));

            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(payload, Map.class);
            UserDTO newUserDto = new UserDTO();
            newUserDto.setEmail((String) map.get("email"));
            newUserDto.setFullName((String) map.get("name"));
            return newUserDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UserDTO> getAllUser() {
        return this.userClient.getAllUsers();
    }

}
