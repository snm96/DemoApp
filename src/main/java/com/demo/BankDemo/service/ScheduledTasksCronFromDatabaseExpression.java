package com.demo.BankDemo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.demo.BankDemo.beans.Configuration;
import com.demo.BankDemo.beans.DataModel;
import com.demo.BankDemo.beans.Employee;
import com.demo.BankDemo.repository.ConfigurationRepository;
import com.demo.BankDemo.repository.DataModelRepository;
import com.demo.BankDemo.repository.EmployeeRepository;
import com.demo.BankDemo.utils.CSVHelper;
import com.demo.BankDemo.utils.ExcelUtils;

@Service
public class ScheduledTasksCronFromDatabaseExpression {

	@Autowired
	private ConfigurationRepository configurationRepository;

	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private MatchingService matchingService;
	
	@Autowired
	private DataModelRepository repo;

	@Value("${basic.path}")
	private String path;
	
	@Value("${basic.srcFilePath}")
	private String srcFilePath;
	
	@Value("${basic.destFilePath}")
	private String destFilePath;
	
	@Value("${basic.leftFilePath}")
	private String leftFilePath;
	
	@Value("${basic.rightFilePath}")
	private String rightFilePath;
	
	@SuppressWarnings("resource")
	@Scheduled(cron = "#{@getCronForDataModelUpload}")
	public Boolean readAndInsertRecordsInDB() {
		try {
			InputStream leftStream = new FileInputStream(leftFilePath);
			InputStream rightStream = new FileInputStream(rightFilePath);
			
			if(leftStream == null || rightStream == null)
				throw new RuntimeException("Make Sure All Required Files are available on Source Path..");
			
			
			Set<DataModel> leftRecords = CSVHelper.csvToDataModelSet(leftStream);
			Set<DataModel> rightRecords = CSVHelper.csvToDataModelSet(rightStream);
			
			matchingService.processReconciliation(leftRecords, rightRecords);
			
			archiveProcessedFiles();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Scheduled(cron = "#{@getCronForReportExport}")
	public void exportReport() {
		try {
			destFilePath = destFilePath + "report.csv";
			CSVHelper.dataModelToCSV(repo.findAll(), destFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void archiveProcessedFiles() {
		try {
			final String processedSuffix = "_Processed";

			// source files
			File leftFile = new File(leftFilePath);
			File rightFile = new File(rightFilePath);

			// target files
			File leftArcFile = new File(destFilePath + leftFile.getName() + processedSuffix);
			File rightArcFile = new File(destFilePath + rightFile.getName() + processedSuffix);

			// move the files from source to target path
			Files.move(leftFile.toPath(), leftArcFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.move(rightFile.toPath(), rightArcFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "#{@getCronExpressionFromDb}")
	public void scheduleTasksCronFromDatabaseExpression() {
		// CODE TO GENERTAE FILE WITH DETAILS OF ACCOUNTS ASSOCIATED TO EMPLOYEE AND PLACE AT DIFFERENT PATHS BASED ON ENV
		try {
			List<Employee> employeeDetails = empRepo.findAll(Sort.by("employeeId").ascending());
			ExcelUtils utils = new ExcelUtils(employeeDetails);
			utils.export(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public String getCronForDataModelUpload() {
		Configuration configuration = configurationRepository.findByName("dataModelTaskSchedule");
		return configuration.getExpression();
	}
	
	@Bean
	public String getCronForReportExport() {
		Configuration configuration = configurationRepository.findByName("reportSchedule");
		return configuration.getExpression();
	}
	
	@Bean
	public String getCronExpressionFromDb() {
		Configuration configuration = configurationRepository.findByName("taskSchedule");
		return configuration.getExpression();
	}
	
	

}