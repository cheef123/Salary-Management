package com.cognizant.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.dashboard.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	// Order by ascending/descending
	List<Employee> findAllOrderByIdAsc();
	List<Employee> findAllOrderByIdDesc();
	
	List<Employee> findAllOrderByNameAsc();
	List<Employee> findAllOrderByNameDesc();
	
	List<Employee> findAllOrderByLoginAsc();
	List<Employee> findAllOrderByLoginDesc();
	
	List<Employee> findAllOrderBySalaryAsc();
	List<Employee> findAllOrderBySalaryDesc();
	
	//Filter based on salary range
	List<Employee> findBySalaryBetween(double minSalary, double maxSalary);
	
	
}
