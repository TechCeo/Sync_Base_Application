package com.xxm.syncbase.application.fileobjects.dto;

import com.xxm.syncbase.application.bankfiles.dto.BankBookDTO;
import com.xxm.syncbase.core.dto.AbstractDto;

public class BookObjectDTO extends AbstractDto {
    private String name;
    private String description;
    private String mappedColumn;
    private String mappedColumnType;
    private String defaultValue;
    private Boolean enabled;
    private BankBookDTO bankBookDTO;

    public BookObjectDTO() {
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getMappedColumn() {
        return this.mappedColumn;
    }

    public String getMappedColumnType() {
        return this.mappedColumnType;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public BankBookDTO getBankBookDTO() {
        return this.bankBookDTO;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setMappedColumn(final String mappedColumn) {
        this.mappedColumn = mappedColumn;
    }

    public void setMappedColumnType(final String mappedColumnType) {
        this.mappedColumnType = mappedColumnType;
    }

    public void setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public void setBankBookDTO(final BankBookDTO bankBookDTO) {
        this.bankBookDTO = bankBookDTO;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BookObjectDTO)) {
            return false;
        } else {
            BookObjectDTO other = (BookObjectDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label95: {
                    Object this$name = this.getName();
                    Object other$name = other.getName();
                    if (this$name == null) {
                        if (other$name == null) {
                            break label95;
                        }
                    } else if (this$name.equals(other$name)) {
                        break label95;
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

                Object this$mappedColumn = this.getMappedColumn();
                Object other$mappedColumn = other.getMappedColumn();
                if (this$mappedColumn == null) {
                    if (other$mappedColumn != null) {
                        return false;
                    }
                } else if (!this$mappedColumn.equals(other$mappedColumn)) {
                    return false;
                }

                label74: {
                    Object this$mappedColumnType = this.getMappedColumnType();
                    Object other$mappedColumnType = other.getMappedColumnType();
                    if (this$mappedColumnType == null) {
                        if (other$mappedColumnType == null) {
                            break label74;
                        }
                    } else if (this$mappedColumnType.equals(other$mappedColumnType)) {
                        break label74;
                    }

                    return false;
                }

                label67: {
                    Object this$defaultValue = this.getDefaultValue();
                    Object other$defaultValue = other.getDefaultValue();
                    if (this$defaultValue == null) {
                        if (other$defaultValue == null) {
                            break label67;
                        }
                    } else if (this$defaultValue.equals(other$defaultValue)) {
                        break label67;
                    }

                    return false;
                }

                Object this$enabled = this.getEnabled();
                Object other$enabled = other.getEnabled();
                if (this$enabled == null) {
                    if (other$enabled != null) {
                        return false;
                    }
                } else if (!this$enabled.equals(other$enabled)) {
                    return false;
                }

                Object this$bankBookDTO = this.getBankBookDTO();
                Object other$bankBookDTO = other.getBankBookDTO();
                if (this$bankBookDTO == null) {
                    if (other$bankBookDTO != null) {
                        return false;
                    }
                } else if (!this$bankBookDTO.equals(other$bankBookDTO)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BookObjectDTO;
    }

    public int hashCode() {
        int result = 1;
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $mappedColumn = this.getMappedColumn();
        result = result * 59 + ($mappedColumn == null ? 43 : $mappedColumn.hashCode());
        Object $mappedColumnType = this.getMappedColumnType();
        result = result * 59 + ($mappedColumnType == null ? 43 : $mappedColumnType.hashCode());
        Object $defaultValue = this.getDefaultValue();
        result = result * 59 + ($defaultValue == null ? 43 : $defaultValue.hashCode());
        Object $enabled = this.getEnabled();
        result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
        Object $bankBookDTO = this.getBankBookDTO();
        result = result * 59 + ($bankBookDTO == null ? 43 : $bankBookDTO.hashCode());
        return result;
    }

    public String toString() {
        return "BookObjectDTO(name=" + this.getName() + ", description=" + this.getDescription() + ", mappedColumn=" + this.getMappedColumn() + ", mappedColumnType=" + this.getMappedColumnType() + ", defaultValue=" + this.getDefaultValue() + ", enabled=" + this.getEnabled() + ", bankBookDTO=" + this.getBankBookDTO() + ")";
    }
}
