/*    */ package BOOT-INF.classes.com.arythium.syncbase.core.dto;
/*    */ 
/*    */ public abstract class AbstractDto {
/*    */   private Long id;
/*    */   private int version;
/*    */   
/*  7 */   public void setId(Long id) { this.id = id; } private Date dateCreated; private Date dateDeleted; private String delFlag; public void setVersion(int version) { this.version = version; } public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; } public void setDateDeleted(Date dateDeleted) { this.dateDeleted = dateDeleted; } public void setDelFlag(String delFlag) { this.delFlag = delFlag; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.core.dto.AbstractDto)) return false;  com.arythium.syncbase.core.dto.AbstractDto other = (com.arythium.syncbase.core.dto.AbstractDto)o; if (!other.canEqual(this)) return false;  Object this$id = getId(), other$id = other.getId(); if ((this$id == null) ? (other$id != null) : !this$id.equals(other$id)) return false;  if (getVersion() != other.getVersion()) return false;  Object this$dateCreated = getDateCreated(), other$dateCreated = other.getDateCreated(); if ((this$dateCreated == null) ? (other$dateCreated != null) : !this$dateCreated.equals(other$dateCreated)) return false;  Object this$dateDeleted = getDateDeleted(), other$dateDeleted = other.getDateDeleted(); if ((this$dateDeleted == null) ? (other$dateDeleted != null) : !this$dateDeleted.equals(other$dateDeleted)) return false;  Object this$delFlag = getDelFlag(), other$delFlag = other.getDelFlag(); return !((this$delFlag == null) ? (other$delFlag != null) : !this$delFlag.equals(other$delFlag)); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.core.dto.AbstractDto; } public int hashCode() { int PRIME = 59; result = 1; Object $id = getId(); result = result * 59 + (($id == null) ? 43 : $id.hashCode()); result = result * 59 + getVersion(); Object $dateCreated = getDateCreated(); result = result * 59 + (($dateCreated == null) ? 43 : $dateCreated.hashCode()); Object $dateDeleted = getDateDeleted(); result = result * 59 + (($dateDeleted == null) ? 43 : $dateDeleted.hashCode()); Object $delFlag = getDelFlag(); return result * 59 + (($delFlag == null) ? 43 : $delFlag.hashCode()); } public String toString() { return "AbstractDto(id=" + getId() + ", version=" + getVersion() + ", dateCreated=" + getDateCreated() + ", dateDeleted=" + getDateDeleted() + ", delFlag=" + getDelFlag() + ")"; }
/*    */ 
/*    */   
/* 10 */   public Long getId() { return this.id; }
/* 11 */   public int getVersion() { return this.version; }
/* 12 */   public Date getDateCreated() { return this.dateCreated; }
/* 13 */   public Date getDateDeleted() { return this.dateDeleted; } public String getDelFlag() {
/* 14 */     return this.delFlag;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\dto\AbstractDto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */