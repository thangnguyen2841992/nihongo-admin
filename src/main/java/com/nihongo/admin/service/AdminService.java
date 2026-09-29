package com.nihongo.admin.service;

import com.nihongo.admin.model.UserDTO;

import java.util.List;

public interface AdminService {
    List<UserDTO> getAllUser();
}
