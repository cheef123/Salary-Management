package com.cognizant.upload.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class uses the concurrent field to check if an upload is ongoing at the
 * same time. Uploads will only be able to occur when concurrent flag is false.
 * This field is updated whenever an upload commences or ends.
 * 
 * @author cheef
 *
 */
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
