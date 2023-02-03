package com.cronberry.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.cronberry.service.constants.CronberryEnums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_roles")
public class UsersRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleid;
	
	
	@Column(name = "name")
	private String name;

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	CronberryEnums.Status status;

	@CreationTimestamp
	@Column(name = "created")
	private Timestamp created;

		

}
