/*    */ package BOOT-INF.classes.com.arythium.syncbase.web;
/*    */ 
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.web.bind.annotation.GetMapping;
/*    */ import org.springframework.web.bind.annotation.RequestParam;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ public class indexController
/*    */ {
/* 16 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.web.indexController.class);
/*    */ 
/*    */ 
/*    */   
/*    */   @GetMapping({"/login"})
/*    */   public String loginPage(@RequestParam(value = "failed", required = false) boolean failed, @RequestParam(value = "expired", required = false) boolean expired, Model model) {
/* 22 */     if (failed) {
/* 23 */       model.addAttribute("failed", Boolean.valueOf(failed));
/*    */     }
/* 25 */     if (expired) {
/* 26 */       model.addAttribute("expired", Boolean.valueOf(expired));
/*    */     }
/*    */     
/* 29 */     return "login";
/*    */   }
/*    */   
/*    */   @GetMapping({"/log"})
/*    */   public String log() {
/* 34 */     logger.trace("This is a TRACE level message");
/* 35 */     logger.debug("This is a DEBUG level message");
/* 36 */     logger.info("This is an INFO level message");
/* 37 */     logger.warn("This is a WARN level message");
/* 38 */     logger.error("This is an ERROR level message");
/* 39 */     return "See the log for details";
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\web\indexController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */