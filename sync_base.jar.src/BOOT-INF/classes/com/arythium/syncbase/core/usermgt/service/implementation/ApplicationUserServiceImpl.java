/*     */ package BOOT-INF.classes.com.arythium.syncbase.core.usermgt.service.implementation;
/*     */ 
/*     */ import com.arythium.syncbase.core.code.service.CodeService;
/*     */ import com.arythium.syncbase.core.rolemgt.models.Role;
/*     */ import com.arythium.syncbase.core.rolemgt.models.RoleType;
/*     */ import com.arythium.syncbase.core.rolemgt.repositories.RoleRepository;
/*     */ import com.arythium.syncbase.core.setting.service.SettingService;
/*     */ import com.arythium.syncbase.core.usermgt.dto.ApplicationUserDTO;
/*     */ import com.arythium.syncbase.core.usermgt.dto.UpdatePasswordDTO;
/*     */ import com.arythium.syncbase.core.usermgt.exception.AppUserServiceException;
/*     */ import com.arythium.syncbase.core.usermgt.model.SystemUser;
/*     */ import com.arythium.syncbase.core.usermgt.repository.ApplicationUserRepository;
/*     */ import com.arythium.syncbase.core.usermgt.service.ApplicationUserService;
/*     */ import java.util.Date;
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
/*     */ import org.springframework.data.domain.Page;
/*     */ import org.springframework.data.domain.PageImpl;
/*     */ import org.springframework.data.domain.Pageable;
/*     */ import org.springframework.security.crypto.password.PasswordEncoder;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class ApplicationUserServiceImpl
/*     */   implements ApplicationUserService
/*     */ {
/*  36 */   private ModelMapper modelMapper = new ModelMapper();
/*     */   @Autowired
/*     */   private MessageSource messageSource;
/*  39 */   private Locale locale = LocaleContextHolder.getLocale();
/*     */   
/*     */   @Autowired
/*     */   private ApplicationUserRepository applicationUserRepository;
/*     */   
/*     */   @Autowired
/*     */   private RoleRepository roleRepository;
/*     */   
/*     */   @Autowired
/*     */   private SettingService settingService;
/*     */   
/*     */   @Autowired
/*     */   private CodeService codeService;
/*     */   
/*  53 */   private Logger logger = LoggerFactory.getLogger(getClass());
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private PasswordEncoder passwordEncoder;
/*     */ 
/*     */ 
/*     */   
/*     */   public String createSystemUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException {
/*  62 */     this.logger.debug("Creating system user: {}", applicationUserDTO);
/*     */     
/*     */     try {
/*  65 */       SystemUser systemUser = convertDtoToEntity(applicationUserDTO);
/*  66 */       String password = "";
/*  67 */       if (Objects.nonNull(this.applicationUserRepository.findByUserName(systemUser.getUserName()))) {
/*  68 */         throw new AppUserServiceException(this.messageSource.getMessage("user.add.exist", null, this.locale));
/*     */       }
/*     */       
/*  71 */       if (this.settingService.isSettingAvailable("ACTIVE_DIRECTORY_INTEGRATION"))
/*     */       {
/*  73 */         throw new AppUserServiceException(this.messageSource.getMessage("user.add.add.notfound", null, this.locale));
/*     */       }
/*     */       
/*  76 */       if (this.settingService.isSettingAvailable("USER_CREATION_GENERATE_PASSWORD")) {
/*     */         
/*  78 */         systemUser.setPassword(this.passwordEncoder.encode(password));
/*  79 */         systemUser.setChangePassword(true);
/*  80 */         systemUser.setNoOfWrongLoginCount(Integer.valueOf(0));
/*     */       } 
/*     */       
/*  83 */       SystemUser newUser = (SystemUser)this.applicationUserRepository.save(systemUser);
/*  84 */       this.logger.info("New user [{}] created", newUser.getUserName());
/*     */       
/*  86 */       return this.messageSource.getMessage("user.add.success", null, this.locale);
/*     */     }
/*  88 */     catch (Exception e) {
/*  89 */       this.logger.error(e.getMessage(), e);
/*  90 */       throw new AppUserServiceException(this.messageSource.getMessage("user.add.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String createBranchUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException {
/*  98 */     this.logger.debug("Creating branch user: {}", applicationUserDTO);
/*     */     
/* 100 */     validateNewUser(applicationUserDTO);
/* 101 */     return createUser(applicationUserDTO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String createUser(ApplicationUserDTO applicationUserDTO) {
/* 108 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateNewUser(ApplicationUserDTO userDTO) {
/* 113 */     this.logger.debug("Validating new user: {}", userDTO);
/*     */     
/* 115 */     SystemUser systemUser = this.applicationUserRepository.findByUserName(userDTO.getUserName());
/*     */     
/* 117 */     if (systemUser != null)
/*     */     {
/* 119 */       throw new AppUserServiceException("Username [" + userDTO.getUserName() + "] already exists");
/*     */     }
/*     */     
/* 122 */     systemUser = this.applicationUserRepository.findByEmail(userDTO.getEmail());
/*     */     
/* 124 */     if (systemUser != null)
/*     */     {
/* 126 */       throw new AppUserServiceException("Email address [" + userDTO.getEmail() + "] already exists");
/*     */     }
/*     */     
/* 129 */     validateContactDetails(userDTO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String updateSystemUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException {
/*     */     try {
/* 138 */       SystemUser systemUser = this.applicationUserRepository.findById(applicationUserDTO.getId()).orElse(null);
/* 139 */       systemUser.setVersion(applicationUserDTO.getVersion());
/* 140 */       systemUser.setFirstName(applicationUserDTO.getFirstName());
/* 141 */       systemUser.setLastName(applicationUserDTO.getLastName());
/* 142 */       systemUser.setEmail(applicationUserDTO.getEmail());
/* 143 */       systemUser.setPhoneNumber(applicationUserDTO.getPhoneNumber());
/*     */       
/* 145 */       if (applicationUserDTO.getBranchId() != null);
/*     */ 
/*     */       
/* 148 */       SystemUser updatedUser = (SystemUser)this.applicationUserRepository.save(systemUser);
/*     */       
/* 150 */       this.logger.info("Updated user [{}]", updatedUser.getUserName());
/* 151 */       return this.messageSource.getMessage("user.update.success", null, this.locale);
/*     */     }
/* 153 */     catch (Exception e) {
/* 154 */       this.logger.error(e.getMessage(), e);
/* 155 */       throw new AppUserServiceException(this.messageSource.getMessage("user.update.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String updateBranchUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException {
/* 163 */     this.logger.debug("Updating branch user:  {}", applicationUserDTO);
/* 164 */     validateExistingUser(applicationUserDTO);
/* 165 */     return updateUser(applicationUserDTO);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String updateUser(ApplicationUserDTO applicationUserDTO) {
/* 171 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateExistingUser(ApplicationUserDTO userDTO) {
/* 177 */     this.logger.debug("Validating existing user: {}", userDTO);
/*     */     
/* 179 */     SystemUser systemUser = this.applicationUserRepository.findByUserName(userDTO.getUserName());
/*     */     
/* 181 */     if (systemUser != null && !systemUser.getId().equals(userDTO.getId()))
/*     */     {
/* 183 */       throw new AppUserServiceException("Username [" + userDTO.getUserName() + "] already exists");
/*     */     }
/*     */     
/* 186 */     systemUser = this.applicationUserRepository.findByEmail(userDTO.getEmail());
/*     */     
/* 188 */     if (systemUser != null && !systemUser.getId().equals(userDTO.getId()))
/*     */     {
/* 190 */       throw new AppUserServiceException("Email address [" + userDTO.getEmail() + "] already exists");
/*     */     }
/* 192 */     validateContactDetails(userDTO);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateContactDetails(ApplicationUserDTO userDTO) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String deleteSystemUser(Long userId) throws AppUserServiceException {
/* 217 */     this.logger.debug("Deleting  system user with Id [{}]", userId);
/* 218 */     return deleteUser(userId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String deleteBranchUser(Long userId) throws AppUserServiceException {
/* 225 */     this.logger.debug("Deleting branch user with Id [{}]", userId);
/* 226 */     return deleteUser(userId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String deleteUser(Long userId) {
/*     */     try {
/* 234 */       this.logger.warn("Deleted user with Id [{}]", userId);
/* 235 */       return this.messageSource.getMessage("user.delete.success", null, this.locale);
/*     */     }
/* 237 */     catch (Exception e) {
/* 238 */       this.logger.error(e.getMessage(), e);
/* 239 */       throw new AppUserServiceException(this.messageSource.getMessage("user.delete.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String enableSystemUser(Long userId) throws AppUserServiceException {
/* 247 */     this.logger.debug("Enabling system user [{}]", userId);
/* 248 */     return enableUser(userId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String enableBranchUser(Long userId) throws AppUserServiceException {
/* 254 */     this.logger.debug("Enabling branch user [{}]", userId);
/* 255 */     return enableUser(userId);
/*     */   }
/*     */   
/*     */   private String enableUser(Long userId) {
/*     */     try {
/* 260 */       SystemUser systemUser = this.applicationUserRepository.findById(userId).orElse(null);
/* 261 */       systemUser.setEnabled(true);
/* 262 */       this.applicationUserRepository.save(systemUser);
/* 263 */       return this.messageSource.getMessage("user.enable.success", null, this.locale);
/*     */     }
/* 265 */     catch (Exception e) {
/* 266 */       this.logger.error(e.getMessage(), e);
/* 267 */       throw new AppUserServiceException(this.messageSource.getMessage("user.enable.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String disableSystemUser(Long userId) throws AppUserServiceException {
/* 274 */     this.logger.debug("Disabling system user [{}]", userId);
/* 275 */     return disableUser(userId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String disableBranchUser(Long userId) throws AppUserServiceException {
/* 281 */     this.logger.debug("Disabling branch user [{}]", userId);
/* 282 */     return disableUser(userId);
/*     */   }
/*     */   
/*     */   private String disableUser(Long userId) {
/*     */     try {
/* 287 */       SystemUser systemUser = this.applicationUserRepository.findById(userId).orElse(null);
/* 288 */       systemUser.setEnabled(false);
/* 289 */       this.applicationUserRepository.save(systemUser);
/* 290 */       return this.messageSource.getMessage("user.disable.success", null, this.locale);
/*     */     }
/* 292 */     catch (Exception e) {
/* 293 */       this.logger.error(e.getMessage(), e);
/* 294 */       throw new AppUserServiceException(this.messageSource.getMessage("user.disable.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String resetPassword(Long userId) throws AppUserServiceException {
/* 301 */     if (this.settingService.isSettingAvailable("USER_PASSWORD_RESET")) {
/*     */       try {
/* 303 */         String password = "";
/* 304 */         SystemUser systemUser = this.applicationUserRepository.findById(userId).orElse(null);
/* 305 */         systemUser.setEnabled(true);
/*     */ 
/*     */         
/* 308 */         systemUser.setChangePassword(true);
/* 309 */         systemUser.setPassword(this.passwordEncoder.encode(password));
/* 310 */         this.applicationUserRepository.save(systemUser);
/* 311 */         if (this.settingService.isSettingAvailable("USER_PASSWORD_RESET_SEND_EMAIL"));
/*     */ 
/*     */ 
/*     */         
/* 315 */         if (this.settingService.isSettingAvailable("USER_PASSWORD_RESET_SEND_SMS"));
/*     */ 
/*     */ 
/*     */         
/* 319 */         return this.messageSource.getMessage("user.password.reset.success", null, this.locale);
/*     */       }
/* 321 */       catch (Exception e) {
/* 322 */         this.logger.error(e.getMessage(), e);
/* 323 */         throw new AppUserServiceException(this.messageSource.getMessage("user.password.reset.failure", null, this.locale), e);
/*     */       } 
/*     */     }
/* 326 */     throw new AppUserServiceException(this.messageSource.getMessage("user.password.reset.disabled", null, this.locale));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String updatePassword(UpdatePasswordDTO updatePasswordDTO) throws AppUserServiceException {
/* 332 */     if (this.settingService.isSettingAvailable("USER_PASSWORD_UPDATE")) {
/*     */       try {
/* 334 */         SystemUser systemUser = this.applicationUserRepository.findById(updatePasswordDTO.getId()).orElse(null);
/* 335 */         if (!this.passwordEncoder.matches(updatePasswordDTO.getOldPassword(), systemUser.getPassword())) {
/* 336 */           this.logger.info("old password does not match");
/* 337 */           throw new AppUserServiceException("Password does not match");
/*     */         } 
/* 339 */         systemUser.setPassword(this.passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
/*     */         
/* 341 */         systemUser.setEnabled(true);
/* 342 */         systemUser.setChangePassword(false);
/* 343 */         this.applicationUserRepository.save(systemUser);
/* 344 */         if (this.settingService.isSettingAvailable("USER_PASSWORD_UPDATE_SEND_EMAIL"));
/*     */ 
/*     */ 
/*     */         
/* 348 */         if (this.settingService.isSettingAvailable("USER_PASSWORD_UPDATE_SEND_SMS"));
/*     */ 
/*     */ 
/*     */         
/* 352 */         return this.messageSource.getMessage("user.password.update.success", null, this.locale);
/*     */       }
/* 354 */       catch (Exception e) {
/* 355 */         this.logger.error(e.getMessage(), e);
/* 356 */         throw new AppUserServiceException(this.messageSource.getMessage("user.update.reset.failure", null, this.locale), e);
/*     */       } 
/*     */     }
/* 359 */     throw new AppUserServiceException(this.messageSource.getMessage("user.password.update.disabled", null, this.locale));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loginUser(Long userId) throws AppUserServiceException {
/*     */     try {
/* 366 */       SystemUser systemUser = this.applicationUserRepository.findById(userId).get();
/* 367 */       systemUser.setLastLoginDate(new Date());
/* 368 */       systemUser.setLoggedOn(true);
/* 369 */       this.applicationUserRepository.save(systemUser);
/*     */     
/*     */     }
/* 372 */     catch (Exception e) {
/* 373 */       this.logger.error(e.getMessage(), e);
/* 374 */       throw new AppUserServiceException(this.messageSource.getMessage("user.logon.record.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void logoutUser(Long userId) throws AppUserServiceException {
/*     */     try {
/* 382 */       SystemUser systemUser = this.applicationUserRepository.findById(userId).get();
/* 383 */       systemUser.setLoggedOn(false);
/* 384 */       this.applicationUserRepository.save(systemUser);
/*     */     
/*     */     }
/* 387 */     catch (Exception e) {
/* 388 */       this.logger.error(e.getMessage(), e);
/* 389 */       throw new AppUserServiceException(this.messageSource.getMessage("user.logout.record.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplicationUserDTO findByUsername(String username) throws AppUserServiceException {
/*     */     try {
/* 398 */       SystemUser systemUser = this.applicationUserRepository.findByUserName(username);
/*     */       
/* 400 */       return convertEntityToDto(systemUser);
/* 401 */     } catch (Exception e) {
/* 402 */       this.logger.error(e.getMessage(), e);
/* 403 */       throw new AppUserServiceException(this.messageSource.getMessage("user.find.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SystemUser findUserByUsername(String username) throws AppUserServiceException {
/* 410 */     this.logger.debug("Retrieving user [{}]", username);
/*     */     try {
/* 412 */       SystemUser systemUser = this.applicationUserRepository.findByUserName(username);
/* 413 */       return systemUser;
/*     */     }
/* 415 */     catch (Exception e) {
/* 416 */       this.logger.error(e.getMessage(), e);
/* 417 */       throw new AppUserServiceException(this.messageSource.getMessage("user.find.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplicationUserDTO findByEmail(String email) throws AppUserServiceException {
/*     */     try {
/* 425 */       SystemUser systemUser = this.applicationUserRepository.findByEmail(email);
/*     */       
/* 427 */       return convertEntityToDto(systemUser);
/* 428 */     } catch (Exception e) {
/* 429 */       this.logger.error(e.getMessage(), e);
/* 430 */       throw new AppUserServiceException(this.messageSource.getMessage("user.find.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplicationUserDTO convertEntityToDto(SystemUser systemUser) {
/* 438 */     ApplicationUserDTO applicationUserDTO = (ApplicationUserDTO)this.modelMapper.map(systemUser, ApplicationUserDTO.class);
/* 439 */     if (Objects.nonNull(systemUser.getLastLoginDate()));
/*     */ 
/*     */     
/* 442 */     applicationUserDTO.setFullName(systemUser.getFirstName() + " " + systemUser.getLastName());
/* 443 */     Role userRole = systemUser.getRole();
/* 444 */     applicationUserDTO.setRoleId(userRole.getId());
/* 445 */     applicationUserDTO.setRoleName(userRole.getName());
/* 446 */     applicationUserDTO.setRoleType(userRole.getRoleType().name());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 451 */     return applicationUserDTO;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SystemUser convertDtoToEntity(ApplicationUserDTO applicationUserDTO) {
/* 457 */     SystemUser systemUser = (SystemUser)this.modelMapper.map(applicationUserDTO, SystemUser.class);
/*     */     
/* 459 */     if (applicationUserDTO.getBranchId() != null);
/*     */ 
/*     */     
/* 462 */     return systemUser;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ApplicationUserDTO> convertEntitiesToDtos(List<SystemUser> systemUsers) {
/* 468 */     return (List<ApplicationUserDTO>)systemUsers.stream().map(applicationUser -> convertEntityToDto(applicationUser)).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Page<ApplicationUserDTO> getSystemUsers(Pageable pageable) {
/* 475 */     Page<SystemUser> users = this.applicationUserRepository.findByRole_RoleType(RoleType.SYSTEM, pageable);
/* 476 */     return (Page<ApplicationUserDTO>)new PageImpl(convertEntitiesToDtos(users.getContent()), pageable, users.getTotalElements());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Page<ApplicationUserDTO> getBranchUsers(Pageable pageable) {
/* 482 */     Page<SystemUser> users = this.applicationUserRepository.findByRole_RoleType(RoleType.BRANCH, pageable);
/* 483 */     return (Page<ApplicationUserDTO>)new PageImpl(convertEntitiesToDtos(users.getContent()), pageable, users.getTotalElements());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplicationUserDTO getUser(Long userId) {
/* 489 */     this.logger.debug("Retrieving user [{}]", userId);
/* 490 */     SystemUser systemUser = this.applicationUserRepository.findById(userId).orElse(null);
/* 491 */     return convertEntityToDto(systemUser);
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\usermgt\service\implementation\ApplicationUserServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */