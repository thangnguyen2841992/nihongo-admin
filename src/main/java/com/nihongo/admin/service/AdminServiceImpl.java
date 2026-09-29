package com.nihongo.admin.service;

import com.nihongo.admin.model.UserDTO;
import com.nihongo.admin.repository.UserClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserClient userClient;

    public AdminServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public List<UserDTO> getAllUser() {
        return this.userClient.getAllUsers();
    }

}
