package com.demo.BankDemo.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.demo.BankDemo.beans.DataModel;

public class CSVHelper {

	public static final String TYPE = "text/csv";
	public static final String[] HEADERs = { "ID", "Source Name", "Destination Name", "Transaction Type", "Amount", "Created Date" };
	
	public static List<DataModel> csvToDataModelBean(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			
			List<DataModel> dataList = new ArrayList<>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				DataModel dataModelObject = new DataModel(csvRecord.get("SourceName"),
						csvRecord.get("DestinationName"), csvRecord.get("TransactionType"),
						Double.parseDouble(csvRecord.get("Amount")), new java.util.Date(), Long.parseLong(csvRecord.get("TransactionId")));
				
				dataList.add(dataModelObject);
			}
			
			return dataList;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}
	
	public static Set<DataModel> csvToDataModelSet(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			
			Set<DataModel> dataList = new HashSet<>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				DataModel dataModelObject = new DataModel(csvRecord.get("SourceName"),
						csvRecord.get("DestinationName"), csvRecord.get("TransactionType"),
						Double.parseDouble(csvRecord.get("Amount")), new java.util.Date(), Long.parseLong(csvRecord.get("TransactionId")));
				
				dataList.add(dataModelObject);
			}
			
			return dataList;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}
	
	public static void dataModelToCSV(List<DataModel> models, String reportPath) {
		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(reportPath));
				CSVPrinter csvPrinter = CSVFormat.DEFAULT.withHeader("Id", "Source Name", "Transaction Type", "Amount",
						"Created Date", "Status", "Destination Name", "Transaction Id", "Side").print(bufferedWriter)) {
			models.forEach(model -> {
				try {
					csvPrinter.printRecord(model.getId(), model.getSourceName(), model.getTransactionType(),
							model.getAmount(), model.getCreatedDate(), model.getStatus(), model.getDestinationName(),
							model.getTransactionId(), model.getSide());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Iterable<CSVRecord> getRecordIterator(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			return csvParser.getRecords();
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}
}
