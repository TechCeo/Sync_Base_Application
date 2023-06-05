package com.xxm.syncbase.core.rolemgt.service;


import com.xxm.syncbase.core.exceptions.SyncBaseException;
import com.xxm.syncbase.core.rolemgt.dtos.PermissionDTO;
import com.xxm.syncbase.core.rolemgt.dtos.RoleDTO;
import com.xxm.syncbase.core.rolemgt.models.Permission;
import com.xxm.syncbase.core.rolemgt.models.Role;
import com.xxm.syncbase.core.usermgt.model.SystemUser;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface RoleService {
  @PreAuthorize("hasAuthority('ADD_ROLE')")
  String addRole(RoleDTO roleDTO) throws SyncBaseException;

  Role createRole(RoleDTO roleDTO);

  @PreAuthorize("hasAuthority('GET_ROLE')")
  RoleDTO getRole(Long id);

  Role getRole(String roleName);

  List<RoleDTO> getRoles();

  List<RoleDTO> getSystemRoles();

  List<RoleDTO> getBranchRoles();

  @PreAuthorize("hasAuthority('UPDATE_ROLE')")
  String updateRole(RoleDTO roleDTO) throws SyncBaseException;

  @PreAuthorize("hasAuthority('DELETE_ROLE')")
  String deleteRole(Long id) throws SyncBaseException;

  String addPermission(PermissionDTO permissionDTO) throws SyncBaseException;

  boolean permissionExists(String code);

  @PreAuthorize("hasAuthority('GET_PERMISSION')")
  PermissionDTO getPermission(Long id);

  Collection<PermissionDTO> getPermissions();

  List<Permission> getAllPermissions();

  @PreAuthorize("hasAuthority('VIEW_ROLES')")
  Page<RoleDTO> getRoles(Pageable pageDetails);

  @PreAuthorize("hasAuthority('VIEW_PERMISSIONS')")
  Page<PermissionDTO> getPermissions(Pageable pageDetails);

  @PreAuthorize("hasAuthority('UPDATE_PERMISSION')")
  String updatePermission(PermissionDTO permissionDTO) throws SyncBaseException;

  @PreAuthorize("hasAuthority('DELETE_PERMISSION')")
  String deletePermission(Long id) throws SyncBaseException;

  Iterable<PermissionDTO> getPermissionsNotInRole(RoleDTO role);

  @PreAuthorize("hasAuthority('VIEW_ROLE_USERS')")
  Page<SystemUser> getPersons(RoleDTO roledto, Pageable pageDetails);

  @PreAuthorize("hasAuthority('VIEW_ROLES')")
  Page<RoleDTO> findRoles(String pattern, Pageable pageDetails);

  @PreAuthorize("hasAuthority('VIEW_PERMISSIONS')")
  Page<PermissionDTO> findPermissions(String pattern, Pageable pageDetails);
}
