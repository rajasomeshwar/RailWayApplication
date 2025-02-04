package com.dto;



public class RegisterRequest {
    private String username;
    private String password;
    private String role;
    
	public RegisterRequest(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	public String getRole() {
		// TODO Auto-generated method stub
		return this.role;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "RegisterRequest [username=" + username + ", password=" + password + ", role=" + role + "]";
	}
	
}
