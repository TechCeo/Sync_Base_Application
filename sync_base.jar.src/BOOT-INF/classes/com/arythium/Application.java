/*     */ package BOOT-INF.classes.com.arythium;
/*     */ 
/*     */ import com.arythium.syncbase.application.bankfiles.dto.BankBookDTO;
/*     */ import com.arythium.syncbase.application.bankfiles.service.BookService;
/*     */ import com.arythium.syncbase.application.fileobjects.dto.BookObjectDTO;
/*     */ import com.arythium.syncbase.application.fileobjects.service.BookObjectService;
/*     */ import com.arythium.syncbase.core.exceptions.SyncBaseException;
/*     */ import com.arythium.syncbase.core.usermgt.model.SystemUser;
/*     */ import com.arythium.syncbase.core.usermgt.repository.ApplicationUserRepository;
/*     */ import com.opencsv.CSVReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.boot.CommandLineRunner;
/*     */ import org.springframework.boot.SpringApplication;
/*     */ import org.springframework.boot.autoconfigure.SpringBootApplication;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.security.crypto.password.PasswordEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SpringBootApplication
/*     */ public class Application
/*     */ {
/*     */   @Value("${syncbase.files.directory}")
/*     */   private String fileDirectory;
/*     */   @Value("${syncbase.files.config_file1}")
/*     */   private String configFile1;
/*     */   @Value("${syncbase.files.config_file2}")
/*     */   private String configFile2;
/*     */   
/*     */   public static void main(String[] args) {
/*  39 */     SpringApplication.run(com.arythium.Application.class, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Bean
/*     */   public CommandLineRunner demo(BookService bookService, BookObjectService feedsService, ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder) {
/*  50 */     return args -> {
/*     */         configureBankBooks(this.configFile1, bookService);
/*     */         configureBankBookObjects(this.configFile2, bookService, feedsService);
/*     */         configureSystemUser(applicationUserRepository, passwordEncoder);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void configureBankBooks(String fileName, BookService bookService) {
/*  64 */     File file = getFileFromPath(fileName);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  69 */       CSVReader reader = new CSVReader(new FileReader(file));
/*     */       
/*  71 */       long cnt = 0L; String[] line;
/*  72 */       while ((line = reader.readNext()) != null) {
/*  73 */         if (cnt != 0L) {
/*     */ 
/*     */           
/*  76 */           List<String> datas = Arrays.asList(line);
/*  77 */           BankBookDTO bankBookDTO = new BankBookDTO();
/*  78 */           bankBookDTO.setBookName(datas.get(0));
/*  79 */           bankBookDTO.setBookPrefix(datas.get(1));
/*  80 */           bankBookDTO.setMappedTableName(datas.get(2));
/*  81 */           bankBookDTO.setEnabled(Boolean.valueOf(true));
/*     */           
/*  83 */           BankBookDTO check = bookService.getBookByName(bankBookDTO.getBookName());
/*  84 */           if (check == null) {
/*  85 */             bookService.addBook(bankBookDTO);
/*     */           } else {
/*  87 */             bookService.updateBook(bankBookDTO);
/*     */           } 
/*  89 */           System.out.println(datas);
/*     */         } 
/*  91 */         cnt++;
/*     */       } 
/*     */       
/*  94 */       reader.close();
/*     */     }
/*  96 */     catch (IOException e) {
/*  97 */       e.printStackTrace();
/*  98 */     } catch (Exception e) {
/*  99 */       e.printStackTrace();
/* 100 */       throw new SyncBaseException("Error Occurred configuring book " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void configureSystemUser(ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder) {
/* 107 */     String adminName = "admin";
/* 108 */     SystemUser systemUser = userRepository.findByUserName(adminName);
/* 109 */     if (systemUser == null) {
/*     */       
/* 111 */       systemUser = new SystemUser();
/* 112 */       systemUser.setEmail("admin@testmail.com");
/* 113 */       systemUser.setFirstName("admin");
/* 114 */       systemUser.setLastName("user");
/* 115 */       systemUser.setUserName(adminName);
/* 116 */       systemUser.setPassword(passwordEncoder.encode("password@123"));
/*     */       
/* 118 */       userRepository.save(systemUser);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void configureBankBookObjects(String fileName, BookService bookService, BookObjectService feedsService) {
/* 127 */     File file = getFileFromPath(fileName);
/*     */ 
/*     */     
/*     */     try {
/* 131 */       CSVReader reader = new CSVReader(new FileReader(file));
/*     */       
/* 133 */       long cnt = 0L; String[] line;
/* 134 */       while ((line = reader.readNext()) != null) {
/* 135 */         if (cnt != 0L) {
/*     */           
/* 137 */           List<String> datas = Arrays.asList(line);
/* 138 */           BookObjectDTO feedObjectDTO = new BookObjectDTO();
/* 139 */           BankBookDTO bankBookDTO = bookService.getBookByName(datas.get(0));
/*     */           
/* 141 */           if (bankBookDTO == null) {
/* 142 */             throw new SyncBaseException("Invalid Book Name at row : " + cnt);
/*     */           }
/* 144 */           feedObjectDTO.setBankBookDTO(bankBookDTO);
/* 145 */           feedObjectDTO.setName(datas.get(1));
/* 146 */           feedObjectDTO.setDescription(datas.get(2));
/* 147 */           feedObjectDTO.setMappedColumn(datas.get(3));
/* 148 */           feedObjectDTO.setMappedColumnType(datas.get(4));
/* 149 */           feedObjectDTO.setDefaultValue(datas.get(5));
/* 150 */           feedObjectDTO.setEnabled(Boolean.valueOf(true));
/*     */           
/* 152 */           BookObjectDTO check = feedsService.getFeedByNameAndBookname(feedObjectDTO.getName(), feedObjectDTO.getBankBookDTO().getBookName());
/* 153 */           if (check == null) {
/* 154 */             feedsService.addBookObject(feedObjectDTO);
/*     */           } else {
/* 156 */             feedsService.updateBookObject(feedObjectDTO);
/*     */           } 
/*     */         } 
/* 159 */         cnt++;
/*     */       } 
/*     */       
/* 162 */       reader.close();
/*     */     }
/* 164 */     catch (IOException e) {
/* 165 */       e.printStackTrace();
/* 166 */       throw new SyncBaseException("Error Occurred configuring book objects" + e.getMessage());
/* 167 */     } catch (Exception e) {
/* 168 */       e.printStackTrace();
/* 169 */       throw new SyncBaseException("Error Occurred configuring book objects" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private File getFileFromPath(String fileName) {
/* 178 */     ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 179 */     System.out.println("here is :: " + classLoader.getResource(""));
/* 180 */     File file = new File(this.fileDirectory + fileName, "");
/* 181 */     if (!file.exists()) {
/* 182 */       System.out.println("file doesnt exist " + file);
/* 183 */       File folder = new File(classLoader.getResource("").getFile());
/*     */       
/* 185 */       File[] folderItems = folder.listFiles();
/* 186 */       for (File f : folderItems) {
/* 187 */         if (f.isFile() && f.getName().matches(fileName)) {
/* 188 */           file = f;
/*     */         }
/*     */       } 
/*     */     } 
/* 192 */     return file;
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\Application.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */