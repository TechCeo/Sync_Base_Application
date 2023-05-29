/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.config;
/*    */ 
/*    */ import org.modelmapper.ModelMapper;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.context.support.ResourceBundleMessageSource;
/*    */ 
/*    */ @Configuration
/*    */ public class Configuration
/*    */ {
/*    */   @Bean
/*    */   public ResourceBundleMessageSource messageSource() {
/* 13 */     ResourceBundleMessageSource source = new ResourceBundleMessageSource();
/* 14 */     String[] baseNames = { "i18n/messages", "i18n/menu" };
/* 15 */     source.setBasenames(baseNames);
/* 16 */     source.setCacheSeconds(1000);
/* 17 */     source.setUseCodeAsDefaultMessage(true);
/* 18 */     return source;
/*    */   }
/*    */ 
/*    */   
/*    */   @Bean
/*    */   public ModelMapper modelMapper() {
/* 24 */     return new ModelMapper();
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\config\Configuration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */