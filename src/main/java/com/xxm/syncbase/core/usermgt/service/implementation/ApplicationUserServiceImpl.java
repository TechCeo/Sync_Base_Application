package com.xxm.syncbase.core.usermgt.service.implementation;

import com.xxm.syncbase.core.code.service.CodeService;
import com.xxm.syncbase.core.rolemgt.models.Role;
import com.xxm.syncbase.core.rolemgt.models.RoleType;
import com.xxm.syncbase.core.rolemgt.repositories.RoleRepository;
import com.xxm.syncbase.core.setting.service.SettingService;
import com.xxm.syncbase.core.usermgt.dto.ApplicationUserDTO;
import com.xxm.syncbase.core.usermgt.dto.UpdatePasswordDTO;
import com.xxm.syncbase.core.usermgt.exception.AppUserServiceException;
import com.xxm.syncbase.core.usermgt.model.SystemUser;
import com.xxm.syncbase.core.usermgt.repository.ApplicationUserRepository;
import com.xxm.syncbase.core.usermgt.service.ApplicationUserService;
import java.util.Date;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private MessageSource messageSource;
    private Locale locale = LocaleContextHolder.getLocale();
    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SettingService settingService;
    @Autowired
    private CodeService codeService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApplicationUserServiceImpl() {
    }

    public String createSystemUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException {
        this.logger.debug("Creating system user: {}", applicationUserDTO);

        try {
            SystemUser systemUser = this.convertDtoToEntity(applicationUserDTO);
            String password = "";
            if (Objects.nonNull(this.applicationUserRepository.findByUserName(systemUser.getUserName()))) {
                throw new AppUserServiceException(this.messageSource.getMessage("user.add.exist", (Object[])null, this.locale));
            } else if (this.settingService.isSettingAvailable("ACTIVE_DIRECTORY_INTEGRATION")) {
                throw new AppUserServiceException(this.messageSource.getMessage("user.add.add.notfound", (Object[])null, this.locale));
            } else {
                if (this.settingService.isSettingAvailable("USER_CREATION_GENERATE_PASSWORD")) {
                    systemUser.setPassword(this.passwordEncoder.encode(password));
                    systemUser.setChangePassword(true);
                    systemUser.setNoOfWrongLoginCount(0);
                }

                SystemUser newUser = (SystemUser)this.applicationUserRepository.save(systemUser);
                this.logger.info("New user [{}] created", newUser.getUserName());
                return this.messageSource.getMessage("user.add.success", (Object[])null, this.locale);
            }
        } catch (Exception var5) {
            this.logger.error(var5.getMessage(), var5);
            throw new AppUserServiceException(this.messageSource.getMessage("user.add.failure", (Object[])null, this.locale), var5);
        }
    }

    public String createBranchUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException {
        this.logger.debug("Creating branch user: {}", applicationUserDTO);
        this.validateNewUser(applicationUserDTO);
        return this.createUser(applicationUserDTO);
    }

    private String createUser(ApplicationUserDTO applicationUserDTO) {
        return "";
    }

    public void validateNewUser(ApplicationUserDTO userDTO) {
        this.logger.debug("Validating new user: {}", userDTO);
        SystemUser systemUser = this.applicationUserRepository.findByUserName(userDTO.getUserName());
        if (systemUser != null) {
            throw new AppUserServiceException("Username [" + userDTO.getUserName() + "] already exists");
        } else {
            systemUser = this.applicationUserRepository.findByEmail(userDTO.getEmail());
            if (systemUser != null) {
                throw new AppUserServiceException("Email address [" + userDTO.getEmail() + "] already exists");
            } else {
                this.validateContactDetails(userDTO);
            }
        }
    }

    public String updateSystemUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException {
        try {
            SystemUser systemUser = (SystemUser)this.applicationUserRepository.findById(applicationUserDTO.getId()).orElse(null);
            systemUser.setVersion(applicationUserDTO.getVersion());
            systemUser.setFirstName(applicationUserDTO.getFirstName());
            systemUser.setLastName(applicationUserDTO.getLastName());
            systemUser.setEmail(applicationUserDTO.getEmail());
            systemUser.setPhoneNumber(applicationUserDTO.getPhoneNumber());
            if (applicationUserDTO.getBranchId() != null) {
            }

            SystemUser updatedUser = (SystemUser)this.applicationUserRepository.save(systemUser);
            this.logger.info("Updated user [{}]", updatedUser.getUserName());
            return this.messageSource.getMessage("user.update.success", (Object[])null, this.locale);
        } catch (Exception var4) {
            this.logger.error(var4.getMessage(), var4);
            throw new AppUserServiceException(this.messageSource.getMessage("user.update.failure", (Object[])null, this.locale), var4);
        }
    }

    public String updateBranchUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException {
        this.logger.debug("Updating branch user:  {}", applicationUserDTO);
        this.validateExistingUser(applicationUserDTO);
        return this.updateUser(applicationUserDTO);
    }

    private String updateUser(ApplicationUserDTO applicationUserDTO) {
        return "";
    }

    public void validateExistingUser(ApplicationUserDTO userDTO) {
        this.logger.debug("Validating existing user: {}", userDTO);
        SystemUser systemUser = this.applicationUserRepository.findByUserName(userDTO.getUserName());
        if (systemUser != null && !systemUser.getId().equals(userDTO.getId())) {
            throw new AppUserServiceException("Username [" + userDTO.getUserName() + "] already exists");
        } else {
            systemUser = this.applicationUserRepository.findByEmail(userDTO.getEmail());
            if (systemUser != null && !systemUser.getId().equals(userDTO.getId())) {
                throw new AppUserServiceException("Email address [" + userDTO.getEmail() + "] already exists");
            } else {
                this.validateContactDetails(userDTO);
            }
        }
    }

    private void validateContactDetails(ApplicationUserDTO userDTO) {
    }

    public String deleteSystemUser(Long userId) throws AppUserServiceException {
        this.logger.debug("Deleting  system user with Id [{}]", userId);
        return this.deleteUser(userId);
    }

    public String deleteBranchUser(Long userId) throws AppUserServiceException {
        this.logger.debug("Deleting branch user with Id [{}]", userId);
        return this.deleteUser(userId);
    }

    private String deleteUser(Long userId) {
        try {
            this.logger.warn("Deleted user with Id [{}]", userId);
            return this.messageSource.getMessage("user.delete.success", (Object[])null, this.locale);
        } catch (Exception var3) {
            this.logger.error(var3.getMessage(), var3);
            throw new AppUserServiceException(this.messageSource.getMessage("user.delete.failure", (Object[])null, this.locale), var3);
        }
    }

    public String enableSystemUser(Long userId) throws AppUserServiceException {
        this.logger.debug("Enabling system user [{}]", userId);
        return this.enableUser(userId);
    }

    public String enableBranchUser(Long userId) throws AppUserServiceException {
        this.logger.debug("Enabling branch user [{}]", userId);
        return this.enableUser(userId);
    }

    private String enableUser(Long userId) {
        try {
            SystemUser systemUser = (SystemUser)this.applicationUserRepository.findById(userId).orElse(null);
            systemUser.setEnabled(true);
            this.applicationUserRepository.save(systemUser);
            return this.messageSource.getMessage("user.enable.success", (Object[])null, this.locale);
        } catch (Exception var3) {
            this.logger.error(var3.getMessage(), var3);
            throw new AppUserServiceException(this.messageSource.getMessage("user.enable.failure", (Object[])null, this.locale), var3);
        }
    }

    public String disableSystemUser(Long userId) throws AppUserServiceException {
        this.logger.debug("Disabling system user [{}]", userId);
        return this.disableUser(userId);
    }

    public String disableBranchUser(Long userId) throws AppUserServiceException {
        this.logger.debug("Disabling branch user [{}]", userId);
        return this.disableUser(userId);
    }

    private String disableUser(Long userId) {
        try {
            SystemUser systemUser = (SystemUser)this.applicationUserRepository.findById(userId).orElse(null);
            systemUser.setEnabled(false);
            this.applicationUserRepository.save(systemUser);
            return this.messageSource.getMessage("user.disable.success", (Object[])null, this.locale);
        } catch (Exception var3) {
            this.logger.error(var3.getMessage(), var3);
            throw new AppUserServiceException(this.messageSource.getMessage("user.disable.failure", (Object[])null, this.locale), var3);
        }
    }

    public String resetPassword(Long userId) throws AppUserServiceException {
        if (this.settingService.isSettingAvailable("USER_PASSWORD_RESET")) {
            try {
                String password = "";
                SystemUser systemUser = (SystemUser)this.applicationUserRepository.findById(userId).orElse(null);
                systemUser.setEnabled(true);
                systemUser.setChangePassword(true);
                systemUser.setPassword(this.passwordEncoder.encode(password));
                this.applicationUserRepository.save(systemUser);
                if (this.settingService.isSettingAvailable("USER_PASSWORD_RESET_SEND_EMAIL")) {
                }

                if (this.settingService.isSettingAvailable("USER_PASSWORD_RESET_SEND_SMS")) {
                }

                return this.messageSource.getMessage("user.password.reset.success", (Object[])null, this.locale);
            } catch (Exception var4) {
                this.logger.error(var4.getMessage(), var4);
                throw new AppUserServiceException(this.messageSource.getMessage("user.password.reset.failure", (Object[])null, this.locale), var4);
            }
        } else {
            throw new AppUserServiceException(this.messageSource.getMessage("user.password.reset.disabled", (Object[])null, this.locale));
        }
    }

    public String updatePassword(UpdatePasswordDTO updatePasswordDTO) throws AppUserServiceException {
        if (this.settingService.isSettingAvailable("USER_PASSWORD_UPDATE")) {
            try {
                SystemUser systemUser = (SystemUser)this.applicationUserRepository.findById(updatePasswordDTO.getId()).orElse(null);
                if (!this.passwordEncoder.matches(updatePasswordDTO.getOldPassword(), systemUser.getPassword())) {
                    this.logger.info("old password does not match");
                    throw new AppUserServiceException("Password does not match");
                } else {
                    systemUser.setPassword(this.passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
                    systemUser.setEnabled(true);
                    systemUser.setChangePassword(false);
                    this.applicationUserRepository.save(systemUser);
                    if (this.settingService.isSettingAvailable("USER_PASSWORD_UPDATE_SEND_EMAIL")) {
                    }

                    if (this.settingService.isSettingAvailable("USER_PASSWORD_UPDATE_SEND_SMS")) {
                    }

                    return this.messageSource.getMessage("user.password.update.success", (Object[])null, this.locale);
                }
            } catch (Exception var3) {
                this.logger.error(var3.getMessage(), var3);
                throw new AppUserServiceException(this.messageSource.getMessage("user.update.reset.failure", (Object[])null, this.locale), var3);
            }
        } else {
            throw new AppUserServiceException(this.messageSource.getMessage("user.password.update.disabled", (Object[])null, this.locale));
        }
    }

    public void loginUser(Long userId) throws AppUserServiceException {
        try {
            SystemUser systemUser = (SystemUser)this.applicationUserRepository.findById(userId).get();
            systemUser.setLastLoginDate(new Date());
            systemUser.setLoggedOn(true);
            this.applicationUserRepository.save(systemUser);
        } catch (Exception var3) {
            this.logger.error(var3.getMessage(), var3);
            throw new AppUserServiceException(this.messageSource.getMessage("user.logon.record.failure", (Object[])null, this.locale), var3);
        }
    }

    public void logoutUser(Long userId) throws AppUserServiceException {
        try {
            SystemUser systemUser = (SystemUser)this.applicationUserRepository.findById(userId).get();
            systemUser.setLoggedOn(false);
            this.applicationUserRepository.save(systemUser);
        } catch (Exception var3) {
            this.logger.error(var3.getMessage(), var3);
            throw new AppUserServiceException(this.messageSource.getMessage("user.logout.record.failure", (Object[])null, this.locale), var3);
        }
    }

    public ApplicationUserDTO findByUsername(String username) throws AppUserServiceException {
        try {
            SystemUser systemUser = this.applicationUserRepository.findByUserName(username);
            return this.convertEntityToDto(systemUser);
        } catch (Exception var3) {
            this.logger.error(var3.getMessage(), var3);
            throw new AppUserServiceException(this.messageSource.getMessage("user.find.failure", (Object[])null, this.locale), var3);
        }
    }

    public SystemUser findUserByUsername(String username) throws AppUserServiceException {
        this.logger.debug("Retrieving user [{}]", username);

        try {
            SystemUser systemUser = this.applicationUserRepository.findByUserName(username);
            return systemUser;
        } catch (Exception var3) {
            this.logger.error(var3.getMessage(), var3);
            throw new AppUserServiceException(this.messageSource.getMessage("user.find.failure", (Object[])null, this.locale), var3);
        }
    }

    public ApplicationUserDTO findByEmail(String email) throws AppUserServiceException {
        try {
            SystemUser systemUser = this.applicationUserRepository.findByEmail(email);
            return this.convertEntityToDto(systemUser);
        } catch (Exception var3) {
            this.logger.error(var3.getMessage(), var3);
            throw new AppUserServiceException(this.messageSource.getMessage("user.find.failure", (Object[])null, this.locale), var3);
        }
    }

    public ApplicationUserDTO convertEntityToDto(SystemUser systemUser) {
        ApplicationUserDTO applicationUserDTO = (ApplicationUserDTO)this.modelMapper.map(systemUser, ApplicationUserDTO.class);
        if (Objects.nonNull(systemUser.getLastLoginDate())) {
        }

        applicationUserDTO.setFullName(systemUser.getFirstName() + " " + systemUser.getLastName());
        Role userRole = systemUser.getRole();
        applicationUserDTO.setRoleId(userRole.getId());
        applicationUserDTO.setRoleName(userRole.getName());
        applicationUserDTO.setRoleType(userRole.getRoleType().name());
        return applicationUserDTO;
    }

    public SystemUser convertDtoToEntity(ApplicationUserDTO applicationUserDTO) {
        SystemUser systemUser = (SystemUser)this.modelMapper.map(applicationUserDTO, SystemUser.class);
        if (applicationUserDTO.getBranchId() != null) {
        }

        return systemUser;
    }

    private List<ApplicationUserDTO> convertEntitiesToDtos(List<SystemUser> systemUsers) {
        return (List)systemUsers.stream().map((applicationUser) -> {
            return this.convertEntityToDto(applicationUser);
        }).collect(Collectors.toList());
    }

    public Page<ApplicationUserDTO> getSystemUsers(Pageable pageable) {
        Page<SystemUser> users = this.applicationUserRepository.findByRole_RoleType(RoleType.SYSTEM, pageable);
        return new PageImpl(this.convertEntitiesToDtos(users.getContent()), pageable, users.getTotalElements());
    }

    public Page<ApplicationUserDTO> getBranchUsers(Pageable pageable) {
        Page<SystemUser> users = this.applicationUserRepository.findByRole_RoleType(RoleType.BRANCH, pageable);
        return new PageImpl(this.convertEntitiesToDtos(users.getContent()), pageable, users.getTotalElements());
    }

    public ApplicationUserDTO getUser(Long userId) {
        this.logger.debug("Retrieving user [{}]", userId);
        SystemUser systemUser = (SystemUser)this.applicationUserRepository.findById(userId).orElse(null);
        return this.convertEntityToDto(systemUser);
    }
}
