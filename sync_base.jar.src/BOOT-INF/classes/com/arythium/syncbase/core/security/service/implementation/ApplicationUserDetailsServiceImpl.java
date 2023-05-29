/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.security.service.implementation;
/*    */ 
/*    */ import com.arythium.syncbase.core.security.service.implementation.CustomUserPrincipal;
/*    */ import com.arythium.syncbase.core.usermgt.exception.AppUserServiceException;
/*    */ import com.arythium.syncbase.core.usermgt.model.AbstractUser;
/*    */ import com.arythium.syncbase.core.usermgt.model.SystemUser;
/*    */ import com.arythium.syncbase.core.usermgt.service.ApplicationUserService;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.security.core.userdetails.UserDetails;
/*    */ import org.springframework.security.core.userdetails.UserDetailsService;
/*    */ import org.springframework.security.core.userdetails.UsernameNotFoundException;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service("appUserDetailsService")
/*    */ public class ApplicationUserDetailsServiceImpl
/*    */   implements UserDetailsService
/*    */ {
/* 24 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.core.security.service.implementation.ApplicationUserDetailsServiceImpl.class);
/*    */   
/*    */   private final ApplicationUserService applicationUserService;
/*    */   
/*    */   @Autowired
/*    */   public ApplicationUserDetailsServiceImpl(ApplicationUserService applicationUserService) {
/* 30 */     this.applicationUserService = applicationUserService;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UserDetails loadUserByUsername(String username) throws AppUserServiceException {
/* 36 */     logger.debug("User [{}] logging in", username);
/*    */     
/* 38 */     SystemUser systemUser = this.applicationUserService.findUserByUsername(username);
/*    */     
/* 40 */     if (systemUser == null) {
/* 41 */       throw new UsernameNotFoundException("Username not found: " + username);
/*    */     }
/* 43 */     return (UserDetails)new CustomUserPrincipal((AbstractUser)systemUser);
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\security\service\implementation\ApplicationUserDetailsServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */