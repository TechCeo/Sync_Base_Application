/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.usermgt.exception;
/*    */ 
/*    */ import com.arythium.syncbase.core.exceptions.SyncBaseException;
/*    */ 
/*    */ public class UserNotFoundException
/*    */   extends SyncBaseException
/*    */ {
/*    */   public UserNotFoundException() {
/*  9 */     super("User not found");
/*    */   }
/*    */   
/*    */   public UserNotFoundException(Throwable cause) {
/* 13 */     super("User not found", cause);
/*    */   }
/*    */   
/*    */   public UserNotFoundException(String message) {
/* 17 */     super(message);
/*    */   }
/*    */   
/*    */   public UserNotFoundException(String message, Throwable cause) {
/* 21 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\usermgt\exception\UserNotFoundException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */