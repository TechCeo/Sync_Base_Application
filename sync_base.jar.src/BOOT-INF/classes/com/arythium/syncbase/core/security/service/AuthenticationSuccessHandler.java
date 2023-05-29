/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.security.service;
/*    */ 
/*    */ import com.arythium.syncbase.core.security.service.LoginService;
/*    */ import com.arythium.syncbase.core.usermgt.model.SystemUser;
/*    */ import com.arythium.syncbase.core.usermgt.repository.ApplicationUserRepository;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.security.core.Authentication;
/*    */ import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class AuthenticationSuccessHandler
/*    */   extends SimpleUrlAuthenticationSuccessHandler
/*    */ {
/* 25 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.core.security.service.AuthenticationSuccessHandler.class);
/*    */ 
/*    */   
/*    */   @Autowired
/*    */   private LoginService loginService;
/*    */   
/*    */   @Autowired
/*    */   private ApplicationUserRepository userRepository;
/*    */ 
/*    */   
/*    */   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
/* 36 */     logger.info("Authentication successful");
/* 37 */     SystemUser user = this.userRepository.findByUserName(authentication.getName());
/* 38 */     this.loginService.updateLastLogin(user);
/* 39 */     super.onAuthenticationSuccess(request, response, authentication);
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\security\service\AuthenticationSuccessHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */