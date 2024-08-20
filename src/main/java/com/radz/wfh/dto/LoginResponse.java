package com.radz.wfh.dto;

import com.radz.wfh.constant.LoginStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private Long id;
    private String name;
    private LoginStatus loginStatus;
}
