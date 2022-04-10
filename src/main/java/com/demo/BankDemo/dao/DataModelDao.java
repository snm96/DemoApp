package com.demo.BankDemo.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.demo.BankDemo.beans.DataModel;

@Repository
public class DataModelDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public void batchInsert(List<DataModel> modelsToInsert) {
		StopWatch timer = new StopWatch();

		final String sql = "INSERT INTO DATA_MODEL (SOURCE_NAME, DESTINATION_NAME, TRANSACTION_TYPE, AMOUNT, CREATED_DATE, STATUS)"
				+ " VALUES(?,?,?,?,?,?)";

		timer.start();
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				DataModel model = modelsToInsert.get(i);
				ps.setString(1, model.getSourceName());
				ps.setString(2, model.getDestinationName());
				ps.setString(3, model.getTransactionType());
				ps.setDouble(4, model.getAmount());
				ps.setDate(5, new java.sql.Date(model.getCreatedDate().getTime()));
				ps.setString(6, "UNRESOLVED");
			}

			@Override
			public int getBatchSize() {
				return modelsToInsert.size();
			}

		});

		timer.stop();
		System.out.println("Time Taken for insert operation :: " + timer.getTotalTimeSeconds());
	}
	
	public void batchInsert(List<DataModel> models, String side, String status) {

		final String sql = "INSERT INTO DATA_MODEL (SOURCE_NAME, DESTINATION_NAME, TRANSACTION_TYPE, AMOUNT, CREATED_DATE, STATUS, TRANSACTION_ID, SIDE)"
				+ " VALUES(?,?,?,?,?,?,?,?)";
		StopWatch timer = new StopWatch();
		timer.start();
		jdbcTemplate.batchUpdate(sql, models, models.size(), new ParameterizedPreparedStatementSetter<DataModel>() {
			public void setValues(PreparedStatement ps, DataModel model) throws SQLException {
				ps.setString(1, model.getSourceName());
				ps.setString(2, model.getDestinationName());
				ps.setString(3, model.getTransactionType());
				ps.setDouble(4, model.getAmount());
				ps.setDate(5, new java.sql.Date(model.getCreatedDate().getTime()));
				ps.setString(6, status);
				ps.setLong(7, model.getTransactionId());
				ps.setString(8, side);
			}
		});
		timer.stop();
		System.out.println("Time Taken for insert operation :: " + timer.getTotalTimeSeconds());

	}

	public void batchUpdate(String status) {
		final String sql = "UPDATE DATA_MODEL SET STATUS='" + status + "' WHERE STATUS!='" + status + "'";
		
		StopWatch timer = new StopWatch();
		timer.start();
		jdbcTemplate.update(sql);
		timer.stop();
		System.out.println("Time Taken for update operation :: " + timer.getTotalTimeSeconds());
	}

}
