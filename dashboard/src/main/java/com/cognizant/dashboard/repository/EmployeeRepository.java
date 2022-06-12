package com.cognizant.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.dashboard.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	// Order by ascending/descending
	List<Employee> findBySalaryBetweenOrderByIdAsc(double min, double max);
	List<Employee> findBySalaryBetweenOrderByIdDesc(double min, double max);
	
	List<Employee> findBySalaryBetweenOrderByNameAsc(double min, double max);
	List<Employee> findBySalaryBetweenOrderByNameDesc(double min, double max);
	
	List<Employee> findBySalaryBetweenOrderByLoginAsc(double min, double max);
	List<Employee> findBySalaryBetweenOrderByLoginDesc(double min, double max);
	
	List<Employee> findBySalaryBetweenOrderBySalaryAsc(double min, double max);
	List<Employee> findBySalaryBetweenOrderBySalaryDesc(double min, double max);
//	
//	//Filter based on salary range
//	List<Employee> findBySalaryBetween(double minSalary, double maxSalary);
	
	//Custom
	@Query(value = "select * from Employee e where e.salary between ?1 and ?2 order by ?3 ASC limit ?4 offset ?5", nativeQuery = true)
	List<Employee> findAllEmployeesAsc(double minSalary, double maxSalary, String sort, int limit, int offset);
	
	@Query(value = "select * from Employee e where e.salary between ?1 and ?2 order by ?3 DESC limit ?4 offset ?5", nativeQuery = true)
	List<Employee> findAllEmployeesDesc(double minSalary, double maxSalary, String sort, int limit, int offset);

	@Query(value = "select * from Employee e order by e.?1",nativeQuery = true)
	List<Employee> testing(String sort);

}
