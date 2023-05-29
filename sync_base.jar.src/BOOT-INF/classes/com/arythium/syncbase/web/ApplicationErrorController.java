/*    */ package BOOT-INF.classes.com.arythium.syncbase.web;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.boot.web.servlet.error.ErrorAttributes;
/*    */ import org.springframework.boot.web.servlet.error.ErrorController;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.context.request.WebRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ public class ApplicationErrorController
/*    */   implements ErrorController
/*    */ {
/*    */   private static final String PATH = "/error";
/*    */   @Autowired
/*    */   private ErrorAttributes errorAttributes;
/* 27 */   private Logger logger = LoggerFactory.getLogger(getClass());
/*    */ 
/*    */   
/*    */   @Value("${dev.members.mail}")
/*    */   private String devMembersMails;
/*    */ 
/*    */ 
/*    */   
/*    */   @RequestMapping({"/error"})
/*    */   public String handleError(WebRequest request) {
/* 37 */     Map<String, Object> errorDetails = this.errorAttributes.getErrorAttributes(request, true);
/*    */     
/* 39 */     String errorPath = (String)errorDetails.get("path");
/* 40 */     String statusCode = errorDetails.get("status").toString();
/* 41 */     Object exception = errorDetails.get("exception");
/*    */     
/* 43 */     this.logger.error("Page Error Detail: {}", errorDetails.get("error"));
/*    */ 
/*    */     
/* 46 */     if (exception != null) {
/* 47 */       sendNotification(errorDetails);
/*    */     }
/*    */     
/* 50 */     if ("403".equals(statusCode)) {
/* 51 */       return "error403";
/*    */     }
/*    */     
/* 54 */     if ("404".equals(statusCode)) {
/* 55 */       return "error404";
/*    */     }
/*    */     
/* 58 */     String subPath = StringUtils.substringAfter(errorPath, "/");
/*    */ 
/*    */     
/* 61 */     if (subPath != null) {
/* 62 */       return "error";
/*    */     }
/*    */     
/* 65 */     return "redirect:/login";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getErrorPath() {
/* 71 */     return "/error";
/*    */   }
/*    */   
/*    */   private void sendNotification(Map errorDetails) {}
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\web\ApplicationErrorController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */