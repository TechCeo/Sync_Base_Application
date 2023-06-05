package com.xxm.syncbase.core.rolemgt.repositories;

import com.xxm.syncbase.core.rolemgt.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
  Permission findFirstByNameIgnoreCase(String s);

  Iterable<Permission> findByIdNotIn(Long[] permissions);

  Permission findFirstByName(String operation);

  boolean existsByName(String operation);
}
