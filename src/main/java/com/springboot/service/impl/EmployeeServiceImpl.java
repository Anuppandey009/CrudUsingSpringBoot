package com.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.exception.ResourceNotFoundException;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import com.springboot.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository employeeRepository;
	
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}



	@Override
	public Employee saveEmployee(Employee employee) {
		
		return employeeRepository.save(employee);
	}



	@Override
	public List<Employee> getAllEmployees() {
		
		return employeeRepository.findAll();
	}



	@Override
	public Employee getEmployeeById(long id) {
	
		Optional<Employee> employee = employeeRepository.findById(id);
		
		if(employee.isPresent()) {
			 return employee.get();
		}
		else {
	         throw new ResourceNotFoundException("Employee", "Id", id);
		}
	}



	@Override
	public Employee updateEmployee(Employee employee, long id) {
		// step1 we need to check whether employee with given id exists in the database or not
		
		Employee existingEmployee= employeeRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Employee", "Id", id));
		
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());

      employeeRepository.save(existingEmployee);
      return existingEmployee;
	}



	@Override
	public void deleteEmployee(long id) {
		employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee", "Id", id));
		employeeRepository.deleteById(id);
		
	}

}
