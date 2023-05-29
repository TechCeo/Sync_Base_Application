/*     */ package BOOT-INF.classes.com.arythium.syncbase.application.fileobjects.entity;
/*     */ 
/*     */ import com.arythium.syncbase.application.bankfiles.entity.BankBook;
/*     */ import com.arythium.syncbase.core.entity.AbstractEntity;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.ManyToOne;
/*     */ import javax.persistence.Table;
/*     */ import org.hibernate.annotations.Where;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @Table(name = "bank_file_objects")
/*     */ @Where(clause = "del_flag='N'")
/*     */ public class BookObject
/*     */   extends AbstractEntity
/*     */ {
/*     */   private String name;
/*     */   private String description;
/*     */   @Column(name = "mapped_column")
/*     */   private String mappedColumn;
/*     */   @Column(name = "mapped_column_type")
/*     */   private String mappedColumnType;
/*     */   @Column(name = "default_value")
/*     */   private String defaultValue;
/*     */   private Boolean enabled;
/*     */   @ManyToOne
/*     */   private BankBook bankBook;
/*     */   
/*     */   public boolean equals(Object o) {
/*  34 */     if (this == o) return true; 
/*  35 */     if (o == null || getClass() != o.getClass()) return false; 
/*  36 */     if (!super.equals(o)) return false; 
/*  37 */     com.arythium.syncbase.application.fileobjects.entity.BookObject that = (com.arythium.syncbase.application.fileobjects.entity.BookObject)o;
/*  38 */     return (this.name.equals(that.name) && this.bankBook
/*  39 */       .getBookName().equals(that.bankBook.getBookName()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  44 */     int result = super.hashCode();
/*  45 */     result = 31 * result + this.name.hashCode() + this.bankBook.hashCode();
/*  46 */     return result;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  50 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  54 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  58 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/*  62 */     this.description = description;
/*     */   }
/*     */   
/*     */   public String getMappedColumn() {
/*  66 */     return this.mappedColumn;
/*     */   }
/*     */   
/*     */   public void setMappedColumn(String mappedColumn) {
/*  70 */     this.mappedColumn = mappedColumn;
/*     */   }
/*     */   
/*     */   public String getMappedColumnType() {
/*  74 */     return this.mappedColumnType;
/*     */   }
/*     */   
/*     */   public void setMappedColumnType(String mappedColumnType) {
/*  78 */     this.mappedColumnType = mappedColumnType;
/*     */   }
/*     */   
/*     */   public String getDefaultValue() {
/*  82 */     return this.defaultValue;
/*     */   }
/*     */   
/*     */   public void setDefaultValue(String defaultValue) {
/*  86 */     this.defaultValue = defaultValue;
/*     */   }
/*     */   
/*     */   public Boolean getEnabled() {
/*  90 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public void setEnabled(Boolean enabled) {
/*  94 */     this.enabled = enabled;
/*     */   }
/*     */   
/*     */   public BankBook getBankBook() {
/*  98 */     return this.bankBook;
/*     */   }
/*     */   
/*     */   public void setBankBook(BankBook bankBook) {
/* 102 */     this.bankBook = bankBook;
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\fileobjects\entity\BookObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */