package com.xxm.syncbase.application.fileobjects.entity;

import com.xxm.syncbase.application.bankfiles.entity.BankBook;
import com.xxm.syncbase.core.entity.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Where;

@Entity
@Table(
        name = "bank_book_object"
)
@Where(
        clause = "del_flag='N'"
)
public class BookObject extends AbstractEntity {
    private String name;
    private String description;
    @Column(
            name = "mapped_column"
    )
    private String mappedColumn;
    @Column(
            name = "mapped_column_type"
    )
    private String mappedColumnType;
    @Column(
            name = "default_value"
    )
    private String defaultValue;
    private Boolean enabled;
    @ManyToOne
    private BankBook bankBook;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            if (!super.equals(o)) {
                return false;
            } else {
                BookObject that = (BookObject)o;
                return this.name.equals(that.name) && this.bankBook.equals(that.bankBook);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.name.hashCode() + this.bankBook.hashCode();
        return result;
    }

    public BookObject() {
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

    public BankBook getBankBook() {
        return this.bankBook;
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

    public void setBankBook(final BankBook bankBook) {
        this.bankBook = bankBook;
    }

    public String toString() {
        return "BookObject(name=" + this.getName() + ", description=" + this.getDescription() + ", mappedColumn=" + this.getMappedColumn() + ", mappedColumnType=" + this.getMappedColumnType() + ", defaultValue=" + this.getDefaultValue() + ", enabled=" + this.getEnabled() + ", bankBook=" + this.getBankBook() + ")";
    }
}
