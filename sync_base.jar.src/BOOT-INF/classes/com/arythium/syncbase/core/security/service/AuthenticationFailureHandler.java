/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.security.service;
/*    */ 
/*    */ import com.arythium.syncbase.core.security.service.LoginService;
/*    */ import com.arythium.syncbase.core.usermgt.model.SystemUser;
/*    */ import com.arythium.syncbase.core.usermgt.repository.ApplicationUserRepository;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.security.core.AuthenticationException;
/*    */ import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class AuthenticationFailureHandler
/*    */   extends SimpleUrlAuthenticationFailureHandler
/*    */ {
/* 27 */   private Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.core.security.service.AuthenticationFailureHandler.class);
/*    */ 
/*    */   
/*    */   @Autowired
/*    */   private ApplicationUserRepository userRepository;
/*    */   
/*    */   @Autowired
/*    */   private LoginService loginService;
/*    */ 
/*    */   
/*    */   public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
/* 38 */     setDefaultFailureUrl("/login?failed=true");
/* 39 */     String usernameAndToken = request.getParameter("username");
/* 40 */     String[] strings = StringUtils.split(usernameAndToken, ":");
/*    */ 
/*    */     
/* 43 */     if (strings != null && strings.length == 2) {
/* 44 */       String username = strings[0];
/* 45 */       this.logger.info("user {}", username);
/* 46 */       SystemUser systemUser = this.userRepository.findByUserName(username);
/* 47 */       if (systemUser != null) {
/* 48 */         this.loginService.updateUserLoginAttempt(systemUser);
/* 49 */         this.logger.error("Failed authentication for user [{}] ", username);
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     this.logger.error("Failed authentication using credentials: {}  {}", usernameAndToken, exception.getMessage());
/* 54 */     super.onAuthenticationFailure(request, response, exception);
/* 55 */     request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception.getMessage());
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\security\service\AuthenticationFailureHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */