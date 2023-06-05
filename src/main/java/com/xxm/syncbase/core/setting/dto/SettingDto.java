package com.xxm.syncbase.core.setting.dto;

import com.xxm.syncbase.core.dto.AbstractDto;
import com.xxm.syncbase.core.utility.PrettySerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import javax.validation.constraints.NotEmpty;

public class SettingDto extends AbstractDto implements PrettySerializer {
    private @NotEmpty(
            message = "Name is required"
    ) String name;
    private String description;
    private @NotEmpty(
            message = "Code is required"
    ) String code;
    private String value;
    private boolean enabled;
    private String csrf;

    @JsonIgnore
    public JsonSerializer<SettingDto> getSerializer() {
        return new JsonSerializer<SettingDto>() {
            public void serialize(SettingDto setting, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeStringField("Name", setting.name);
                gen.writeStringField("Description", setting.description);
                gen.writeStringField("Code", setting.code);
                gen.writeStringField("Value", setting.value);
                gen.writeStringField("Enabled", String.valueOf(setting.enabled));
                gen.writeEndObject();
            }
        };
    }

    public SettingDto() {
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getCsrf() {
        return this.csrf;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setCsrf(final String csrf) {
        this.csrf = csrf;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SettingDto)) {
            return false;
        } else {
            SettingDto other = (SettingDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label75: {
                    Object this$name = this.getName();
                    Object other$name = other.getName();
                    if (this$name == null) {
                        if (other$name == null) {
                            break label75;
                        }
                    } else if (this$name.equals(other$name)) {
                        break label75;
                    }

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

                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                label54: {
                    Object this$value = this.getValue();
                    Object other$value = other.getValue();
                    if (this$value == null) {
                        if (other$value == null) {
                            break label54;
                        }
                    } else if (this$value.equals(other$value)) {
                        break label54;
                    }

                    return false;
                }

                if (this.isEnabled() != other.isEnabled()) {
                    return false;
                } else {
                    Object this$csrf = this.getCsrf();
                    Object other$csrf = other.getCsrf();
                    if (this$csrf == null) {
                        if (other$csrf != null) {
                            return false;
                        }
                    } else if (!this$csrf.equals(other$csrf)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SettingDto;
    }

    public int hashCode() {

        int result = 1;
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $value = this.getValue();
        result = result * 59 + ($value == null ? 43 : $value.hashCode());
        result = result * 59 + (this.isEnabled() ? 79 : 97);
        Object $csrf = this.getCsrf();
        result = result * 59 + ($csrf == null ? 43 : $csrf.hashCode());
        return result;
    }

    public String toString() {
        return "SettingDto(name=" + this.getName() + ", description=" + this.getDescription() + ", code=" + this.getCode() + ", value=" + this.getValue() + ", enabled=" + this.isEnabled() + ", csrf=" + this.getCsrf() + ")";
    }
}