/*    */ package BOOT-INF.classes.com.arythium.syncbase.application.bankfiles.dto;
/*    */ public class BankBookDTO extends AbstractDto {
/*    */   private String bookName;
/*    */   private String description;
/*    */   
/*  6 */   public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.application.bankfiles.dto.BankBookDTO)) return false;  com.arythium.syncbase.application.bankfiles.dto.BankBookDTO other = (com.arythium.syncbase.application.bankfiles.dto.BankBookDTO)o; if (!other.canEqual(this)) return false;  Object this$bookName = getBookName(), other$bookName = other.getBookName(); if ((this$bookName == null) ? (other$bookName != null) : !this$bookName.equals(other$bookName)) return false;  Object this$description = getDescription(), other$description = other.getDescription(); if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description)) return false;  Object this$bookPrefix = getBookPrefix(), other$bookPrefix = other.getBookPrefix(); if ((this$bookPrefix == null) ? (other$bookPrefix != null) : !this$bookPrefix.equals(other$bookPrefix)) return false;  Object this$mappedTableName = getMappedTableName(), other$mappedTableName = other.getMappedTableName(); if ((this$mappedTableName == null) ? (other$mappedTableName != null) : !this$mappedTableName.equals(other$mappedTableName)) return false;  Object this$enabled = getEnabled(), other$enabled = other.getEnabled(); return !((this$enabled == null) ? (other$enabled != null) : !this$enabled.equals(other$enabled)); } private String bookPrefix; private String mappedTableName; private Boolean enabled; protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.application.bankfiles.dto.BankBookDTO; } public int hashCode() { int PRIME = 59; result = 1; Object $bookName = getBookName(); result = result * 59 + (($bookName == null) ? 43 : $bookName.hashCode()); Object $description = getDescription(); result = result * 59 + (($description == null) ? 43 : $description.hashCode()); Object $bookPrefix = getBookPrefix(); result = result * 59 + (($bookPrefix == null) ? 43 : $bookPrefix.hashCode()); Object $mappedTableName = getMappedTableName(); result = result * 59 + (($mappedTableName == null) ? 43 : $mappedTableName.hashCode()); Object $enabled = getEnabled(); return result * 59 + (($enabled == null) ? 43 : $enabled.hashCode()); } public String toString() { return "BankBookDTO(bookName=" + getBookName() + ", description=" + getDescription() + ", bookPrefix=" + getBookPrefix() + ", mappedTableName=" + getMappedTableName() + ", enabled=" + getEnabled() + ")"; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getBookName() {
/* 16 */     return this.bookName;
/*    */   }
/*    */   
/*    */   public void setBookName(String bookName) {
/* 20 */     this.bookName = bookName;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 24 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 28 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getBookPrefix() {
/* 32 */     return this.bookPrefix;
/*    */   }
/*    */   
/*    */   public void setBookPrefix(String bookPrefix) {
/* 36 */     this.bookPrefix = bookPrefix;
/*    */   }
/*    */   
/*    */   public String getMappedTableName() {
/* 40 */     return this.mappedTableName;
/*    */   }
/*    */   
/*    */   public void setMappedTableName(String mappedTableName) {
/* 44 */     this.mappedTableName = mappedTableName;
/*    */   }
/*    */   
/*    */   public Boolean getEnabled() {
/* 48 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public void setEnabled(Boolean enabled) {
/* 52 */     this.enabled = enabled;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\bankfiles\dto\BankBookDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */