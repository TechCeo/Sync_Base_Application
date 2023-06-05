package com.xxm.syncbase.core.rolemgt.models;

import com.xxm.syncbase.core.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.Where;

@Entity
@Where(
        clause = "del_Flag='N'"
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role extends AbstractEntity {
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST}
    )
    @JoinTable(
            name = "role_permission",
            joinColumns = {@JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "permission_id",
                    referencedColumnName = "id"
            )}
    )
    private List<Permission> permissions = new ArrayList();
    @ElementCollection
    private Set<String> approvables;

    public Role() {
    }

    public JsonSerializer<Role> getSerializer() {
        return new JsonSerializer<Role>() {
            public void serialize(Role value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                gen.writeStartObject();
                gen.writeStringField("name", value.getName());
                gen.writeStringField("description", value.getDescription());
                List<String> perms = new ArrayList();
                Iterator var5 = value.getPermissions().iterator();

                while(var5.hasNext()) {
                    Permission p = (Permission)var5.next();
                    perms.add(p.getName());
                }

                gen.writeObjectField("permissions", perms);
                gen.writeEndObject();
            }
        };
    }

    public Set<String> getApprovables() {
        return this.approvables;
    }

    public void setApprovables(Set<String> approvables) {
        this.approvables = approvables;
    }

    public List<String> getDefaultSearchFields() {
        return Arrays.asList("name", "description");
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

    public RoleType getRoleType() {
        return this.roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public List<Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String toString() {
        return "Role(name=" + this.getName() + ", description=" + this.getDescription() + ", roleType=" + this.getRoleType() + ", permissions=" + this.getPermissions() + ", approvables=" + this.getApprovables() + ")";
    }
}
