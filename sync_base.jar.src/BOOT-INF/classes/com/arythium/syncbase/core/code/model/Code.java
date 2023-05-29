/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.code.model;
/*    */ 
/*    */ import com.arythium.syncbase.core.entity.AbstractEntity;
/*    */ import com.arythium.syncbase.core.utility.PrettySerializer;
/*    */ import com.fasterxml.jackson.annotation.JsonIgnore;
/*    */ import com.fasterxml.jackson.databind.JsonSerializer;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Table;
/*    */ import javax.persistence.UniqueConstraint;
/*    */ import org.hibernate.annotations.Where;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ @Table(name = "code", uniqueConstraints = {@UniqueConstraint(columnNames = {"code", "type"})})
/*    */ @Where(clause = "del_Flag='N'")
/*    */ public class Code
/*    */   extends AbstractEntity
/*    */   implements PrettySerializer
/*    */ {
/*    */   private String code;
/*    */   private String type;
/*    */   private String description;
/*    */   @Column(name = "extra_info")
/*    */   private String extraInfo;
/*    */   
/*    */   public void setCode(String code) {
/* 31 */     this.code = code; } public void setType(String type) { this.type = type; } public void setDescription(String description) { this.description = description; } public void setExtraInfo(String extraInfo) { this.extraInfo = extraInfo; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.core.code.model.Code)) return false;  com.arythium.syncbase.core.code.model.Code other = (com.arythium.syncbase.core.code.model.Code)o; if (!other.canEqual(this)) return false;  Object this$code = getCode(), other$code = other.getCode(); if ((this$code == null) ? (other$code != null) : !this$code.equals(other$code)) return false;  Object this$type = getType(), other$type = other.getType(); if ((this$type == null) ? (other$type != null) : !this$type.equals(other$type)) return false;  Object this$description = getDescription(), other$description = other.getDescription(); if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description)) return false;  Object this$extraInfo = getExtraInfo(), other$extraInfo = other.getExtraInfo(); return !((this$extraInfo == null) ? (other$extraInfo != null) : !this$extraInfo.equals(other$extraInfo)); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.core.code.model.Code; } public int hashCode() { int PRIME = 59; result = 1; Object $code = getCode(); result = result * 59 + (($code == null) ? 43 : $code.hashCode()); Object $type = getType(); result = result * 59 + (($type == null) ? 43 : $type.hashCode()); Object $description = getDescription(); result = result * 59 + (($description == null) ? 43 : $description.hashCode()); Object $extraInfo = getExtraInfo(); return result * 59 + (($extraInfo == null) ? 43 : $extraInfo.hashCode()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCode() {
/* 37 */     return this.code;
/* 38 */   } public String getType() { return this.type; } public String getDescription() {
/* 39 */     return this.description;
/*    */   }
/*    */   public String getExtraInfo() {
/* 42 */     return this.extraInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @JsonIgnore
/*    */   public JsonSerializer<com.arythium.syncbase.core.code.model.Code> getSerializer() {
/* 49 */     return (JsonSerializer<com.arythium.syncbase.core.code.model.Code>)new Object(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @JsonIgnore
/*    */   public List<String> getDefaultSearchFields() {
/* 68 */     return Arrays.asList(new String[] { "code", "type" });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 74 */     return "Code{code='" + this.code + '\'' + ", type='" + this.type + '\'' + ", description='" + this.description + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\code\model\Code.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */