package com.xxm.syncbase.core.usermgt.repository;


import com.xxm.syncbase.core.rolemgt.models.Role;
import com.xxm.syncbase.core.rolemgt.models.RoleType;
import com.xxm.syncbase.core.usermgt.model.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<SystemUser, Long> {
  SystemUser findByUserName(String userName);

  SystemUser findByEmail(String email);

  Page<SystemUser> findByRole(Role role, Pageable pageable);

  Page<SystemUser> findByRole_RoleType(RoleType roleType, Pageable pageable);

  Integer countByRole(Role role);
}
