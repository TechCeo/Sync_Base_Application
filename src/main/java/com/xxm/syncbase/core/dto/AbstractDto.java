 package com.xxm.syncbase.core.dto;

 import java.util.Date;

 public abstract class AbstractDto {
     private Long id;
     private int version;
     private Date dateCreated;
     private Date dateDeleted;
     private String delFlag;

     public AbstractDto() {
     }

     public Long getId() {
         return this.id;
     }

     public int getVersion() {
         return this.version;
     }

     public Date getDateCreated() {
         return this.dateCreated;
     }

     public Date getDateDeleted() {
         return this.dateDeleted;
     }

     public String getDelFlag() {
         return this.delFlag;
     }

     public void setId(final Long id) {
         this.id = id;
     }

     public void setVersion(final int version) {
         this.version = version;
     }

     public void setDateCreated(final Date dateCreated) {
         this.dateCreated = dateCreated;
     }

     public void setDateDeleted(final Date dateDeleted) {
         this.dateDeleted = dateDeleted;
     }

     public void setDelFlag(final String delFlag) {
         this.delFlag = delFlag;
     }

     public boolean equals(final Object o) {
         if (o == this) {
             return true;
         } else if (!(o instanceof AbstractDto)) {
             return false;
         } else {
             AbstractDto other = (AbstractDto)o;
             if (!other.canEqual(this)) {
                 return false;
             } else {
                 label63: {
                     Object this$id = this.getId();
                     Object other$id = other.getId();
                     if (this$id == null) {
                         if (other$id == null) {
                             break label63;
                         }
                     } else if (this$id.equals(other$id)) {
                         break label63;
                     }

                     return false;
                 }

                 if (this.getVersion() != other.getVersion()) {
                     return false;
                 } else {
                     label55: {
                         Object this$dateCreated = this.getDateCreated();
                         Object other$dateCreated = other.getDateCreated();
                         if (this$dateCreated == null) {
                             if (other$dateCreated == null) {
                                 break label55;
                             }
                         } else if (this$dateCreated.equals(other$dateCreated)) {
                             break label55;
                         }

                         return false;
                     }

                     Object this$dateDeleted = this.getDateDeleted();
                     Object other$dateDeleted = other.getDateDeleted();
                     if (this$dateDeleted == null) {
                         if (other$dateDeleted != null) {
                             return false;
                         }
                     } else if (!this$dateDeleted.equals(other$dateDeleted)) {
                         return false;
                     }

                     Object this$delFlag = this.getDelFlag();
                     Object other$delFlag = other.getDelFlag();
                     if (this$delFlag == null) {
                         if (other$delFlag != null) {
                             return false;
                         }
                     } else if (!this$delFlag.equals(other$delFlag)) {
                         return false;
                     }

                     return true;
                 }
             }
         }
     }

     protected boolean canEqual(final Object other) {
         return other instanceof AbstractDto;
     }

     public int hashCode() {
         int result = 1;
         Object $id = this.getId();
         result = result * 59 + ($id == null ? 43 : $id.hashCode());
         result = result * 59 + this.getVersion();
         Object $dateCreated = this.getDateCreated();
         result = result * 59 + ($dateCreated == null ? 43 : $dateCreated.hashCode());
         Object $dateDeleted = this.getDateDeleted();
         result = result * 59 + ($dateDeleted == null ? 43 : $dateDeleted.hashCode());
         Object $delFlag = this.getDelFlag();
         result = result * 59 + ($delFlag == null ? 43 : $delFlag.hashCode());
         return result;
     }

     public String toString() {
         return "AbstractDto(id=" + this.getId() + ", version=" + this.getVersion() + ", dateCreated=" + this.getDateCreated() + ", dateDeleted=" + this.getDateDeleted() + ", delFlag=" + this.getDelFlag() + ")";
     }
 }