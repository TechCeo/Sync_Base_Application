/*    */ package BOOT-INF.classes.com.arythium.syncbase.web.dashboard;
/*    */ 
/*    */ import com.arythium.syncbase.application.filesync.dto.BookSyncDashBoardAudit;
/*    */ import com.arythium.syncbase.application.filesync.service.BookSyncService;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.web.bind.annotation.GetMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ @RequestMapping({""})
/*    */ public class DashBoardController
/*    */ {
/* 18 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.web.dashboard.DashBoardController.class);
/*    */   
/*    */   @Autowired
/*    */   BookSyncService bookSyncService;
/*    */ 
/*    */   
/*    */   @RequestMapping({"/"})
/*    */   public String index() {
/* 26 */     return "redirect:/dashboard";
/*    */   }
/*    */ 
/*    */   
/*    */   @GetMapping({"/dashboard"})
/*    */   public String dashBoardIndex(Model model) {
/* 32 */     BookSyncDashBoardAudit bookSyncDashBoardAudit = this.bookSyncService.getSyncDashBoardData();
/* 33 */     model.addAttribute("dashBoardData", bookSyncDashBoardAudit);
/* 34 */     return "dashboard/index";
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\web\dashboard\DashBoardController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */