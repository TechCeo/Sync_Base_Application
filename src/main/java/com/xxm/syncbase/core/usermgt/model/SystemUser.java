package com.xxm.syncbase.core.usermgt.model;

import javax.persistence.Entity;
import org.hibernate.annotations.Where;

@Entity(
        name = "app_system_user"
)
@Where(
        clause = "del_Flag='N'"
)
public class SystemUser extends AbstractUser {
    public SystemUser() {
    }

    public String toString() {
        return "SystemUser()";
    }
}
