package com.cognizant.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.upload.entity.ConcurrentFlag;
import com.prototype.auditauthentication.model.UserCredentials;

/**
 * 
 * This is an interface which is used to communicate with the database.
 *          Whatever the data we want to fetch from database related to
 *          {@link ConcurrentFlag} we can use this interface for this purpose. This will
 *          extend {@link JpaRepository} which is a JPA specific extension of
 *          Repository. It contains the full API of CrudRepository.So it
 *          contains API for basic CRUD operations and also API for pagination
 *          and sorting.
 *
 *  
 */
@Repository
public interface ConcurrentFlagRepository extends JpaRepository<ConcurrentFlag, Integer> {

}
