package com.cognizant.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.dashboard.entity.Employee;

/**
 * This interface helps with getting the employee details based on minSalary and
 * maxSalary specified, and will sort them in either ascending or descending
 * manner
 * 
 * @author cheef
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	List<Employee> findBySalaryBetweenOrderByIdAsc(double min, double max);

	List<Employee> findBySalaryBetweenOrderByIdDesc(double min, double max);

	List<Employee> findBySalaryBetweenOrderByNameAsc(double min, double max);

	List<Employee> findBySalaryBetweenOrderByNameDesc(double min, double max);

	List<Employee> findBySalaryBetweenOrderByLoginAsc(double min, double max);

	List<Employee> findBySalaryBetweenOrderByLoginDesc(double min, double max);

	List<Employee> findBySalaryBetweenOrderBySalaryAsc(double min, double max);

	List<Employee> findBySalaryBetweenOrderBySalaryDesc(double min, double max);

}
