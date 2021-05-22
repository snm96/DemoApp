package com.demo.BankDemo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.demo.BankDemo.beans.BankAccount;
import com.demo.BankDemo.beans.Employee;

public class ExcelUtils {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Employee> employeeList;

	public ExcelUtils(List<Employee> employeeList) {
		this.employeeList = employeeList;
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Employee Details");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "EMPLOYEE_ID", style);
		createCell(row, 1, "EMPLOYEE_NAME", style);
		createCell(row, 2, "BANK_ACCOUNT_ID", style);
		createCell(row, 3, "BANK_NAME", style);
		createCell(row, 4, "ACCOUNT_TYPE", style);
	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}
		else if (value instanceof Long){
			cell.setCellValue((Long) value);
		}else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (Employee emp : employeeList) {
			for (BankAccount acc : emp.getAccount()) {
				Row row = sheet.createRow(rowCount++);
				int columnCount = 0;
				createCell(row, columnCount++, emp.getEmployeeId(), style);
				createCell(row, columnCount++, emp.getEmployeeName(), style);
				createCell(row, columnCount++, acc.getBankAccountId(), style);
				createCell(row, columnCount++, acc.getBankName(), style);
				createCell(row, columnCount++, acc.getAccountType(), style);
			}
		}
	}

	public void export(String exportPath) throws IOException {
		writeHeaderLine();
		writeDataLines();
		File file = new File(exportPath);
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		workbook.close();
	}

}
