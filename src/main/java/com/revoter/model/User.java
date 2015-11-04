package com.revoter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = -4372211868888224182L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private long id;
	
	@Column(name="username")
	@NotEmpty
	private String username;
	
	@Column(name="password")
	@NotEmpty
	@JsonIgnore
	private String password;
	
	@Column(name="first_name")
	@NotEmpty
	private String firstName;
	
	@Column(name="last_name")
	@NotEmpty
	private String lastName;
	
	@Column(name="admin", columnDefinition="char(3)")
	@Type(type="yes_no")
	@NotEmpty
	private boolean admin;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}
}
