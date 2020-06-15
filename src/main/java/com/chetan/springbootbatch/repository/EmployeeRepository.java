package com.chetan.springbootbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chetan.springbootbatch.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
