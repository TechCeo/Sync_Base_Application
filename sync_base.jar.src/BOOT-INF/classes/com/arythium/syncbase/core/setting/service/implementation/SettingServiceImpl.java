/*     */ package BOOT-INF.classes.com.arythium.syncbase.core.setting.service.implementation;
/*     */ 
/*     */ import com.arythium.syncbase.core.exceptions.SyncBaseException;
/*     */ import com.arythium.syncbase.core.setting.dto.SettingDto;
/*     */ import com.arythium.syncbase.core.setting.model.AppSetting;
/*     */ import com.arythium.syncbase.core.setting.repository.AppSettingRepository;
/*     */ import com.arythium.syncbase.core.setting.service.SettingService;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Collectors;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.MessageSource;
/*     */ import org.springframework.context.i18n.LocaleContextHolder;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class SettingServiceImpl
/*     */   implements SettingService
/*     */ {
/*  32 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.core.setting.service.implementation.SettingServiceImpl.class);
/*     */   
/*     */   private AppSettingRepository appSettingRepository;
/*     */   private MessageSource messageSource;
/*     */   private ModelMapper modelMapper;
/*  37 */   private final Locale locale = LocaleContextHolder.getLocale();
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   public SettingServiceImpl(AppSettingRepository appSettingRepository, MessageSource messageSource, ModelMapper modelMapper) {
/*  42 */     this.appSettingRepository = appSettingRepository;
/*  43 */     this.messageSource = messageSource;
/*  44 */     this.modelMapper = modelMapper;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String addSetting(SettingDto settingDto) {
/*  50 */     logger.debug("Adding setting {}", settingDto);
/*     */     
/*  52 */     validateNonExistingSetting(settingDto);
/*  53 */     AppSetting setting = convertDtoToEntity(settingDto);
/*  54 */     setting.setDelFlag("N");
/*     */     
/*     */     try {
/*  57 */       AppSetting newSetting = (AppSetting)this.appSettingRepository.save(setting);
/*  58 */       logger.info("Added setting {}", newSetting);
/*  59 */       return this.messageSource.getMessage("setting.add.success", null, this.locale);
/*  60 */     } catch (Exception e) {
/*  61 */       logger.error("Failed to add setting {}", settingDto, e);
/*  62 */       throw new SyncBaseException(this.messageSource.getMessage("setting.add.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String updateSetting(SettingDto settingDto) {
/*  71 */     logger.info("Updating setting {}", settingDto);
/*     */     
/*  73 */     AppSetting appSetting = this.appSettingRepository.findById(settingDto.getId()).orElse(null);
/*     */     
/*  75 */     if (appSetting == null) {
/*  76 */       throw new SyncBaseException("Setting Not Found");
/*     */     }
/*     */     
/*  79 */     appSetting.setName(settingDto.getName());
/*  80 */     appSetting.setDescription(settingDto.getDescription());
/*  81 */     appSetting.setValue(settingDto.getValue());
/*  82 */     appSetting.setCode(settingDto.getCode());
/*     */     
/*  84 */     this.appSettingRepository.save(appSetting);
/*     */     
/*  86 */     return this.messageSource.getMessage("setting.update.success", null, this.locale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateNonExistingSetting(SettingDto settingDto) {
/*  93 */     AppSetting setting = this.appSettingRepository.findByCode(settingDto.getCode());
/*     */     
/*  95 */     if (setting != null && !setting.getId().equals(settingDto.getId())) {
/*  96 */       throw new SyncBaseException(this.messageSource.getMessage("setting.exists", null, this.locale));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SettingDto getSettingById(Long id) {
/* 103 */     logger.debug("Getting setting by Id [{}]", id);
/*     */     
/* 105 */     AppSetting setting = this.appSettingRepository.findById(id).orElse(null);
/* 106 */     return convertEntityToDto(setting);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String deleteSetting(Long id) {
/* 113 */     logger.debug("Deleting setting with Id {}", id);
/*     */     
/* 115 */     AppSetting setting = this.appSettingRepository.findById(id).orElse(null);
/*     */     
/*     */     try {
/* 118 */       this.appSettingRepository.delete(setting);
/* 119 */       logger.warn("Deleted setting {}", setting);
/* 120 */       return this.messageSource.getMessage("setting.delete.success", null, this.locale);
/* 121 */     } catch (Exception e) {
/* 122 */       logger.error("Failed to delete setting {}", setting, e);
/* 123 */       throw new SyncBaseException(this.messageSource.getMessage("setting.delete.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSettingAvailable(String code) {
/* 130 */     boolean check = false;
/* 131 */     logger.info("checking ->{} configuration", code);
/* 132 */     AppSetting setting = this.appSettingRepository.findByCode(code);
/* 133 */     if (Objects.nonNull(setting)) {
/* 134 */       check = setting.isEnabled();
/* 135 */       logger.debug("AppSetting [{}] enabled status: {}", Boolean.valueOf(check));
/*     */     } 
/* 137 */     return check;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SettingDto> getSettings() {
/* 142 */     List<AppSetting> settings = this.appSettingRepository.findAll();
/* 143 */     List<SettingDto> SettingDtos = convertEntitiesToDtos(settings);
/* 144 */     return SettingDtos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SettingDto convertEntityToDto(AppSetting setting) {
/* 150 */     SettingDto settingDto = (SettingDto)this.modelMapper.map(setting, SettingDto.class);
/* 151 */     return settingDto;
/*     */   }
/*     */ 
/*     */   
/*     */   private AppSetting convertDtoToEntity(SettingDto settingDto) {
/* 156 */     AppSetting setting = (AppSetting)this.modelMapper.map(settingDto, AppSetting.class);
/* 157 */     return setting;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<SettingDto> convertEntitiesToDtos(List<AppSetting> settings) {
/* 163 */     return (List<SettingDto>)settings.stream().map(setting -> convertEntityToDto(setting)).collect(Collectors.toList());
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\setting\service\implementation\SettingServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */