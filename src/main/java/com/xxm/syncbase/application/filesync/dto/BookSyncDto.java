package com.xxm.syncbase.application.filesync.dto;

import com.xxm.syncbase.core.dto.AbstractDto;
import java.util.Date;

public class BookSyncDto extends AbstractDto {
    private String bookName;
    private String processId;
    private String status;
    private Date completedOn;
    private Date startedOn;
    private Long lastCount;
    private String message;

    public BookSyncDto() {
    }

    public String getBookName() {
        return this.bookName;
    }

    public String getProcessId() {
        return this.processId;
    }

    public String getStatus() {
        return this.status;
    }

    public Date getCompletedOn() {
        return this.completedOn;
    }

    public Date getStartedOn() {
        return this.startedOn;
    }

    public Long getLastCount() {
        return this.lastCount;
    }

    public String getMessage() {
        return this.message;
    }

    public void setBookName(final String bookName) {
        this.bookName = bookName;
    }

    public void setProcessId(final String processId) {
        this.processId = processId;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setCompletedOn(final Date completedOn) {
        this.completedOn = completedOn;
    }

    public void setStartedOn(final Date startedOn) {
        this.startedOn = startedOn;
    }

    public void setLastCount(final Long lastCount) {
        this.lastCount = lastCount;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BookSyncDto)) {
            return false;
        } else {
            BookSyncDto other = (BookSyncDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label95: {
                    Object this$bookName = this.getBookName();
                    Object other$bookName = other.getBookName();
                    if (this$bookName == null) {
                        if (other$bookName == null) {
                            break label95;
                        }
                    } else if (this$bookName.equals(other$bookName)) {
                        break label95;
                    }

                    return false;
                }

                Object this$processId = this.getProcessId();
                Object other$processId = other.getProcessId();
                if (this$processId == null) {
                    if (other$processId != null) {
                        return false;
                    }
                } else if (!this$processId.equals(other$processId)) {
                    return false;
                }

                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    if (other$status != null) {
                        return false;
                    }
                } else if (!this$status.equals(other$status)) {
                    return false;
                }

                label74: {
                    Object this$completedOn = this.getCompletedOn();
                    Object other$completedOn = other.getCompletedOn();
                    if (this$completedOn == null) {
                        if (other$completedOn == null) {
                            break label74;
                        }
                    } else if (this$completedOn.equals(other$completedOn)) {
                        break label74;
                    }

                    return false;
                }

                label67: {
                    Object this$startedOn = this.getStartedOn();
                    Object other$startedOn = other.getStartedOn();
                    if (this$startedOn == null) {
                        if (other$startedOn == null) {
                            break label67;
                        }
                    } else if (this$startedOn.equals(other$startedOn)) {
                        break label67;
                    }

                    return false;
                }

                Object this$lastCount = this.getLastCount();
                Object other$lastCount = other.getLastCount();
                if (this$lastCount == null) {
                    if (other$lastCount != null) {
                        return false;
                    }
                } else if (!this$lastCount.equals(other$lastCount)) {
                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BookSyncDto;
    }

    public int hashCode() {
        int result = 1;
        Object $bookName = this.getBookName();
        result = result * 59 + ($bookName == null ? 43 : $bookName.hashCode());
        Object $processId = this.getProcessId();
        result = result * 59 + ($processId == null ? 43 : $processId.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Object $completedOn = this.getCompletedOn();
        result = result * 59 + ($completedOn == null ? 43 : $completedOn.hashCode());
        Object $startedOn = this.getStartedOn();
        result = result * 59 + ($startedOn == null ? 43 : $startedOn.hashCode());
        Object $lastCount = this.getLastCount();
        result = result * 59 + ($lastCount == null ? 43 : $lastCount.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "BookSyncDto(bookName=" + this.getBookName() + ", processId=" + this.getProcessId() + ", status=" + this.getStatus() + ", completedOn=" + this.getCompletedOn() + ", startedOn=" + this.getStartedOn() + ", lastCount=" + this.getLastCount() + ", message=" + this.getMessage() + ")";
    }
}
