package com.chun.springboot.jwt.dto;

import java.util.List;

public class JwtResponse {
    private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
    private String phone;
    private String fullName;
    private Integer status;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username, String email, String phone, String fullName,
            Integer status, List<String> roles) {
        this.token = accessToken; 
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.status = status;
        this.roles = roles;
    }

    public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getRoles() {
		return roles;
	}
}
