package com.xxm.syncbase.core.usermgt.model;


import com.xxm.syncbase.core.entity.AbstractEntity;
import com.xxm.syncbase.core.rolemgt.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractUser extends AbstractEntity {
    @Column(
            unique = true
    )
    protected String userName;
    protected String firstName;
    protected String lastName;
    @Column(
            unique = true
    )
    protected String email;
    protected String password;
    protected boolean enabled = true;
    protected Date lastLoginDate;
    protected Integer noOfWrongLoginCount = 0;
    protected String phoneNumber;
    protected boolean changePassword = true;
    protected boolean isLoggedOn;
    @ManyToOne
    protected Role role;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getNoOfWrongLoginCount() {
        return this.noOfWrongLoginCount;
    }

    public void setNoOfWrongLoginCount(Integer noOfWrongLoginCount) {
        this.noOfWrongLoginCount = noOfWrongLoginCount;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isChangePassword() {
        return this.changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    public boolean isLoggedOn() {
        return this.isLoggedOn;
    }

    public void setLoggedOn(boolean loggedOn) {
        this.isLoggedOn = loggedOn;
    }

    @JsonIgnore
    public List<String> getDefaultSearchFields() {
        return Arrays.asList("userName", "firstName", "lastName", "email");
    }

    public AbstractUser() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractUser)) return false;

        AbstractUser that = (AbstractUser) o;

        if (isEnabled() != that.isEnabled()) return false;
        if (isChangePassword() != that.isChangePassword()) return false;
        if (isLoggedOn() != that.isLoggedOn()) return false;
        if (getUserName() != null ? !getUserName().equals(that.getUserName()) : that.getUserName() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) return false;
        if (getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null)
            return false;
        if (getLastLoginDate() != null ? !getLastLoginDate().equals(that.getLastLoginDate()) : that.getLastLoginDate() != null)
            return false;
        if (getNoOfWrongLoginCount() != null ? !getNoOfWrongLoginCount().equals(that.getNoOfWrongLoginCount()) : that.getNoOfWrongLoginCount() != null)
            return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(that.getPhoneNumber()) : that.getPhoneNumber() != null)
            return false;
        return getRole() != null ? getRole().equals(that.getRole()) : that.getRole() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserName() != null ? getUserName().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (isEnabled() ? 1 : 0);
        result = 31 * result + (getLastLoginDate() != null ? getLastLoginDate().hashCode() : 0);
        result = 31 * result + (getNoOfWrongLoginCount() != null ? getNoOfWrongLoginCount().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (isChangePassword() ? 1 : 0);
        result = 31 * result + (isLoggedOn() ? 1 : 0);
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        return result;
    }

    public String toString() {
        return "AbstractUser(userName=" + this.getUserName() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", email=" + this.getEmail() + ", password=" + this.getPassword() + ", enabled=" + this.isEnabled() + ", lastLoginDate=" + this.getLastLoginDate() + ", noOfWrongLoginCount=" + this.getNoOfWrongLoginCount() + ", phoneNumber=" + this.getPhoneNumber() + ", changePassword=" + this.isChangePassword() + ", isLoggedOn=" + this.isLoggedOn() + ", role=" + this.getRole() + ")";
    }
}