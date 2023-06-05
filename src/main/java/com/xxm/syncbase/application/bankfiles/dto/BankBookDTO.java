package com.xxm.syncbase.application.bankfiles.dto;


import com.xxm.syncbase.core.dto.AbstractDto;

public class BankBookDTO extends AbstractDto {
  private String bookName;
  private String description;
  private String bookPrefix;
  private String mappedTableName;
  private Boolean enabled;

  public BankBookDTO() {
  }

  public String getBookName() {
    return this.bookName;
  }

  public String getDescription() {
    return this.description;
  }

  public String getBookPrefix() {
    return this.bookPrefix;
  }

  public String getMappedTableName() {
    return this.mappedTableName;
  }

  public Boolean getEnabled() {
    return this.enabled;
  }

  public void setBookName(final String bookName) {
    this.bookName = bookName;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public void setBookPrefix(final String bookPrefix) {
    this.bookPrefix = bookPrefix;
  }

  public void setMappedTableName(final String mappedTableName) {
    this.mappedTableName = mappedTableName;
  }

  public void setEnabled(final Boolean enabled) {
    this.enabled = enabled;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof BankBookDTO)) {
      return false;
    } else {
      BankBookDTO other = (BankBookDTO)o;
      if (!other.canEqual(this)) {
        return false;
      } else {
        label71: {
          Object this$bookName = this.getBookName();
          Object other$bookName = other.getBookName();
          if (this$bookName == null) {
            if (other$bookName == null) {
              break label71;
            }
          } else if (this$bookName.equals(other$bookName)) {
            break label71;
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

        label57: {
          Object this$bookPrefix = this.getBookPrefix();
          Object other$bookPrefix = other.getBookPrefix();
          if (this$bookPrefix == null) {
            if (other$bookPrefix == null) {
              break label57;
            }
          } else if (this$bookPrefix.equals(other$bookPrefix)) {
            break label57;
          }

          return false;
        }

        Object this$mappedTableName = this.getMappedTableName();
        Object other$mappedTableName = other.getMappedTableName();
        if (this$mappedTableName == null) {
          if (other$mappedTableName != null) {
            return false;
          }
        } else if (!this$mappedTableName.equals(other$mappedTableName)) {
          return false;
        }

        Object this$enabled = this.getEnabled();
        Object other$enabled = other.getEnabled();
        if (this$enabled == null) {
          if (other$enabled == null) {
            return true;
          }
        } else if (this$enabled.equals(other$enabled)) {
          return true;
        }

        return false;
      }
    }
  }

  protected boolean canEqual(final Object other) {
    return other instanceof BankBookDTO;
  }

  public int hashCode() {
    int result = 1;
    Object $bookName = this.getBookName();
    result = result * 59 + ($bookName == null ? 43 : $bookName.hashCode());
    Object $description = this.getDescription();
    result = result * 59 + ($description == null ? 43 : $description.hashCode());
    Object $bookPrefix = this.getBookPrefix();
    result = result * 59 + ($bookPrefix == null ? 43 : $bookPrefix.hashCode());
    Object $mappedTableName = this.getMappedTableName();
    result = result * 59 + ($mappedTableName == null ? 43 : $mappedTableName.hashCode());
    Object $enabled = this.getEnabled();
    result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
    return result;
  }

  public String toString() {
    return "BankBookDTO(bookName=" + this.getBookName() + ", description=" + this.getDescription() + ", bookPrefix=" + this.getBookPrefix() + ", mappedTableName=" + this.getMappedTableName() + ", enabled=" + this.getEnabled() + ")";
  }
}
