/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.exceptions;
/*    */ 
/*    */ public class SyncBaseException
/*    */   extends RuntimeException {
/*    */   public SyncBaseException() {
/*  6 */     super("Failed to perform the requested action");
/*    */   }
/*    */   
/*    */   public SyncBaseException(Throwable cause) {
/* 10 */     super("Failed to perform the requested action", cause);
/*    */   }
/*    */   
/*    */   public SyncBaseException(String message) {
/* 14 */     super(message);
/*    */   }
/*    */   
/*    */   public SyncBaseException(String message, Throwable cause) {
/* 18 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\exceptions\SyncBaseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */