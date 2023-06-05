package com.xxm.syncbase.core.setting.service.implementation;

import com.xxm.syncbase.core.exceptions.SyncBaseException;
import com.xxm.syncbase.core.setting.dto.SettingDto;
import com.xxm.syncbase.core.setting.model.AppSetting;
import com.xxm.syncbase.core.setting.repository.AppSettingRepository;
import com.xxm.syncbase.core.setting.service.SettingService;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {
    private static final Logger logger = LoggerFactory.getLogger(SettingServiceImpl.class);
    private AppSettingRepository appSettingRepository;
    private MessageSource messageSource;
    private ModelMapper modelMapper;
    private final Locale locale = LocaleContextHolder.getLocale();

    @Autowired
    public SettingServiceImpl(AppSettingRepository appSettingRepository, MessageSource messageSource, ModelMapper modelMapper) {
        this.appSettingRepository = appSettingRepository;
        this.messageSource = messageSource;
        this.modelMapper = modelMapper;
    }

    public String addSetting(SettingDto settingDto) {
        logger.debug("Adding setting {}", settingDto);
        this.validateNonExistingSetting(settingDto);
        AppSetting setting = this.convertDtoToEntity(settingDto);
        setting.setDelFlag("N");

        try {
            AppSetting newSetting = (AppSetting)this.appSettingRepository.save(setting);
            logger.info("Added setting {}", newSetting);
            return this.messageSource.getMessage("setting.add.success", (Object[])null, this.locale);
        } catch (Exception var4) {
            logger.error("Failed to add setting {}", settingDto, var4);
            throw new SyncBaseException(this.messageSource.getMessage("setting.add.failure", (Object[])null, this.locale), var4);
        }
    }

    public String updateSetting(SettingDto settingDto) {
        logger.info("Updating setting {}", settingDto);
        AppSetting appSetting = (AppSetting)this.appSettingRepository.findById(settingDto.getId()).orElse(null);
        if (appSetting == null) {
            throw new SyncBaseException("Setting Not Found");
        } else {
            appSetting.setName(settingDto.getName());
            appSetting.setDescription(settingDto.getDescription());
            appSetting.setValue(settingDto.getValue());
            appSetting.setCode(settingDto.getCode());
            this.appSettingRepository.save(appSetting);
            return this.messageSource.getMessage("setting.update.success", (Object[])null, this.locale);
        }
    }

    private void validateNonExistingSetting(SettingDto settingDto) {
        AppSetting setting = this.appSettingRepository.findByCode(settingDto.getCode());
        if (setting != null && !setting.getId().equals(settingDto.getId())) {
            throw new SyncBaseException(this.messageSource.getMessage("setting.exists", (Object[])null, this.locale));
        }
    }

    public SettingDto getSettingById(Long id) {
        logger.debug("Getting setting by Id [{}]", id);
        AppSetting setting = (AppSetting)this.appSettingRepository.findById(id).orElse(null);
        return this.convertEntityToDto(setting);
    }

    public String deleteSetting(Long id) {
        logger.debug("Deleting setting with Id {}", id);
        AppSetting setting = (AppSetting)this.appSettingRepository.findById(id).orElse(null);

        try {
            this.appSettingRepository.delete(setting);
            logger.warn("Deleted setting {}", setting);
            return this.messageSource.getMessage("setting.delete.success", (Object[])null, this.locale);
        } catch (Exception var4) {
            logger.error("Failed to delete setting {}", setting, var4);
            throw new SyncBaseException(this.messageSource.getMessage("setting.delete.failure", (Object[])null, this.locale), var4);
        }
    }

    public boolean isSettingAvailable(String code) {
        boolean check = false;
        logger.info("checking ->{} configuration", code);
        AppSetting setting = this.appSettingRepository.findByCode(code);
        if (Objects.nonNull(setting)) {
            check = setting.isEnabled();
            logger.debug("AppSetting [{}] enabled status: {}", check);
        }

        return check;
    }

    public List<SettingDto> getSettings() {
        List<AppSetting> settings = this.appSettingRepository.findAll();
        List<SettingDto> SettingDtos = this.convertEntitiesToDtos(settings);
        return SettingDtos;
    }

    private SettingDto convertEntityToDto(AppSetting setting) {
        SettingDto settingDto = (SettingDto)this.modelMapper.map(setting, SettingDto.class);
        return settingDto;
    }

    private AppSetting convertDtoToEntity(SettingDto settingDto) {
        AppSetting setting = (AppSetting)this.modelMapper.map(settingDto, AppSetting.class);
        return setting;
    }

    private List<SettingDto> convertEntitiesToDtos(List<AppSetting> settings) {
        return (List)settings.stream().map((setting) -> {
            return this.convertEntityToDto(setting);
        }).collect(Collectors.toList());
    }
}
