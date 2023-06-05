//package com.xxm.syncbase.application.filesync.entity;
//
//import com.xxm.syncbase.core.entity.AbstractEntity;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(
//        name = "book_sync_audit"
//)
//public class BookSyncAudit extends AbstractEntity {
//    @ManyToOne
//    private BookSync bookSync;
//    @Column(
//            name = "initiated_by"
//    )
//    private String initiatedBy;
//    @Column(
//            name = "sync_status"
//    )
//    private String syncStatus;
//
//    public BookSyncAudit() {
//    }
//
//    public BookSync getBookSync() {
//        return this.bookSync;
//    }
//
//    public String getInitiatedBy() {
//        return this.initiatedBy;
//    }
//
//    public String getSyncStatus() {
//        return this.syncStatus;
//    }
//
//    public void setBookSync(final BookSync bookSync) {
//        this.bookSync = bookSync;
//    }
//
//    public void setInitiatedBy(final String initiatedBy) {
//        this.initiatedBy = initiatedBy;
//    }
//
//    public void setSyncStatus(final String syncStatus) {
//        this.syncStatus = syncStatus;
//    }
//
//    public boolean equals(final Object o) {
//        if (o == this) {
//            return true;
//        } else if (!(o instanceof BookSyncAudit)) {
//            return false;
//        } else {
//            BookSyncAudit other = (BookSyncAudit)o;
//            if (!other.canEqual(this)) {
//                return false;
//            } else {
//                label47: {
//                    Object this$bookSync = this.getBookSync();
//                    Object other$bookSync = other.getBookSync();
//                    if (this$bookSync == null) {
//                        if (other$bookSync == null) {
//                            break label47;
//                        }
//                    } else if (this$bookSync.equals(other$bookSync)) {
//                        break label47;
//                    }
//
//                    return false;
//                }
//
//                Object this$initiatedBy = this.getInitiatedBy();
//                Object other$initiatedBy = other.getInitiatedBy();
//                if (this$initiatedBy == null) {
//                    if (other$initiatedBy != null) {
//                        return false;
//                    }
//                } else if (!this$initiatedBy.equals(other$initiatedBy)) {
//                    return false;
//                }
//
//                Object this$syncStatus = this.getSyncStatus();
//                Object other$syncStatus = other.getSyncStatus();
//                if (this$syncStatus == null) {
//                    if (other$syncStatus != null) {
//                        return false;
//                    }
//                } else if (!this$syncStatus.equals(other$syncStatus)) {
//                    return false;
//                }
//
//                return true;
//            }
//        }
//    }
//
//    protected boolean canEqual(final Object other) {
//        return other instanceof BookSyncAudit;
//    }
//
//    public int hashCode() {
//        int result = 1;
//        Object $bookSync = this.getBookSync();
//        result = result * 59 + ($bookSync == null ? 43 : $bookSync.hashCode());
//        Object $initiatedBy = this.getInitiatedBy();
//        result = result * 59 + ($initiatedBy == null ? 43 : $initiatedBy.hashCode());
//        Object $syncStatus = this.getSyncStatus();
//        result = result * 59 + ($syncStatus == null ? 43 : $syncStatus.hashCode());
//        return result;
//    }
//
//    public String toString() {
//        return "BookSyncAudit(bookSync=" + this.getBookSync() + ", initiatedBy=" + this.getInitiatedBy() + ", syncStatus=" + this.getSyncStatus() + ")";
//    }
//}
