package com.demo.BankDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.BankDemo.beans.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
	
  Configuration findByName(String name);
  
}