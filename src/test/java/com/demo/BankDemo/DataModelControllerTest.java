package com.demo.BankDemo;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.demo.BankDemo.controller.DataModelController;

@RunWith(SpringRunner.class)
@WebMvcTest(DataModelController.class)
@AutoConfigureMockMvc
public class DataModelControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void checkUnMacthedRecordCount() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
	            .get("/getUnMatchedItems")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(6)));
	}

}
