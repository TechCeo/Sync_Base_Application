/*     */ package BOOT-INF.classes.com.arythium.syncbase.application.bankfiles.service.implementation;
/*     */ 
/*     */ import com.arythium.syncbase.application.bankfiles.dto.BankBookDTO;
/*     */ import com.arythium.syncbase.application.bankfiles.entity.BankBook;
/*     */ import com.arythium.syncbase.application.bankfiles.repository.BookRepository;
/*     */ import com.arythium.syncbase.application.bankfiles.service.BookService;
/*     */ import com.arythium.syncbase.application.fileobjects.dto.BookObjectDTO;
/*     */ import com.arythium.syncbase.application.fileobjects.service.BookObjectService;
/*     */ import com.arythium.syncbase.application.filesync.dto.BookSyncDto;
/*     */ import com.arythium.syncbase.application.filesync.service.BookSyncService;
/*     */ import com.arythium.syncbase.application.filesync.service.implementation.DBUtilServiceImpl;
/*     */ import com.arythium.syncbase.core.code.service.CodeService;
/*     */ import com.arythium.syncbase.core.exceptions.SyncBaseException;
/*     */ import com.opencsv.CSVReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class BookServiceImpl
/*     */   implements BookService
/*     */ {
/*  35 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.application.bankfiles.service.implementation.BookServiceImpl.class);
/*     */   
/*     */   @Autowired
/*     */   BookRepository bookRepository;
/*     */   
/*     */   @Autowired
/*     */   ModelMapper modelMapper;
/*     */   
/*     */   @Autowired
/*     */   CodeService codeService;
/*     */   
/*     */   @Autowired
/*     */   DBUtilServiceImpl dbUtilService;
/*     */   
/*     */   @Autowired
/*     */   BookSyncService bookSyncService;
/*     */   
/*     */   @Autowired
/*     */   BookObjectService bookObjectService;
/*     */   
/*     */   @Value("${book.config.header_columns}")
/*     */   private String[] configureHeaders;
/*     */ 
/*     */   
/*     */   public List<BankBookDTO> getAllBooks() {
/*  60 */     List<BankBookDTO> bankBookDTOS = new ArrayList<>();
/*     */     
/*  62 */     this.bookRepository.findAll().forEach(bankBook -> bankBookDTOS.add(this.modelMapper.map(bankBook, BankBookDTO.class)));
/*     */ 
/*     */     
/*  65 */     return bankBookDTOS;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BankBookDTO> getUnFedBooks() {
/*  70 */     List<BankBookDTO> bankBookDTOS = new ArrayList<>();
/*     */     
/*  72 */     this.bookRepository.findByEnabledTrue().forEach(bankBook -> {
/*     */           BookSyncDto syncAudit = this.bookSyncService.getLatestSyncByBookName(bankBook.getBookName());
/*     */ 
/*     */           
/*     */           if (syncAudit != null) {
/*     */             if (syncAudit.getStatus().equals("COMPLETED") || syncAudit.getStatus().equals("STOPPED")) {
/*     */               bankBookDTOS.add(this.modelMapper.map(bankBook, BankBookDTO.class));
/*     */             } else {
/*     */               logger.error("{} is currently {}, started at :{} ", new Object[] { bankBook.getBookName(), syncAudit.getStatus(), syncAudit.getDateCreated() });
/*     */             } 
/*     */           } else {
/*     */             bankBookDTOS.add(this.modelMapper.map(bankBook, BankBookDTO.class));
/*     */           } 
/*     */         });
/*     */ 
/*     */     
/*  88 */     return bankBookDTOS;
/*     */   }
/*     */ 
/*     */   
/*     */   public BankBookDTO getBookByName(String bookName) {
/*  93 */     BankBook book = this.bookRepository.findByBookName(bookName);
/*  94 */     if (book == null) {
/*  95 */       return null;
/*     */     }
/*  97 */     BankBookDTO bankBookDTO = (BankBookDTO)this.modelMapper.map(book, BankBookDTO.class);
/*     */     
/*  99 */     return bankBookDTO;
/*     */   }
/*     */ 
/*     */   
/*     */   public BankBookDTO getBookById(Long bookId) {
/* 104 */     BankBook book = this.bookRepository.findById(bookId).orElse(null);
/* 105 */     if (book == null) {
/* 106 */       return null;
/*     */     }
/* 108 */     BankBookDTO bankBookDTO = (BankBookDTO)this.modelMapper.map(book, BankBookDTO.class);
/*     */     
/* 110 */     return bankBookDTO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BankBookDTO addBook(BankBookDTO bankBookDTO) {
/* 117 */     if (bankBookDTO == null) {
/* 118 */       throw new SyncBaseException("Book Cannot be null");
/*     */     }
/*     */     
/* 121 */     BankBook check = this.bookRepository.findByBookName(bankBookDTO.getBookName());
/* 122 */     if (check != null) {
/* 123 */       throw new SyncBaseException("File Already exist");
/*     */     }
/*     */     
/* 126 */     BankBook book = (BankBook)this.modelMapper.map(bankBookDTO, BankBook.class);
/*     */ 
/*     */     
/* 129 */     book.setFeedObjects(null);
/* 130 */     book.setDelFlag("N");
/* 131 */     book = (BankBook)this.bookRepository.save(book);
/* 132 */     logger.info("Book Added :: {} ", book.getBookName());
/* 133 */     bankBookDTO.setId(book.getId());
/* 134 */     return bankBookDTO;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BankBookDTO updateBook(BankBookDTO bankBookDTO) {
/* 140 */     BankBook check = this.bookRepository.findByBookName(bankBookDTO.getBookName());
/* 141 */     if (check == null) {
/* 142 */       throw new SyncBaseException("File Does not exist");
/*     */     }
/* 144 */     check.setBookPrefix(bankBookDTO.getBookPrefix());
/* 145 */     check.setMappedTableName(bankBookDTO.getMappedTableName());
/* 146 */     check.setDescription(bankBookDTO.getDescription());
/* 147 */     check.setEnabled(bankBookDTO.getEnabled());
/*     */     
/* 149 */     check = (BankBook)this.bookRepository.save(check);
/*     */     
/* 151 */     logger.info("File Config {} has been Updated", check.getBookName());
/* 152 */     bankBookDTO.setId(check.getId());
/* 153 */     return bankBookDTO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String uploadBooks(MultipartFile multipartFile) {
/* 160 */     System.out.println("conn :: " + this.configureHeaders);
/* 161 */     String fileName = multipartFile.getOriginalFilename();
/*     */     
/* 163 */     File file = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
/*     */     try {
/* 165 */       multipartFile.transferTo(file);
/*     */       
/* 167 */       CSVReader reader = new CSVReader(new FileReader(file));
/*     */       
/* 169 */       long cnt = 0L; String[] line;
/* 170 */       while ((line = reader.readNext()) != null) {
/* 171 */         if (cnt == 0L) {
/* 172 */           List<String> csvHeader = Arrays.asList(this.configureHeaders);
/* 173 */           if (!csvHeader.equals(Arrays.asList(line))) {
/* 174 */             throw new SyncBaseException("Invalid Header Found in Upload");
/*     */           }
/*     */         } else {
/* 177 */           List<String> datas = Arrays.asList(line);
/* 178 */           BankBookDTO bankBookDTO = new BankBookDTO();
/* 179 */           bankBookDTO.setBookName(datas.get(0));
/* 180 */           bankBookDTO.setMappedTableName(datas.get(2));
/* 181 */           bankBookDTO.setBookPrefix(datas.get(1));
/* 182 */           bankBookDTO.setEnabled(Boolean.valueOf(true));
/* 183 */           bankBookDTO.setDelFlag("N");
/*     */           
/* 185 */           BankBookDTO check = getBookByName(bankBookDTO.getBookName());
/* 186 */           if (check == null) {
/* 187 */             addBook(bankBookDTO);
/*     */           } else {
/* 189 */             updateBook(bankBookDTO);
/*     */           } 
/*     */         } 
/* 192 */         cnt++;
/*     */       } 
/*     */       
/* 195 */       reader.close();
/*     */     }
/* 197 */     catch (IOException e) {
/* 198 */       e.printStackTrace();
/* 199 */       throw new SyncBaseException("Error Occurred configuring book " + e.getMessage());
/* 200 */     } catch (Exception e) {
/* 201 */       e.printStackTrace();
/* 202 */       throw new SyncBaseException("Error Occurred configuring book " + e.getMessage());
/*     */     } 
/* 204 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteBook(Long id) {
/* 210 */     BankBook bankBook = this.bookRepository.findById(id).orElse(null);
/* 211 */     if (bankBook == null) {
/* 212 */       throw new SyncBaseException("Book Not Found");
/*     */     }
/*     */     
/* 215 */     List<BookObjectDTO> bookObjectDTOS = this.bookObjectService.getFeedsByBookId(bankBook.getId());
/*     */     
/* 217 */     for (int i = 0; i < bookObjectDTOS.size(); i++) {
/* 218 */       BookObjectDTO bookObjectDTO = bookObjectDTOS.get(i);
/* 219 */       this.bookObjectService.deleteBookObject(bookObjectDTO.getId());
/*     */     } 
/* 221 */     bankBook.setDelFlag("Y");
/* 222 */     bankBook.setDeletedOn(new Date());
/* 223 */     this.bookRepository.save(bankBook);
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\bankfiles\service\implementation\BookServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */