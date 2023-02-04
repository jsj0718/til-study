package me.jsj.demospringsecurity.account.dto;

import lombok.Data;

@Data
public class CreateAccountRequestDto {
    private String username;
    private String password;
    private String role;
}
