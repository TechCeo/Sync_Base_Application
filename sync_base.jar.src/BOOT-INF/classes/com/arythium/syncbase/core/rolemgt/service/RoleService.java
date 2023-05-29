package BOOT-INF.classes.com.arythium.syncbase.core.rolemgt.service;

import com.arythium.syncbase.core.exceptions.SyncBaseException;
import com.arythium.syncbase.core.rolemgt.dtos.PermissionDTO;
import com.arythium.syncbase.core.rolemgt.dtos.RoleDTO;
import com.arythium.syncbase.core.rolemgt.models.Permission;
import com.arythium.syncbase.core.rolemgt.models.Role;
import com.arythium.syncbase.core.usermgt.model.SystemUser;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface RoleService {
  @PreAuthorize("hasAuthority('ADD_ROLE')")
  String addRole(RoleDTO paramRoleDTO) throws SyncBaseException;
  
  Role createRole(RoleDTO paramRoleDTO);
  
  @PreAuthorize("hasAuthority('GET_ROLE')")
  RoleDTO getRole(Long paramLong);
  
  Role getRole(String paramString);
  
  List<RoleDTO> getRoles();
  
  List<RoleDTO> getSystemRoles();
  
  List<RoleDTO> getBranchRoles();
  
  @PreAuthorize("hasAuthority('UPDATE_ROLE')")
  String updateRole(RoleDTO paramRoleDTO) throws SyncBaseException;
  
  @PreAuthorize("hasAuthority('DELETE_ROLE')")
  String deleteRole(Long paramLong) throws SyncBaseException;
  
  String addPermission(PermissionDTO paramPermissionDTO) throws SyncBaseException;
  
  boolean permissionExists(String paramString);
  
  @PreAuthorize("hasAuthority('GET_PERMISSION')")
  PermissionDTO getPermission(Long paramLong);
  
  Collection<PermissionDTO> getPermissions();
  
  List<Permission> getAllPermissions();
  
  @PreAuthorize("hasAuthority('VIEW_ROLES')")
  Page<RoleDTO> getRoles(Pageable paramPageable);
  
  @PreAuthorize("hasAuthority('VIEW_PERMISSIONS')")
  Page<PermissionDTO> getPermissions(Pageable paramPageable);
  
  @PreAuthorize("hasAuthority('UPDATE_PERMISSION')")
  String updatePermission(PermissionDTO paramPermissionDTO) throws SyncBaseException;
  
  @PreAuthorize("hasAuthority('DELETE_PERMISSION')")
  String deletePermission(Long paramLong) throws SyncBaseException;
  
  Iterable<PermissionDTO> getPermissionsNotInRole(RoleDTO paramRoleDTO);
  
  @PreAuthorize("hasAuthority('VIEW_ROLE_USERS')")
  Page<SystemUser> getPersons(RoleDTO paramRoleDTO, Pageable paramPageable);
  
  @PreAuthorize("hasAuthority('VIEW_ROLES')")
  Page<RoleDTO> findRoles(String paramString, Pageable paramPageable);
  
  @PreAuthorize("hasAuthority('VIEW_PERMISSIONS')")
  Page<PermissionDTO> findPermissions(String paramString, Pageable paramPageable);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\rolemgt\service\RoleService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */