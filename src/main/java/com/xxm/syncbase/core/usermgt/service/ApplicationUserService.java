package com.xxm.syncbase.core.usermgt.service;

import com.xxm.syncbase.core.usermgt.dto.ApplicationUserDTO;
import com.xxm.syncbase.core.usermgt.dto.UpdatePasswordDTO;
import com.xxm.syncbase.core.usermgt.exception.AppUserServiceException;
import com.xxm.syncbase.core.usermgt.model.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ApplicationUserService {
  @PreAuthorize("hasAuthority('CREATE_SYSTEM_USER')")
  String createSystemUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException;

  void validateNewUser(ApplicationUserDTO userDTO);

  @PreAuthorize("hasAuthority('CREATE_BRANCH_USER')")
  String createBranchUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException;

  @PreAuthorize("hasAuthority('UPDATE_SYSTEM_USER')")
  String updateSystemUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException;

  void validateExistingUser(ApplicationUserDTO userDTO);

  @PreAuthorize("hasAuthority('UPDATE_BRANCH_USER')")
  String updateBranchUser(ApplicationUserDTO applicationUserDTO) throws AppUserServiceException;

  @PreAuthorize("hasAuthority('DELETE_SYSTEM_USER')")
  String deleteSystemUser(Long userId) throws AppUserServiceException;

  @PreAuthorize("hasAuthority('DELETE_BRANCH_USER')")
  String deleteBranchUser(Long userId) throws AppUserServiceException;

  @PreAuthorize("hasAuthority('ENABLE_SYSTEM_USER')")
  String enableSystemUser(Long userId) throws AppUserServiceException;

  @PreAuthorize("hasAuthority('ENABLE_BRANCH_USER')")
  String enableBranchUser(Long userId) throws AppUserServiceException;

  @PreAuthorize("hasAuthority('DISABLE_SYSTEM_USER')")
  String disableSystemUser(Long userId) throws AppUserServiceException;

  @PreAuthorize("hasAuthority('DISABLE_BRANCH_USER')")
  String disableBranchUser(Long userId) throws AppUserServiceException;

  String resetPassword(Long userId) throws AppUserServiceException;

  String updatePassword(UpdatePasswordDTO updatePasswordDTO) throws AppUserServiceException;

  void loginUser(Long userId) throws AppUserServiceException;

  void logoutUser(Long userId) throws AppUserServiceException;

  ApplicationUserDTO findByUsername(String username) throws AppUserServiceException;

  SystemUser findUserByUsername(String username) throws AppUserServiceException;

  @PreAuthorize("hasAuthority('VIEW_SYSTEM_USERS')")
  Page<ApplicationUserDTO> getSystemUsers(Pageable pageable);

  @PreAuthorize("hasAuthority('VIEW_BRANCH_USERS')")
  Page<ApplicationUserDTO> getBranchUsers(Pageable pageable);

  @PreAuthorize("hasAuthority('GET_USER')")
  ApplicationUserDTO getUser(Long userId);

  ApplicationUserDTO findByEmail(String email) throws AppUserServiceException;

  SystemUser convertDtoToEntity(ApplicationUserDTO applicationUserDTO);

  ApplicationUserDTO convertEntityToDto(SystemUser systemUser);
}
