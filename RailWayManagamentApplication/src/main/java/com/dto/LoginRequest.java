package com.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
	public String getUsername() {
		// TODO 
		return this.username;
	}
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
}