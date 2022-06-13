package com.cognizant.upload.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class stores the information of an Employee into the database
 * @author cheef
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	@Id
	@Column(name = "id")
	private String empId;

	@Column(name = "login")
	private String empLogin;

	@Column(name = "name")
	private String empName;

	@Column(name = "salary")
	private double empSalary;

}
