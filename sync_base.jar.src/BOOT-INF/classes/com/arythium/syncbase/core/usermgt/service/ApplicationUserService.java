package BOOT-INF.classes.com.arythium.syncbase.core.usermgt.service;

import com.arythium.syncbase.core.usermgt.dto.ApplicationUserDTO;
import com.arythium.syncbase.core.usermgt.dto.UpdatePasswordDTO;
import com.arythium.syncbase.core.usermgt.exception.AppUserServiceException;
import com.arythium.syncbase.core.usermgt.model.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ApplicationUserService {
  @PreAuthorize("hasAuthority('CREATE_SYSTEM_USER')")
  String createSystemUser(ApplicationUserDTO paramApplicationUserDTO) throws AppUserServiceException;
  
  void validateNewUser(ApplicationUserDTO paramApplicationUserDTO);
  
  @PreAuthorize("hasAuthority('CREATE_BRANCH_USER')")
  String createBranchUser(ApplicationUserDTO paramApplicationUserDTO) throws AppUserServiceException;
  
  @PreAuthorize("hasAuthority('UPDATE_SYSTEM_USER')")
  String updateSystemUser(ApplicationUserDTO paramApplicationUserDTO) throws AppUserServiceException;
  
  void validateExistingUser(ApplicationUserDTO paramApplicationUserDTO);
  
  @PreAuthorize("hasAuthority('UPDATE_BRANCH_USER')")
  String updateBranchUser(ApplicationUserDTO paramApplicationUserDTO) throws AppUserServiceException;
  
  @PreAuthorize("hasAuthority('DELETE_SYSTEM_USER')")
  String deleteSystemUser(Long paramLong) throws AppUserServiceException;
  
  @PreAuthorize("hasAuthority('DELETE_BRANCH_USER')")
  String deleteBranchUser(Long paramLong) throws AppUserServiceException;
  
  @PreAuthorize("hasAuthority('ENABLE_SYSTEM_USER')")
  String enableSystemUser(Long paramLong) throws AppUserServiceException;
  
  @PreAuthorize("hasAuthority('ENABLE_BRANCH_USER')")
  String enableBranchUser(Long paramLong) throws AppUserServiceException;
  
  @PreAuthorize("hasAuthority('DISABLE_SYSTEM_USER')")
  String disableSystemUser(Long paramLong) throws AppUserServiceException;
  
  @PreAuthorize("hasAuthority('DISABLE_BRANCH_USER')")
  String disableBranchUser(Long paramLong) throws AppUserServiceException;
  
  String resetPassword(Long paramLong) throws AppUserServiceException;
  
  String updatePassword(UpdatePasswordDTO paramUpdatePasswordDTO) throws AppUserServiceException;
  
  void loginUser(Long paramLong) throws AppUserServiceException;
  
  void logoutUser(Long paramLong) throws AppUserServiceException;
  
  ApplicationUserDTO findByUsername(String paramString) throws AppUserServiceException;
  
  SystemUser findUserByUsername(String paramString) throws AppUserServiceException;
  
  @PreAuthorize("hasAuthority('VIEW_SYSTEM_USERS')")
  Page<ApplicationUserDTO> getSystemUsers(Pageable paramPageable);
  
  @PreAuthorize("hasAuthority('VIEW_BRANCH_USERS')")
  Page<ApplicationUserDTO> getBranchUsers(Pageable paramPageable);
  
  @PreAuthorize("hasAuthority('GET_USER')")
  ApplicationUserDTO getUser(Long paramLong);
  
  ApplicationUserDTO findByEmail(String paramString) throws AppUserServiceException;
  
  SystemUser convertDtoToEntity(ApplicationUserDTO paramApplicationUserDTO);
  
  ApplicationUserDTO convertEntityToDto(SystemUser paramSystemUser);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\cor\\usermgt\service\ApplicationUserService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */