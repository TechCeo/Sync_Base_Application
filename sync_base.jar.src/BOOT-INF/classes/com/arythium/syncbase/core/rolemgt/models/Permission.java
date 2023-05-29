/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.rolemgt.models;
/*    */ 
/*    */ import com.arythium.syncbase.core.entity.AbstractEntity;
/*    */ import com.arythium.syncbase.core.rolemgt.models.PermissionType;
/*    */ import com.fasterxml.jackson.annotation.JsonIgnore;
/*    */ import com.fasterxml.jackson.databind.JsonSerializer;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.EnumType;
/*    */ import javax.persistence.Enumerated;
/*    */ import org.hibernate.annotations.Where;
/*    */ 
/*    */ @Entity
/*    */ @Where(clause = "del_Flag='N'")
/*    */ public class Permission
/*    */   extends AbstractEntity {
/*    */   private String name;
/*    */   
/*    */   public String toString() {
/* 21 */     return "Permission(name=" + getName() + ", description=" + getDescription() + ", permissionType=" + getPermissionType() + ")";
/*    */   }
/*    */ 
/*    */   
/*    */   private String description;
/*    */   
/*    */   @Enumerated(EnumType.STRING)
/*    */   private PermissionType permissionType;
/*    */ 
/*    */   
/*    */   public String getName() {
/* 32 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 36 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 40 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 44 */     this.description = description;
/*    */   }
/*    */   
/*    */   public PermissionType getPermissionType() {
/* 48 */     return this.permissionType;
/*    */   }
/*    */   
/*    */   public void setPermissionType(PermissionType permissionType) {
/* 52 */     this.permissionType = permissionType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @JsonIgnore
/*    */   public List<String> getDefaultSearchFields() {
/* 60 */     return Arrays.asList(new String[] { "name", "description" });
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonSerializer<com.arythium.syncbase.core.rolemgt.models.Permission> getSerializer() {
/* 65 */     return (JsonSerializer<com.arythium.syncbase.core.rolemgt.models.Permission>)new Object(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\rolemgt\models\Permission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */