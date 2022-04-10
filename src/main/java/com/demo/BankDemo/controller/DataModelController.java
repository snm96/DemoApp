package com.demo.BankDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.BankDemo.beans.DataModel;
import com.demo.BankDemo.dao.DataModelDao;
import com.demo.BankDemo.repository.DataModelRepository;

@RestController
public class DataModelController {
	
	@Autowired
	private DataModelRepository repo;
	
	@Autowired
	private DataModelDao dataModelDao;

	@GetMapping(value = "/getDataModels")
	public ResponseEntity<Page<DataModel>> getDataModels() {
		int pageNo = 0;
		int pageSize = 500;
		Pageable page = PageRequest.of(pageNo, pageSize);
		return new ResponseEntity<>(repo.findAll(page), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getUnMatchedItems")
	public ResponseEntity<List<DataModel>> getUnMatchedItems() {
		int pageNo = 0;
		int pageSize = 500;
		Pageable page = PageRequest.of(pageNo, pageSize);
		return new ResponseEntity<>(repo.findByStatusIs("OPEN", page), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getMatchedItems")
	public ResponseEntity<List<DataModel>> getMatchedItems() {
		int pageNo = 0;
		int pageSize = 500;
		Pageable page = PageRequest.of(pageNo, pageSize);
		return new ResponseEntity<>(repo.findByStatusIs("RESOLVED", page), HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateDataModelStatus")
	public ResponseEntity<String> updateDataModelStatus( @RequestParam("status") String status) {
		dataModelDao.batchUpdate(status);
		return new ResponseEntity<>("Update Successful", HttpStatus.OK);
	}
	
}
