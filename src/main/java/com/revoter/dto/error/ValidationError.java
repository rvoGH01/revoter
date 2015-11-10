package com.revoter.dto.error;

import com.revoter.model.BaseEntity;

public class ValidationError extends BaseEntity {
	private static final long serialVersionUID = 5795254482752399347L;
	
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}