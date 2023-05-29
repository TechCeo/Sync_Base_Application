/*    */ package BOOT-INF.classes.com.arythium.syncbase.application.bankfiles.entity;
/*    */ @Entity
/*    */ @Table(name = "bank_file")
/*    */ @Where(clause = "del_flag='N'")
/*    */ public class BankBook extends AbstractEntity { @Column(name = "book_name")
/*    */   private String bookName; @Column(name = "book_prefix")
/*    */   private String bookPrefix;
/*    */   @Column(name = "mapped_table_name")
/*    */   private String mappedTableName;
/*    */   
/* 11 */   public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.application.bankfiles.entity.BankBook)) return false;  com.arythium.syncbase.application.bankfiles.entity.BankBook other = (com.arythium.syncbase.application.bankfiles.entity.BankBook)o; if (!other.canEqual(this)) return false;  Object this$bookName = getBookName(), other$bookName = other.getBookName(); if ((this$bookName == null) ? (other$bookName != null) : !this$bookName.equals(other$bookName)) return false;  Object this$bookPrefix = getBookPrefix(), other$bookPrefix = other.getBookPrefix(); if ((this$bookPrefix == null) ? (other$bookPrefix != null) : !this$bookPrefix.equals(other$bookPrefix)) return false;  Object this$mappedTableName = getMappedTableName(), other$mappedTableName = other.getMappedTableName(); if ((this$mappedTableName == null) ? (other$mappedTableName != null) : !this$mappedTableName.equals(other$mappedTableName)) return false;  Object this$description = getDescription(), other$description = other.getDescription(); if ((this$description == null) ? (other$description != null) : !this$description.equals(other$description)) return false;  Object this$enabled = getEnabled(), other$enabled = other.getEnabled(); if ((this$enabled == null) ? (other$enabled != null) : !this$enabled.equals(other$enabled)) return false;  Object<BookObject> this$feedObjects = (Object<BookObject>)getFeedObjects(), other$feedObjects = (Object<BookObject>)other.getFeedObjects(); return !((this$feedObjects == null) ? (other$feedObjects != null) : !this$feedObjects.equals(other$feedObjects)); } private String description; private Boolean enabled; @Column(name = "book_name") @OneToMany(mappedBy = "bankBook", fetch = FetchType.EAGER) private List<BookObject> feedObjects; protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.application.bankfiles.entity.BankBook; } public int hashCode() { int PRIME = 59; result = 1; Object $bookName = getBookName(); result = result * 59 + (($bookName == null) ? 43 : $bookName.hashCode()); Object $bookPrefix = getBookPrefix(); result = result * 59 + (($bookPrefix == null) ? 43 : $bookPrefix.hashCode()); Object $mappedTableName = getMappedTableName(); result = result * 59 + (($mappedTableName == null) ? 43 : $mappedTableName.hashCode()); Object $description = getDescription(); result = result * 59 + (($description == null) ? 43 : $description.hashCode()); Object $enabled = getEnabled(); result = result * 59 + (($enabled == null) ? 43 : $enabled.hashCode()); Object<BookObject> $feedObjects = (Object<BookObject>)getFeedObjects(); return result * 59 + (($feedObjects == null) ? 43 : $feedObjects.hashCode()); } public String toString() { return "BankBook(bookName=" + getBookName() + ", bookPrefix=" + getBookPrefix() + ", mappedTableName=" + getMappedTableName() + ", description=" + getDescription() + ", enabled=" + getEnabled() + ", feedObjects=" + getFeedObjects() + ")"; }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getBookName() {
/* 36 */     return this.bookName;
/*    */   }
/*    */   
/*    */   public void setBookName(String bookName) {
/* 40 */     this.bookName = bookName;
/*    */   }
/*    */   
/*    */   public String getBookPrefix() {
/* 44 */     return this.bookPrefix;
/*    */   }
/*    */   
/*    */   public void setBookPrefix(String bookPrefix) {
/* 48 */     this.bookPrefix = bookPrefix;
/*    */   }
/*    */   
/*    */   public String getMappedTableName() {
/* 52 */     return this.mappedTableName;
/*    */   }
/*    */   
/*    */   public void setMappedTableName(String mappedTableName) {
/* 56 */     this.mappedTableName = mappedTableName;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 60 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 64 */     this.description = description;
/*    */   }
/*    */   
/*    */   public Boolean getEnabled() {
/* 68 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public void setEnabled(Boolean enabled) {
/* 72 */     this.enabled = enabled;
/*    */   }
/*    */   
/*    */   public List<BookObject> getFeedObjects() {
/* 76 */     return this.feedObjects;
/*    */   }
/*    */   
/*    */   public void setFeedObjects(List<BookObject> feedObjects) {
/* 80 */     this.feedObjects = feedObjects;
/*    */   } }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\bankfiles\entity\BankBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */