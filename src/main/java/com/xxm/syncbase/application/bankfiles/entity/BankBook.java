 package com.xxm.syncbase.application.bankfiles.entity;

 import com.xxm.syncbase.application.fileobjects.entity.BookObject;
 import com.xxm.syncbase.core.entity.AbstractEntity;
 import java.util.List;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.OneToMany;
 import javax.persistence.Table;
 import org.hibernate.annotations.Where;

 @Entity
 @Table(
         name = "bank_book"
 )
 @Where(
         clause = "del_flag='N'"
 )
 public class BankBook extends AbstractEntity {
     @Column(
             name = "book_name"
     )
     private String bookName;
     @Column(
             name = "book_prefix"
     )
     private String bookPrefix;
     @Column(
             name = "mapped_table_name"
     )
     private String mappedTableName;
     private String description;
     private Boolean enabled;
     @Column(
             name = "book_name"
     )
     @OneToMany(
             mappedBy = "bankBook",
             fetch = FetchType.EAGER
     )
     private List<BookObject> feedObjects;

     public BankBook() {
     }

     public String getBookName() {
         return this.bookName;
     }

     public String getBookPrefix() {
         return this.bookPrefix;
     }

     public String getMappedTableName() {
         return this.mappedTableName;
     }

     public String getDescription() {
         return this.description;
     }

     public Boolean getEnabled() {
         return this.enabled;
     }

     public List<BookObject> getFeedObjects() {
         return this.feedObjects;
     }

     public void setBookName(final String bookName) {
         this.bookName = bookName;
     }

     public void setBookPrefix(final String bookPrefix) {
         this.bookPrefix = bookPrefix;
     }

     public void setMappedTableName(final String mappedTableName) {
         this.mappedTableName = mappedTableName;
     }

     public void setDescription(final String description) {
         this.description = description;
     }

     public void setEnabled(final Boolean enabled) {
         this.enabled = enabled;
     }

     public void setFeedObjects(final List<BookObject> feedObjects) {
         this.feedObjects = feedObjects;
     }

     public boolean equals(final Object o) {
         if (o == this) {
             return true;
         } else if (!(o instanceof BankBook)) {
             return false;
         } else {
             BankBook other = (BankBook)o;
             if (!other.canEqual(this)) {
                 return false;
             } else {
                 Object this$bookName = this.getBookName();
                 Object other$bookName = other.getBookName();
                 if (this$bookName == null) {
                     if (other$bookName != null) {
                         return false;
                     }
                 } else if (!this$bookName.equals(other$bookName)) {
                     return false;
                 }

                 Object this$bookPrefix = this.getBookPrefix();
                 Object other$bookPrefix = other.getBookPrefix();
                 if (this$bookPrefix == null) {
                     if (other$bookPrefix != null) {
                         return false;
                     }
                 } else if (!this$bookPrefix.equals(other$bookPrefix)) {
                     return false;
                 }

                 Object this$mappedTableName = this.getMappedTableName();
                 Object other$mappedTableName = other.getMappedTableName();
                 if (this$mappedTableName == null) {
                     if (other$mappedTableName != null) {
                         return false;
                     }
                 } else if (!this$mappedTableName.equals(other$mappedTableName)) {
                     return false;
                 }

                 label62: {
                     Object this$description = this.getDescription();
                     Object other$description = other.getDescription();
                     if (this$description == null) {
                         if (other$description == null) {
                             break label62;
                         }
                     } else if (this$description.equals(other$description)) {
                         break label62;
                     }

                     return false;
                 }

                 label55: {
                     Object this$enabled = this.getEnabled();
                     Object other$enabled = other.getEnabled();
                     if (this$enabled == null) {
                         if (other$enabled == null) {
                             break label55;
                         }
                     } else if (this$enabled.equals(other$enabled)) {
                         break label55;
                     }

                     return false;
                 }

                 Object this$feedObjects = this.getFeedObjects();
                 Object other$feedObjects = other.getFeedObjects();
                 if (this$feedObjects == null) {
                     if (other$feedObjects != null) {
                         return false;
                     }
                 } else if (!this$feedObjects.equals(other$feedObjects)) {
                     return false;
                 }

                 return true;
             }
         }
     }

     protected boolean canEqual(final Object other) {
         return other instanceof BankBook;
     }

     public int hashCode() {
         int result = 1;
         Object $bookName = this.getBookName();
         result = result * 59 + ($bookName == null ? 43 : $bookName.hashCode());
         Object $bookPrefix = this.getBookPrefix();
         result = result * 59 + ($bookPrefix == null ? 43 : $bookPrefix.hashCode());
         Object $mappedTableName = this.getMappedTableName();
         result = result * 59 + ($mappedTableName == null ? 43 : $mappedTableName.hashCode());
         Object $description = this.getDescription();
         result = result * 59 + ($description == null ? 43 : $description.hashCode());
         Object $enabled = this.getEnabled();
         result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
         Object $feedObjects = this.getFeedObjects();
         result = result * 59 + ($feedObjects == null ? 43 : $feedObjects.hashCode());
         return result;
     }

     public String toString() {
         return "BankBook(bookName=" + this.getBookName() + ", bookPrefix=" + this.getBookPrefix() + ", mappedTableName=" + this.getMappedTableName() + ", description=" + this.getDescription() + ", enabled=" + this.getEnabled() + ", feedObjects=" + this.getFeedObjects() + ")";
     }
 }