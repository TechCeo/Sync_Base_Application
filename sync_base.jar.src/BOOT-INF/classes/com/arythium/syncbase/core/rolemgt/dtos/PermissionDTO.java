/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.rolemgt.dtos;
/*    */ 
/*    */ 
/*    */ public class PermissionDTO {
/*    */   private Long id;
/*    */   private int version;
/*    */   
/*  8 */   public void setId(Long id) { this.id = id; } @NotEmpty private String name; private String description; private String type; public void setVersion(int version) { this.version = version; } public void setName(String name) { this.name = name; } public void setDescription(String description) { this.description = description; } public void setType(String type) { this.type = type; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.core.rolemgt.dtos.PermissionDTO)) return false;  com.arythium.syncbase.core.rolemgt.dtos.PermissionDTO other = (com.arythium.syncbase.core.rolemgt.dtos.PermissionDTO)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  if (getVersion() != other.getVersion()) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$description = getDescription(), other$description = other.getDescription(); if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description)) return false;  Object this$type = getType(), other$type = other.getType(); return !((this$type == null) ? (other$type != null) : !this$type.equals(other$type)); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.core.rolemgt.dtos.PermissionDTO; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); result = result * 59 + getVersion(); Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $description = getDescription(); result = result * 59 + (($description == null) ? 43 : $description.hashCode()); Object $type = getType(); return result * 59 + (($type == null) ? 43 : $type.hashCode()); } public String toString() { return "PermissionDTO(id=" + getId() + ", version=" + getVersion() + ", name=" + getName() + ", description=" + getDescription() + ", type=" + getType() + ")"; }
/*    */ 
/*    */   
/* 11 */   public Long getId() { return this.id; } public int getVersion() {
/* 12 */     return this.version;
/*    */   }
/* 14 */   public String getName() { return this.name; }
/* 15 */   public String getDescription() { return this.description; } public String getType() {
/* 16 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\rolemgt\dtos\PermissionDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */