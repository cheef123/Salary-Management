package com.cognizant.dashboard.entity;

import javax.persistence.Entity;
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
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	
	@Id
	private String id;
	private String name;
	private String login;
	private double salary;

}
