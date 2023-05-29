/*     */ package BOOT-INF.classes.com.arythium.syncbase.application.filesync.service.implementation;
/*     */ 
/*     */ import com.arythium.syncbase.application.bankfiles.dto.BankBookDTO;
/*     */ import com.arythium.syncbase.application.fileobjects.dto.BookObjectDTO;
/*     */ import com.arythium.syncbase.application.fileobjects.service.BookObjectService;
/*     */ import com.arythium.syncbase.application.filesync.dto.FeedQueryDTO;
/*     */ import com.arythium.syncbase.application.filesync.repository.DBUtilsRepository;
/*     */ import com.arythium.syncbase.application.filesync.service.DBUtilService;
/*     */ import com.arythium.syncbase.core.exceptions.SyncBaseException;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class DBUtilServiceImpl
/*     */   implements DBUtilService
/*     */ {
/*     */   @Autowired
/*     */   DBUtilsRepository dbUtilsRepository;
/*     */   @Autowired
/*     */   BookObjectService bookObjectService;
/*     */   
/*     */   public FeedQueryDTO generateQuery(String[] csvHeaders, BankBookDTO bookDTO) {
/*  32 */     System.out.println();
/*  33 */     FeedQueryDTO feedQueryDTO = new FeedQueryDTO();
/*  34 */     ArrayList<Integer> cellsToIgnore = new ArrayList<>();
/*  35 */     ArrayList<Integer> dateCells = new ArrayList<>();
/*  36 */     ArrayList<Integer> timeStampCells = new ArrayList<>();
/*  37 */     ArrayList<Integer> yearCells = new ArrayList<>();
/*  38 */     Map<Integer, String> patternCells = new HashMap<>();
/*  39 */     ArrayList<Integer> numberCells = new ArrayList<>();
/*  40 */     ArrayList<String> defaultValues = new ArrayList<>();
/*  41 */     String objectTable = bookDTO.getMappedTableName();
/*  42 */     String finalQuery = "";
/*  43 */     int columnLength = csvHeaders.length;
/*  44 */     boolean firstColumnSeen = false;
/*     */ 
/*     */ 
/*     */     
/*  48 */     for (int i = 0; i < csvHeaders.length; i++) {
/*     */       
/*  50 */       String columnHeader = csvHeaders[i];
/*     */       
/*  52 */       BookObjectDTO feedObjectDTO = this.bookObjectService.getFeedByNameAndBookname(columnHeader, bookDTO.getBookName());
/*     */       
/*  54 */       if (feedObjectDTO == null) {
/*  55 */         System.out.println("column header is not found, so corresponding data would be skipped " + columnHeader);
/*  56 */         columnLength--;
/*  57 */         cellsToIgnore.add(Integer.valueOf(i));
/*  58 */         if (i == csvHeaders.length - 1) {
/*  59 */           finalQuery = finalQuery + ") values (" + StringUtils.repeat("?", ",", columnLength) + "}";
/*     */         }
/*     */       } else {
/*     */         
/*  63 */         columnHeader = feedObjectDTO.getMappedColumn();
/*     */ 
/*     */         
/*  66 */         defaultValues.add(feedObjectDTO.getDefaultValue());
/*     */         
/*  68 */         if (feedObjectDTO.getMappedColumnType().equalsIgnoreCase("DATE")) {
/*  69 */           dateCells.add(Integer.valueOf(i));
/*     */         }
/*  71 */         if (feedObjectDTO.getMappedColumnType().equalsIgnoreCase("NUMBER")) {
/*  72 */           numberCells.add(Integer.valueOf(i));
/*     */         }
/*  74 */         if (feedObjectDTO.getMappedColumnType().equalsIgnoreCase("TIMESTAMP")) {
/*  75 */           timeStampCells.add(Integer.valueOf(i));
/*     */         }
/*  77 */         if (feedObjectDTO.getMappedColumnType().equalsIgnoreCase("YEAR")) {
/*  78 */           yearCells.add(Integer.valueOf(i));
/*     */         }
/*  80 */         if (feedObjectDTO.getMappedColumnType().contains("PATTERN")) {
/*  81 */           patternCells.put(Integer.valueOf(i), feedObjectDTO.getMappedColumnType().split("_")[1]);
/*     */         }
/*     */         
/*  84 */         if (!firstColumnSeen) {
/*  85 */           finalQuery = finalQuery + "insert into " + objectTable + "(" + columnHeader;
/*  86 */           firstColumnSeen = true;
/*  87 */         } else if (i == csvHeaders.length - 1) {
/*  88 */           finalQuery = finalQuery + "," + columnHeader + ") values (" + StringUtils.repeat("?", ",", columnLength) + ")";
/*     */         } else {
/*  90 */           finalQuery = finalQuery + "," + columnHeader;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     feedQueryDTO.setIgnoredCells(cellsToIgnore);
/*  96 */     feedQueryDTO.setDateCells(dateCells);
/*  97 */     feedQueryDTO.setTimeStampCells(timeStampCells);
/*  98 */     feedQueryDTO.setNumberCells(numberCells);
/*  99 */     feedQueryDTO.setQueryString(finalQuery);
/* 100 */     feedQueryDTO.setNumOfValues(columnLength);
/* 101 */     feedQueryDTO.setCellsDefaultValue(defaultValues);
/* 102 */     feedQueryDTO.setYearCells(yearCells);
/* 103 */     feedQueryDTO.setPatternCells(patternCells);
/* 104 */     return feedQueryDTO;
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveRecord(String[] csvRows, FeedQueryDTO feedQueryDTO) {
/*     */     try {
/* 110 */       this.dbUtilsRepository.insertWithQuery(csvRows, feedQueryDTO);
/* 111 */     } catch (ParseException e) {
/* 112 */       e.printStackTrace();
/* 113 */       throw new SyncBaseException("Error occurred saving record" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\filesync\service\implementation\DBUtilServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */