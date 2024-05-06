package com.learningtech.model;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class Users extends BaseEntity implements UserDetails{

	private static final long serialVersionUID = 101L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name="user_id")
	private String userId;
	
	@Column(name="first_name",nullable = false)
	private String firstName;
	
	@Column(name="last_name",nullable = false)
	private String lastName;
	
	@Column(name="user_email",nullable = false)
	private String userEmail;
	
	@Column(name="user_name",nullable = false,unique = true)
	private String userName;
	
	@Column(name="password",nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name="role",nullable = false)
	private Role role;

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
