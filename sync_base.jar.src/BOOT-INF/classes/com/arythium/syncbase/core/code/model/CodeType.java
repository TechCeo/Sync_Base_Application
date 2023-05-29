/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.code.model;
/*    */ public class CodeType {
/*    */   private String type;
/*    */   
/*  5 */   public void setType(String type) { this.type = type; } private CodeType() {} public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.core.code.model.CodeType)) return false;  com.arythium.syncbase.core.code.model.CodeType other = (com.arythium.syncbase.core.code.model.CodeType)o; if (!other.canEqual(this)) return false;  Object this$type = getType(), other$type = other.getType(); return !((this$type == null) ? (other$type != null) : !this$type.equals(other$type)); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.core.code.model.CodeType; } public int hashCode() { int PRIME = 59; result = 1; Object $type = getType(); return result * 59 + (($type == null) ? 43 : $type.hashCode()); } public String toString() { return "CodeType(type=" + getType() + ")"; }
/*    */ 
/*    */   
/*    */   public String getType() {
/*  9 */     return this.type;
/*    */   }
/*    */   public CodeType(String type) {
/* 12 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\code\model\CodeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */