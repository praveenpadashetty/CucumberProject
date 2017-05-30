package com.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cucumber.api.java.en.Given;


public final class ExcelReader {

	public List<List<String>> globalList = null;

	private static ExcelReader readData = null;

	public static List<Map<String, String>> testData = null;

	private static Map<String, String> rowTestData = null;

	private ExcelReader() {
		super();
	}

	public static ExcelReader getInstance() {
		if (readData == null) {
			readData = new ExcelReader();
		}
		return readData;
	}

	@Given("^Get test data from excel sheet$")
	public Map<String, String> getTestData() throws IOException  {
//			final int rowNo = Integer.parseInt(row);
			readSheetData("./res/Test.xlsx", "Details");
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			int i = 0;
			for (Map<String, String> obj : testData) {
//				if (obj.get(Constants.FEATURE).equals(feature)) {
//					data.add(i, obj);
//					i++;
				System.out.println(obj);
//				}
			}
//			if (data.size() < rowNo) {
//				System.out.println("Please enter valid row number");
//			}
//			rowTestData = data.get(rowNo - 1);
		
		return rowTestData;
	}

	private void readSheetData(final String testDataPath, final String sheetName) throws IOException  {
		System.out.println("Reading tests from sheet [" + sheetName + "]");
		Workbook workbook = null;
		Sheet sheet = null;
		boolean isHeader = true;
		List<String> headers = new ArrayList<String>();
		Map<String, Integer> headerSeqNumber = null;
		testData = new ArrayList<Map<String, String>>();
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
			FileInputStream file = new FileInputStream(new File(testDataPath));
			if (testDataPath.endsWith("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (testDataPath.endsWith("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			sheet = workbook.getSheet(sheetName);

			for (int iRowCnt = 0; iRowCnt <= sheet.getLastRowNum(); iRowCnt++) {
				System.out.println("reading row #" + (iRowCnt + 1) + " from sheet [" + sheetName + "]");
				final Row row = sheet.getRow(iRowCnt);
				if (row != null) {
					if (isHeader) {
						final int iColCount = row.getLastCellNum();
						headers = getHeaderData(iColCount, iRowCnt, sheet, sheetName, isHeader);
						headerSeqNumber = assignHeaderSeqNumber(headers);
						isHeader = false;
					} else {
						dataMap = getDataMap(headerSeqNumber, row);
						testData.add(dataMap);
					}
				}
			}
			System.out.println("Completed reading tests from sheet [" + sheetName + "]");
		
	}

	private Map<String, String> getDataMap(final Map<String, Integer> headerSeqNumber, final Row row) {
		Iterator<String> columnsIterator = headerSeqNumber.keySet().iterator();
		final Map<String, String> dataMap = new LinkedHashMap<String, String>();
		while (columnsIterator.hasNext()) {
			final String name = columnsIterator.next();
			final Cell cell = row.getCell(headerSeqNumber.get(name));
			final String value = getCellValue(cell);
			if (name != null) {
				dataMap.put(name, value);
			}
		}
		return dataMap;
	}

	private Map<String, Integer> assignHeaderSeqNumber(final List<String> headers) {
		final Map<String, Integer> headerSeqNumber = new LinkedHashMap<String, Integer>();
		int i = 0;
		for (String columnName : headers) {
			headerSeqNumber.put(columnName, i);
			i++;
		}
		return headerSeqNumber;

	}

	private List<String> getHeaderData(final int iColCount, final int iRowCnt, final Sheet sheet,
			final String sheetName, boolean isHeader)  {
		final List<String> headers = new ArrayList<String>();
		for (int jColCnt = 0; jColCnt < iColCount; jColCnt++) {
			String colValue = null;
			System.out.println("reading cell value for #" + (iRowCnt + 1) + ",#" + (jColCnt + 1) + " from sheet [" + sheetName
					+ "]");
			final Row rowObj = sheet.getRow(iRowCnt);
			final Cell cell = rowObj.getCell(jColCnt);
			colValue = getCellValue(cell);
			headers.add(colValue);
		}
		return headers;
	}

	private String getCellValue(final Cell cell) {
		String value = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				value = Double.toString(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			}
		}
		return value;
	}

	public Map<String, String> getRowTestData() {
		return rowTestData;
	}
}