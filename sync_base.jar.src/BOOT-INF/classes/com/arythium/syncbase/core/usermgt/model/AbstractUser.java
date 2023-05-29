/*     */ package BOOT-INF.classes.com.arythium.syncbase.core.usermgt.model;
/*     */ 
/*     */ import com.arythium.syncbase.core.rolemgt.models.Role;
/*     */ import com.fasterxml.jackson.annotation.JsonIgnore;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.ManyToOne;
/*     */ import javax.persistence.MappedSuperclass;
/*     */ 
/*     */ @MappedSuperclass
/*     */ public abstract class AbstractUser extends AbstractEntity {
/*     */   @Column(unique = true)
/*     */   protected String userName;
/*     */   protected String firstName;
/*     */   
/*  16 */   public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.core.usermgt.model.AbstractUser)) return false;  com.arythium.syncbase.core.usermgt.model.AbstractUser other = (com.arythium.syncbase.core.usermgt.model.AbstractUser)o; if (!other.canEqual(this)) return false;  Object this$userName = getUserName(), other$userName = other.getUserName(); if ((this$userName == null) ? (other$userName != null) : !this$userName.equals(other$userName)) return false;  Object this$firstName = getFirstName(), other$firstName = other.getFirstName(); if ((this$firstName == null) ? (other$firstName != null) : !this$firstName.equals(other$firstName)) return false;  Object this$lastName = getLastName(), other$lastName = other.getLastName(); if ((this$lastName == null) ? (other$lastName != null) : !this$lastName.equals(other$lastName)) return false;  Object this$email = getEmail(), other$email = other.getEmail(); if ((this$email == null) ? (other$email != null) : !this$email.equals(other$email)) return false;  Object this$password = getPassword(), other$password = other.getPassword(); if ((this$password == null) ? (other$password != null) : !this$password.equals(other$password)) return false;  if (isEnabled() != other.isEnabled()) return false;  Object this$lastLoginDate = getLastLoginDate(), other$lastLoginDate = other.getLastLoginDate(); if ((this$lastLoginDate == null) ? (other$lastLoginDate != null) : !this$lastLoginDate.equals(other$lastLoginDate)) return false;  Object this$noOfWrongLoginCount = getNoOfWrongLoginCount(), other$noOfWrongLoginCount = other.getNoOfWrongLoginCount(); if ((this$noOfWrongLoginCount == null) ? (other$noOfWrongLoginCount != null) : !this$noOfWrongLoginCount.equals(other$noOfWrongLoginCount)) return false;  Object this$phoneNumber = getPhoneNumber(), other$phoneNumber = other.getPhoneNumber(); if ((this$phoneNumber == null) ? (other$phoneNumber != null) : !this$phoneNumber.equals(other$phoneNumber)) return false;  if (isChangePassword() != other.isChangePassword()) return false;  if (isLoggedOn() != other.isLoggedOn()) return false;  Object this$role = getRole(), other$role = other.getRole(); return !((this$role == null) ? (other$role != null) : !this$role.equals(other$role)); } protected String lastName; @Column(unique = true) protected String email; protected String password; protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.core.usermgt.model.AbstractUser; } public int hashCode() { int PRIME = 59; result = 1; Object $userName = getUserName(); result = result * 59 + (($userName == null) ? 43 : $userName.hashCode()); Object $firstName = getFirstName(); result = result * 59 + (($firstName == null) ? 43 : $firstName.hashCode()); Object $lastName = getLastName(); result = result * 59 + (($lastName == null) ? 43 : $lastName.hashCode()); Object $email = getEmail(); result = result * 59 + (($email == null) ? 43 : $email.hashCode()); Object $password = getPassword(); result = result * 59 + (($password == null) ? 43 : $password.hashCode()); result = result * 59 + (isEnabled() ? 79 : 97); Object $lastLoginDate = getLastLoginDate(); result = result * 59 + (($lastLoginDate == null) ? 43 : $lastLoginDate.hashCode()); Object $noOfWrongLoginCount = getNoOfWrongLoginCount(); result = result * 59 + (($noOfWrongLoginCount == null) ? 43 : $noOfWrongLoginCount.hashCode()); Object $phoneNumber = getPhoneNumber(); result = result * 59 + (($phoneNumber == null) ? 43 : $phoneNumber.hashCode()); result = result * 59 + (isChangePassword() ? 79 : 97); result = result * 59 + (isLoggedOn() ? 79 : 97); Object $role = getRole(); return result * 59 + (($role == null) ? 43 : $role.hashCode()); } public String toString() { return "AbstractUser(userName=" + getUserName() + ", firstName=" + getFirstName() + ", lastName=" + getLastName() + ", email=" + getEmail() + ", password=" + getPassword() + ", enabled=" + isEnabled() + ", lastLoginDate=" + getLastLoginDate() + ", noOfWrongLoginCount=" + getNoOfWrongLoginCount() + ", phoneNumber=" + getPhoneNumber() + ", changePassword=" + isChangePassword() + ", isLoggedOn=" + isLoggedOn() + ", role=" + getRole() + ")"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean enabled = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Date lastLoginDate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   protected Integer noOfWrongLoginCount = Integer.valueOf(0);
/*     */   
/*     */   protected String phoneNumber;
/*     */   
/*     */   protected boolean changePassword = true;
/*     */   
/*     */   protected boolean isLoggedOn;
/*     */   
/*     */   @ManyToOne
/*     */   protected Role role;
/*     */   
/*     */   public String getUserName() {
/*  48 */     return this.userName;
/*     */   }
/*     */   
/*     */   public void setUserName(String userName) {
/*  52 */     this.userName = userName;
/*     */   }
/*     */   
/*     */   public String getFirstName() {
/*  56 */     return this.firstName;
/*     */   }
/*     */   
/*     */   public void setFirstName(String firstName) {
/*  60 */     this.firstName = firstName;
/*     */   }
/*     */   
/*     */   public String getLastName() {
/*  64 */     return this.lastName;
/*     */   }
/*     */   
/*     */   public void setLastName(String lastName) {
/*  68 */     this.lastName = lastName;
/*     */   }
/*     */   
/*     */   public String getEmail() {
/*  72 */     return this.email;
/*     */   }
/*     */   
/*     */   public void setEmail(String email) {
/*  76 */     this.email = email;
/*     */   }
/*     */   
/*     */   public String getPassword() {
/*  80 */     return this.password;
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/*  84 */     this.password = password;
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/*  88 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  92 */     this.enabled = enabled;
/*     */   }
/*     */   
/*     */   public Date getLastLoginDate() {
/*  96 */     return this.lastLoginDate;
/*     */   }
/*     */   
/*     */   public void setLastLoginDate(Date lastLoginDate) {
/* 100 */     this.lastLoginDate = lastLoginDate;
/*     */   }
/*     */   
/*     */   public Integer getNoOfWrongLoginCount() {
/* 104 */     return this.noOfWrongLoginCount;
/*     */   }
/*     */   
/*     */   public void setNoOfWrongLoginCount(Integer noOfWrongLoginCount) {
/* 108 */     this.noOfWrongLoginCount = noOfWrongLoginCount;
/*     */   }
/*     */   
/*     */   public String getPhoneNumber() {
/* 112 */     return this.phoneNumber;
/*     */   }
/*     */   
/*     */   public void setPhoneNumber(String phoneNumber) {
/* 116 */     this.phoneNumber = phoneNumber;
/*     */   }
/*     */   
/*     */   public Role getRole() {
/* 120 */     return this.role;
/*     */   }
/*     */   
/*     */   public void setRole(Role role) {
/* 124 */     this.role = role;
/*     */   }
/*     */   
/*     */   public boolean isChangePassword() {
/* 128 */     return this.changePassword;
/*     */   }
/*     */   
/*     */   public void setChangePassword(boolean changePassword) {
/* 132 */     this.changePassword = changePassword;
/*     */   }
/*     */   
/*     */   public boolean isLoggedOn() {
/* 136 */     return this.isLoggedOn;
/*     */   }
/*     */   
/*     */   public void setLoggedOn(boolean loggedOn) {
/* 140 */     this.isLoggedOn = loggedOn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @JsonIgnore
/*     */   public List<String> getDefaultSearchFields() {
/* 147 */     return Arrays.asList(new String[] { "userName", "firstName", "lastName", "email" });
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\usermgt\model\AbstractUser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */