/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.exceptions;
/*    */ 
/*    */ public class FileStorageException
/*    */   extends RuntimeException {
/*    */   public FileStorageException() {
/*  6 */     super("Failed to perform the requested action");
/*    */   }
/*    */   
/*    */   public FileStorageException(Throwable cause) {
/* 10 */     super("Failed to perform the requested action", cause);
/*    */   }
/*    */   
/*    */   public FileStorageException(String message) {
/* 14 */     super(message);
/*    */   }
/*    */   
/*    */   public FileStorageException(String message, Throwable cause) {
/* 18 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\exceptions\FileStorageException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */