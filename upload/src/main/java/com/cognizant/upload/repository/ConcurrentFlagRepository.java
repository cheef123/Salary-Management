package com.cognizant.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.upload.entity.ConcurrentFlag;

@Repository
public interface ConcurrentFlagRepository extends JpaRepository<ConcurrentFlag, Integer> {

}
