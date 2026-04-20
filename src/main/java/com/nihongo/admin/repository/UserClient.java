package com.nihongo.admin.repository;

import com.nihongo.admin.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "gateway-service", url = "http://localhost:8082")
public interface UserClient {
    @GetMapping("/api/users/getAllUsers")
    List<UserDTO> getAllUsers();
}
