package BOOT-INF.classes.com.arythium.syncbase.core.setting.repository;

import com.arythium.syncbase.core.setting.model.AppSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppSettingRepository extends JpaRepository<AppSetting, Long> {
  AppSetting findByCode(String paramString);
  
  boolean existsByCode(String paramString);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\setting\repository\AppSettingRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */