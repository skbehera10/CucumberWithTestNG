package com.sauce.demo.automation.utilities;

import static com.sauce.demo.automation.utilities.AppConstants.EXCEL;
import static com.sauce.demo.automation.utilities.AppConstants.TESTDATA_FILENAME;
import static com.sauce.demo.automation.utilities.AppConstants.TESTDATA_PATH;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sauce.demo.automation.projectlib.GlobalVariables;

import org.apache.poi.ss.usermodel.DataFormatter;

public class ExcelHelper {

	private static XSSFWorkbook testDataWorkBook = null;
	private static ExcelHelper helperObj;
	private Map<String, String> testDataMap = null;
	private static Logger logger = Logger.getLogger(ExcelHelper.class);

	public ExcelHelper() {
	}

	public static ExcelHelper getInstance() {
		if (helperObj == null) {
			synchronized (ExcelHelper.class) {
				if (helperObj == null) {
					helperObj = new ExcelHelper();
					helperObj.loadTestDataWorkBook();
				}
			}
		}
		return helperObj;
	}

	public static void closeExcelInstance() {
		try {
			testDataWorkBook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadTestDataWorkBook() {
		try {
			if (testDataWorkBook == null) {
				String testDataFileName = getTestDataFileName();
				InputStream resourceStream = getClass().getResourceAsStream(getTestDataFileName());
				testDataWorkBook = new XSSFWorkbook(resourceStream);
				logger.info("-----------------------------------------------------------------------------------------------------");
				logger.info("Loaded test data file: " + testDataFileName);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getTestDataFileName() {
		if (PropHolder.getEnvironment() == null) {
			return TESTDATA_PATH + TESTDATA_FILENAME + EXCEL;
		}
		return TESTDATA_PATH + TESTDATA_FILENAME + "_" + PropHolder.getEnvironment() + EXCEL;
	}

	public Map<String, String> getDataMap() {
		return testDataMap;
	}

	public Map<String, String> getDataForScenario(String scenarioName) {
		try {
			if (testDataWorkBook == null) {
				loadTestDataWorkBook();
			} else {
				logger.info("ExcelHelper : Workbook already in the memory.");
			}
			if (scenarioName != null) {
				XSSFSheet sheet = testDataWorkBook.getSheetAt(0);
				int reqdCol = getColumnNumForScenario(scenarioName, sheet);
				if (reqdCol > 0) {
					testDataMap = new HashMap<String, String>();
					testDataMap.put("Scenario Name: ", scenarioName);
					int numRows = sheet.getPhysicalNumberOfRows();
					logger.info("ExcelHelper : Number of rows : " + numRows);
					for (int rowNum = 1; rowNum < numRows; rowNum++) {
						String attrName = getCellValue(sheet.getRow(rowNum).getCell(0));
						String attrValue = getCellValue(sheet.getRow(rowNum).getCell(reqdCol));
						testDataMap.put(attrName, attrValue);
					}
				} else {
					logger.error("No data found for scenario : " + scenarioName);
				}
			}
		} catch (Exception e) {
			logger.error("Exception caught while reading data for scenario : " + scenarioName);
			e.printStackTrace();
		}
		logger.info("ExcelHelper testDataMap : " + testDataMap);
		return testDataMap;
	}

	public Map<String, String> getEntityTestData() {
		try {
			if (testDataWorkBook == null) {
				loadTestDataWorkBook();
			} else {
				logger.info("ExcelHelper : Workbook already in the memory.");
			}
			XSSFSheet sheet = testDataWorkBook.getSheetAt(0);
			int reqdCol = getColumnNumForScenario("Creation of Audit Entity", sheet);
			if (reqdCol > 0) {
				testDataMap = new HashMap<String, String>();
				testDataMap.put("Scenario Name: ", "Creation of Audit Entity");
				int numRows = sheet.getPhysicalNumberOfRows();
				logger.info("ExcelHelper : Number of rows : " + numRows);
				for (int rowNum = 1; rowNum < numRows; rowNum++) {
					String attrName = getCellValue(sheet.getRow(rowNum).getCell(0));
					String attrValue = getCellValue(sheet.getRow(rowNum).getCell(reqdCol));
					testDataMap.put(attrName, attrValue);
				}
			}
		} catch (Exception e) {
			logger.error("Exception caught while reading data for: Creation of Audit Entity");
			e.printStackTrace();
		}
		logger.info("ExcelHelper testDataMap : " + testDataMap);
		return testDataMap;
	}
	
	
	public Map<String, String> getEntitySavedData() {
		try {
			if (testDataWorkBook == null) {
				loadTestDataWorkBook();
			} else {
				logger.info("ExcelHelper : Workbook already in the memory.");
			}
			XSSFSheet sheet = testDataWorkBook.getSheet("Saved Data");
			int reqdCol = getColumnNumForScenario("Value", sheet);
			if (reqdCol > 0) {
				testDataMap = new HashMap<String, String>();
				testDataMap.put("Data Attribute ", "Value");
				int numRows = sheet.getPhysicalNumberOfRows();
				logger.info("ExcelHelper : Number of rows : " + numRows);
				for (int rowNum = 1; rowNum < numRows; rowNum++) {
					String attrName = getCellValue(sheet.getRow(rowNum).getCell(0));
					String attrValue = getCellValue(sheet.getRow(rowNum).getCell(reqdCol));
					testDataMap.put(attrName, attrValue);
				}
			}
		} catch (Exception e) {
			logger.error("Exception caught while reading data for: Audit Saved Data");
			e.printStackTrace();
		}
		logger.info("ExcelHelper testDataMap : " + testDataMap);
		return testDataMap;
	}
	
	

	/**
	 * Function Name: writeToCell
	 * Description: This function is used to write a value into excel cell
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 27/02/2021
	 * @param rVal - This int argument holds row value
	 * @param cVal - This int argument holds column value
	 * @return val - This argument holds value that need to be written into the excel cel
	 * Usage: ExcelHelper.writeToCell(1, 1, "ENT-1398979");
	 */
	public static void writeToCell(int rVal, int cVal, String val) throws IOException {
		try {
			OutputStream os = null;
			XSSFSheet sheet = testDataWorkBook.getSheet("Saved Data");
			XSSFRow row = null;
			XSSFCell cell = null;
			row = sheet.getRow(rVal);
			if (row == null)
				row = sheet.createRow(rVal);
			cell = row.getCell(cVal);
			if (cell == null) {
				cell = row.createCell(cVal);
			}
			cell.toString();
			cell.setCellValue(val);
			os = new FileOutputStream("./src/test/resources/testdata/TestData_qa.xlsx");
			testDataWorkBook.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getDataForRPA(String scenarioName) {
		try {
			if (testDataWorkBook == null) {
				loadTestDataWorkBook();
			} else {
				logger.info("ExcelHelper : Workbook already in the memory.");
			}
			if (scenarioName != null) {
				XSSFSheet sheet = testDataWorkBook.getSheet("GRC_TestData");
				int reqdCol = getColumnNumForScenario(scenarioName, sheet);
				if (reqdCol > 0) {
					testDataMap = new LinkedHashMap<String, String>();
					testDataMap.put("Scenario Name: ", scenarioName);
					int numRows = sheet.getPhysicalNumberOfRows();
					logger.info("ExcelHelper : Number of rows : " + numRows);
					for (int rowNum = 1; rowNum < numRows; rowNum++) {
						String attrName = getCellValue(sheet.getRow(rowNum).getCell(0));
						String attrValue = getCellValue(sheet.getRow(rowNum).getCell(reqdCol));
						testDataMap.put(attrName, attrValue);
					}
				} else {
					logger.error("No data found for scenario : " + scenarioName);
				}
			}
		} catch (Exception e) {
			logger.error("Exception caught while reading data for scenario : " + scenarioName);
			e.printStackTrace();
		}
		logger.info("ExcelHelper testDataMap : " + testDataMap);
		return testDataMap;
	}

	private int getColumnNumForScenario(String scenarioName, XSSFSheet sheet) {
		Row scnNamesRow = sheet.getRow(0);
		int numCols = scnNamesRow.getPhysicalNumberOfCells();
		for (int colNum = 1; colNum <= numCols; colNum++) {
			Cell cell = scnNamesRow.getCell(colNum);
			String cellValue = getCellValue(cell);
			// if (cellValue != null && !cellValue.equals("") && cellValue.equalsIgnoreCase(scenarioName)) {
			if (cellValue != null && !cellValue.equals("") && scenarioName.contains(cellValue)) {
				return colNum;
			}
		}
		// Scenario data not supplied
		return -1;
	}

	private static String getCellValue(Cell cell) {
		try {
			switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case NUMERIC:
				DataFormatter dataFormatter = new DataFormatter();
				String cellVal = dataFormatter.formatCellValue(cell);
				return cellVal;
			default:
				break;
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}

	/*
	 * Function Name: getTestData
	 * Description: This function is used to get multiple columns data and store in list hashmap
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 06/01/2020
	 * @param sheetName - Excel sheetname
	 * Usage: List<Map<String, String>> testData = ExcelHelper.getTestData(sheetName);
	 */
	public static List<Map<String, String>> getTestData(String sheetName) {
		List<Map<String, String>> mapLists = new ArrayList<>();
		if (sheetName != null) {
			XSSFSheet sheet = testDataWorkBook.getSheet(sheetName);
			int rows = sheet.getLastRowNum();
			int cols = sheet.getRow(0).getLastCellNum();
			for (int j = 1; j < cols; j++) {
				Map<String, String> myMap = new HashMap<>();
				for (int i = 1; i < rows + 1; i++) {
					Row r = CellUtil.getRow(i, sheet);
					String key = getCellValue(CellUtil.getCell(r, 0));
					String value = getCellValue(CellUtil.getCell(r, j));
					myMap.put(key, value);
				}
				mapLists.add(myMap);
			}
		}
		return mapLists;
	}

	public static int getSheetColsNum(String sheetName) {
		int colsNum = 0;
		if (sheetName != null) {
			XSSFSheet sheet = testDataWorkBook.getSheet(sheetName);
			colsNum = sheet.getRow(0).getLastCellNum();
		}
		return colsNum;
	}

	public static List<Map<String, String>> getData(String tcID) {
		List<Map<String, String>> mapLists = new ArrayList<>();
		int c = 0;
		int tcRow = 0;
		int rowCount = 0;
		int tcLastRow = 0;
		if (tcID != null) {
			XSSFSheet sheet = testDataWorkBook.getSheet("Other");
			rowCount = sheet.getLastRowNum();
			// Get first row of TC test data
			for (int r = 0; r < rowCount; r++) {
				try {
					String tc = getCellValue(sheet.getRow(r).getCell(1));
					if (tc.equals(tcID.trim())) {
						tcRow = r;
						break;
					}
				} catch (Exception e) {
					continue;
				}
			}
			// Get last row of TC test data
			try {
				for (c = tcRow; c <= rowCount + 1; c++) {
					getCellValue(sheet.getRow(c).getCell(0));
				}
			} catch (Exception e) {
				tcLastRow = c;
			}
			int rows = tcLastRow;
			int cols = sheet.getRow(tcRow + 1).getLastCellNum();
			GlobalVariables.tcColumns = cols;
			for (int j = 1; j < cols; j++) {
				Map<String, String> myMap = new HashMap<>();
				for (int i = tcRow + 2; i < rows; i++) {
					Row r = CellUtil.getRow(i, sheet);
					String key = getCellValue(CellUtil.getCell(r, 0));
					String value = getCellValue(CellUtil.getCell(r, j));
					myMap.put(key, value);
				}
				mapLists.add(myMap);
			}
			logger.info("No. of test data sets: " + (cols - 1));
			int attributes = (tcLastRow - (tcRow + 1)) - 1;
			logger.info("No. of data attributes: " + attributes);
		}
		return mapLists;
	}

	
	public static String readFromExcel(String FileName, int rowNum, int ColNum) throws IOException{
	  
		 XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(FileName));
		 XSSFSheet myExcelSheet = myExcelBook.getSheet("Valuation Audit");
		
		String attrName = getCellValue(myExcelSheet.getRow(rowNum).getCell(ColNum));
		System.out.println("Value for row number "+rowNum+ " Column Number "+ColNum+ " is : " + attrName);	
		return attrName;
		}
	
	public static int findRow(String FileName, int Col, String cellContent) throws FileNotFoundException, IOException {

		 
		 XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(FileName));
		 XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);
		int rowTotal = myExcelSheet.getLastRowNum();
		 System.out.println("Total Number of Row is " +rowTotal );
		for (int i=6; i<=rowTotal; i++) {
		 //XSSFRow row = myExcelSheet.getRow(i);
		 //String cellvalue = row.getCell(Col).getStringCellValue();
		 String cellvalue = getCellValue(myExcelSheet.getRow(i).getCell(Col));
		 System.out.println("Row value is " +cellvalue );
		 if(cellContent.equals(cellvalue))
             return i;	
		}
	return 0;
	}
	
	public static int findColumn(String FileName, int rowNo, String cellContent) throws FileNotFoundException, IOException {

		 
		 XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(FileName));
		 XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);
		int ColTotal = myExcelSheet.getRow(3).getLastCellNum();
		 System.out.println("Total Number of Column is " +ColTotal );
		for (int i=1; i<ColTotal; i++) {
		 String cellvalue = getCellValue(myExcelSheet.getRow(rowNo).getCell(i));
		 //System.out.println("Column Number is " +i + " value is " +cellvalue );
		 if(cellContent.equals(cellvalue))
            return i;	
		}
	return 0;
	}
}
