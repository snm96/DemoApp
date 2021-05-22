package com.demo.BankDemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.demo.BankDemo.beans.Configuration;
import com.demo.BankDemo.beans.Employee;

import com.demo.BankDemo.repository.ConfigurationRepository;
import com.demo.BankDemo.repository.EmployeeRepository;
import com.demo.BankDemo.utils.ExcelUtils;

@Service
public class ScheduledTasksCronFromDatabaseExpression {

	@Autowired
	private ConfigurationRepository configurationRepository;

	@Autowired
	private EmployeeRepository empRepo;

	@Value("${basic.path}")
	private String path;

	@Scheduled(cron = "#{@getCronExpressionFromDb}")
	public void scheduleTasksCronFromDatabaseExpression() {
		// CODE TO GENERTAE FILE AND PLACE AT DIFFERENT PATHS
		try {
			List<Employee> employeeDetails = empRepo.findAll(Sort.by("employeeId").ascending());
			ExcelUtils utils = new ExcelUtils(employeeDetails);
			utils.export(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public String getCronExpressionFromDb() {
		Configuration configuration = configurationRepository.findByName("taskSchedule");
		return configuration.getExpression();
	}
}