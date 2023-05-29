/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.security.service;
/*    */ 
/*    */ import com.arythium.syncbase.core.usermgt.model.SystemUser;
/*    */ import com.arythium.syncbase.core.usermgt.repository.ApplicationUserRepository;
/*    */ import java.util.Date;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.security.crypto.password.PasswordEncoder;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class LoginService
/*    */ {
/* 23 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.core.security.service.LoginService.class);
/*    */ 
/*    */   
/*    */   @Autowired
/*    */   private ApplicationUserRepository userRepository;
/*    */ 
/*    */   
/*    */   @Autowired
/*    */   private PasswordEncoder passwordEncoder;
/*    */ 
/*    */   
/*    */   public void updateUserLoginAttempt(SystemUser user) {
/* 35 */     int numOfLoginAttempts = user.getNoOfWrongLoginCount().intValue();
/* 36 */     user.setNoOfWrongLoginCount(Integer.valueOf(++numOfLoginAttempts));
/* 37 */     this.userRepository.save(user);
/* 38 */     logger.info("Updated failed login count for user [{}]", user.getUserName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateLastLogin(SystemUser user) {
/* 45 */     user.setLastLoginDate(new Date());
/* 46 */     this.userRepository.save(user);
/* 47 */     logger.info("Updated last login date for user [{}]", user.getUserName());
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\security\service\LoginService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */