package com.cronberry.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.cronberry.service.constants.CronberryEnums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Transactional
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userid;

	@NotBlank(message = "please fill your name")
	private String name;

	@Email(message = "please fill your valid email")
	@Column(unique = true)
	private String email;

	@Size(min = 0, max = 10, message = "please fill your valid Phone-Number")
	private String phonnumber;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dob;

	@Column(name = "token")
	private String token;

	@Size(min = 6, message = "password must be at least 6 characters")
	private String password;

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private Status status;

	@CreationTimestamp
	@Column
	private Timestamp created;

	private LocalDateTime updated = LocalDateTime.now();

	private String countryCodes;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapsId("roleid")
	private Set<UsersRole> roles = new HashSet<>();

}
