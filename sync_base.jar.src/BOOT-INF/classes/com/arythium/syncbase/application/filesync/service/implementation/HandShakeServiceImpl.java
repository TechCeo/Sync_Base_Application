/*     */ package BOOT-INF.classes.com.arythium.syncbase.application.filesync.service.implementation;
/*     */ 
/*     */ import com.arythium.syncbase.application.bankfiles.dto.BankBookDTO;
/*     */ import com.arythium.syncbase.application.bankfiles.dto.BookSyncStatus;
/*     */ import com.arythium.syncbase.application.bankfiles.service.BookService;
/*     */ import com.arythium.syncbase.application.filesync.dto.BookSyncDto;
/*     */ import com.arythium.syncbase.application.filesync.dto.FeedQueryDTO;
/*     */ import com.arythium.syncbase.application.filesync.service.BookSyncService;
/*     */ import com.arythium.syncbase.application.filesync.service.DBUtilService;
/*     */ import com.arythium.syncbase.application.filesync.service.HandShakeService;
/*     */ import com.arythium.syncbase.core.exceptions.SyncBaseException;
/*     */ import com.opencsv.CSVReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.scheduling.annotation.Scheduled;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class HandShakeServiceImpl
/*     */   implements HandShakeService
/*     */ {
/*  33 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.application.filesync.service.implementation.HandShakeServiceImpl.class);
/*     */ 
/*     */   
/*     */   @Value("${syncbase.files.directory}")
/*     */   private String fileDirectory;
/*     */ 
/*     */   
/*     */   @Value("${syncbase.files.search_pattern}")
/*     */   private String searchPattern;
/*     */   
/*     */   @Value("${syncbase.files.seperator}")
/*     */   private String fileSeperator;
/*     */   
/*     */   @Autowired
/*     */   BookService bookService;
/*     */   
/*     */   @Autowired
/*     */   BookSyncService bookSyncService;
/*     */   
/*     */   @Autowired
/*     */   DBUtilService dbUtilService;
/*     */ 
/*     */   
/*     */   public List<String> getPendingFiles(String filePrefix) {
/*  57 */     List<String> pendingFiles = new ArrayList<>();
/*     */     
/*  59 */     ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/*     */     
/*  61 */     File folder = new File(this.fileDirectory);
/*  62 */     if (!folder.exists()) {
/*  63 */       folder = new File(classLoader.getResource("").getFile());
/*     */     }
/*     */     
/*  66 */     File[] folderItems = folder.listFiles();
/*  67 */     for (File f : folderItems) {
/*     */       
/*  69 */       if (f.isFile() && f.getName().matches(filePrefix + this.searchPattern)) {
/*  70 */         pendingFiles.add(f.getPath());
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return pendingFiles;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateFeed(String fileName, BankBookDTO bankBookDTO) {
/*  80 */     long cnt = 0L;
/*  81 */     BookSyncDto bookSyncDto = new BookSyncDto();
/*  82 */     logger.info("Data sync started using :: {}", fileName);
/*     */     
/*  84 */     File file = new File(fileName);
/*     */     
/*  86 */     FeedQueryDTO feedQueryDTO = null;
/*     */     
/*  88 */     CSVReader reader = null;
/*  89 */     boolean containsHeader = true;
/*     */     
/*     */     try {
/*  92 */       bookSyncDto.setBookName(file.getName());
/*  93 */       bookSyncDto.setStatus(String.valueOf(BookSyncStatus.PROCESSING));
/*  94 */       bookSyncDto.setLastCount(Long.valueOf((cnt > 0L) ? (cnt - 1L) : cnt));
/*  95 */       this.bookSyncService.addBookSync(bookSyncDto);
/*  96 */       char csvDelimitter = (this.fileSeperator == null) ? ',' : this.fileSeperator.charAt(0);
/*  97 */       reader = new CSVReader(new FileReader(file), csvDelimitter);
/*     */       
/*     */       String[] line;
/* 100 */       while ((line = reader.readNext()) != null) {
/* 101 */         if (containsHeader && cnt == 0L) {
/* 102 */           feedQueryDTO = this.dbUtilService.generateQuery(line, bankBookDTO);
/* 103 */           logger.info("Query Properties {} ", feedQueryDTO);
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 109 */           logger.info("{}. {}", Long.valueOf(cnt), feedQueryDTO.getQueryString());
/*     */           try {
/* 111 */             this.dbUtilService.saveRecord(line, feedQueryDTO);
/* 112 */           } catch (Exception e) {
/* 113 */             e.printStackTrace();
/* 114 */             if (reader != null) {
/* 115 */               reader.close();
/*     */             }
/* 117 */             throw new SyncBaseException("Error occurred inserting record at row: " + cnt + " Cause : " + e.getCause().getCause());
/*     */           } 
/*     */         } 
/*     */         
/* 121 */         boolean isPaused = this.bookSyncService.isSyncPaused(bookSyncDto);
/* 122 */         if (isPaused) {
/* 123 */           logger.error("Sync was paused at {} ", Long.valueOf(cnt));
/*     */           break;
/*     */         } 
/* 126 */         cnt++;
/*     */       } 
/*     */       
/* 129 */       reader.close();
/*     */       
/* 131 */       logger.info("Data sync ended using :: {}", fileName);
/*     */       
/* 133 */       backOutFile(fileName, "success");
/*     */       
/* 135 */       bookSyncDto.setStatus(String.valueOf(BookSyncStatus.COMPLETED));
/* 136 */       bookSyncDto.setLastCount(Long.valueOf((cnt > 0L) ? (cnt - 1L) : cnt));
/* 137 */       this.bookSyncService.addBookSync(bookSyncDto);
/*     */ 
/*     */     
/*     */     }
/* 141 */     catch (IOException e) {
/* 142 */       e.printStackTrace();
/* 143 */       bookSyncDto.setStatus(String.valueOf(BookSyncStatus.STOPPED));
/* 144 */       bookSyncDto.setLastCount(Long.valueOf((cnt > 0L) ? (cnt - 1L) : cnt));
/* 145 */       bookSyncDto.setMessage(e.getMessage());
/* 146 */       this.bookSyncService.addBookSync(bookSyncDto);
/* 147 */       backOutFile(fileName, "failed");
/* 148 */       throw new SyncBaseException("Error occurred completing sync ");
/* 149 */     } catch (Exception e) {
/* 150 */       e.printStackTrace();
/* 151 */       bookSyncDto.setStatus(String.valueOf(BookSyncStatus.STOPPED));
/* 152 */       bookSyncDto.setLastCount(Long.valueOf((cnt > 0L) ? (cnt - 1L) : cnt));
/* 153 */       bookSyncDto.setMessage(e.getMessage());
/* 154 */       this.bookSyncService.addBookSync(bookSyncDto);
/* 155 */       backOutFile(fileName, "failed");
/* 156 */       throw new SyncBaseException("Error occurred inserting record ");
/*     */     } 
/* 158 */     logger.info("");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
/*     */   public void scheduledFeedGeneration() {
/* 165 */     logger.info("**** Scheduled JOB for data handshake has begun ***");
/* 166 */     List<BankBookDTO> bankBookDTOS = this.bookService.getUnFedBooks();
/* 167 */     logger.info("Number of books to process :: {}", Integer.valueOf(bankBookDTOS.size()));
/* 168 */     for (BankBookDTO bankBookDTO : bankBookDTOS) {
/*     */       
/* 170 */       logger.info("[ Generating feeds for  {} ]", bankBookDTO.getBookName());
/* 171 */       List<String> csvFiles = getPendingFiles(bankBookDTO.getBookPrefix());
/* 172 */       if (csvFiles.size() > 0) {
/* 173 */         generateFeed(csvFiles.get(0), bankBookDTO); continue;
/*     */       } 
/* 175 */       logger.info("[ No File seen for {} data sync ]", bankBookDTO.getBookName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void backOutFile(String fileName, String status) {
/* 183 */     Path source = Paths.get(fileName, new String[0]);
/* 184 */     String backoutFileName = fileName + "." + status;
/*     */     try {
/* 186 */       Files.move(source, source.resolveSibling(backoutFileName), new java.nio.file.CopyOption[0]);
/* 187 */     } catch (IOException e) {
/* 188 */       e.printStackTrace();
/*     */     } 
/* 190 */     logger.info("{} file has been backed out to {}", fileName, backoutFileName);
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\filesync\service\implementation\HandShakeServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */