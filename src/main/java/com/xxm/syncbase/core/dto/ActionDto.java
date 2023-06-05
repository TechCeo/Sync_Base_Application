package com.xxm.syncbase.core.dto;

public class ActionDto extends AbstractDto {
    private boolean enabled;
    private String code;
    private String description;

    public ActionDto() {
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ActionDto)) {
            return false;
        } else {
            ActionDto other = (ActionDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isEnabled() != other.isEnabled()) {
                return false;
            } else {
                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$description = this.getDescription();
                Object other$description = other.getDescription();
                if (this$description == null) {
                    if (other$description != null) {
                        return false;
                    }
                } else if (!this$description.equals(other$description)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ActionDto;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + (this.isEnabled() ? 79 : 97);
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        return result;
    }

    public String toString() {
        return "ActionDto(enabled=" + this.isEnabled() + ", code=" + this.getCode() + ", description=" + this.getDescription() + ")";
    }
}
