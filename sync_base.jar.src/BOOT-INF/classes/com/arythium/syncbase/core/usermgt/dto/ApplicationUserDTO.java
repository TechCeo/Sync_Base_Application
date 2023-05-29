/*     */ package BOOT-INF.classes.com.arythium.syncbase.core.usermgt.dto;
/*     */ 
/*     */ import com.arythium.syncbase.core.dto.AbstractDto;
/*     */ import com.arythium.syncbase.core.utility.PrettySerializer;
/*     */ import com.fasterxml.jackson.databind.JsonSerializer;
/*     */ import javax.validation.constraints.NotEmpty;
/*     */ import javax.validation.constraints.NotNull;
/*     */ 
/*     */ public class ApplicationUserDTO extends AbstractDto implements PrettySerializer {
/*     */   @NotEmpty(message = "Username is required")
/*     */   private String userName;
/*     */   
/*     */   public String toString() {
/*  14 */     return "ApplicationUserDTO(userName=" + getUserName() + ", firstName=" + getFirstName() + ", lastName=" + getLastName() + ", fullName=" + getFullName() + ", email=" + getEmail() + ", password=" + getPassword() + ", enabled=" + isEnabled() + ", lastLogin=" + getLastLogin() + ", noOfWrongLoginCount=" + getNoOfWrongLoginCount() + ", phoneNumber=" + getPhoneNumber() + ", changePassword=" + isChangePassword() + ", isLoggedOn=" + isLoggedOn() + ", roleId=" + getRoleId() + ", roleName=" + getRoleName() + ", roleType=" + getRoleType() + ", branchId=" + getBranchId() + ", branchName=" + getBranchName() + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   @NotEmpty(message = "First name is required")
/*     */   private String firstName;
/*     */   
/*     */   @NotEmpty(message = "Last name is required")
/*     */   private String lastName;
/*     */   
/*     */   private String fullName;
/*     */   @NotEmpty(message = "Email address is required")
/*     */   private String email;
/*     */   private String password;
/*     */   private boolean enabled;
/*     */   private String lastLogin;
/*     */   private Integer noOfWrongLoginCount;
/*     */   private String phoneNumber;
/*     */   private boolean changePassword;
/*     */   private boolean isLoggedOn;
/*     */   @NotNull(message = "Role is required")
/*     */   private Long roleId;
/*     */   private String roleName;
/*     */   private String roleType;
/*     */   private Long branchId;
/*     */   private String branchName;
/*     */   
/*     */   public String getUserName() {
/*  42 */     return this.userName;
/*     */   }
/*     */   
/*     */   public void setUserName(String userName) {
/*  46 */     this.userName = userName;
/*     */   }
/*     */   
/*     */   public String getFirstName() {
/*  50 */     return this.firstName;
/*     */   }
/*     */   
/*     */   public void setFirstName(String firstName) {
/*  54 */     this.firstName = firstName;
/*     */   }
/*     */   
/*     */   public String getLastName() {
/*  58 */     return this.lastName;
/*     */   }
/*     */   
/*     */   public void setLastName(String lastName) {
/*  62 */     this.lastName = lastName;
/*     */   }
/*     */   
/*     */   public String getFullName() {
/*  66 */     return this.fullName;
/*     */   }
/*     */   
/*     */   public void setFullName(String fullName) {
/*  70 */     this.fullName = fullName;
/*     */   }
/*     */   
/*     */   public String getEmail() {
/*  74 */     return this.email;
/*     */   }
/*     */   
/*     */   public void setEmail(String email) {
/*  78 */     this.email = email;
/*     */   }
/*     */   
/*     */   public String getPassword() {
/*  82 */     return this.password;
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/*  86 */     this.password = password;
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/*  90 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  94 */     this.enabled = enabled;
/*     */   }
/*     */   
/*     */   public String getLastLogin() {
/*  98 */     return this.lastLogin;
/*     */   }
/*     */   
/*     */   public void setLastLogin(String lastLogin) {
/* 102 */     this.lastLogin = lastLogin;
/*     */   }
/*     */   
/*     */   public Integer getNoOfWrongLoginCount() {
/* 106 */     return this.noOfWrongLoginCount;
/*     */   }
/*     */   
/*     */   public void setNoOfWrongLoginCount(Integer noOfWrongLoginCount) {
/* 110 */     this.noOfWrongLoginCount = noOfWrongLoginCount;
/*     */   }
/*     */   
/*     */   public String getPhoneNumber() {
/* 114 */     return this.phoneNumber;
/*     */   }
/*     */   
/*     */   public void setPhoneNumber(String phoneNumber) {
/* 118 */     this.phoneNumber = phoneNumber;
/*     */   }
/*     */   
/*     */   public boolean isChangePassword() {
/* 122 */     return this.changePassword;
/*     */   }
/*     */   
/*     */   public void setChangePassword(boolean changePassword) {
/* 126 */     this.changePassword = changePassword;
/*     */   }
/*     */   
/*     */   public boolean isLoggedOn() {
/* 130 */     return this.isLoggedOn;
/*     */   }
/*     */   
/*     */   public void setLoggedOn(boolean loggedOn) {
/* 134 */     this.isLoggedOn = loggedOn;
/*     */   }
/*     */   
/*     */   public Long getRoleId() {
/* 138 */     return this.roleId;
/*     */   }
/*     */   
/*     */   public void setRoleId(Long roleId) {
/* 142 */     this.roleId = roleId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRoleName() {
/* 147 */     return this.roleName;
/*     */   }
/*     */   
/*     */   public void setRoleName(String roleName) {
/* 151 */     this.roleName = roleName;
/*     */   }
/*     */   
/*     */   public String getRoleType() {
/* 155 */     return this.roleType;
/*     */   }
/*     */   
/*     */   public void setRoleType(String roleType) {
/* 159 */     this.roleType = roleType;
/*     */   }
/*     */   
/*     */   public Long getBranchId() {
/* 163 */     return this.branchId;
/*     */   }
/*     */   
/*     */   public void setBranchId(Long branchId) {
/* 167 */     this.branchId = branchId;
/*     */   }
/*     */   
/*     */   public String getBranchName() {
/* 171 */     return this.branchName;
/*     */   }
/*     */   
/*     */   public void setBranchName(String branchName) {
/* 175 */     this.branchName = branchName;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonSerializer<com.arythium.syncbase.core.usermgt.dto.ApplicationUserDTO> getSerializer() {
/* 180 */     return (JsonSerializer<com.arythium.syncbase.core.usermgt.dto.ApplicationUserDTO>)new Object(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\usermgt\dto\ApplicationUserDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */