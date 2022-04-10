package com.demo.BankDemo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.BankDemo.beans.DataModel;

@Repository
public interface DataModelRepository extends JpaRepository<DataModel, Long> {
	
	Page<DataModel> findAll(Pageable pageable);
	
	List<DataModel> findByStatusIs(String status, Pageable pageable);
}
