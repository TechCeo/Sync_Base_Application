package BOOT-INF.classes.com.arythium.syncbase.core.usermgt.repository;

import com.arythium.syncbase.core.rolemgt.models.Role;
import com.arythium.syncbase.core.rolemgt.models.RoleType;
import com.arythium.syncbase.core.usermgt.model.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<SystemUser, Long> {
  SystemUser findByUserName(String paramString);
  
  SystemUser findByEmail(String paramString);
  
  Page<SystemUser> findByRole(Role paramRole, Pageable paramPageable);
  
  Page<SystemUser> findByRole_RoleType(RoleType paramRoleType, Pageable paramPageable);
  
  Integer countByRole(Role paramRole);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\usermgt\repository\ApplicationUserRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */