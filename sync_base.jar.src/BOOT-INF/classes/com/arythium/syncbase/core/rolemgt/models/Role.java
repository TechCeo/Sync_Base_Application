/*     */ package BOOT-INF.classes.com.arythium.syncbase.core.rolemgt.models;
/*     */ import com.arythium.syncbase.core.entity.AbstractEntity;
/*     */ import com.arythium.syncbase.core.rolemgt.models.Permission;
/*     */ import com.arythium.syncbase.core.rolemgt.models.RoleType;
/*     */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*     */ import com.fasterxml.jackson.databind.JsonSerializer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.ManyToMany;
/*     */ import org.hibernate.annotations.Where;
/*     */ 
/*     */ @Entity
/*     */ @Where(clause = "del_Flag='N'")
/*     */ @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/*     */ public class Role extends AbstractEntity {
/*     */   public String toString() {
/*  20 */     return "Role(name=" + getName() + ", description=" + getDescription() + ", roleType=" + getRoleType() + ", permissions=" + getPermissions() + ", approvables=" + getApprovables() + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   private String name;
/*     */   
/*     */   private String description;
/*     */   @Enumerated(EnumType.STRING)
/*     */   private RoleType roleType;
/*     */   @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
/*     */   @JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
/*  31 */   private List<Permission> permissions = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ElementCollection
/*     */   private Set<String> approvables;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonSerializer<com.arythium.syncbase.core.rolemgt.models.Role> getSerializer() {
/*  43 */     return (JsonSerializer<com.arythium.syncbase.core.rolemgt.models.Role>)new Object(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getApprovables() {
/*  62 */     return this.approvables;
/*     */   }
/*     */   
/*     */   public void setApprovables(Set<String> approvables) {
/*  66 */     this.approvables = approvables;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getDefaultSearchFields() {
/*  71 */     return Arrays.asList(new String[] { "name", "description" });
/*     */   }
/*     */   
/*     */   public String getName() {
/*  75 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  79 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  83 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/*  87 */     this.description = description;
/*     */   }
/*     */   
/*     */   public RoleType getRoleType() {
/*  91 */     return this.roleType;
/*     */   }
/*     */   
/*     */   public void setRoleType(RoleType roleType) {
/*  95 */     this.roleType = roleType;
/*     */   }
/*     */   
/*     */   public List<Permission> getPermissions() {
/*  99 */     return this.permissions;
/*     */   }
/*     */   
/*     */   public void setPermissions(List<Permission> permissions) {
/* 103 */     this.permissions = permissions;
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\rolemgt\models\Role.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */