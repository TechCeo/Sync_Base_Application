package com.xxm.syncbase.core.rolemgt.repositories;

import com.xxm.syncbase.core.rolemgt.models.Role;
import com.xxm.syncbase.core.rolemgt.models.RoleType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findFirstByNameIgnoreCase(String s);

  Role findFirstByRoleType(RoleType roleType);

  Role findByNameAndRoleType(String name, RoleType type);

  List<Role> findByRoleType(RoleType type);
}