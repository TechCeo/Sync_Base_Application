package com.xxm.syncbase.core.code.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.xxm.syncbase.core.entity.AbstractEntity;
import com.xxm.syncbase.core.utility.PrettySerializer;

import java.io.IOException;
import javax.validation.constraints.NotEmpty;

public class CodeDTO extends AbstractEntity implements PrettySerializer {
    private @NotEmpty(
            message = "code"
    ) String code;
    private @NotEmpty(
            message = "type"
    ) String type;
    private @NotEmpty(
            message = "description"
    ) String description;
    private String extraInfo;
    private int version;

    @JsonIgnore
    public JsonSerializer<CodeDTO> getSerializer() {
        return new JsonSerializer<CodeDTO>() {
            public void serialize(CodeDTO code, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeStringField("Code", code.code);
                gen.writeStringField("Type", code.type);
                gen.writeStringField("Description", code.description);
                gen.writeStringField("Extra Info", code.extraInfo);
                gen.writeEndObject();
            }
        };
    }

    public CodeDTO() {
    }

    public String getCode() {
        return this.code;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public String getExtraInfo() {
        return this.extraInfo;
    }

    public int getVersion() {
        return this.version;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setExtraInfo(final String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CodeDTO)) {
            return false;
        } else {
            CodeDTO other = (CodeDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label63: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label63;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label63;
                    }

                    return false;
                }

                Object this$type = this.getType();
                Object other$type = other.getType();
                if (this$type == null) {
                    if (other$type != null) {
                        return false;
                    }
                } else if (!this$type.equals(other$type)) {
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

                label42: {
                    Object this$extraInfo = this.getExtraInfo();
                    Object other$extraInfo = other.getExtraInfo();
                    if (this$extraInfo == null) {
                        if (other$extraInfo == null) {
                            break label42;
                        }
                    } else if (this$extraInfo.equals(other$extraInfo)) {
                        break label42;
                    }

                    return false;
                }

                if (this.getVersion() != other.getVersion()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CodeDTO;
    }

    public int hashCode() {
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $extraInfo = this.getExtraInfo();
        result = result * 59 + ($extraInfo == null ? 43 : $extraInfo.hashCode());
        result = result * 59 + this.getVersion();
        return result;
    }

    public String toString() {
        return "CodeDTO(code=" + this.getCode() + ", type=" + this.getType() + ", description=" + this.getDescription() + ", extraInfo=" + this.getExtraInfo() + ", version=" + this.getVersion() + ")";
    }
}
