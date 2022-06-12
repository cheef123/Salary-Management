package com.cognizant.upload.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flag")
public class ConcurrentFlag {
	@Id
	private int id;
	private boolean concurrent;
}
