package BOOT-INF.classes.com.arythium.syncbase.core.rolemgt.repositories;

import com.arythium.syncbase.core.rolemgt.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
  Permission findFirstByNameIgnoreCase(String paramString);
  
  Iterable<Permission> findByIdNotIn(Long[] paramArrayOfLong);
  
  Permission findFirstByName(String paramString);
  
  boolean existsByName(String paramString);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\rolemgt\repositories\PermissionRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */