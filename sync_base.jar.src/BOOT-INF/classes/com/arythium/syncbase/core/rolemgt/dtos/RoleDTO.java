/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.rolemgt.dtos;
/*    */ 
/*    */ 
/*    */ public class RoleDTO {
/*    */   private Long id;
/*    */   private int version;
/*    */   @NotEmpty(message = "Name is required")
/*    */   private String name;
/*    */   
/* 10 */   public void setId(Long id) { this.id = id; } private String description; @NotEmpty(message = "Type is required") private String type; private List<PermissionDTO> permissions; private Set<String> approvables; public void setVersion(int version) { this.version = version; } public void setName(String name) { this.name = name; } public void setDescription(String description) { this.description = description; } public void setType(String type) { this.type = type; } public void setPermissions(List<PermissionDTO> permissions) { this.permissions = permissions; } public void setApprovables(Set<String> approvables) { this.approvables = approvables; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.core.rolemgt.dtos.RoleDTO)) return false;  com.arythium.syncbase.core.rolemgt.dtos.RoleDTO other = (com.arythium.syncbase.core.rolemgt.dtos.RoleDTO)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  if (getVersion() != other.getVersion()) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$description = getDescription(), other$description = other.getDescription(); if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description)) return false;  Object this$type = getType(), other$type = other.getType(); if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type)) return false;  Object<PermissionDTO> this$permissions = (Object<PermissionDTO>)getPermissions(), other$permissions = (Object<PermissionDTO>)other.getPermissions(); if ((this$permissions == null) ? (other$permissions != null) : !this$permissions.equals(other$permissions)) return false;  Object<String> this$approvables = (Object<String>)getApprovables(), other$approvables = (Object<String>)other.getApprovables(); return !((this$approvables == null) ? (other$approvables != null) : !this$approvables.equals(other$approvables)); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.core.rolemgt.dtos.RoleDTO; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); result = result * 59 + getVersion(); Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $description = getDescription(); result = result * 59 + (($description == null) ? 43 : $description.hashCode()); Object $type = getType(); result = result * 59 + (($type == null) ? 43 : $type.hashCode()); Object<PermissionDTO> $permissions = (Object<PermissionDTO>)getPermissions(); result = result * 59 + (($permissions == null) ? 43 : $permissions.hashCode()); Object<String> $approvables = (Object<String>)getApprovables(); return result * 59 + (($approvables == null) ? 43 : $approvables.hashCode()); } public String toString() { return "RoleDTO(id=" + getId() + ", version=" + getVersion() + ", name=" + getName() + ", description=" + getDescription() + ", type=" + getType() + ", permissions=" + getPermissions() + ", approvables=" + getApprovables() + ")"; }
/*    */ 
/*    */   
/* 13 */   public Long getId() { return this.id; } public int getVersion() {
/* 14 */     return this.version;
/*    */   }
/* 16 */   public String getName() { return this.name; } public String getDescription() {
/* 17 */     return this.description;
/*    */   }
/* 19 */   public String getType() { return this.type; }
/* 20 */   public List<PermissionDTO> getPermissions() { return this.permissions; } public Set<String> getApprovables() {
/* 21 */     return this.approvables;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\rolemgt\dtos\RoleDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */