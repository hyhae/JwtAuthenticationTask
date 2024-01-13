package com.santechture.api.service;

import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.dto.admin.AdminDto;
import com.santechture.api.entity.Admin;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.repository.AdminRepository;
import com.santechture.api.validation.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminService {


    private final AdminRepository adminRepository;


    private final TokenService tokenService;

    public AdminService(AdminRepository adminRepository, TokenService tokenService) {
        this.adminRepository = adminRepository;
        this.tokenService = tokenService;
    }

    public ResponseEntity<GeneralResponse> login(LoginRequest request) throws BusinessExceptions {

        Admin admin = adminRepository.findByUsernameIgnoreCase(request.getUsername());

        if (Objects.isNull(admin) || !admin.getPassword().equals(request.getPassword())) {
            throw new BusinessExceptions("login.credentials.not.match");
        }
        tokenService.saveNewTokenForAdmin(admin);
        admin.setTokens(tokenService.findAllValidTokensByAdmin(admin));
        AdminDto responseDTO = new AdminDto(admin);
        return new GeneralResponse().response(responseDTO);
    }

    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsernameIgnoreCase(username);
    }
}
