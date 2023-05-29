/*     */ package BOOT-INF.classes.com.arythium.syncbase.web.books;
/*     */ import com.arythium.syncbase.application.bankfiles.dto.BankBookDTO;
/*     */ import com.arythium.syncbase.application.bankfiles.service.BookService;
/*     */ import com.arythium.syncbase.application.fileobjects.dto.BookObjectDTO;
/*     */ import com.arythium.syncbase.application.fileobjects.service.BookObjectService;
/*     */ import com.arythium.syncbase.core.exceptions.SyncBaseException;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.validation.Valid;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.MessageSource;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.Model;
/*     */ import org.springframework.validation.BindingResult;
/*     */ import org.springframework.web.bind.annotation.GetMapping;
/*     */ import org.springframework.web.bind.annotation.ModelAttribute;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/*     */ 
/*     */ @Controller
/*     */ @RequestMapping({"/books"})
/*     */ public class BookController {
/*  29 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.web.books.BookController.class);
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private MessageSource messageSource;
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   BookService bookService;
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   BookObjectService bookObjectService;
/*     */ 
/*     */   
/*     */   @GetMapping({""})
/*     */   public String index(Model model) {
/*  46 */     List<BankBookDTO> bankBookDTOS = this.bookService.getAllBooks();
/*  47 */     model.addAttribute("books", bankBookDTOS);
/*  48 */     return "books/list";
/*     */   }
/*     */   
/*     */   @GetMapping({"/add"})
/*     */   public String addBookView(Model model) {
/*  53 */     model.addAttribute("book", new BankBookDTO());
/*  54 */     return "books/add";
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/add"}, method = {RequestMethod.POST})
/*     */   public String addBook(@Valid @ModelAttribute("book") BankBookDTO bankBookDTO, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale) {
/*  60 */     logger.info("BankBook details: {}", bankBookDTO);
/*     */     try {
/*  62 */       bankBookDTO = this.bookService.addBook(bankBookDTO);
/*  63 */       model.addAttribute("successMessage", "Successfully added Book");
/*  64 */       model.addAttribute("book", new BankBookDTO());
/*  65 */     } catch (SyncBaseException e) {
/*  66 */       model.addAttribute("errorMessage", e.getMessage());
/*  67 */     } catch (Exception e) {
/*  68 */       model.addAttribute("errorMessage", e.getMessage());
/*     */     } 
/*  70 */     return "books/add";
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/bulk/upload"}, method = {RequestMethod.POST})
/*     */   public String uploadBooks(@RequestParam("uploadFile") MultipartFile file, Model model, RedirectAttributes redirectAttributes) {
/*  76 */     if (file.isEmpty()) {
/*  77 */       logger.info("Empty file");
/*  78 */       redirectAttributes.addFlashAttribute("errorMessage", "Please select a file to upload");
/*  79 */       return "redirect:/books/add";
/*     */     } 
/*     */     try {
/*  82 */       this.bookService.uploadBooks(file);
/*  83 */       model.addAttribute("successMessage", "Successfully Uploaded Books");
/*  84 */     } catch (SyncBaseException e) {
/*  85 */       model.addAttribute("errorMessage", e.getMessage());
/*  86 */     } catch (Exception e) {
/*  87 */       model.addAttribute("errorMessage", e.getMessage());
/*     */     } 
/*  89 */     List<BankBookDTO> bankBookDTOS = this.bookService.getAllBooks();
/*  90 */     model.addAttribute("books", bankBookDTOS);
/*  91 */     return "books/list";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GetMapping({"/{id}"})
/*     */   public String getBookView(@PathVariable("id") Long id, Model model) {
/*  99 */     BankBookDTO bankBookDTO = this.bookService.getBookById(id);
/* 100 */     model.addAttribute("book", bankBookDTO);
/* 101 */     return "books/edit";
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/{id}/edit"}, method = {RequestMethod.POST})
/*     */   public String editBook(@PathVariable("id") Long id, @Valid @ModelAttribute("book") BankBookDTO bankBookDTO, Model model) {
/* 107 */     logger.info("BankBook details: {}", bankBookDTO);
/*     */     try {
/* 109 */       bankBookDTO = this.bookService.updateBook(bankBookDTO);
/* 110 */       model.addAttribute("successMessage", "Successfully Updated Book");
/* 111 */     } catch (SyncBaseException e) {
/* 112 */       model.addAttribute("errorMessage", e.getMessage());
/* 113 */       bankBookDTO = this.bookService.getBookById(id);
/* 114 */     } catch (Exception e) {
/* 115 */       model.addAttribute("errorMessage", e.getMessage());
/* 116 */       bankBookDTO = this.bookService.getBookById(id);
/*     */     } 
/*     */     
/* 119 */     model.addAttribute("book", bankBookDTO);
/* 120 */     return "books/edit";
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/{id}/delete"}, method = {RequestMethod.GET})
/*     */   public String deleteBook(@PathVariable("id") Long id, Model model, Locale locale) {
/*     */     try {
/* 127 */       this.bookService.deleteBook(id);
/* 128 */       model.addAttribute("successMessage", "Successfully Deleted Book");
/* 129 */     } catch (SyncBaseException e) {
/* 130 */       model.addAttribute("errorMessage", e.getMessage());
/* 131 */     } catch (Exception e) {
/* 132 */       model.addAttribute("errorMessage", e.getMessage());
/*     */     } 
/* 134 */     List<BankBookDTO> bankBookDTOS = this.bookService.getAllBooks();
/* 135 */     model.addAttribute("books", bankBookDTOS);
/* 136 */     return "books/list";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GetMapping({"/{bankBookId}/objects"})
/*     */   public String getBookObjectsView(@PathVariable("bankBookId") Long bankBookId, Model model, RedirectAttributes redirectAttributes) {
/* 147 */     List<BookObjectDTO> feedObjectDTOList = this.bookObjectService.getFeedsByBookId(bankBookId);
/* 148 */     model.addAttribute("bookobjects", feedObjectDTOList);
/* 149 */     model.addAttribute("bookId", bankBookId);
/* 150 */     return "bookobjects/list";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GetMapping({"/{bankBookId}/objects/add"})
/*     */   public String addBookObjectsView(@PathVariable("bankBookId") Long bankBookId, Model model, RedirectAttributes redirectAttributes) {
/* 158 */     model.addAttribute("bookobject", new BookObjectDTO());
/* 159 */     model.addAttribute("bookId", bankBookId);
/* 160 */     return "bookobjects/add";
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/{bankBookId}/objects/add"}, method = {RequestMethod.POST})
/*     */   public String addBookObject(@PathVariable("bankBookId") Long bankBookId, @Valid @ModelAttribute("bookobject") BookObjectDTO bookObjectDTO, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale) {
/* 166 */     logger.info("bookObjectDTO details: {}", bookObjectDTO);
/*     */     
/*     */     try {
/* 169 */       BankBookDTO bankBookDTO = this.bookService.getBookById(bankBookId);
/* 170 */       bookObjectDTO.setBankBookDTO(bankBookDTO);
/* 171 */       this.bookObjectService.addBookObject(bookObjectDTO);
/* 172 */       List<BookObjectDTO> feedObjectDTOList = this.bookObjectService.getFeedsByBookId(bankBookId);
/* 173 */       model.addAttribute("bookobjects", feedObjectDTOList);
/* 174 */       model.addAttribute("bookId", bankBookId);
/* 175 */       model.addAttribute("successMessage", "Successfully Added Object");
/* 176 */       return "bookobjects/list";
/* 177 */     } catch (SyncBaseException e) {
/* 178 */       e.printStackTrace();
/* 179 */       model.addAttribute("errorMessage", e.getMessage());
/* 180 */     } catch (Exception e) {
/* 181 */       e.printStackTrace();
/* 182 */       model.addAttribute("errorMessage", e.getMessage());
/*     */     } 
/*     */     
/* 185 */     model.addAttribute("bookobject", bookObjectDTO);
/* 186 */     model.addAttribute("bookId", bankBookId);
/* 187 */     return bankBookId + "bookobjects/add";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/{bookId}/objects/bulk/upload"}, method = {RequestMethod.POST})
/*     */   public String uploadBookObjects(@PathVariable("bookId") Long bookId, @RequestParam("uploadFile") MultipartFile file, Model model, RedirectAttributes redirectAttributes) {
/* 194 */     if (file.isEmpty()) {
/* 195 */       logger.info("Empty Object file");
/* 196 */       model.addAttribute("errorMessage", "Please select a file to upload");
/*     */     } else {
/*     */       try {
/* 199 */         this.bookObjectService.uploadBookObjects(file, bookId);
/* 200 */         model.addAttribute("successMessage", "Successfully Uploaded Books");
/* 201 */       } catch (SyncBaseException e) {
/* 202 */         model.addAttribute("errorMessage", e.getMessage());
/* 203 */       } catch (Exception e) {
/* 204 */         model.addAttribute("errorMessage", e.getMessage());
/*     */       } 
/*     */     } 
/*     */     
/* 208 */     List<BookObjectDTO> feedObjectDTOList = this.bookObjectService.getFeedsByBookId(bookId);
/* 209 */     model.addAttribute("bookobjects", feedObjectDTOList);
/* 210 */     model.addAttribute("bookId", bookId);
/* 211 */     return "bookobjects/list";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GetMapping({"/{bankBookId}/objects/{bookObjectId}"})
/*     */   public String getBookObjectView(@PathVariable("bankBookId") Long bankBookId, @PathVariable("bookObjectId") Long bookObjectId, Model model, RedirectAttributes redirectAttributes) {
/* 218 */     BookObjectDTO feedObjectDTO = this.bookObjectService.getBookObjectById(bookObjectId);
/* 219 */     model.addAttribute("bookobject", feedObjectDTO);
/* 220 */     model.addAttribute("bookId", bankBookId);
/* 221 */     return "bookobjects/edit";
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/{bankBookId}/objects/{bookObjectId}"}, method = {RequestMethod.POST})
/*     */   public String editBookObject(@PathVariable("bankBookId") Long bankBookId, @PathVariable("bookObjectId") Long bookObjectId, @Valid @ModelAttribute("bookobject") BookObjectDTO bookObjectDTO, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale) {
/* 227 */     logger.info("bookObjectDTO details: {}", bookObjectDTO);
/*     */     
/*     */     try {
/* 230 */       BankBookDTO bankBookDTO = this.bookService.getBookById(bankBookId);
/* 231 */       if (bankBookDTO == null) {
/* 232 */         throw new SyncBaseException("Book Not Found :: " + bankBookId);
/*     */       }
/* 234 */       bookObjectDTO.setBankBookDTO(bankBookDTO);
/* 235 */       this.bookObjectService.updateBookObject(bookObjectDTO);
/* 236 */       model.addAttribute("successMessage", "Successfully Updated Object");
/* 237 */     } catch (SyncBaseException e) {
/* 238 */       e.printStackTrace();
/* 239 */       model.addAttribute("errorMessage", e.getMessage());
/* 240 */     } catch (Exception e) {
/* 241 */       e.printStackTrace();
/* 242 */       model.addAttribute("errorMessage", e.getMessage());
/*     */     } 
/*     */     
/* 245 */     BookObjectDTO bookObjectDTO1 = this.bookObjectService.getBookObjectById(bookObjectId);
/* 246 */     model.addAttribute("bookobjects", bookObjectDTO1);
/* 247 */     model.addAttribute("bookId", bankBookId);
/* 248 */     return "bookobjects/edit";
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/{bankBookId}/objects/{bookObjectId}/delete"}, method = {RequestMethod.GET})
/*     */   public String deleteBookObject(@PathVariable("bankBookId") Long bankBookId, @PathVariable("bookObjectId") Long bookObjectId, Model model, Locale locale) {
/*     */     try {
/* 255 */       this.bookObjectService.deleteBookObject(bookObjectId);
/* 256 */       model.addAttribute("successMessage", "Successfully Deleted Object");
/* 257 */     } catch (SyncBaseException e) {
/* 258 */       e.printStackTrace();
/* 259 */       model.addAttribute("errorMessage", e.getMessage());
/* 260 */     } catch (Exception e) {
/* 261 */       e.printStackTrace();
/* 262 */       model.addAttribute("errorMessage", e.getMessage());
/*     */     } 
/*     */     
/* 265 */     List<BookObjectDTO> feedObjectDTOList = this.bookObjectService.getFeedsByBookId(bankBookId);
/* 266 */     model.addAttribute("bookobjects", feedObjectDTOList);
/* 267 */     model.addAttribute("bookId", bankBookId);
/* 268 */     return "bookobjects/list";
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\web\books\BookController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */