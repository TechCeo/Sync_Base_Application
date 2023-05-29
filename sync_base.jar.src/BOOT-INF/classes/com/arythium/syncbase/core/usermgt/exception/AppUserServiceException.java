/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.usermgt.exception;
/*    */ 
/*    */ import com.arythium.syncbase.core.exceptions.SyncBaseException;
/*    */ 
/*    */ public class AppUserServiceException extends SyncBaseException {
/*    */   String message;
/*    */   Object obj;
/*    */   
/*    */   public AppUserServiceException() {
/* 10 */     super("Failed to perform the requested action");
/*    */   }
/*    */   
/*    */   public AppUserServiceException(Throwable cause) {
/* 14 */     super("Failed to perform the requested action", cause);
/*    */   }
/*    */   
/*    */   public AppUserServiceException(String message) {
/* 18 */     this.message = message;
/*    */   }
/*    */   
/*    */   public AppUserServiceException(String message, Throwable cause) {
/* 22 */     super(message, cause);
/*    */   }
/*    */   public AppUserServiceException(String message, Object obj) {
/* 25 */     this.message = message;
/* 26 */     this.obj = obj;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 31 */     return this.message;
/*    */   }
/*    */   
/*    */   public void setMessage(String message) {
/* 35 */     this.message = message;
/*    */   }
/*    */   
/*    */   public Object getObj() {
/* 39 */     return this.obj;
/*    */   }
/*    */   
/*    */   public void setObj(Object obj) {
/* 43 */     this.obj = obj;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\usermgt\exception\AppUserServiceException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */