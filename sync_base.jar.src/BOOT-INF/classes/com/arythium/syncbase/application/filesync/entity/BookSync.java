/*    */ package BOOT-INF.classes.com.arythium.syncbase.application.filesync.entity;
/*    */ @Entity
/*    */ @Table(name = "file_sync")
/*    */ public class BookSync extends AbstractEntity {
/*    */   @Column(name = "process_id")
/*    */   private String processId;
/*    */   @Column(name = "book_name")
/*    */   private String bookName;
/*    */   
/* 10 */   public void setProcessId(String processId) { this.processId = processId; } @Enumerated(EnumType.STRING) private BookSyncStatus status; @Column(name = "completed_on") private Date completedOn; public void setBookName(String bookName) { this.bookName = bookName; } public void setStatus(BookSyncStatus status) { this.status = status; } public void setCompletedOn(Date completedOn) { this.completedOn = completedOn; } public void setStartedOn(Date startedOn) { this.startedOn = startedOn; } public void setLastCount(Long lastCount) { this.lastCount = lastCount; } public void setMessage(String message) { this.message = message; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof com.arythium.syncbase.application.filesync.entity.BookSync)) return false;  com.arythium.syncbase.application.filesync.entity.BookSync other = (com.arythium.syncbase.application.filesync.entity.BookSync)o; if (!other.canEqual(this)) return false;  Object this$processId = getProcessId(), other$processId = other.getProcessId(); if ((this$processId == null) ? (other$processId != null) : !this$processId.equals(other$processId)) return false;  Object this$bookName = getBookName(), other$bookName = other.getBookName(); if ((this$bookName == null) ? (other$bookName != null) : !this$bookName.equals(other$bookName)) return false;  Object this$status = getStatus(), other$status = other.getStatus(); if ((this$status == null) ? (other$status != null) : !this$status.equals(other$status)) return false;  Object this$completedOn = getCompletedOn(), other$completedOn = other.getCompletedOn(); if ((this$completedOn == null) ? (other$completedOn != null) : !this$completedOn.equals(other$completedOn)) return false;  Object this$startedOn = getStartedOn(), other$startedOn = other.getStartedOn(); if ((this$startedOn == null) ? (other$startedOn != null) : !this$startedOn.equals(other$startedOn)) return false;  Object this$lastCount = getLastCount(), other$lastCount = other.getLastCount(); if ((this$lastCount == null) ? (other$lastCount != null) : !this$lastCount.equals(other$lastCount)) return false;  Object this$message = getMessage(), other$message = other.getMessage(); return !((this$message == null) ? (other$message != null) : !this$message.equals(other$message)); } protected boolean canEqual(Object other) { return other instanceof com.arythium.syncbase.application.filesync.entity.BookSync; } public int hashCode() { int PRIME = 59; result = 1; Object $processId = getProcessId(); result = result * 59 + (($processId == null) ? 43 : $processId.hashCode()); Object $bookName = getBookName(); result = result * 59 + (($bookName == null) ? 43 : $bookName.hashCode()); Object $status = getStatus(); result = result * 59 + (($status == null) ? 43 : $status.hashCode()); Object $completedOn = getCompletedOn(); result = result * 59 + (($completedOn == null) ? 43 : $completedOn.hashCode()); Object $startedOn = getStartedOn(); result = result * 59 + (($startedOn == null) ? 43 : $startedOn.hashCode()); Object $lastCount = getLastCount(); result = result * 59 + (($lastCount == null) ? 43 : $lastCount.hashCode()); Object $message = getMessage(); return result * 59 + (($message == null) ? 43 : $message.hashCode()); } public String toString() { return "BookSync(processId=" + getProcessId() + ", bookName=" + getBookName() + ", status=" + getStatus() + ", completedOn=" + getCompletedOn() + ", startedOn=" + getStartedOn() + ", lastCount=" + getLastCount() + ", message=" + getMessage() + ")"; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getProcessId() {
/* 17 */     return this.processId;
/*    */   }
/*    */   public String getBookName() {
/* 20 */     return this.bookName;
/*    */   }
/*    */   public BookSyncStatus getStatus() {
/* 23 */     return this.status;
/*    */   }
/*    */   public Date getCompletedOn() {
/* 26 */     return this.completedOn;
/*    */   } @Column(name = "started_on")
/* 28 */   private Date startedOn = this.dateCreated; @Column(name = "last_count")
/* 29 */   private Long lastCount; private String message; public Date getStartedOn() { return this.startedOn; }
/*    */   
/*    */   public Long getLastCount() {
/* 32 */     return this.lastCount;
/*    */   } public String getMessage() {
/* 34 */     return this.message;
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\filesync\entity\BookSync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */