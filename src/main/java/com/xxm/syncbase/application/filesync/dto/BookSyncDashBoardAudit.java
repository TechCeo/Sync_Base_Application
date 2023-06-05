package com.xxm.syncbase.application.filesync.dto;

import com.xxm.syncbase.core.dto.AbstractDto;

public class BookSyncDashBoardAudit extends AbstractDto {
    private String PROCESSED_BOOKS_COUNT;
    private String COMPLETED_BOOKS_COUNT;
    private String PAUSED_BOOKS_COUNT;
    private String STOPPED_BOOKS_COUNT;

    public BookSyncDashBoardAudit() {
    }

    public String getPROCESSED_BOOKS_COUNT() {
        return this.PROCESSED_BOOKS_COUNT;
    }

    public String getCOMPLETED_BOOKS_COUNT() {
        return this.COMPLETED_BOOKS_COUNT;
    }

    public String getPAUSED_BOOKS_COUNT() {
        return this.PAUSED_BOOKS_COUNT;
    }

    public String getSTOPPED_BOOKS_COUNT() {
        return this.STOPPED_BOOKS_COUNT;
    }

    public void setPROCESSED_BOOKS_COUNT(final String PROCESSED_BOOKS_COUNT) {
        this.PROCESSED_BOOKS_COUNT = PROCESSED_BOOKS_COUNT;
    }

    public void setCOMPLETED_BOOKS_COUNT(final String COMPLETED_BOOKS_COUNT) {
        this.COMPLETED_BOOKS_COUNT = COMPLETED_BOOKS_COUNT;
    }

    public void setPAUSED_BOOKS_COUNT(final String PAUSED_BOOKS_COUNT) {
        this.PAUSED_BOOKS_COUNT = PAUSED_BOOKS_COUNT;
    }

    public void setSTOPPED_BOOKS_COUNT(final String STOPPED_BOOKS_COUNT) {
        this.STOPPED_BOOKS_COUNT = STOPPED_BOOKS_COUNT;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BookSyncDashBoardAudit)) {
            return false;
        } else {
            BookSyncDashBoardAudit other = (BookSyncDashBoardAudit)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$PROCESSED_BOOKS_COUNT = this.getPROCESSED_BOOKS_COUNT();
                    Object other$PROCESSED_BOOKS_COUNT = other.getPROCESSED_BOOKS_COUNT();
                    if (this$PROCESSED_BOOKS_COUNT == null) {
                        if (other$PROCESSED_BOOKS_COUNT == null) {
                            break label59;
                        }
                    } else if (this$PROCESSED_BOOKS_COUNT.equals(other$PROCESSED_BOOKS_COUNT)) {
                        break label59;
                    }

                    return false;
                }

                Object this$COMPLETED_BOOKS_COUNT = this.getCOMPLETED_BOOKS_COUNT();
                Object other$COMPLETED_BOOKS_COUNT = other.getCOMPLETED_BOOKS_COUNT();
                if (this$COMPLETED_BOOKS_COUNT == null) {
                    if (other$COMPLETED_BOOKS_COUNT != null) {
                        return false;
                    }
                } else if (!this$COMPLETED_BOOKS_COUNT.equals(other$COMPLETED_BOOKS_COUNT)) {
                    return false;
                }

                Object this$PAUSED_BOOKS_COUNT = this.getPAUSED_BOOKS_COUNT();
                Object other$PAUSED_BOOKS_COUNT = other.getPAUSED_BOOKS_COUNT();
                if (this$PAUSED_BOOKS_COUNT == null) {
                    if (other$PAUSED_BOOKS_COUNT != null) {
                        return false;
                    }
                } else if (!this$PAUSED_BOOKS_COUNT.equals(other$PAUSED_BOOKS_COUNT)) {
                    return false;
                }

                Object this$STOPPED_BOOKS_COUNT = this.getSTOPPED_BOOKS_COUNT();
                Object other$STOPPED_BOOKS_COUNT = other.getSTOPPED_BOOKS_COUNT();
                if (this$STOPPED_BOOKS_COUNT == null) {
                    if (other$STOPPED_BOOKS_COUNT != null) {
                        return false;
                    }
                } else if (!this$STOPPED_BOOKS_COUNT.equals(other$STOPPED_BOOKS_COUNT)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BookSyncDashBoardAudit;
    }

    public int hashCode() {

        int result = 1;
        Object $PROCESSED_BOOKS_COUNT = this.getPROCESSED_BOOKS_COUNT();
        result = result * 59 + ($PROCESSED_BOOKS_COUNT == null ? 43 : $PROCESSED_BOOKS_COUNT.hashCode());
        Object $COMPLETED_BOOKS_COUNT = this.getCOMPLETED_BOOKS_COUNT();
        result = result * 59 + ($COMPLETED_BOOKS_COUNT == null ? 43 : $COMPLETED_BOOKS_COUNT.hashCode());
        Object $PAUSED_BOOKS_COUNT = this.getPAUSED_BOOKS_COUNT();
        result = result * 59 + ($PAUSED_BOOKS_COUNT == null ? 43 : $PAUSED_BOOKS_COUNT.hashCode());
        Object $STOPPED_BOOKS_COUNT = this.getSTOPPED_BOOKS_COUNT();
        result = result * 59 + ($STOPPED_BOOKS_COUNT == null ? 43 : $STOPPED_BOOKS_COUNT.hashCode());
        return result;
    }

    public String toString() {
        return "BookSyncDashBoardAudit(PROCESSED_BOOKS_COUNT=" + this.getPROCESSED_BOOKS_COUNT() + ", COMPLETED_BOOKS_COUNT=" + this.getCOMPLETED_BOOKS_COUNT() + ", PAUSED_BOOKS_COUNT=" + this.getPAUSED_BOOKS_COUNT() + ", STOPPED_BOOKS_COUNT=" + this.getSTOPPED_BOOKS_COUNT() + ")";
    }
}
