/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.code.dto;
/*    */ 
/*    */ import com.arythium.syncbase.core.entity.AbstractEntity;
/*    */ import com.arythium.syncbase.core.utility.PrettySerializer;
/*    */ import com.fasterxml.jackson.annotation.JsonIgnore;
/*    */ import com.fasterxml.jackson.databind.JsonSerializer;
/*    */ import javax.validation.constraints.NotEmpty;
/*    */ 
/*    */ public class CodeDTO extends AbstractEntity implements PrettySerializer {
/*    */   @NotEmpty(message = "code")
/*    */   private String code;
/*    */   @NotEmpty(message = "type")
/*    */   private String type;
/*    */   @NotEmpty(message = "description")
/*    */   private String description;
/*    */   private String extraInfo;
/*    */   private int version;
/*    */   
/* 19 */   public void setCode(String code) { this.code = code; } public void setType(String type) { this.type = type; } public void setDescription(String description) { this.description = description; } public void setExtraInfo(String extraInfo) { this.extraInfo = extraInfo; } public void setVersion(int version) { this.version = version; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.core.code.dto.CodeDTO)) return false;  com.arythium.syncbase.core.code.dto.CodeDTO other = (com.arythium.syncbase.core.code.dto.CodeDTO)o; if (!other.canEqual(this)) return false;  Object this$code = getCode(), other$code = other.getCode(); if ((this$code == null) ? (other$code != null) : !this$code.equals(other$code)) return false;  Object this$type = getType(), other$type = other.getType(); if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type)) return false;  Object this$description = getDescription(), other$description = other.getDescription(); if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description)) return false;  Object this$extraInfo = getExtraInfo(), other$extraInfo = other.getExtraInfo(); return ((this$extraInfo == null) ? (other$extraInfo != null) : !this$extraInfo.equals(other$extraInfo)) ? false : (!(getVersion() != other.getVersion())); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.core.code.dto.CodeDTO; } public int hashCode() { int PRIME = 59; result = 1; Object $code = getCode(); result = result * 59 + (($code == null) ? 43 : $code.hashCode()); Object $type = getType(); result = result * 59 + (($type == null) ? 43 : $type.hashCode()); Object $description = getDescription(); result = result * 59 + (($description == null) ? 43 : $description.hashCode()); Object $extraInfo = getExtraInfo(); result = result * 59 + (($extraInfo == null) ? 43 : $extraInfo.hashCode()); return result * 59 + getVersion(); } public String toString() { return "CodeDTO(code=" + getCode() + ", type=" + getType() + ", description=" + getDescription() + ", extraInfo=" + getExtraInfo() + ", version=" + getVersion() + ")"; }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCode() {
/* 24 */     return this.code;
/*    */   }
/*    */   public String getType() {
/* 27 */     return this.type;
/*    */   }
/*    */   public String getDescription() {
/* 30 */     return this.description;
/*    */   }
/* 32 */   public String getExtraInfo() { return this.extraInfo; } public int getVersion() {
/* 33 */     return this.version;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @JsonIgnore
/*    */   public JsonSerializer<com.arythium.syncbase.core.code.dto.CodeDTO> getSerializer() {
/* 40 */     return (JsonSerializer<com.arythium.syncbase.core.code.dto.CodeDTO>)new Object(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\code\dto\CodeDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */