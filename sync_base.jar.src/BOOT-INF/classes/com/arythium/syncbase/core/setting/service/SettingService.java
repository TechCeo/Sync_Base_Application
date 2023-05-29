package BOOT-INF.classes.com.arythium.syncbase.core.setting.service;

import com.arythium.syncbase.core.setting.dto.SettingDto;
import java.util.List;

public interface SettingService {
  String addSetting(SettingDto paramSettingDto);
  
  String updateSetting(SettingDto paramSettingDto);
  
  SettingDto getSettingById(Long paramLong);
  
  String deleteSetting(Long paramLong);
  
  boolean isSettingAvailable(String paramString);
  
  List<SettingDto> getSettings();
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\setting\service\SettingService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */