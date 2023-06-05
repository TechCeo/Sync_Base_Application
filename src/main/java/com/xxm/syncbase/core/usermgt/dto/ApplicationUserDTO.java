package com.xxm.syncbase.core.usermgt.dto;

import com.xxm.syncbase.core.dto.AbstractDto;
import com.xxm.syncbase.core.utility.PrettySerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ApplicationUserDTO extends AbstractDto implements PrettySerializer {
    private @NotEmpty(
            message = "Username is required"
    ) String userName;
    private @NotEmpty(
            message = "First name is required"
    ) String firstName;
    private @NotEmpty(
            message = "Last name is required"
    ) String lastName;
    private String fullName;
    private @NotEmpty(
            message = "Email address is required"
    ) String email;
    private String password;
    private boolean enabled;
    private String lastLogin;
    private Integer noOfWrongLoginCount;
    private String phoneNumber;
    private boolean changePassword;
    private boolean isLoggedOn;
    private @NotNull(
            message = "Role is required"
    ) Long roleId;
    private String roleName;
    private String roleType;
    private Long branchId;
    private String branchName;

    public ApplicationUserDTO() {
    }

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

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
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

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Long getBranchId() {
        return this.branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public JsonSerializer<ApplicationUserDTO> getSerializer() {
        return new JsonSerializer<ApplicationUserDTO>() {
            public void serialize(ApplicationUserDTO value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeStringField("First name", value.getFirstName());
                gen.writeStringField("Last name", value.getLastName());
                gen.writeStringField("User name", value.getUserName());
                gen.writeStringField("Email", value.getEmail());
                gen.writeStringField("Phone number", value.getPhoneNumber());
                gen.writeStringField("Role", value.getRoleName());
                gen.writeEndObject();
            }
        };
    }

    public String toString() {
        return "ApplicationUserDTO(userName=" + this.getUserName() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", fullName=" + this.getFullName() + ", email=" + this.getEmail() + ", password=" + this.getPassword() + ", enabled=" + this.isEnabled() + ", lastLogin=" + this.getLastLogin() + ", noOfWrongLoginCount=" + this.getNoOfWrongLoginCount() + ", phoneNumber=" + this.getPhoneNumber() + ", changePassword=" + this.isChangePassword() + ", isLoggedOn=" + this.isLoggedOn() + ", roleId=" + this.getRoleId() + ", roleName=" + this.getRoleName() + ", roleType=" + this.getRoleType() + ", branchId=" + this.getBranchId() + ", branchName=" + this.getBranchName() + ")";
    }
}
