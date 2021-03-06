package com.thoughtworks.excelparser.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.excelparser.exception.ExcelParsingException;

public class HSSFHelperTest {
	private HSSFHelper hssfHelper;
	private Sheet sheet;
	private String sheetName = "Sheet1";
	private InputStream inputStream;

	@Before
	public void setUp() throws IOException {
		hssfHelper = new HSSFHelper();
		inputStream = getClass().getClassLoader().getResourceAsStream("Student Profile.xls");
		sheet = new HSSFWorkbook(inputStream).getSheet(sheetName);
	}

	@After
	public void tearDown() throws IOException {
		inputStream.close();
	}

	@Test(expected = ExcelParsingException.class)
	public void testShouldThrowExceptionOnInvalidDateCell() throws ExcelParsingException {
		int rowNumber = 2;
		int columnNumber = 2;
		Cell cell = hssfHelper.getCell(sheet, rowNumber, columnNumber);
		hssfHelper.getDateCell(cell, sheetName, rowNumber, columnNumber);
	}

	@Test
	public void testShouldReturnValidDate() throws ExcelParsingException {
		int rowNumber = 6;
		int columnNumber = 4;
		Cell cell = hssfHelper.getCell(sheet, rowNumber, columnNumber);
		assertNotNull(hssfHelper.getDateCell(cell, sheetName, rowNumber, columnNumber));
	}

	@Test
	public void testShouldReturnValidStringValue() throws ExcelParsingException {
		assertEquals("1", hssfHelper.getStringCell(hssfHelper.getCell(sheet, 6, 1)));
		assertEquals("A", hssfHelper.getStringCell(hssfHelper.getCell(sheet, 6, 5)));
		assertEquals("James", hssfHelper.getStringCell(hssfHelper.getCell(sheet, 8, 3)));
	}

	@Test
	public void testShouldReturnValidNumericValue() throws ExcelParsingException {
		assertEquals(1, hssfHelper.getIntegerCell(hssfHelper.getCell(sheet, 6, 1), false, sheetName, 6, 1).intValue());
		assertEquals(0, hssfHelper.getIntegerCell(hssfHelper.getCell(sheet, 6, 8), true, sheetName, 6, 8).intValue());
		assertNull(hssfHelper.getIntegerCell(hssfHelper.getCell(sheet, 6, 8), false, sheetName, 6, 8));
		
		assertEquals(2001L, hssfHelper.getLongCell(hssfHelper.getCell(sheet, 6, 2), false, sheetName, 6, 2).longValue());
		assertEquals(0L, hssfHelper.getLongCell(hssfHelper.getCell(sheet, 10, 2), true, sheetName, 10, 2).longValue());
		assertNull(hssfHelper.getLongCell(hssfHelper.getCell(sheet, 10, 2), false, sheetName, 10, 2));
		
		assertEquals(new Double(450.3), hssfHelper.getDoubleCell(hssfHelper.getCell(sheet, 7, 8), false, sheetName, 7, 8));
		assertEquals(new Double(300), hssfHelper.getDoubleCell(hssfHelper.getCell(sheet, 8, 8), false, sheetName, 8, 8));
		
	}
}
