package com.xxm.syncbase.core.dto;

public class ResponseDTO {
    private String respCode;
    private String respDescription;
    private Object respBody;

    public ResponseDTO() {
    }

    public String getRespCode() {
        return this.respCode;
    }

    public String getRespDescription() {
        return this.respDescription;
    }

    public Object getRespBody() {
        return this.respBody;
    }

    public void setRespCode(final String respCode) {
        this.respCode = respCode;
    }

    public void setRespDescription(final String respDescription) {
        this.respDescription = respDescription;
    }

    public void setRespBody(final Object respBody) {
        this.respBody = respBody;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseDTO)) {
            return false;
        } else {
            ResponseDTO other = (ResponseDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$respCode = this.getRespCode();
                    Object other$respCode = other.getRespCode();
                    if (this$respCode == null) {
                        if (other$respCode == null) {
                            break label47;
                        }
                    } else if (this$respCode.equals(other$respCode)) {
                        break label47;
                    }

                    return false;
                }

                Object this$respDescription = this.getRespDescription();
                Object other$respDescription = other.getRespDescription();
                if (this$respDescription == null) {
                    if (other$respDescription != null) {
                        return false;
                    }
                } else if (!this$respDescription.equals(other$respDescription)) {
                    return false;
                }

                Object this$respBody = this.getRespBody();
                Object other$respBody = other.getRespBody();
                if (this$respBody == null) {
                    if (other$respBody != null) {
                        return false;
                    }
                } else if (!this$respBody.equals(other$respBody)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResponseDTO;
    }

    public int hashCode() {
        int result = 1;
        Object $respCode = this.getRespCode();
        result = result * 59 + ($respCode == null ? 43 : $respCode.hashCode());
        Object $respDescription = this.getRespDescription();
        result = result * 59 + ($respDescription == null ? 43 : $respDescription.hashCode());
        Object $respBody = this.getRespBody();
        result = result * 59 + ($respBody == null ? 43 : $respBody.hashCode());
        return result;
    }

    public String toString() {
        return "ResponseDTO(respCode=" + this.getRespCode() + ", respDescription=" + this.getRespDescription() + ", respBody=" + this.getRespBody() + ")";
    }
}
