/*    */ package BOOT-INF.classes.com.arythium.syncbase.web.codes;
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
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ @RequestMapping({"/codes"})
/*    */ public class CodeController
/*    */ {
/* 20 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.web.codes.CodeController.class);
/*    */ 
/*    */   
/*    */   @Autowired
/*    */   private BookSyncService bookSyncService;
/*    */ 
/*    */ 
/*    */   
/*    */   @GetMapping({""})
/*    */   public String index(Model model) {
/* 30 */     List<BookSyncDto> bookSyncDtoList = this.bookSyncService.getSyncAudit();
/* 31 */     model.addAttribute("syncAudits", bookSyncDtoList);
/* 32 */     return "codes/list";
/*    */   }
/*    */ 
/*    */   
/*    */   @GetMapping({"/add"})
/*    */   public String addPage() {
/* 38 */     return "codes/add";
/*    */   }
/*    */ 
/*    */   
/*    */   @GetMapping({"/{id}/edit"})
/*    */   public String editPage() {
/* 44 */     return "codes/edit";
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\web\codes\CodeController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */