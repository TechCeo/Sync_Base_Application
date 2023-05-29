/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.setting.dto;
/*    */ 
/*    */ import com.arythium.syncbase.core.dto.AbstractDto;
/*    */ import com.fasterxml.jackson.annotation.JsonIgnore;
/*    */ import com.fasterxml.jackson.databind.JsonSerializer;
/*    */ import javax.validation.constraints.NotEmpty;
/*    */ 
/*    */ public class SettingDto extends AbstractDto implements PrettySerializer {
/*    */   @NotEmpty(message = "Name is required")
/*    */   private String name;
/*    */   private String description;
/*    */   @NotEmpty(message = "Code is required")
/*    */   private String code;
/*    */   private String value;
/*    */   private boolean enabled;
/*    */   private String csrf;
/*    */   
/* 18 */   public void setName(String name) { this.name = name; } public void setDescription(String description) { this.description = description; } public void setCode(String code) { this.code = code; } public void setValue(String value) { this.value = value; } public void setEnabled(boolean enabled) { this.enabled = enabled; } public void setCsrf(String csrf) { this.csrf = csrf; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.core.setting.dto.SettingDto)) return false;  com.arythium.syncbase.core.setting.dto.SettingDto other = (com.arythium.syncbase.core.setting.dto.SettingDto)o; if (!other.canEqual(this)) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$description = getDescription(), other$description = other.getDescription(); if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description)) return false;  Object this$code = getCode(), other$code = other.getCode(); if ((this$code == null) ? (other$code != null) : !this$code.equals(other$code)) return false;  Object this$value = getValue(), other$value = other.getValue(); if ((this$value == null) ? (other$value != null) : !this$value.equals(other$value)) return false;  if (isEnabled() != other.isEnabled()) return false;  Object this$csrf = getCsrf(), other$csrf = other.getCsrf(); return !((this$csrf == null) ? (other$csrf != null) : !this$csrf.equals(other$csrf)); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.core.setting.dto.SettingDto; } public int hashCode() { int PRIME = 59; result = 1; Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $description = getDescription(); result = result * 59 + (($description == null) ? 43 : $description.hashCode()); Object $code = getCode(); result = result * 59 + (($code == null) ? 43 : $code.hashCode()); Object $value = getValue(); result = result * 59 + (($value == null) ? 43 : $value.hashCode()); result = result * 59 + (isEnabled() ? 79 : 97); Object $csrf = getCsrf(); return result * 59 + (($csrf == null) ? 43 : $csrf.hashCode()); } public String toString() { return "SettingDto(name=" + getName() + ", description=" + getDescription() + ", code=" + getCode() + ", value=" + getValue() + ", enabled=" + isEnabled() + ", csrf=" + getCsrf() + ")"; }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 22 */     return this.name; } public String getDescription() {
/* 23 */     return this.description;
/*    */   }
/* 25 */   public String getCode() { return this.code; }
/* 26 */   public String getValue() { return this.value; }
/* 27 */   public boolean isEnabled() { return this.enabled; } public String getCsrf() {
/* 28 */     return this.csrf;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @JsonIgnore
/*    */   public JsonSerializer<com.arythium.syncbase.core.setting.dto.SettingDto> getSerializer() {
/* 38 */     return (JsonSerializer<com.arythium.syncbase.core.setting.dto.SettingDto>)new Object(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\setting\dto\SettingDto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */