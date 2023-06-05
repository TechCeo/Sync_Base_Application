package com.xxm.syncbase.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import org.modelmapper.ModelMapper;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable, SerializableEntity<AbstractEntity> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    Long id;
    @Version
    protected int version;
    @Column(
            name = "del_flag"
    )
    protected String delFlag = "N";
    @Column(
            name = "deleted_on"
    )
    protected Date deletedOn;
    @Column(
            name = "date_created"
    )
    protected Date dateCreated = new Date();

    public int hashCode() {
        int result = 1;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            AbstractEntity other = (AbstractEntity)obj;
            if (this.id == null) {
                if (other.id != null) {
                    return false;
                }
            } else if (!this.id.equals(other.id)) {
                return false;
            }

            return true;
        }
    }

    public String serialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(this);
        return data;
    }

    public void deserialize(String data) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        AbstractEntity readValue = (AbstractEntity)mapper.readValue(data, this.getClass());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(readValue, this);
    }

    @JsonIgnore
    public List<String> getDefaultSearchFields() {
        return new ArrayList();
    }

    public String toString() {
        return "id=" + this.id + ", version=" + this.version + ", delFlag='" + this.delFlag + '\'' + ", deletedOn=" + this.deletedOn + '}';
    }

    public AbstractEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public int getVersion() {
        return this.version;
    }

    public String getDelFlag() {
        return this.delFlag;
    }

    public Date getDeletedOn() {
        return this.deletedOn;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public void setDelFlag(final String delFlag) {
        this.delFlag = delFlag;
    }

    public void setDeletedOn(final Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    public void setDateCreated(final Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}