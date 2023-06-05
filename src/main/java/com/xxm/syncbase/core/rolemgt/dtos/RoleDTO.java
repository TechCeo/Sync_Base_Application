package com.xxm.syncbase.core.rolemgt.dtos;



import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotEmpty;

public class RoleDTO {
    private Long id;
    private int version;
    private @NotEmpty(
            message = "Name is required"
    ) String name;
    private String description;
    private @NotEmpty(
            message = "Type is required"
    ) String type;
    private List<PermissionDTO> permissions;
    private Set<String> approvables;

    public RoleDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public int getVersion() {
        return this.version;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getType() {
        return this.type;
    }

    public List<PermissionDTO> getPermissions() {
        return this.permissions;
    }

    public Set<String> getApprovables() {
        return this.approvables;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setPermissions(final List<PermissionDTO> permissions) {
        this.permissions = permissions;
    }

    public void setApprovables(final Set<String> approvables) {
        this.approvables = approvables;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RoleDTO)) {
            return false;
        } else {
            RoleDTO other = (RoleDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label87: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label87;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label87;
                    }

                    return false;
                }

                if (this.getVersion() != other.getVersion()) {
                    return false;
                } else {
                    Object this$name = this.getName();
                    Object other$name = other.getName();
                    if (this$name == null) {
                        if (other$name != null) {
                            return false;
                        }
                    } else if (!this$name.equals(other$name)) {
                        return false;
                    }

                    label72: {
                        Object this$description = this.getDescription();
                        Object other$description = other.getDescription();
                        if (this$description == null) {
                            if (other$description == null) {
                                break label72;
                            }
                        } else if (this$description.equals(other$description)) {
                            break label72;
                        }

                        return false;
                    }

                    label65: {
                        Object this$type = this.getType();
                        Object other$type = other.getType();
                        if (this$type == null) {
                            if (other$type == null) {
                                break label65;
                            }
                        } else if (this$type.equals(other$type)) {
                            break label65;
                        }

                        return false;
                    }

                    Object this$permissions = this.getPermissions();
                    Object other$permissions = other.getPermissions();
                    if (this$permissions == null) {
                        if (other$permissions != null) {
                            return false;
                        }
                    } else if (!this$permissions.equals(other$permissions)) {
                        return false;
                    }

                    Object this$approvables = this.getApprovables();
                    Object other$approvables = other.getApprovables();
                    if (this$approvables == null) {
                        if (other$approvables != null) {
                            return false;
                        }
                    } else if (!this$approvables.equals(other$approvables)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RoleDTO;
    }

    public int hashCode() {
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        result = result * 59 + this.getVersion();
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        Object $permissions = this.getPermissions();
        result = result * 59 + ($permissions == null ? 43 : $permissions.hashCode());
        Object $approvables = this.getApprovables();
        result = result * 59 + ($approvables == null ? 43 : $approvables.hashCode());
        return result;
    }

    public String toString() {
        return "RoleDTO(id=" + this.getId() + ", version=" + this.getVersion() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", type=" + this.getType() + ", permissions=" + this.getPermissions() + ", approvables=" + this.getApprovables() + ")";
    }
}