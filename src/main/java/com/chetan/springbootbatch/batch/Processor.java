package com.chetan.springbootbatch.batch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.chetan.springbootbatch.model.Employee;

@Component
public class Processor implements ItemProcessor<Employee,Employee> {
	
	private static final Map<String, String> DEPT_NAME = new HashMap<>();
	
	public Processor() {
	DEPT_NAME.put("1", "JavaTechnology");
	DEPT_NAME.put("2", "DotNetTechnology");
	DEPT_NAME.put("3", "Account");
	DEPT_NAME.put("4", "Operations");
	}

	@Override
	public Employee process(Employee employee) throws Exception {
		String deptCode = employee.getDept();
		String dept = DEPT_NAME.get(deptCode);
		employee.setDept(dept);
		System.out.println(String.format("Converted from [%s] to [%s]", deptCode, dept));
		return employee;
	}

}
