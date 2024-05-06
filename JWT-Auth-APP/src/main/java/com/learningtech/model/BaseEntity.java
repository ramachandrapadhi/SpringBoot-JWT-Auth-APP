package com.learningtech.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name="created_at",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdAt;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_at", columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updatedAt;
	
	@Column(name="updated_by")
	private String updatedBy;
}
