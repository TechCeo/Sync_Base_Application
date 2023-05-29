/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.setting.model;
/*    */ 
/*    */ import com.arythium.syncbase.core.entity.AbstractEntity;
/*    */ import com.arythium.syncbase.core.utility.PrettySerializer;
/*    */ import com.fasterxml.jackson.annotation.JsonIgnore;
/*    */ import com.fasterxml.jackson.databind.JsonSerializer;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Table;
/*    */ import org.hibernate.annotations.Where;
/*    */ 
/*    */ @Entity
/*    */ @Table(name = "app_setting")
/*    */ @Where(clause = "del_Flag='N'")
/*    */ public class AppSetting extends AbstractEntity implements PrettySerializer {
/*    */   private String name;
/*    */   private String code;
/*    */   private String description;
/*    */   private String value;
/*    */   private boolean enabled;
/*    */   
/*    */   public void setName(String name) {
/* 24 */     this.name = name; } public void setCode(String code) { this.code = code; } public void setDescription(String description) { this.description = description; } public void setValue(String value) { this.value = value; } public void setEnabled(boolean enabled) { this.enabled = enabled; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.core.setting.model.AppSetting)) return false;  com.arythium.syncbase.core.setting.model.AppSetting other = (com.arythium.syncbase.core.setting.model.AppSetting)o; if (!other.canEqual(this)) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$code = getCode(), other$code = other.getCode(); if ((this$code == null) ? (other$code != null) : !this$code.equals(other$code)) return false;  Object this$description = getDescription(), other$description = other.getDescription(); if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description)) return false;  Object this$value = getValue(), other$value = other.getValue(); return ((this$value == null) ? (other$value != null) : !this$value.equals(other$value)) ? false : (!(isEnabled() != other.isEnabled())); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.core.setting.model.AppSetting; } public int hashCode() { int PRIME = 59; result = 1; Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $code = getCode(); result = result * 59 + (($code == null) ? 43 : $code.hashCode()); Object $description = getDescription(); result = result * 59 + (($description == null) ? 43 : $description.hashCode()); Object $value = getValue(); result = result * 59 + (($value == null) ? 43 : $value.hashCode()); return result * 59 + (isEnabled() ? 79 : 97); } public String toString() { return "AppSetting(name=" + getName() + ", code=" + getCode() + ", description=" + getDescription() + ", value=" + getValue() + ", enabled=" + isEnabled() + ")"; }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 29 */     return this.name; }
/* 30 */   public String getCode() { return this.code; }
/* 31 */   public String getDescription() { return this.description; }
/* 32 */   public String getValue() { return this.value; } public boolean isEnabled() {
/* 33 */     return this.enabled;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @JsonIgnore
/*    */   public List<String> getDefaultSearchFields() {
/* 40 */     return Arrays.asList(new String[] { "name", "code" });
/*    */   }
/*    */ 
/*    */   
/*    */   @JsonIgnore
/*    */   public JsonSerializer<com.arythium.syncbase.core.setting.model.AppSetting> getSerializer() {
/* 46 */     return (JsonSerializer<com.arythium.syncbase.core.setting.model.AppSetting>)new Object(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\setting\model\AppSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */