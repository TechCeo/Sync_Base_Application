/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.utility;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class AppUtility
/*    */ {
/*    */   public static String randomNumber(int len) {
/*  8 */     String AB = "0123456789";
/*  9 */     Random rnd = new Random();
/* 10 */     StringBuilder sb = new StringBuilder(len);
/* 11 */     for (int i = 0; i < len; i++) {
/* 12 */       sb.append("0123456789".charAt(rnd.nextInt("0123456789".length())));
/*    */     }
/* 14 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public static String randomString(int len) {
/* 18 */     String AB = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789123456789";
/* 19 */     Random rnd = new Random();
/* 20 */     StringBuilder sb = new StringBuilder(len);
/* 21 */     for (int i = 0; i < len; i++) {
/* 22 */       sb.append("ABCDEFGHJKLMNPQRSTUVWXYZ123456789123456789".charAt(rnd.nextInt("ABCDEFGHJKLMNPQRSTUVWXYZ123456789123456789".length())));
/*    */     }
/* 24 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\utility\AppUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */