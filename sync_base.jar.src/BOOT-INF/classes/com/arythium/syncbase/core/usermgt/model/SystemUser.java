/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.usermgt.model;
/*    */ import com.arythium.syncbase.core.usermgt.model.AbstractUser;
/*    */ import javax.persistence.Entity;
/*    */ import org.hibernate.annotations.Where;
/*    */ 
/*    */ @Entity(name = "app_system_user")
/*    */ @Where(clause = "del_Flag='N'")
/*    */ public class SystemUser extends AbstractUser {
/*    */   public String toString() {
/* 10 */     return "SystemUser()";
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\usermgt\model\SystemUser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */