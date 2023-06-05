package com.xxm.syncbase.core.rolemgt.service.implementation;

import com.xxm.syncbase.core.exceptions.SyncBaseException;
import com.xxm.syncbase.core.rolemgt.dtos.PermissionDTO;
import com.xxm.syncbase.core.rolemgt.dtos.RoleDTO;
import com.xxm.syncbase.core.rolemgt.models.Permission;
import com.xxm.syncbase.core.rolemgt.models.PermissionType;
import com.xxm.syncbase.core.rolemgt.models.Role;
import com.xxm.syncbase.core.rolemgt.models.RoleType;
import com.xxm.syncbase.core.rolemgt.repositories.PermissionRepository;
import com.xxm.syncbase.core.rolemgt.repositories.RoleRepository;
import com.xxm.syncbase.core.rolemgt.service.RoleService;
import com.xxm.syncbase.core.usermgt.model.SystemUser;
import com.xxm.syncbase.core.usermgt.repository.ApplicationUserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private PermissionRepository permissionRepo;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private ApplicationUserRepository userRepo;
    private ModelMapper modelMapper = new ModelMapper();
    private Locale locale = LocaleContextHolder.getLocale();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RoleServiceImpl() {
    }

    public String addRole(RoleDTO roleDTO) throws SyncBaseException {
        Role role = this.roleRepo.findFirstByNameIgnoreCase(roleDTO.getName());
        if (role != null) {
            throw new SyncBaseException(this.messageSource.getMessage("role.exist", (Object[])null, this.locale));
        } else {
            try {
                this.logger.info("Added role before save call");
                role = this.convertDTOToEntity(roleDTO);
                this.roleRepo.save(role);
                this.logger.info("Added role {}", role.toString());
                return this.messageSource.getMessage("role.add.success", (Object[])null, this.locale);
            } catch (SyncBaseException var4) {
                throw new SyncBaseException(this.messageSource.getMessage("role.add.failure", (Object[])null, this.locale), var4);
            }
        }
    }

    @Transactional
    public Role createRole(RoleDTO roleDTO) {
        Role role = this.roleRepo.findFirstByNameIgnoreCase(roleDTO.getName());
        if (role != null) {
            throw new SyncBaseException(this.messageSource.getMessage("role.exist", (Object[])null, this.locale));
        } else {
            try {
                role = this.convertDTOToEntity(roleDTO);
                this.roleRepo.save(role);
                this.logger.info("Added role {}", role.toString());
                return role;
            } catch (SyncBaseException var4) {
                throw new SyncBaseException(this.messageSource.getMessage("role.add.failure", (Object[])null, this.locale), var4);
            }
        }
    }

    public RoleDTO getRole(Long id) {
        Role role = null;
        return this.convertEntityToDTO((Role)role);
    }

    public Role getRole(String roleName) {
        Role role = this.roleRepo.findFirstByNameIgnoreCase(roleName);
        return role;
    }

    public List<RoleDTO> getRoles() {
        List<Role> roles = new ArrayList();
        Iterator var2 = this.roleRepo.findAll().iterator();

        while(var2.hasNext()) {
            Role r = (Role)var2.next();
            roles.add(r);
        }

        return this.convertRoleEntitiesToDTOs(roles);
    }

    public String updateRole(RoleDTO roleDTO) throws SyncBaseException {
        Role role = this.roleRepo.findByNameAndRoleType(roleDTO.getName(), RoleType.valueOf(roleDTO.getType()));
        if (role != null && !roleDTO.getId().equals(role.getId())) {
            throw new SyncBaseException(this.messageSource.getMessage("role.exists", (Object[])null, this.locale));
        } else {
            try {
                role = this.convertDTOToEntity(roleDTO);
                this.roleRepo.save(role);
                return this.messageSource.getMessage("role.update.success", (Object[])null, this.locale);
            } catch (Exception var4) {
                throw new SyncBaseException(this.messageSource.getMessage("role.update.failure", (Object[])null, this.locale), var4);
            }
        }
    }

    public String deleteRole(Long id) throws SyncBaseException {
        Role role = null;
        Integer users = this.countUsers((Role)role);
        if (users > 0) {
            throw new SyncBaseException(this.messageSource.getMessage("role.delete.users.exist", (Object[])null, this.locale));
        } else {
            try {
                ((Role)role).setDelFlag("Y");
                this.roleRepo.delete(role);
                return this.messageSource.getMessage("role.delete.success", (Object[])null, this.locale);
            } catch (SyncBaseException var5) {
                throw new SyncBaseException(this.messageSource.getMessage("role.delete.failure", (Object[])null, this.locale), var5);
            }
        }
    }

    public String addPermission(PermissionDTO permissionDTO) throws SyncBaseException {
        try {
            Permission permission = this.convertDTOToEntity(permissionDTO);
            this.permissionRepo.save(permission);
            this.logger.info("Added permission {}", permission.toString());
            return this.messageSource.getMessage("permission.add.success", (Object[])null, this.locale);
        } catch (Exception var3) {
            throw new SyncBaseException(this.messageSource.getMessage("permission.add.failure", (Object[])null, this.locale), var3);
        }
    }

    public boolean permissionExists(String name) {
        return this.permissionRepo.existsByName(name);
    }

    public PermissionDTO getPermission(Long id) {
        Permission permission = this.permissionRepo.findById(id).orElse(null);
        return this.convertEntityToDTO(permission);
    }

    public Collection<PermissionDTO> getPermissions() {
        Iterable<Permission> permissions = this.permissionRepo.findAll();
        return this.convertPermissionEntitiesToDTOs(permissions);
    }

    public List<Permission> getAllPermissions() {
        Iterable<Permission> permissions = this.permissionRepo.findAll();
        return (List<Permission>) permissions;
    }

    public Page<RoleDTO> getRoles(Pageable pageDetails) {
        Page<Role> page = this.roleRepo.findAll(pageDetails);
        this.logger.debug("Total roles: {}", page.getTotalElements());
        List<RoleDTO> dtOs = this.convertRoleEntitiesToDTOs(page.getContent());
        long t = page.getTotalElements();
        Page<RoleDTO> pageImpl = new PageImpl(dtOs, pageDetails, t);
        return pageImpl;
    }

    public List<RoleDTO> getSystemRoles() {
        List<Role> roles = this.roleRepo.findByRoleType(RoleType.SYSTEM);
        return this.convertRoleEntitiesToDTOs(roles);
    }

    public List<RoleDTO> getBranchRoles() {
        List<Role> roles = this.roleRepo.findByRoleType(RoleType.BRANCH);
        return this.convertRoleEntitiesToDTOs(roles);
    }

    public Page<PermissionDTO> getPermissions(Pageable pageDetails) {
        Page<Permission> page = this.permissionRepo.findAll(pageDetails);
        List<PermissionDTO> dtOs = this.convertPermissionEntitiesToDTOs(page.getContent());
        long t = page.getTotalElements();
        Page<PermissionDTO> pageImpl = new PageImpl(dtOs, pageDetails, t);
        return pageImpl;
    }

    public String updatePermission(PermissionDTO permissionDTO) throws SyncBaseException {
        try {
            Permission permission = this.permissionRepo.findById(permissionDTO.getId()).orElseThrow(()-> new RuntimeException());
            permission.setVersion(permissionDTO.getVersion());
            permission.setName(permissionDTO.getName());
            permission.setDescription(permissionDTO.getDescription());
            permission.setPermissionType(PermissionType.valueOf(permissionDTO.getType()));
            this.permissionRepo.save(permission);
            this.logger.info("Updated permission {}", permission.toString());
            return this.messageSource.getMessage("permission.update.success", null, this.locale);
        } catch (SyncBaseException var3) {
            throw var3;
        } catch (Exception var4) {
            throw new SyncBaseException(this.messageSource.getMessage("permission.add.failure", (Object[])null, this.locale), var4);
        }
    }

    public String deletePermission(Long id) throws SyncBaseException {
        try {
            Permission permission = (Permission)this.permissionRepo.findById(id).orElse(null);
            this.permissionRepo.delete(permission);
            this.logger.warn("Deleted permission with Id {}", id);
            return this.messageSource.getMessage("permission.delete.success",null, this.locale);
        } catch (SyncBaseException var3) {
            throw var3;
        } catch (Exception var4) {
            throw new SyncBaseException(this.messageSource.getMessage("permission.delete.failure", null, this.locale), var4);
        }
    }

    public RoleDTO convertEntityToDTO(Role role) {
        RoleDTO roleDTO = (RoleDTO)this.modelMapper.map(role, RoleDTO.class);
        roleDTO.setType(role.getRoleType().toString());
        return (RoleDTO)this.modelMapper.map(role, RoleDTO.class);
    }

    private Role convertDTOToEntity(RoleDTO roleDTO) {
        Role role = (Role)this.modelMapper.map(roleDTO, Role.class);
        role.setRoleType(RoleType.valueOf(roleDTO.getType()));
        role.setPermissions(this.convertPermissionDTOsToEntities(roleDTO.getPermissions()));
        return role;
    }

    private List<RoleDTO> convertRoleEntitiesToDTOs(List<Role> roles) {
        List<RoleDTO> roleDTOList = new ArrayList();
        Iterator var3 = roles.iterator();

        while(var3.hasNext()) {
            Role role = (Role)var3.next();
            RoleDTO roleDTO = (RoleDTO)this.modelMapper.map(role, RoleDTO.class);
            roleDTOList.add(roleDTO);
        }

        return roleDTOList;
    }

    private PermissionDTO convertEntityToDTO(Permission permission) {
        PermissionDTO permissionDTO = (PermissionDTO)this.modelMapper.map(permission, PermissionDTO.class);
        permissionDTO.setType(permission.getPermissionType().toString());
        return permissionDTO;
    }

    private Permission convertDTOToEntity(PermissionDTO permissionDTO) {
        Permission permission;
        if (permissionDTO.getId() == null) {
            permission = (Permission)this.modelMapper.map(permissionDTO, Permission.class);
            permission.setPermissionType(PermissionType.valueOf(permissionDTO.getType()));
        } else {
            permission = (Permission)this.permissionRepo.findById(permissionDTO.getId()).orElse(null);
            if (permissionDTO.getName() != null) {
                permission.setName(permissionDTO.getName());
            }

            if (permissionDTO.getDescription() != null) {
                permission.setDescription(permissionDTO.getDescription());
            }

            if (permissionDTO.getType() != null) {
                permission.setPermissionType(PermissionType.valueOf(permissionDTO.getType()));
            }
        }

        return permission;
    }

    private List<PermissionDTO> convertPermissionEntitiesToDTOs(Iterable<Permission> permissions) {
        List<PermissionDTO> permissionDTOList = new ArrayList();
        Iterator var3 = permissions.iterator();

        while(var3.hasNext()) {
            Permission permission = (Permission)var3.next();
            PermissionDTO permissionDTO = this.convertEntityToDTO(permission);
            permissionDTOList.add(permissionDTO);
        }

        return permissionDTOList;
    }

    private List<Permission> convertPermissionDTOsToEntities(Iterable<PermissionDTO> permissionDTOs) {
        List<Permission> permissions = new ArrayList();
        Iterator var3 = permissionDTOs.iterator();

        while(var3.hasNext()) {
            PermissionDTO permissionDTO = (PermissionDTO)var3.next();
            Permission permission = this.convertDTOToEntity(permissionDTO);
            permissions.add(permission);
        }

        return permissions;
    }

    public Iterable<PermissionDTO> getPermissionsNotInRole(RoleDTO role) {
        Long[] permissionArray = new Long[role.getPermissions().size()];
        int idx = 0;

        for(Iterator var4 = role.getPermissions().iterator(); var4.hasNext(); ++idx) {
            PermissionDTO perm = (PermissionDTO)var4.next();
            permissionArray[idx] = perm.getId();
        }

        if (permissionArray.length == 0) {
            permissionArray = new Long[]{-1L};
        }

        Iterable<Permission> permissionsNotInRole = this.permissionRepo.findByIdNotIn(permissionArray);
        this.logger.debug("Permissions not in role: " + permissionsNotInRole);
        return this.convertPermissionEntitiesToDTOs(permissionsNotInRole);
    }

    public Page<SystemUser> getPersons(RoleDTO roledto, Pageable pageDetails) {
        Role role = (Role)this.roleRepo.findById(roledto.getId()).orElseThrow(()-> new RuntimeException());
        Page<SystemUser> pageImpl = null;
        Page<SystemUser> users = this.userRepo.findByRole(role, pageDetails);
        long elements = users.getTotalElements();
        List<SystemUser> userList = users.getContent();
        pageImpl = new PageImpl(userList, pageDetails, elements);
        return pageImpl;
    }

    private Integer countUsers(Role role) {
        Integer cnt = 0;
        cnt = this.userRepo.countByRole(role);
        return cnt;
    }

    public Page<RoleDTO> findRoles(String pattern, Pageable pageDetails) {
        Page<Role> page = null;
        List<RoleDTO> dtOs = this.convertRoleEntitiesToDTOs(((Page)page).getContent());
        long t = ((Page)page).getTotalElements();
        Page<RoleDTO> pageImpl = new PageImpl(dtOs, pageDetails, t);
        return pageImpl;
    }

    public Page<PermissionDTO> findPermissions(String pattern, Pageable pageDetails) {
        Page<Permission> page = null;
        List<PermissionDTO> dtOs = this.convertPermissionEntitiesToDTOs(((Page)page).getContent());
        long t = ((Page)page).getTotalElements();
        Page<PermissionDTO> pageImpl = new PageImpl(dtOs, pageDetails, t);
        return pageImpl;
    }
}