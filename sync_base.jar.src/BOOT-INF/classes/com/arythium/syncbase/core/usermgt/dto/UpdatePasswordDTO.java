/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.usermgt.dto;
/*    */ 
/*    */ 
/*    */ public class UpdatePasswordDTO
/*    */ {
/*    */   private Long id;
/*    */   private String confirmpassword;
/*    */   private String oldPassword;
/*    */   private String newPassword;
/*    */   
/*    */   public Long getId() {
/* 12 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(Long id) {
/* 16 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getConfirmpassword() {
/* 20 */     return this.confirmpassword;
/*    */   }
/*    */   
/*    */   public void setConfirmpassword(String confirmpassword) {
/* 24 */     this.confirmpassword = confirmpassword;
/*    */   }
/*    */   
/*    */   public String getOldPassword() {
/* 28 */     return this.oldPassword;
/*    */   }
/*    */   
/*    */   public void setOldPassword(String oldPassword) {
/* 32 */     this.oldPassword = oldPassword;
/*    */   }
/*    */   
/*    */   public String getNewPassword() {
/* 36 */     return this.newPassword;
/*    */   }
/*    */   
/*    */   public void setNewPassword(String newPassword) {
/* 40 */     this.newPassword = newPassword;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\usermgt\dto\UpdatePasswordDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */