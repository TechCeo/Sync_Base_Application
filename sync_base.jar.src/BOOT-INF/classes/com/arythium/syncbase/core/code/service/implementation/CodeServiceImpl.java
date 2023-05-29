/*     */ package BOOT-INF.classes.com.arythium.syncbase.core.code.service.implementation;
/*     */ 
/*     */ import com.arythium.syncbase.core.code.dto.CodeDTO;
/*     */ import com.arythium.syncbase.core.code.model.Code;
/*     */ import com.arythium.syncbase.core.code.model.CodeType;
/*     */ import com.arythium.syncbase.core.code.repository.CodeRepository;
/*     */ import com.arythium.syncbase.core.code.service.CodeService;
/*     */ import com.arythium.syncbase.core.exceptions.SyncBaseException;
/*     */ import java.util.ArrayList;
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
/*     */ public class CodeServiceImpl
/*     */   implements CodeService
/*     */ {
/*  35 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.core.code.service.implementation.CodeServiceImpl.class);
/*  36 */   private static final Locale locale = LocaleContextHolder.getLocale();
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private CodeRepository codeRepository;
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private MessageSource messageSource;
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private ModelMapper modelMapper;
/*     */ 
/*     */   
/*     */   public CodeDTO getCode(Long id) {
/*  52 */     logger.debug("Retrieving code with Id [{}]", id);
/*  53 */     Code code = this.codeRepository.findById(id).orElse(null);
/*  54 */     return convertEntityToDTO(code);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Code getCodeById(Long id) {
/*  60 */     logger.debug("Retrieving code with Id [{}]", id);
/*  61 */     Code code = this.codeRepository.findById(id).orElse(null);
/*  62 */     return code;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CodeDTO> getCodesByType(String codeType) {
/*  68 */     logger.debug("Retrieving code with type [{}]", codeType);
/*  69 */     Iterable<Code> codes = this.codeRepository.findByType(codeType);
/*  70 */     return convertEntitiesToDTOs(codes);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<CodeDTO> getCodes() {
/*  75 */     Iterable<Code> codes = this.codeRepository.findAll();
/*  76 */     return convertEntitiesToDTOs(codes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String updateCode(CodeDTO codeDTO) throws SyncBaseException {
/*  84 */     logger.debug("Updating code: {}", codeDTO);
/*     */     try {
/*  86 */       Code code = convertDTOToEntity(codeDTO);
/*  87 */       this.codeRepository.save(code);
/*  88 */       logger.info("Updated code with Id {}", code.getId());
/*  89 */       return this.messageSource.getMessage("code.update.success", null, locale);
/*  90 */     } catch (Exception e) {
/*  91 */       throw new SyncBaseException(this.messageSource.getMessage("code.update.failure", null, locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CodeDTO convertEntityToDTO(Code code) {
/*  99 */     return (CodeDTO)this.modelMapper.map(code, CodeDTO.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public Code convertDTOToEntity(CodeDTO codeDTO) {
/* 104 */     return (Code)this.modelMapper.map(codeDTO, Code.class);
/*     */   }
/*     */   
/*     */   public List<CodeDTO> convertEntitiesToDTOs(Iterable<Code> codes) {
/* 108 */     List<CodeDTO> codeDTOList = new ArrayList<>();
/* 109 */     for (Code code : codes) {
/* 110 */       CodeDTO codeDTO = convertEntityToDTO(code);
/* 111 */       codeDTOList.add(codeDTO);
/*     */     } 
/* 113 */     return codeDTOList;
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
/*     */   
/*     */   public CodeDTO getCodeByCode(String code) {
/* 128 */     CodeDTO codeDTO = null;
/* 129 */     if (code != null) {
/* 130 */       Code code1 = this.codeRepository.findByCode(code);
/* 131 */       if (code1 != null) {
/* 132 */         codeDTO = convertEntityToDTO(code1);
/*     */       }
/*     */     } 
/*     */     
/* 136 */     return codeDTO;
/*     */   }
/*     */ 
/*     */   
/*     */   public CodeDTO getCodeByDescription(String description) {
/* 141 */     CodeDTO codeDTO = null;
/* 142 */     if (description != null) {
/* 143 */       Code code = this.codeRepository.findByDescription(description);
/* 144 */       if (code != null) {
/* 145 */         codeDTO = convertEntityToDTO(code);
/*     */       }
/*     */     } 
/*     */     
/* 149 */     return codeDTO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Page<CodeDTO> getCodesByType(String codeType, Pageable pageDetails) {
/* 157 */     Page<Code> page = this.codeRepository.findByType(codeType, pageDetails);
/* 158 */     List<CodeDTO> dtOs = convertEntitiesToDTOs((Iterable<Code>)page);
/* 159 */     long t = page.getTotalElements();
/* 160 */     return (Page<CodeDTO>)new PageImpl(dtOs, pageDetails, t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Page<CodeDTO> findByTypeAndValue(String codeType, String value, Pageable pageDetails) {
/* 166 */     Page<Code> page = this.codeRepository.findByTypeAndValue(codeType, value, pageDetails);
/* 167 */     List<CodeDTO> dtOs = convertEntitiesToDTOs((Iterable<Code>)page);
/* 168 */     long t = page.getTotalElements();
/* 169 */     return (Page<CodeDTO>)new PageImpl(dtOs, pageDetails, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Page<CodeDTO> getCodes(Pageable pageDetails) {
/* 176 */     logger.debug("Retrieving codes");
/* 177 */     Page<Code> page = this.codeRepository.findAll(pageDetails);
/* 178 */     List<CodeDTO> dtOs = convertEntitiesToDTOs(page.getContent());
/* 179 */     long t = page.getTotalElements();
/* 180 */     return (Page<CodeDTO>)new PageImpl(dtOs, pageDetails, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String addCode(CodeDTO codeDTO) throws SyncBaseException {
/*     */     try {
/* 189 */       Code code = convertDTOToEntity(codeDTO);
/* 190 */       this.codeRepository.save(code);
/* 191 */       logger.info("Added new code {} of type {}", code.getDescription(), code.getType());
/* 192 */       return this.messageSource.getMessage("code.add.success", null, locale);
/* 193 */     } catch (Exception e) {
/* 194 */       throw new SyncBaseException(this.messageSource.getMessage("code.add.failure", null, locale), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Code getByTypeAndCode(String type, String code) {
/* 201 */     return this.codeRepository.findFirstByTypeAndCode(type, code);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Page<CodeType> getCodeTypes(Pageable pageDetails) {
/* 208 */     Page<String> allTypes = this.codeRepository.findAllTypes(pageDetails);
/* 209 */     long t = allTypes.getTotalElements();
/* 210 */     List<CodeType> list = new ArrayList<>();
/* 211 */     for (String s : allTypes) {
/* 212 */       list.add(new CodeType(s));
/*     */     }
/* 214 */     return (Page<CodeType>)new PageImpl(list, pageDetails, t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Page<CodeType> findByCodeType(String codeType, Pageable pageDetails) {
/* 220 */     Page<String> allTypes = this.codeRepository.findByCodeType(codeType, pageDetails);
/* 221 */     long t = allTypes.getTotalElements();
/* 222 */     List<CodeType> list = new ArrayList<>();
/* 223 */     for (String s : allTypes) {
/* 224 */       list.add(new CodeType(s));
/*     */     }
/*     */     
/* 227 */     return (Page<CodeType>)new PageImpl(list, pageDetails, t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String deleteCode(Long codeId) throws SyncBaseException {
/*     */     try {
/* 234 */       Code code = this.codeRepository.findById(codeId).orElse(null);
/* 235 */       this.codeRepository.delete(code);
/* 236 */       logger.info("Code {} has been deleted", codeId.toString());
/* 237 */       return this.messageSource.getMessage("code.delete.success", null, locale);
/* 238 */     } catch (Exception e) {
/* 239 */       throw new SyncBaseException(this.messageSource.getMessage("code.delete.failure", null, locale), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\code\service\implementation\CodeServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */