package BOOT-INF.classes.com.arythium.syncbase.core.rolemgt.repositories;

import com.arythium.syncbase.core.rolemgt.models.Role;
import com.arythium.syncbase.core.rolemgt.models.RoleType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findFirstByNameIgnoreCase(String paramString);
  
  Role findFirstByRoleType(RoleType paramRoleType);
  
  Role findByNameAndRoleType(String paramString, RoleType paramRoleType);
  
  List<Role> findByRoleType(RoleType paramRoleType);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\rolemgt\repositories\RoleRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */