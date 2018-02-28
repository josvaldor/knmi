package main.java.org.utn.knmi;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class IO {

	public TreeMap<String, LinkedList<String>> read(String fileName) {
		BufferedReader br = null;
		FileReader fr = null;
		TreeMap<String, LinkedList<String>> map = new TreeMap<String, LinkedList<String>>();
		LinkedList<String> tempuratureList;
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				if (!sCurrentLine.contains("#")) {
					tempuratureList = new LinkedList<String>();
					String[] stringArray = sCurrentLine.trim().split(" ");
					String year = stringArray[0];
					for (int i = 1; i < stringArray.length; i++) {
						if (!stringArray[i].trim().equals("")) {
//							System.out.println(stringArray[i]);
							tempuratureList.add(stringArray[i]);
						}
					}
					map.put(year, tempuratureList);
				}
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
		return map;
	}

	public void writeXLS(String fileName, Object[][] mean, Object[][] anomaly) {
		System.out.println("generating xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Mean Monthly Tempurature");
		int rowNum = 0;
		for (Object[] datatype : mean) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (Object field : datatype) {
				Cell cell = row.createCell(colNum++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Double) {
					cell.setCellValue((Double) field);
				}else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}
		}
		
		if(anomaly != null){
			XSSFSheet sheet2 = workbook.createSheet("Anomaly Monthly Tempurature");
			rowNum = 0;
			for (Object[] datatype : anomaly) {
				Row row = sheet2.createRow(rowNum++);
				int colNum = 0;
				for (Object field : datatype) {
					Cell cell = row.createCell(colNum++);
					if (field instanceof String) {
						cell.setCellValue((String) field);
					} else if (field instanceof Double) {
						cell.setCellValue((Double) field);
					}else if (field instanceof Integer) {
						cell.setCellValue((Integer) field);
					}
				}
			}
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(fileName+".xlsx");
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("xlsx complete");
	}
}
