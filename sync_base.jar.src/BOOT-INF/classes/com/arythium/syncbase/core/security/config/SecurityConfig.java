/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.security.config;
/*    */ 
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*    */ import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*    */ import org.springframework.security.web.session.HttpSessionEventPublisher;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @EnableWebSecurity
/*    */ @EnableGlobalMethodSecurity(prePostEnabled = true)
/*    */ public class SecurityConfig
/*    */ {
/*    */   @Bean
/*    */   public HttpSessionEventPublisher httpSessionEventPublisher() {
/* 40 */     return new HttpSessionEventPublisher();
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public static BCryptPasswordEncoder passwordEncoder() {
/* 45 */     return new BCryptPasswordEncoder();
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\security\config\SecurityConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */