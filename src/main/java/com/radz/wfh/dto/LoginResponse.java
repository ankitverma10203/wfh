package com.radz.wfh.dto;

import com.radz.wfh.constant.LoginStatus;
import com.radz.wfh.constant.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private Long id;
    private String name;
    private Role role;
    private LoginStatus loginStatus;
}
