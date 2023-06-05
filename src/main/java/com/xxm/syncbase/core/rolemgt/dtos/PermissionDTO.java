package com.xxm.syncbase.core.rolemgt.dtos;

import javax.validation.constraints.NotEmpty;

public class PermissionDTO {
    private Long id;
    private int version;
    private @NotEmpty String name;
    private String description;
    private String type;

    public PermissionDTO() {
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

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PermissionDTO)) {
            return false;
        } else {
            PermissionDTO other = (PermissionDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label63: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label63;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label63;
                    }

                    return false;
                }

                if (this.getVersion() != other.getVersion()) {
                    return false;
                } else {
                    label55: {
                        Object this$name = this.getName();
                        Object other$name = other.getName();
                        if (this$name == null) {
                            if (other$name == null) {
                                break label55;
                            }
                        } else if (this$name.equals(other$name)) {
                            break label55;
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

                    Object this$type = this.getType();
                    Object other$type = other.getType();
                    if (this$type == null) {
                        if (other$type != null) {
                            return false;
                        }
                    } else if (!this$type.equals(other$type)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PermissionDTO;
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
        return result;
    }

    public String toString() {
        return "PermissionDTO(id=" + this.getId() + ", version=" + this.getVersion() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", type=" + this.getType() + ")";
    }
}