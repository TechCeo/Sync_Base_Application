/*     */ package BOOT-INF.classes.com.arythium.syncbase.core.rolemgt.service.implementation;
/*     */ 
/*     */ import com.arythium.syncbase.core.exceptions.SyncBaseException;
/*     */ import com.arythium.syncbase.core.rolemgt.dtos.PermissionDTO;
/*     */ import com.arythium.syncbase.core.rolemgt.dtos.RoleDTO;
/*     */ import com.arythium.syncbase.core.rolemgt.models.Permission;
/*     */ import com.arythium.syncbase.core.rolemgt.models.PermissionType;
/*     */ import com.arythium.syncbase.core.rolemgt.models.Role;
/*     */ import com.arythium.syncbase.core.rolemgt.models.RoleType;
/*     */ import com.arythium.syncbase.core.rolemgt.repositories.PermissionRepository;
/*     */ import com.arythium.syncbase.core.rolemgt.repositories.RoleRepository;
/*     */ import com.arythium.syncbase.core.rolemgt.service.RoleService;
/*     */ import com.arythium.syncbase.core.usermgt.model.SystemUser;
/*     */ import com.arythium.syncbase.core.usermgt.repository.ApplicationUserRepository;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.MessageSource;
/*     */ import org.springframework.context.i18n.LocaleContextHolder;
/*     */ import org.springframework.data.domain.Page;
/*     */ import org.springframework.data.domain.PageImpl;
/*     */ import org.springframework.data.domain.Pageable;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class RoleServiceImpl
/*     */   implements RoleService
/*     */ {
/*     */   @Autowired
/*     */   private PermissionRepository permissionRepo;
/*     */   @Autowired
/*     */   private MessageSource messageSource;
/*     */   @Autowired
/*     */   private RoleRepository roleRepo;
/*     */   @Autowired
/*     */   private ApplicationUserRepository userRepo;
/*  50 */   private ModelMapper modelMapper = new ModelMapper();
/*     */   
/*  52 */   private Locale locale = LocaleContextHolder.getLocale();
/*     */   
/*  54 */   private Logger logger = LoggerFactory.getLogger(getClass());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String addRole(RoleDTO roleDTO) throws SyncBaseException {
/*  61 */     Role role = this.roleRepo.findFirstByNameIgnoreCase(roleDTO.getName());
/*     */     
/*  63 */     if (role != null) {
/*  64 */       throw new SyncBaseException(this.messageSource.getMessage("role.exist", null, this.locale));
/*     */     }
/*     */     
/*     */     try {
/*  68 */       this.logger.info("Added role before save call");
/*  69 */       role = convertDTOToEntity(roleDTO);
/*  70 */       this.roleRepo.save(role);
/*  71 */       this.logger.info("Added role {}", role.toString());
/*  72 */       return this.messageSource.getMessage("role.add.success", null, this.locale);
/*     */     }
/*  74 */     catch (SyncBaseException e) {
/*  75 */       throw new SyncBaseException(this.messageSource.getMessage("role.add.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public Role createRole(RoleDTO roleDTO) {
/*  82 */     Role role = this.roleRepo.findFirstByNameIgnoreCase(roleDTO.getName());
/*     */     
/*  84 */     if (role != null) {
/*  85 */       throw new SyncBaseException(this.messageSource.getMessage("role.exist", null, this.locale));
/*     */     }
/*     */     
/*     */     try {
/*  89 */       role = convertDTOToEntity(roleDTO);
/*  90 */       this.roleRepo.save(role);
/*  91 */       this.logger.info("Added role {}", role.toString());
/*  92 */       return role;
/*     */     }
/*  94 */     catch (SyncBaseException e) {
/*  95 */       throw new SyncBaseException(this.messageSource.getMessage("role.add.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RoleDTO getRole(Long id) {
/* 102 */     Role role = null;
/* 103 */     return convertEntityToDTO(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public Role getRole(String roleName) {
/* 108 */     Role role = this.roleRepo.findFirstByNameIgnoreCase(roleName);
/* 109 */     return role;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RoleDTO> getRoles() {
/* 115 */     List<Role> roles = new ArrayList<>();
/* 116 */     for (Role r : this.roleRepo.findAll()) {
/* 117 */       roles.add(r);
/*     */     }
/* 119 */     return convertRoleEntitiesToDTOs(roles);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String updateRole(RoleDTO roleDTO) throws SyncBaseException {
/* 125 */     Role role = this.roleRepo.findByNameAndRoleType(roleDTO.getName(), RoleType.valueOf(roleDTO.getType()));
/*     */     
/* 127 */     if (role != null && !roleDTO.getId().equals(role.getId())) {
/* 128 */       throw new SyncBaseException(this.messageSource.getMessage("role.exists", null, this.locale));
/*     */     }
/*     */     
/*     */     try {
/* 132 */       role = convertDTOToEntity(roleDTO);
/* 133 */       this.roleRepo.save(role);
/*     */       
/* 135 */       return this.messageSource.getMessage("role.update.success", null, this.locale);
/*     */     }
/* 137 */     catch (Exception e) {
/* 138 */       throw new SyncBaseException(this.messageSource.getMessage("role.update.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String deleteRole(Long id) throws SyncBaseException {
/* 146 */     Role role = null;
/* 147 */     Integer users = countUsers(role);
/* 148 */     if (users.intValue() > 0) {
/* 149 */       throw new SyncBaseException(this.messageSource.getMessage("role.delete.users.exist", null, this.locale));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 154 */       role.setDelFlag("Y");
/* 155 */       this.roleRepo.delete(role);
/* 156 */       return this.messageSource.getMessage("role.delete.success", null, this.locale);
/* 157 */     } catch (SyncBaseException e) {
/* 158 */       throw new SyncBaseException(this.messageSource.getMessage("role.delete.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String addPermission(PermissionDTO permissionDTO) throws SyncBaseException {
/*     */     try {
/* 166 */       Permission permission = convertDTOToEntity(permissionDTO);
/* 167 */       this.permissionRepo.save(permission);
/* 168 */       this.logger.info("Added permission {}", permission.toString());
/* 169 */       return this.messageSource.getMessage("permission.add.success", null, this.locale);
/*     */     }
/* 171 */     catch (Exception e) {
/* 172 */       throw new SyncBaseException(this.messageSource.getMessage("permission.add.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean permissionExists(String name) {
/* 178 */     return this.permissionRepo.existsByName(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public PermissionDTO getPermission(Long id) {
/* 183 */     Permission permission = this.permissionRepo.findById(id).orElse(null);
/* 184 */     return convertEntityToDTO(permission);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<PermissionDTO> getPermissions() {
/* 189 */     Iterable<Permission> permissions = this.permissionRepo.findAll();
/* 190 */     return convertPermissionEntitiesToDTOs(permissions);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Permission> getAllPermissions() {
/* 195 */     Iterable<Permission> permissions = this.permissionRepo.findAll();
/* 196 */     return Lists.newArrayList(permissions);
/*     */   }
/*     */ 
/*     */   
/*     */   public Page<RoleDTO> getRoles(Pageable pageDetails) {
/* 201 */     Page<Role> page = this.roleRepo.findAll(pageDetails);
/* 202 */     this.logger.debug("Total roles: {}", Long.valueOf(page.getTotalElements()));
/* 203 */     List<RoleDTO> dtOs = convertRoleEntitiesToDTOs(page.getContent());
/* 204 */     long t = page.getTotalElements();
/* 205 */     return (Page<RoleDTO>)new PageImpl(dtOs, pageDetails, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RoleDTO> getSystemRoles() {
/* 212 */     List<Role> roles = this.roleRepo.findByRoleType(RoleType.SYSTEM);
/* 213 */     return convertRoleEntitiesToDTOs(roles);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<RoleDTO> getBranchRoles() {
/* 218 */     List<Role> roles = this.roleRepo.findByRoleType(RoleType.BRANCH);
/* 219 */     return convertRoleEntitiesToDTOs(roles);
/*     */   }
/*     */ 
/*     */   
/*     */   public Page<PermissionDTO> getPermissions(Pageable pageDetails) {
/* 224 */     Page<Permission> page = this.permissionRepo.findAll(pageDetails);
/* 225 */     List<PermissionDTO> dtOs = convertPermissionEntitiesToDTOs(page.getContent());
/* 226 */     long t = page.getTotalElements();
/* 227 */     return (Page<PermissionDTO>)new PageImpl(dtOs, pageDetails, t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String updatePermission(PermissionDTO permissionDTO) throws SyncBaseException {
/*     */     try {
/* 234 */       Permission permission = this.permissionRepo.findById(permissionDTO.getId()).orElse(null);
/* 235 */       permission.setVersion(permissionDTO.getVersion());
/* 236 */       permission.setName(permissionDTO.getName());
/* 237 */       permission.setDescription(permissionDTO.getDescription());
/* 238 */       permission.setPermissionType(PermissionType.valueOf(permissionDTO.getType()));
/* 239 */       this.permissionRepo.save(permission);
/* 240 */       this.logger.info("Updated permission {}", permission.toString());
/* 241 */       return this.messageSource.getMessage("permission.update.success", null, this.locale);
/*     */     }
/* 243 */     catch (SyncBaseException e) {
/* 244 */       throw e;
/*     */     }
/* 246 */     catch (Exception e) {
/* 247 */       throw new SyncBaseException(this.messageSource.getMessage("permission.add.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String deletePermission(Long id) throws SyncBaseException {
/*     */     try {
/* 254 */       Permission permission = this.permissionRepo.findById(id).orElse(null);
/* 255 */       this.permissionRepo.delete(permission);
/* 256 */       this.logger.warn("Deleted permission with Id {}", id);
/* 257 */       return this.messageSource.getMessage("permission.delete.success", null, this.locale);
/*     */     }
/* 259 */     catch (SyncBaseException e) {
/* 260 */       throw e;
/*     */     }
/* 262 */     catch (Exception e) {
/* 263 */       throw new SyncBaseException(this.messageSource.getMessage("permission.delete.failure", null, this.locale), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public RoleDTO convertEntityToDTO(Role role) {
/* 268 */     RoleDTO roleDTO = (RoleDTO)this.modelMapper.map(role, RoleDTO.class);
/* 269 */     roleDTO.setType(role.getRoleType().toString());
/* 270 */     return (RoleDTO)this.modelMapper.map(role, RoleDTO.class);
/*     */   }
/*     */   
/*     */   private Role convertDTOToEntity(RoleDTO roleDTO) {
/* 274 */     Role role = (Role)this.modelMapper.map(roleDTO, Role.class);
/* 275 */     role.setRoleType(RoleType.valueOf(roleDTO.getType()));
/* 276 */     role.setPermissions(convertPermissionDTOsToEntities(roleDTO.getPermissions()));
/* 277 */     return role;
/*     */   }
/*     */   
/*     */   private List<RoleDTO> convertRoleEntitiesToDTOs(List<Role> roles) {
/* 281 */     List<RoleDTO> roleDTOList = new ArrayList<>();
/* 282 */     for (Role role : roles) {
/* 283 */       RoleDTO roleDTO = (RoleDTO)this.modelMapper.map(role, RoleDTO.class);
/* 284 */       roleDTOList.add(roleDTO);
/*     */     } 
/*     */     
/* 287 */     return roleDTOList;
/*     */   }
/*     */   
/*     */   private PermissionDTO convertEntityToDTO(Permission permission) {
/* 291 */     PermissionDTO permissionDTO = (PermissionDTO)this.modelMapper.map(permission, PermissionDTO.class);
/* 292 */     permissionDTO.setType(permission.getPermissionType().toString());
/* 293 */     return permissionDTO;
/*     */   }
/*     */   
/*     */   private Permission convertDTOToEntity(PermissionDTO permissionDTO) {
/*     */     Permission permission;
/* 298 */     if (permissionDTO.getId() == null) {
/* 299 */       permission = (Permission)this.modelMapper.map(permissionDTO, Permission.class);
/* 300 */       permission.setPermissionType(PermissionType.valueOf(permissionDTO.getType()));
/*     */     } else {
/*     */       
/* 303 */       permission = this.permissionRepo.findById(permissionDTO.getId()).orElse(null);
/* 304 */       if (permissionDTO.getName() != null) {
/* 305 */         permission.setName(permissionDTO.getName());
/*     */       }
/* 307 */       if (permissionDTO.getDescription() != null) {
/* 308 */         permission.setDescription(permissionDTO.getDescription());
/*     */       }
/* 310 */       if (permissionDTO.getType() != null) {
/* 311 */         permission.setPermissionType(PermissionType.valueOf(permissionDTO.getType()));
/*     */       }
/*     */     } 
/*     */     
/* 315 */     return permission;
/*     */   }
/*     */   
/*     */   private List<PermissionDTO> convertPermissionEntitiesToDTOs(Iterable<Permission> permissions) {
/* 319 */     List<PermissionDTO> permissionDTOList = new ArrayList<>();
/* 320 */     for (Permission permission : permissions) {
/* 321 */       PermissionDTO permissionDTO = convertEntityToDTO(permission);
/* 322 */       permissionDTOList.add(permissionDTO);
/*     */     } 
/* 324 */     return permissionDTOList;
/*     */   }
/*     */   
/*     */   private List<Permission> convertPermissionDTOsToEntities(Iterable<PermissionDTO> permissionDTOs) {
/* 328 */     List<Permission> permissions = new ArrayList<>();
/* 329 */     for (PermissionDTO permissionDTO : permissionDTOs) {
/* 330 */       Permission permission = convertDTOToEntity(permissionDTO);
/* 331 */       permissions.add(permission);
/*     */     } 
/* 333 */     return permissions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<PermissionDTO> getPermissionsNotInRole(RoleDTO role) {
/* 339 */     Long[] permissionArray = new Long[role.getPermissions().size()];
/* 340 */     int idx = 0;
/* 341 */     for (PermissionDTO perm : role.getPermissions()) {
/* 342 */       permissionArray[idx] = perm.getId();
/* 343 */       idx++;
/*     */     } 
/*     */     
/* 346 */     if (permissionArray.length == 0)
/* 347 */       permissionArray = new Long[] { Long.valueOf(-1L) }; 
/* 348 */     Iterable<Permission> permissionsNotInRole = this.permissionRepo.findByIdNotIn(permissionArray);
/*     */     
/* 350 */     this.logger.debug("Permissions not in role: " + permissionsNotInRole);
/* 351 */     return convertPermissionEntitiesToDTOs(permissionsNotInRole);
/*     */   }
/*     */ 
/*     */   
/*     */   public Page<SystemUser> getPersons(RoleDTO roledto, Pageable pageDetails) {
/* 356 */     Role role = this.roleRepo.findById(roledto.getId()).orElse(null);
/* 357 */     Page<SystemUser> pageImpl = null;
/*     */     
/* 359 */     Page<SystemUser> users = this.userRepo.findByRole(role, pageDetails);
/* 360 */     long elements = users.getTotalElements();
/* 361 */     List<SystemUser> userList = users.getContent();
/* 362 */     return (Page<SystemUser>)new PageImpl(userList, pageDetails, elements);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer countUsers(Role role) {
/* 368 */     Integer cnt = Integer.valueOf(0);
/* 369 */     cnt = this.userRepo.countByRole(role);
/* 370 */     return cnt;
/*     */   }
/*     */ 
/*     */   
/*     */   public Page<RoleDTO> findRoles(String pattern, Pageable pageDetails) {
/* 375 */     Page<Role> page = null;
/* 376 */     List<RoleDTO> dtOs = convertRoleEntitiesToDTOs(page.getContent());
/* 377 */     long t = page.getTotalElements();
/* 378 */     return (Page<RoleDTO>)new PageImpl(dtOs, pageDetails, t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Page<PermissionDTO> findPermissions(String pattern, Pageable pageDetails) {
/* 384 */     Page<Permission> page = null;
/* 385 */     List<PermissionDTO> dtOs = convertPermissionEntitiesToDTOs(page.getContent());
/* 386 */     long t = page.getTotalElements();
/* 387 */     return (Page<PermissionDTO>)new PageImpl(dtOs, pageDetails, t);
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\rolemgt\service\implementation\RoleServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */