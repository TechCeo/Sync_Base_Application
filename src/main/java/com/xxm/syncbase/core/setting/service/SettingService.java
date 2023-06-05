package com.xxm.syncbase.core.setting.service;

import com.xxm.syncbase.core.setting.dto.SettingDto;
import java.util.List;

public interface SettingService {
  String addSetting(SettingDto settingDto);

  String updateSetting(SettingDto settingDto);

  SettingDto getSettingById(Long id);

  String deleteSetting(Long id);

  boolean isSettingAvailable(String code);

  List<SettingDto> getSettings();
}
