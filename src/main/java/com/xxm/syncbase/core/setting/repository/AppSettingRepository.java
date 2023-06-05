package com.xxm.syncbase.core.setting.repository;

import com.xxm.syncbase.core.setting.model.AppSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppSettingRepository extends JpaRepository<AppSetting, Long> {
  AppSetting findByCode(String code);

  boolean existsByCode(String code);
}
