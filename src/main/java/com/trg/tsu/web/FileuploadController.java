package com.trg.tsu.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.trg.tsu.model.TimeSheet;
import com.trg.tsu.service.TimeSheetService;
import com.trg.tsu.service.UserService;

@Controller
public class FileuploadController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TimeSheetService fileService;
	
	
	public final static int CELL_TYPE_NUMERIC = 0;

	private static String UPLOADED_FOLDER = "D://temp//";
	 @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	    public void  fileUpload(@RequestParam("name") String name,
	        @RequestParam("file") MultipartFile file) throws IOException {
		 
		 try {

	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	            Files.write(path, bytes);
	            	
	            System.out.println("aaaaaa");
/*	            readFile(UPLOADED_FOLDER, file.getOriginalFilename(), file.getOriginalFilename());
*/  
	            importExcelFile(UPLOADED_FOLDER, file.getOriginalFilename());
		 } catch (IOException e) {
	            e.printStackTrace();
	        }

	 
	    }
	 
	 public List importExcelFile(String filePath, String fileName) {
		    DataFormatter formatter = new DataFormatter(Locale.UK);
		    // stores data from excel file
		    List excelDataList = new ArrayList();
		    try {
		      // Import file from source destination
		      FileInputStream file = new FileInputStream(new File(filePath.concat(File.separator.concat(fileName))));

		      // Get the workbook instance for XLS file
		      XSSFWorkbook workbook = new XSSFWorkbook(file);
		      // workbook.setMissingCellPolicy(Row.RETURN_BLANK_AS_NULL);
		      // Get first sheet from the workbook
		      XSSFSheet sheet = workbook.getSheetAt(0);
		      // Iterate through each rows from first sheet
		      Iterator<Row> rowIterator = sheet.iterator();
		      // Skip first row, since it is header row
		      rowIterator.next();
		      while (rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		        int nextCell = 1;
		        int currentCell = 0;
		        // add data of each row
		        ArrayList rowList = new ArrayList();
		        TimeSheet timeSheet = new TimeSheet();
		        // For each row, iterate through each columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) {
		          Cell cell = cellIterator.next();		       
		          currentCell = cell.getColumnIndex();
		          if (currentCell >= nextCell) {
		            int diffInCellCount = currentCell - nextCell;
		            for (int nullLoop = 0; nullLoop <= diffInCellCount; nullLoop++) {
		              rowList.add(" ");
		              nextCell++;
		            }
		          }
		          
		          
		          
		          
		          
		          switch (cell.getCellType()) {
		            case Cell.CELL_TYPE_BOOLEAN:
		              break;
		            case Cell.CELL_TYPE_NUMERIC:
		              if (DateUtil.isCellDateFormatted(cell)) {
		                String date = formatter.formatCellValue(cell);
		                timeSheet.setDate(date);
		              } else if(cell.getColumnIndex()==0) {
		            		timeSheet.setEmpId(Math.round(cell.getNumericCellValue()));
		            	}else if(cell.getColumnIndex()==4) {
		            		timeSheet.setLoggedHours(cell.getNumericCellValue());
		            	}
		              break;
		            case Cell.CELL_TYPE_STRING:
		            	if(cell.getColumnIndex()==2) {
		            		timeSheet.setProjectCode(cell.getStringCellValue());
		            	}else if(cell.getColumnIndex()==3) {
		            		timeSheet.setTaskDescription(cell.getStringCellValue());
		            	}else if(cell.getColumnIndex()==5) {
		            		timeSheet.setRemarks(cell.getStringCellValue());
		            	}
		             
		              break;
		            case Cell.CELL_TYPE_BLANK:
		              break;
		            case Cell.CELL_TYPE_ERROR:
		              break;
		            default:
		              break;
		          }
		          nextCell++;
		        }
		        excelDataList.add(timeSheet);
		        
		      }
		      file.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		      return null;
		    }	
		    fileService.save(excelDataList);
		    return excelDataList;

		  }
	
	    
}
