package com.demo.BankDemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.BankDemo.beans.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findAll();
}
