/*    */ package BOOT-INF.classes.com.arythium.syncbase.web.reports;
/*    */ 
/*    */ import com.arythium.syncbase.application.filesync.dto.BookSyncDto;
/*    */ import com.arythium.syncbase.application.filesync.service.BookSyncService;
/*    */ import java.util.List;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.web.bind.annotation.GetMapping;
/*    */ import org.springframework.web.bind.annotation.PathVariable;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ @RequestMapping({"/reports"})
/*    */ public class ReportController
/*    */ {
/* 21 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.web.reports.ReportController.class);
/*    */ 
/*    */ 
/*    */   
/*    */   @Autowired
/*    */   private BookSyncService bookSyncService;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @GetMapping({"/book_sync"})
/*    */   public String index(Model model) {
/* 33 */     List<BookSyncDto> bookSyncDtoList = this.bookSyncService.getSyncAudit();
/* 34 */     model.addAttribute("syncAudits", bookSyncDtoList);
/* 35 */     return "reports/book_sync/list";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @GetMapping({"/book_sync/{procID}/edit"})
/*    */   public String viewBookSyncAudit(@PathVariable("procID") String procID, Model model) {
/* 42 */     BookSyncDto bookSyncDto = this.bookSyncService.getBookSyncByProcID(procID);
/*    */ 
/*    */ 
/*    */     
/* 46 */     model.addAttribute("book_sync", bookSyncDto);
/*    */     
/* 48 */     return "reports/book_sync/edit";
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\web\reports\ReportController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */