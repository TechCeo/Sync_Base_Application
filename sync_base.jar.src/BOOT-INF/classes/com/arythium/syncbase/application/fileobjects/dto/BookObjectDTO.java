/*    */ package BOOT-INF.classes.com.arythium.syncbase.application.fileobjects.dto;
/*    */ public class BookObjectDTO extends AbstractDto {
/*    */   private String name;
/*    */   private String description;
/*    */   private String mappedColumn;
/*    */   
/*  7 */   public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.application.fileobjects.dto.BookObjectDTO)) return false;  com.arythium.syncbase.application.fileobjects.dto.BookObjectDTO other = (com.arythium.syncbase.application.fileobjects.dto.BookObjectDTO)o; if (!other.canEqual(this)) return false;  Object this$name = getName(), other$name = other.getName(); if ((this$name == null) ? (other$name != null) : !this$name.equals(other$name)) return false;  Object this$description = getDescription(), other$description = other.getDescription(); if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description)) return false;  Object this$mappedColumn = getMappedColumn(), other$mappedColumn = other.getMappedColumn(); if ((this$mappedColumn == null) ? (other$mappedColumn != null) : !this$mappedColumn.equals(other$mappedColumn)) return false;  Object this$mappedColumnType = getMappedColumnType(), other$mappedColumnType = other.getMappedColumnType(); if ((this$mappedColumnType == null) ? (other$mappedColumnType != null) : !this$mappedColumnType.equals(other$mappedColumnType)) return false;  Object this$defaultValue = getDefaultValue(), other$defaultValue = other.getDefaultValue(); if ((this$defaultValue == null) ? (other$defaultValue != null) : !this$defaultValue.equals(other$defaultValue)) return false;  Object this$enabled = getEnabled(), other$enabled = other.getEnabled(); if ((this$enabled == null) ? (other$enabled != null) : !this$enabled.equals(other$enabled)) return false;  Object this$bankBookDTO = getBankBookDTO(), other$bankBookDTO = other.getBankBookDTO(); return !((this$bankBookDTO == null) ? (other$bankBookDTO != null) : !this$bankBookDTO.equals(other$bankBookDTO)); } private String mappedColumnType; private String defaultValue; private Boolean enabled; private BankBookDTO bankBookDTO; protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.application.fileobjects.dto.BookObjectDTO; } public int hashCode() { int PRIME = 59; result = 1; Object $name = getName(); result = result * 59 + (($name == null) ? 43 : $name.hashCode()); Object $description = getDescription(); result = result * 59 + (($description == null) ? 43 : $description.hashCode()); Object $mappedColumn = getMappedColumn(); result = result * 59 + (($mappedColumn == null) ? 43 : $mappedColumn.hashCode()); Object $mappedColumnType = getMappedColumnType(); result = result * 59 + (($mappedColumnType == null) ? 43 : $mappedColumnType.hashCode()); Object $defaultValue = getDefaultValue(); result = result * 59 + (($defaultValue == null) ? 43 : $defaultValue.hashCode()); Object $enabled = getEnabled(); result = result * 59 + (($enabled == null) ? 43 : $enabled.hashCode()); Object $bankBookDTO = getBankBookDTO(); return result * 59 + (($bankBookDTO == null) ? 43 : $bankBookDTO.hashCode()); } public String toString() { return "BookObjectDTO(name=" + getName() + ", description=" + getDescription() + ", mappedColumn=" + getMappedColumn() + ", mappedColumnType=" + getMappedColumnType() + ", defaultValue=" + getDefaultValue() + ", enabled=" + getEnabled() + ", bankBookDTO=" + getBankBookDTO() + ")"; }
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
/*    */   public String getName() {
/* 21 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 25 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 29 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 33 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getMappedColumn() {
/* 37 */     return this.mappedColumn;
/*    */   }
/*    */   
/*    */   public void setMappedColumn(String mappedColumn) {
/* 41 */     this.mappedColumn = mappedColumn;
/*    */   }
/*    */   
/*    */   public String getMappedColumnType() {
/* 45 */     return this.mappedColumnType;
/*    */   }
/*    */   
/*    */   public void setMappedColumnType(String mappedColumnType) {
/* 49 */     this.mappedColumnType = mappedColumnType;
/*    */   }
/*    */   
/*    */   public String getDefaultValue() {
/* 53 */     return this.defaultValue;
/*    */   }
/*    */   
/*    */   public void setDefaultValue(String defaultValue) {
/* 57 */     this.defaultValue = defaultValue;
/*    */   }
/*    */   
/*    */   public Boolean getEnabled() {
/* 61 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public void setEnabled(Boolean enabled) {
/* 65 */     this.enabled = enabled;
/*    */   }
/*    */   
/*    */   public BankBookDTO getBankBookDTO() {
/* 69 */     return this.bankBookDTO;
/*    */   }
/*    */   
/*    */   public void setBankBookDTO(BankBookDTO bankBookDTO) {
/* 73 */     this.bankBookDTO = bankBookDTO;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\fileobjects\dto\BookObjectDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */