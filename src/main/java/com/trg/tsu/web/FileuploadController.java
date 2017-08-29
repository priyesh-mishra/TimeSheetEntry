package com.trg.tsu.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;


import java.util.stream.IntStream;

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
	    public String  fileUpload(@RequestParam("name") String name,
	        @RequestParam("file") MultipartFile file,Model model) throws IOException {
		 
		 try {
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	            Files.write(path, bytes);
	            importExcelFile(UPLOADED_FOLDER, file.getOriginalFilename());
	            model.addAttribute("success", "Your file has been uploaded successfully");
		 } catch (IOException e) {
	            e.printStackTrace();
	        }
		   return "welcome";
	 
	    }
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/readPOI")
	 public String readPOI(Model model,String filePath,@RequestParam("name") String name) throws IOException {
		String fileLocation = UPLOADED_FOLDER.concat(File.separator.concat(name));
	   if (fileLocation != null) {
	       if (fileLocation.endsWith(".xlsx") || fileLocation.endsWith(".xls")) {
	           Map<Integer, List<MyCell>> data
	             = readExcel(fileLocation);
	           model.addAttribute("data", data);
	       } else {
	           model.addAttribute("message", "Not a valid excel file!");
	       }
	   } else {
	       model.addAttribute("message", "File missing! Please upload an excel file.");
	   }
	   return "welcome";
	 }
	 
	 public List importExcelFile(String filePath, String fileName) {
		    DataFormatter formatter = new DataFormatter(Locale.UK);
		    List excelDataList = new ArrayList();
		    try {
		      FileInputStream file = new FileInputStream(new File(filePath.concat(File.separator.concat(fileName))));
		      XSSFWorkbook workbook = new XSSFWorkbook(file);
		      XSSFSheet sheet = workbook.getSheetAt(0);
		      Iterator<Row> rowIterator = sheet.iterator();
		      rowIterator.next();
		      while (rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		        int nextCell = 1;
		        int currentCell = 0;
		        ArrayList rowList = new ArrayList();
		        TimeSheet timeSheet = new TimeSheet();
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
		            	}else if(cell.getColumnIndex()==6) {
		            		timeSheet.setSupCode(Math.round(cell.getNumericCellValue()));
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
	

	    public Map<Integer, List<MyCell>> readExcel(String fileLocation) throws IOException {

	        Map<Integer, List<MyCell>> data = new HashMap<>();
	        FileInputStream fis = new FileInputStream(new File(fileLocation));

	        if (fileLocation.endsWith(".xls")) {
	            data = readHSSFWorkbook(fis);
	        } else if (fileLocation.endsWith(".xlsx")) {
	            data = readXSSFWorkbook(fis);
	        }

	        int maxNrCols = data.values().stream()
	          .mapToInt(List::size)
	          .max()
	          .orElse(0);

	        data.values().stream()
	          .filter(ls -> ls.size() < maxNrCols)
	          .forEach(ls -> {
	              IntStream.range(ls.size(), maxNrCols)
	                .forEach(i -> ls.add(new MyCell("")));
	          });

	        return data;
	    }

	    private String readCellContent(Cell cell) {
	        String content;
	        switch (cell.getCellTypeEnum()) {
	        case STRING:
	            content = cell.getStringCellValue();
	            break;
	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	                content = cell.getDateCellValue() + "";
	            } else {
	                content = cell.getNumericCellValue() + "";
	            }
	            break;
	        case BOOLEAN:
	            content = cell.getBooleanCellValue() + "";
	            break;
	        case FORMULA:
	            content = cell.getCellFormula() + "";
	            break;
	        default:
	            content = "";
	        }
	        return content;
	    }

	    private Map<Integer, List<MyCell>> readHSSFWorkbook(FileInputStream fis) throws IOException {
	        Map<Integer, List<MyCell>> data = new HashMap<>();
	        HSSFWorkbook workbook = null;
	        try {
	            workbook = new HSSFWorkbook(fis);

	            HSSFSheet sheet = workbook.getSheetAt(0);
	            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
	                HSSFRow row = sheet.getRow(i);
	                data.put(i, new ArrayList<>());
	                if (row != null) {
	                    for (int j = 0; j < row.getLastCellNum(); j++) {
	                        HSSFCell cell = row.getCell(j);
	                        if (cell != null) {
	                            HSSFCellStyle cellStyle = cell.getCellStyle();

	                            MyCell myCell = new MyCell();

	                            HSSFColor bgColor = cellStyle.getFillForegroundColorColor();
	                            if (bgColor != null) {
	                                short[] rgbColor = bgColor.getTriplet();
	                                myCell.setBgColor("rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
	                            }
	                            HSSFFont font = cell.getCellStyle()
	                                .getFont(workbook);
	                            myCell.setTextSize(font.getFontHeightInPoints() + "");
	                            if (font.getBold()) {
	                                myCell.setTextWeight("bold");
	                            }
	                            HSSFColor textColor = font.getHSSFColor(workbook);
	                            if (textColor != null) {
	                                short[] rgbColor = textColor.getTriplet();
	                                myCell.setTextColor("rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
	                            }
	                            myCell.setContent(readCellContent(cell));
	                            data.get(i)
	                                .add(myCell);
	                        } else {
	                            data.get(i)
	                                .add(new MyCell(""));
	                        }
	                    }
	                }
	            }
	        } finally {
	            if (workbook != null) {
	                workbook.close();
	            }
	        }
	        return data;
	    }

	    private Map<Integer, List<MyCell>> readXSSFWorkbook(FileInputStream fis) throws IOException {
	        XSSFWorkbook workbook = null;
	        Map<Integer, List<MyCell>> data = new HashMap<>();
	        try {

	            workbook = new XSSFWorkbook(fis);
	            XSSFSheet sheet = workbook.getSheetAt(0);

	            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
	                XSSFRow row = sheet.getRow(i);
	                data.put(i, new ArrayList<>());
	                if (row != null) {
	                    for (int j = 0; j < row.getLastCellNum(); j++) {
	                        XSSFCell cell = row.getCell(j);
	                        if (cell != null) {
	                            XSSFCellStyle cellStyle = cell.getCellStyle();

	                            MyCell myCell = new MyCell();
	                            XSSFColor bgColor = cellStyle.getFillForegroundColorColor();
	                            if (bgColor != null) {
	                                byte[] rgbColor = bgColor.getRGB();
	                                myCell.setBgColor("rgb(" + (rgbColor[0] < 0 ? (rgbColor[0] + 0xff) : rgbColor[0]) + "," + (rgbColor[1] < 0 ? (rgbColor[1] + 0xff) : rgbColor[1]) + "," + (rgbColor[2] < 0 ? (rgbColor[2] + 0xff) : rgbColor[2]) + ")");
	                            }
	                            XSSFFont font = cellStyle.getFont();
	                            myCell.setTextSize(font.getFontHeightInPoints() + "");
	                            if (font.getBold()) {
	                                myCell.setTextWeight("bold");
	                            }
	                            XSSFColor textColor = font.getXSSFColor();
	                            if (textColor != null) {
	                                byte[] rgbColor = textColor.getRGB();
	                                myCell.setTextColor("rgb(" + (rgbColor[0] < 0 ? (rgbColor[0] + 0xff) : rgbColor[0]) + "," + (rgbColor[1] < 0 ? (rgbColor[1] + 0xff) : rgbColor[1]) + "," + (rgbColor[2] < 0 ? (rgbColor[2] + 0xff) : rgbColor[2]) + ")");
	                            }
	                            myCell.setContent(readCellContent(cell));
	                            data.get(i)
	                                .add(myCell);
	                        } else {
	                            data.get(i)
	                                .add(new MyCell(""));
	                        }
	                    }
	                }
	            }
	        } finally {
	            if (workbook != null) {
	                workbook.close();
	            }
	        }
	        return data;
	    }
}
