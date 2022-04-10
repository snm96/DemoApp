package com.demo.BankDemo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.demo.BankDemo.beans.DataModel;
import com.demo.BankDemo.dao.DataModelDao;

@Service
public class MatchingService {
	
	@Autowired
	private DataModelDao dataModelDao;

	public void processReconciliation(Set<DataModel> leftRecords, Set<DataModel> rightRecords) {
		List<DataModel> leftBreakRecords = new ArrayList<>();
		List<DataModel> rightBreakRecords = new ArrayList<>();
		List<DataModel> leftMatchRecords = new ArrayList<>();
		List<DataModel> rightMatchRecords = new ArrayList<>();
		
		StopWatch timer = new StopWatch();
		timer.start();
		Set<DataModel> leftRecordsCopy = new HashSet<>(leftRecords);
		Set<DataModel> rightRecordsCopy = new HashSet<>(rightRecords);
		
		
		if (leftRecords != null && rightRecords != null) {
			leftRecords.removeAll(rightRecords);
			leftBreakRecords.addAll(leftRecords);
			
			rightRecords.removeAll(leftRecordsCopy);
			rightBreakRecords.addAll(rightRecords);
			
			leftRecordsCopy.removeAll(leftBreakRecords);
			leftMatchRecords.addAll(leftRecordsCopy);
			
			rightRecordsCopy.removeAll(rightBreakRecords);
			rightMatchRecords.addAll(rightRecordsCopy);
		}
		timer.stop();
		System.out.println("Time Taken ::" + timer.getTotalTimeSeconds());
		System.out.println("Left Matched Record Count :: " + leftMatchRecords.size());
		System.out.println("Right Matched Record Count :: " + rightMatchRecords.size());
		System.out.println("Left Break Record Count :: " + leftBreakRecords.size());
		System.out.println("Right Break Record Count :: " + rightBreakRecords.size());
		dataModelDao.batchInsert(leftMatchRecords, "LEFT", "RESOLVED");
		dataModelDao.batchInsert(rightMatchRecords, "RIGHT", "RESOLVED");
		dataModelDao.batchInsert(new ArrayList<DataModel>(leftBreakRecords), "LEFT", "OPEN");
		dataModelDao.batchInsert(new ArrayList<DataModel>(rightBreakRecords), "RIGHT", "OPEN");
	}

}
