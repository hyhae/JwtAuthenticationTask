package com.santechture.api.dto.admin;

import com.santechture.api.entity.Admin;
import com.santechture.api.entity.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminDto {

    private Integer adminId;

    private String username;

    private List<String> token;

    public AdminDto(Admin admin) {
        setAdminId(admin.getAdminId());
        setUsername(admin.getUsername());
        setToken(admin.getTokens().stream().map(Token::getToken).collect(Collectors.toList()));
    }

}
