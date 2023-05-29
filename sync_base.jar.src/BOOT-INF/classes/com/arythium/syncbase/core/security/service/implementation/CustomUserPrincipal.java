/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.security.service.implementation;
/*    */ 
/*    */ import com.arythium.syncbase.core.rolemgt.models.Permission;
/*    */ import com.arythium.syncbase.core.security.service.CustomUserDetails;
/*    */ import com.arythium.syncbase.core.usermgt.model.AbstractUser;
/*    */ import com.arythium.syncbase.core.usermgt.model.SystemUser;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import org.springframework.security.core.GrantedAuthority;
/*    */ import org.springframework.security.core.authority.SimpleGrantedAuthority;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomUserPrincipal
/*    */   implements CustomUserDetails
/*    */ {
/*    */   private final AbstractUser user;
/*    */   
/*    */   public CustomUserPrincipal(AbstractUser user) {
/* 25 */     this.user = user;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<? extends GrantedAuthority> getAuthorities() {
/* 31 */     List<GrantedAuthority> authorities = new ArrayList<>();
/* 32 */     getPermissions(this.user).forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
/* 33 */     return authorities;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUsername() {
/* 38 */     return this.user.getUserName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPassword() {
/* 43 */     return this.user.getPassword();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private List<String> getPermissions(AbstractUser user) {
/* 49 */     List<String> permissions = new ArrayList<>();
/*    */     
/* 51 */     SystemUser systemUser = (SystemUser)user;
/* 52 */     if (user.getRole() != null) {
/* 53 */       systemUser.getRole().getPermissions().forEach(permission -> permissions.add(permission.getName()));
/*    */     }
/* 55 */     return permissions;
/*    */   }
/*    */   
/*    */   public AbstractUser getUser() {
/* 59 */     return this.user;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAccountNonExpired() {
/* 64 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAccountNonLocked() {
/* 69 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCredentialsNonExpired() {
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 79 */     return this.user.isEnabled();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 84 */     if (this == o) return true; 
/* 85 */     if (!(o instanceof com.arythium.syncbase.core.security.service.implementation.CustomUserPrincipal)) return false;
/*    */     
/* 87 */     com.arythium.syncbase.core.security.service.implementation.CustomUserPrincipal that = (com.arythium.syncbase.core.security.service.implementation.CustomUserPrincipal)o;
/*    */     
/* 89 */     return this.user.equals(that.user);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 94 */     return this.user.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\security\service\implementation\CustomUserPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */