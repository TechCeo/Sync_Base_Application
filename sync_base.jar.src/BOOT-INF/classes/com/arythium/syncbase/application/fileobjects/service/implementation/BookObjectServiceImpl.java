/*     */ package BOOT-INF.classes.com.arythium.syncbase.application.fileobjects.service.implementation;
/*     */ 
/*     */ import com.arythium.syncbase.application.bankfiles.dto.BankBookDTO;
/*     */ import com.arythium.syncbase.application.bankfiles.service.BookService;
/*     */ import com.arythium.syncbase.application.fileobjects.dto.BookObjectDTO;
/*     */ import com.arythium.syncbase.application.fileobjects.entity.BookObject;
/*     */ import com.arythium.syncbase.application.fileobjects.repository.BookObjectRepository;
/*     */ import com.arythium.syncbase.application.fileobjects.service.BookObjectService;
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
/*     */ import org.springframework.data.domain.Page;
/*     */ import org.springframework.data.domain.Pageable;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class BookObjectServiceImpl
/*     */   implements BookObjectService
/*     */ {
/*  33 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.application.fileobjects.service.implementation.BookObjectServiceImpl.class);
/*     */   
/*     */   @Value("${book_objects.config.header_columns}")
/*     */   private String[] configureHeaders;
/*     */   
/*     */   @Autowired
/*     */   BookObjectRepository bookObjectRepository;
/*     */   
/*     */   @Autowired
/*     */   ModelMapper modelMapper;
/*     */   
/*     */   @Autowired
/*     */   BookService bookService;
/*     */ 
/*     */   
/*     */   public List<BookObjectDTO> getFeedsByBookName(String bookName) {
/*  49 */     List<BookObjectDTO> BookObjectDTOS = new ArrayList<>();
/*  50 */     List<BookObject> feedObjects = this.bookObjectRepository.findByBankBook_BookName(bookName);
/*  51 */     for (BookObject feedObject : feedObjects) {
/*  52 */       BookObjectDTO BookObjectDTO = (BookObjectDTO)this.modelMapper.map(feedObject, BookObjectDTO.class);
/*  53 */       BookObjectDTOS.add(BookObjectDTO);
/*     */     } 
/*  55 */     return BookObjectDTOS;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BookObjectDTO> getFeedsByBookId(Long bankBookId) {
/*  60 */     List<BookObjectDTO> BookObjectDTOS = new ArrayList<>();
/*  61 */     List<BookObject> feedObjects = this.bookObjectRepository.findByBankBook_Id(bankBookId);
/*  62 */     for (BookObject feedObject : feedObjects) {
/*  63 */       BookObjectDTO BookObjectDTO = (BookObjectDTO)this.modelMapper.map(feedObject, BookObjectDTO.class);
/*  64 */       BookObjectDTOS.add(BookObjectDTO);
/*     */     } 
/*     */     
/*  67 */     return BookObjectDTOS;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BookObjectDTO> getPagedFeedsByBookId(Long bankBookId, Pageable pageable) {
/*  72 */     List<BookObjectDTO> BookObjectDTOS = new ArrayList<>();
/*  73 */     Page<BookObject> feedObjects = this.bookObjectRepository.findByBankBook_Id(bankBookId, pageable);
/*  74 */     for (BookObject feedObject : feedObjects.getContent()) {
/*  75 */       BookObjectDTO BookObjectDTO = (BookObjectDTO)this.modelMapper.map(feedObject, BookObjectDTO.class);
/*  76 */       BookObjectDTOS.add(BookObjectDTO);
/*     */     } 
/*  78 */     return BookObjectDTOS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BookObjectDTO> getAllFeeds() {
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BookObjectDTO addBookObject(BookObjectDTO bookObjectDTO) {
/*  94 */     BookObject feedObject = (BookObject)this.modelMapper.map(bookObjectDTO, BookObject.class);
/*     */     
/*  96 */     if (feedObject == null || feedObject.getBankBook() == null) {
/*  97 */       throw new SyncBaseException("Invalid FileObject passed");
/*     */     }
/*     */     
/* 100 */     BookObjectDTO check = getFeedByNameAndBookname(feedObject.getName(), feedObject.getBankBook().getBookName());
/*     */     
/* 102 */     if (check != null) {
/* 103 */       throw new SyncBaseException("BookObject Already Exists");
/*     */     }
/*     */     
/* 106 */     feedObject.setDelFlag("N");
/* 107 */     feedObject.setEnabled(Boolean.valueOf(true));
/* 108 */     feedObject = (BookObject)this.bookObjectRepository.save(feedObject);
/* 109 */     logger.info("File Object {}  has been Added", feedObject.getName());
/* 110 */     bookObjectDTO.setId(feedObject.getId());
/*     */     
/* 112 */     return bookObjectDTO;
/*     */   }
/*     */ 
/*     */   
/*     */   public BookObjectDTO getFeedByNameAndBookname(String ObjectName, String bookName) {
/* 117 */     System.out.println(ObjectName + " " + bookName);
/* 118 */     BankBookDTO bankBookDTO = this.bookService.getBookByName(bookName);
/* 119 */     BookObjectDTO objectDTO = new BookObjectDTO();
/* 120 */     objectDTO.setName(ObjectName);
/* 121 */     objectDTO.setBankBookDTO(bankBookDTO);
/*     */     
/* 123 */     if (bankBookDTO == null) {
/* 124 */       throw new SyncBaseException("Book Not found :: " + bookName);
/*     */     }
/* 126 */     System.out.println(objectDTO);
/* 127 */     BookObject feedObject = this.bookObjectRepository.findByNameAndBankBook_BookName(objectDTO.getName(), objectDTO.getBankBookDTO().getBookName());
/* 128 */     BookObjectDTO BookObjectDTO = null;
/* 129 */     if (feedObject != null) {
/* 130 */       BookObjectDTO = (BookObjectDTO)this.modelMapper.map(feedObject, BookObjectDTO.class);
/*     */     }
/* 132 */     return BookObjectDTO;
/*     */   }
/*     */ 
/*     */   
/*     */   public BookObjectDTO getBookObjectById(Long feedObjectId) {
/* 137 */     BookObject feedObject = this.bookObjectRepository.findById(feedObjectId).orElse(null);
/* 138 */     BookObjectDTO BookObjectDTO = null;
/* 139 */     if (feedObject != null) {
/* 140 */       BookObjectDTO = (BookObjectDTO)this.modelMapper.map(feedObject, BookObjectDTO.class);
/*     */     }
/* 142 */     return BookObjectDTO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BookObjectDTO updateBookObject(BookObjectDTO bookObjectDTO) {
/* 150 */     if (bookObjectDTO == null || bookObjectDTO.getBankBookDTO() == null) {
/* 151 */       throw new SyncBaseException("Invalid FileObject DTO");
/*     */     }
/* 153 */     BankBookDTO bankBookDTO = this.bookService.getBookByName(bookObjectDTO.getBankBookDTO().getBookName());
/* 154 */     if (bankBookDTO == null) {
/* 155 */       throw new SyncBaseException("File Object Not found :: " + bookObjectDTO.getBankBookDTO().getBookName());
/*     */     }
/*     */     
/* 158 */     BookObject feedObject = this.bookObjectRepository.findByNameAndBankBook_BookName(bookObjectDTO.getName(), bookObjectDTO.getBankBookDTO().getBookName());
/*     */     
/* 160 */     if (feedObject == null) {
/* 161 */       throw new SyncBaseException("File Object Does not Exist");
/*     */     }
/*     */     
/* 164 */     feedObject.setDefaultValue(bookObjectDTO.getDefaultValue());
/* 165 */     feedObject.setDescription(bookObjectDTO.getDescription());
/* 166 */     feedObject.setMappedColumn(bookObjectDTO.getMappedColumn());
/* 167 */     feedObject.setEnabled(bookObjectDTO.getEnabled());
/* 168 */     feedObject.setMappedColumnType(bookObjectDTO.getMappedColumnType());
/*     */     
/* 170 */     this.bookObjectRepository.save(feedObject);
/* 171 */     logger.info("File Object {} has been updated", feedObject.getName());
/*     */     
/* 173 */     return bookObjectDTO;
/*     */   }
/*     */ 
/*     */   
/*     */   public String uploadBookObjects(MultipartFile multipartFile, Long bookId) {
/* 178 */     String fileName = multipartFile.getOriginalFilename();
/*     */ 
/*     */     
/* 181 */     File file = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
/*     */     
/* 183 */     System.out.println(file.getPath());
/*     */     try {
/* 185 */       multipartFile.transferTo(file);
/*     */       
/* 187 */       CSVReader reader = new CSVReader(new FileReader(file));
/*     */       
/* 189 */       long cnt = 0L; String[] line;
/* 190 */       while ((line = reader.readNext()) != null) {
/* 191 */         if (cnt == 0L) {
/* 192 */           List<String> csvHeader = Arrays.asList(this.configureHeaders);
/* 193 */           if (!csvHeader.equals(Arrays.asList(line))) {
/* 194 */             throw new SyncBaseException("Invalid Header Found in Upload");
/*     */           }
/*     */         } else {
/* 197 */           List<String> datas = Arrays.asList(line);
/* 198 */           BookObjectDTO feedObjectDTO = new BookObjectDTO();
/*     */           
/* 200 */           BankBookDTO bankBookDTO = this.bookService.getBookByName(datas.get(0));
/* 201 */           if (bankBookDTO == null) {
/* 202 */             throw new SyncBaseException("Invalid Book Name at row : " + cnt);
/*     */           }
/* 204 */           feedObjectDTO.setBankBookDTO(bankBookDTO);
/* 205 */           feedObjectDTO.setMappedColumnType(datas.get(4));
/* 206 */           feedObjectDTO.setName(datas.get(1));
/* 207 */           feedObjectDTO.setDescription(datas.get(2));
/* 208 */           feedObjectDTO.setDefaultValue(datas.get(5));
/* 209 */           feedObjectDTO.setMappedColumn(datas.get(3));
/* 210 */           feedObjectDTO.setDelFlag("N");
/* 211 */           feedObjectDTO.setEnabled(Boolean.valueOf(true));
/*     */           
/* 213 */           BookObjectDTO check = getFeedByNameAndBookname(feedObjectDTO.getName(), feedObjectDTO.getBankBookDTO().getBookName());
/* 214 */           if (check == null) {
/* 215 */             addBookObject(feedObjectDTO);
/*     */           } else {
/* 217 */             updateBookObject(feedObjectDTO);
/*     */           } 
/*     */         } 
/* 220 */         cnt++;
/*     */       } 
/*     */       
/* 223 */       reader.close();
/*     */     }
/* 225 */     catch (IOException e) {
/* 226 */       file.delete();
/* 227 */       e.printStackTrace();
/* 228 */       throw new SyncBaseException(e.getMessage());
/* 229 */     } catch (Exception e) {
/* 230 */       e.printStackTrace();
/* 231 */       throw new SyncBaseException(e.getMessage());
/*     */     } finally {
/*     */       try {
/* 234 */         file.delete();
/* 235 */       } catch (Exception ex) {
/* 236 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 240 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteBookObject(Long id) {
/* 245 */     BookObject bookObject = this.bookObjectRepository.findById(id).orElse(null);
/* 246 */     if (bookObject == null) {
/* 247 */       throw new SyncBaseException("Book Not Found");
/*     */     }
/*     */     
/* 250 */     bookObject.setDelFlag("Y");
/* 251 */     bookObject.setDeletedOn(new Date());
/* 252 */     this.bookObjectRepository.save(bookObject);
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\fileobjects\service\implementation\BookObjectServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */