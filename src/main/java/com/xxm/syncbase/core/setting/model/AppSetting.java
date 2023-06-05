package com.xxm.syncbase.core.setting.model;

import com.xxm.syncbase.core.entity.AbstractEntity;
import com.xxm.syncbase.core.utility.PrettySerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Where;

@Entity
@Table(
        name = "app_setting"
)
@Where(
        clause = "del_Flag='N'"
)
public class AppSetting extends AbstractEntity implements PrettySerializer {
    private String name;
    private String code;
    private String description;
    private String value;
    private boolean enabled;

    @JsonIgnore
    public List<String> getDefaultSearchFields() {
        return Arrays.asList("name", "code");
    }

    @JsonIgnore
    public JsonSerializer<AppSetting> getSerializer() {
        return new JsonSerializer<AppSetting>() {
            public void serialize(AppSetting setting, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeStringField("Name", setting.name);
                gen.writeStringField("Description", setting.description != null ? setting.description : "");
                gen.writeStringField("Code", setting.code);
                gen.writeStringField("Value", setting.value);
                gen.writeStringField("Enabled", String.valueOf(setting.enabled));
                gen.writeEndObject();
            }
        };
    }

    public AppSetting() {
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AppSetting)) {
            return false;
        } else {
            AppSetting other = (AppSetting)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label63: {
                    Object this$name = this.getName();
                    Object other$name = other.getName();
                    if (this$name == null) {
                        if (other$name == null) {
                            break label63;
                        }
                    } else if (this$name.equals(other$name)) {
                        break label63;
                    }

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
                    Object this$value = this.getValue();
                    Object other$value = other.getValue();
                    if (this$value == null) {
                        if (other$value == null) {
                            break label42;
                        }
                    } else if (this$value.equals(other$value)) {
                        break label42;
                    }

                    return false;
                }

                if (this.isEnabled() != other.isEnabled()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AppSetting;
    }

    public int hashCode() {
        int result = 1;
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $value = this.getValue();
        result = result * 59 + ($value == null ? 43 : $value.hashCode());
        result = result * 59 + (this.isEnabled() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "AppSetting(name=" + this.getName() + ", code=" + this.getCode() + ", description=" + this.getDescription() + ", value=" + this.getValue() + ", enabled=" + this.isEnabled() + ")";
    }
}
