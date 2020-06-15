package com.chetan.springbootbatch.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chetan.springbootbatch.model.Employee;
import com.chetan.springbootbatch.repository.EmployeeRepository;

@Component
public class DataBaseWriter implements ItemWriter<Employee> {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void write(List<? extends Employee> employee) throws Exception {
		System.out.println("Data saved for Employees: "+ employee);
		
		employeeRepository.save(employee);
		
		
	}

}
