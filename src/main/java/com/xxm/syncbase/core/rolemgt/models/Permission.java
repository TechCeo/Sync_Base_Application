package com.xxm.syncbase.core.rolemgt.models;


import com.xxm.syncbase.core.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.hibernate.annotations.Where;

@Entity
@Where(
        clause = "del_Flag='N'"
)
public class Permission extends AbstractEntity {
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;

    public Permission() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PermissionType getPermissionType() {
        return this.permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    @JsonIgnore
    public List<String> getDefaultSearchFields() {
        return Arrays.asList("name", "description");
    }

    public JsonSerializer<Permission> getSerializer() {
        return new JsonSerializer<Permission>() {
            public void serialize(Permission value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                gen.writeStartObject();
                gen.writeStringField("name", value.getName());
                gen.writeStringField("description", value.getDescription());
                gen.writeObjectField("permissionType", value.getPermissionType().name());
                gen.writeEndObject();
            }
        };
    }

    public String toString() {
        return "Permission(name=" + this.getName() + ", description=" + this.getDescription() + ", permissionType=" + this.getPermissionType() + ")";
    }
}