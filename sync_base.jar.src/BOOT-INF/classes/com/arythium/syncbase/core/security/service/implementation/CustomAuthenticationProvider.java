/*     */ package BOOT-INF.classes.com.arythium.syncbase.core.security.service.implementation;
/*     */ 
/*     */ import com.arythium.syncbase.core.rolemgt.models.Permission;
/*     */ import com.arythium.syncbase.core.setting.service.SettingService;
/*     */ import com.arythium.syncbase.core.usermgt.model.SystemUser;
/*     */ import com.arythium.syncbase.core.usermgt.service.ApplicationUserService;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.security.authentication.AuthenticationProvider;
/*     */ import org.springframework.security.authentication.BadCredentialsException;
/*     */ import org.springframework.security.authentication.DisabledException;
/*     */ import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
/*     */ import org.springframework.security.core.Authentication;
/*     */ import org.springframework.security.core.AuthenticationException;
/*     */ import org.springframework.security.core.GrantedAuthority;
/*     */ import org.springframework.security.core.authority.SimpleGrantedAuthority;
/*     */ import org.springframework.security.core.userdetails.UsernameNotFoundException;
/*     */ import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class CustomAuthenticationProvider
/*     */   implements AuthenticationProvider
/*     */ {
/*  33 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.core.security.service.implementation.CustomAuthenticationProvider.class);
/*     */   
/*     */   private SystemUser user;
/*     */   
/*     */   @Autowired
/*     */   private SettingService settingService;
/*     */   
/*     */   @Autowired
/*     */   private ApplicationUserService applicationUserService;
/*     */   
/*     */   @Autowired
/*     */   private BCryptPasswordEncoder passwordEncoder;
/*     */   
/*     */   public Authentication authenticate(Authentication authentication) throws AuthenticationException {
/*  47 */     String username = authentication.getName();
/*  48 */     String password = authentication.getCredentials().toString();
/*  49 */     System.out.println(String.format("login %s and password %s", new Object[] { username, password }));
/*     */     
/*  51 */     String hexx = this.passwordEncoder.encode(password);
/*     */     
/*  53 */     System.out.println(hexx);
/*     */     
/*  55 */     if (username == null || username.trim().equals("")) {
/*  56 */       throw new UsernameNotFoundException("Username is required");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  61 */     logger.debug("Authenticating user [{}]", username);
/*     */     
/*  63 */     this.user = this.applicationUserService.findUserByUsername(username);
/*     */     
/*  65 */     if (this.user == null) {
/*  66 */       logger.error("Username [{}] not found", username);
/*  67 */       throw new UsernameNotFoundException("Username [" + username + "] not found");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  72 */     String token = "";
/*     */     
/*  74 */     boolean authenticated = authenticateWithDatabase(this.user, password, token);
/*     */     
/*  76 */     if (!authenticated) {
/*  77 */       logger.error("Invalid credentials");
/*  78 */       throw new BadCredentialsException("Invalid credentials");
/*     */     } 
/*     */     
/*  81 */     if (!this.user.isEnabled()) {
/*  82 */       logger.error("User [{}] is disabled", username);
/*  83 */       throw new DisabledException("User [" + username + "] is disabled");
/*     */     } 
/*     */ 
/*     */     
/*  87 */     logger.info("Authentication successful for user [{}]", username);
/*  88 */     Collection<? extends GrantedAuthority> authorities = getAuthorities(this.user);
/*  89 */     return (Authentication)new UsernamePasswordAuthenticationToken(username, password, authorities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean authenticateWithDatabase(SystemUser user, String password, String token) {
/*  96 */     logger.debug("Authenticating user against internal database");
/*     */     
/*  98 */     if (this.passwordEncoder.matches(password, user.getPassword())) {
/*  99 */       return true;
/*     */     }
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection<? extends GrantedAuthority> getAuthorities(SystemUser user) {
/* 106 */     List<GrantedAuthority> authorities = new ArrayList<>();
/* 107 */     getPermissions(user).forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
/* 108 */     return authorities;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> getPermissions(SystemUser user) {
/* 113 */     List<String> permissions = new ArrayList<>();
/*     */     
/* 115 */     if (user.getRole() != null) {
/* 116 */       user.getRole().getPermissions().forEach(permission -> permissions.add(permission.getName()));
/*     */     }
/* 118 */     return permissions;
/*     */   }
/*     */   
/*     */   public boolean isAccountNonExpired() {
/* 122 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isAccountNonLocked() {
/* 126 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isCredentialsNonExpired() {
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 134 */     return this.user.isEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supports(Class<?> authentication) {
/* 140 */     return authentication.equals(UsernamePasswordAuthenticationToken.class);
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\security\service\implementation\CustomAuthenticationProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */