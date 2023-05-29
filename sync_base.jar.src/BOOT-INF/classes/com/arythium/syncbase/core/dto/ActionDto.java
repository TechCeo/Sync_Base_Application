/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.dto;
/*    */ 
/*    */ 
/*    */ public class ActionDto extends AbstractDto {
/*    */   private boolean enabled;
/*    */   
/*  7 */   public void setEnabled(boolean enabled) { this.enabled = enabled; } private String code; private String description; public void setCode(String code) { this.code = code; } public void setDescription(String description) { this.description = description; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.core.dto.ActionDto)) return false;  com.arythium.syncbase.core.dto.ActionDto other = (com.arythium.syncbase.core.dto.ActionDto)o; if (!other.canEqual(this)) return false;  if (isEnabled() != other.isEnabled()) return false;  Object this$code = getCode(), other$code = other.getCode(); if ((this$code == null) ? (other$code != null) : !this$code.equals(other$code)) return false;  Object this$description = getDescription(), other$description = other.getDescription(); return !((this$description == null) ? (other$description != null) : !this$description.equals(other$description)); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.core.dto.ActionDto; } public int hashCode() { int PRIME = 59; result = 1; result = result * 59 + (isEnabled() ? 79 : 97); Object $code = getCode(); result = result * 59 + (($code == null) ? 43 : $code.hashCode()); Object $description = getDescription(); return result * 59 + (($description == null) ? 43 : $description.hashCode()); } public String toString() { return "ActionDto(enabled=" + isEnabled() + ", code=" + getCode() + ", description=" + getDescription() + ")"; }
/*    */   
/*    */   public boolean isEnabled() {
/* 10 */     return this.enabled;
/*    */   } public String getCode() {
/* 12 */     return this.code;
/*    */   } public String getDescription() {
/* 14 */     return this.description;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\dto\ActionDto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */