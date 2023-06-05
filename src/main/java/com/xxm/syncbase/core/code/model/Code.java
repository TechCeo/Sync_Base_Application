package com.xxm.syncbase.core.code.model;

import com.xxm.syncbase.core.entity.AbstractEntity;
import com.xxm.syncbase.core.utility.PrettySerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Where;

@Entity
@Table(
        name = "code",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"code", "type"}
        )}
)
@Where(
        clause = "del_Flag='N'"
)
public class Code extends AbstractEntity implements PrettySerializer {
    private String code;
    private String type;
    private String description;
    @Column(
            name = "extra_info"
    )
    private String extraInfo;

    @JsonIgnore
    public JsonSerializer<Code> getSerializer() {
        return new JsonSerializer<Code>() {
            public void serialize(Code code, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeStringField("Code", code.code);
                gen.writeStringField("Type", code.type);
                gen.writeStringField("Description", code.description);
                gen.writeStringField("Extra Info", code.extraInfo);
                gen.writeEndObject();
            }
        };
    }

    @JsonIgnore
    public List<String> getDefaultSearchFields() {
        return Arrays.asList("code", "type");
    }

    public String toString() {
        return "Code{code='" + this.code + '\'' + ", type='" + this.type + '\'' + ", description='" + this.description + '\'' + '}';
    }

    public Code() {
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

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Code)) {
            return false;
        } else {
            Code other = (Code)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label59;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label59;
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

                Object this$extraInfo = this.getExtraInfo();
                Object other$extraInfo = other.getExtraInfo();
                if (this$extraInfo == null) {
                    if (other$extraInfo != null) {
                        return false;
                    }
                } else if (!this$extraInfo.equals(other$extraInfo)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Code;
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
        return result;
    }
}