package com.demo.BankDemo;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.BankDemo.service.ScheduledTasksCronFromDatabaseExpression;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankDemoApplicationTests {

	@MockBean 
	private ScheduledTasksCronFromDatabaseExpression scheduledService;
	
	@Test
	public void loadFileAndReconcileData() {
		assertTrue(scheduledService.readAndInsertRecordsInDB());
	}
	
}
