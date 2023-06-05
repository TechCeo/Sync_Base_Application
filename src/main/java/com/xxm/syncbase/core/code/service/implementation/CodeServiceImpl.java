package com.xxm.syncbase.core.code.service.implementation;


import com.xxm.syncbase.core.code.dto.CodeDTO;
import com.xxm.syncbase.core.code.model.Code;
import com.xxm.syncbase.core.code.model.CodeType;
import com.xxm.syncbase.core.code.repository.CodeRepository;
import com.xxm.syncbase.core.code.service.CodeService;
import com.xxm.syncbase.core.exceptions.SyncBaseException;
import java.util.ArrayList;
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

@Service
public class CodeServiceImpl implements CodeService {
    private static final Logger logger = LoggerFactory.getLogger(CodeServiceImpl.class);
    private static final Locale locale = LocaleContextHolder.getLocale();
    @Autowired
    private CodeRepository codeRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ModelMapper modelMapper;

    public CodeServiceImpl() {
    }

    public CodeDTO getCode(Long id) {
        logger.debug("Retrieving code with Id [{}]", id);
        Code code = (Code)this.codeRepository.findById(id).orElse(null);
        return this.convertEntityToDTO(code);
    }

    public Code getCodeById(Long id) {
        logger.debug("Retrieving code with Id [{}]", id);
        Code code = (Code)this.codeRepository.findById(id).orElse(null);
        return code;
    }

    public List<CodeDTO> getCodesByType(String codeType) {
        logger.debug("Retrieving code with type [{}]", codeType);
        Iterable<Code> codes = this.codeRepository.findByType(codeType);
        return this.convertEntitiesToDTOs(codes);
    }

    public Iterable<CodeDTO> getCodes() {
        Iterable<Code> codes = this.codeRepository.findAll();
        return this.convertEntitiesToDTOs(codes);
    }

    public String updateCode(CodeDTO codeDTO) throws SyncBaseException {
        logger.debug("Updating code: {}", codeDTO);

        try {
            Code code = this.convertDTOToEntity(codeDTO);
            this.codeRepository.save(code);
            logger.info("Updated code with Id {}", code.getId());
            return this.messageSource.getMessage("code.update.success", (Object[])null, locale);
        } catch (Exception var3) {
            throw new SyncBaseException(this.messageSource.getMessage("code.update.failure", (Object[])null, locale), var3);
        }
    }

    public CodeDTO convertEntityToDTO(Code code) {
        return (CodeDTO)this.modelMapper.map(code, CodeDTO.class);
    }

    public Code convertDTOToEntity(CodeDTO codeDTO) {
        return (Code)this.modelMapper.map(codeDTO, Code.class);
    }

    public List<CodeDTO> convertEntitiesToDTOs(Iterable<Code> codes) {
        List<CodeDTO> codeDTOList = new ArrayList();
        Iterator var3 = codes.iterator();

        while(var3.hasNext()) {
            Code code = (Code)var3.next();
            CodeDTO codeDTO = this.convertEntityToDTO(code);
            codeDTOList.add(codeDTO);
        }

        return codeDTOList;
    }

    public CodeDTO getCodeByCode(String code) {
        CodeDTO codeDTO = null;
        if (code != null) {
            Code code1 = this.codeRepository.findByCode(code);
            if (code1 != null) {
                codeDTO = this.convertEntityToDTO(code1);
            }
        }

        return codeDTO;
    }

    public CodeDTO getCodeByDescription(String description) {
        CodeDTO codeDTO = null;
        if (description != null) {
            Code code = this.codeRepository.findByDescription(description);
            if (code != null) {
                codeDTO = this.convertEntityToDTO(code);
            }
        }

        return codeDTO;
    }

    public Page<CodeDTO> getCodesByType(String codeType, Pageable pageDetails) {
        Page<Code> page = this.codeRepository.findByType(codeType, pageDetails);
        List<CodeDTO> dtOs = this.convertEntitiesToDTOs(page);
        long t = page.getTotalElements();
        Page<CodeDTO> pageImpl = new PageImpl(dtOs, pageDetails, t);
        return pageImpl;
    }

    public Page<CodeDTO> findByTypeAndValue(String codeType, String value, Pageable pageDetails) {
        Page<Code> page = this.codeRepository.findByTypeAndValue(codeType, value, pageDetails);
        List<CodeDTO> dtOs = this.convertEntitiesToDTOs(page);
        long t = page.getTotalElements();
        Page<CodeDTO> pageImpl = new PageImpl(dtOs, pageDetails, t);
        return pageImpl;
    }

    public Page<CodeDTO> getCodes(Pageable pageDetails) {
        logger.debug("Retrieving codes");
        Page<Code> page = this.codeRepository.findAll(pageDetails);
        List<CodeDTO> dtOs = this.convertEntitiesToDTOs(page.getContent());
        long t = page.getTotalElements();
        Page<CodeDTO> pageImpl = new PageImpl(dtOs, pageDetails, t);
        return pageImpl;
    }

    public String addCode(CodeDTO codeDTO) throws SyncBaseException {
        try {
            Code code = this.convertDTOToEntity(codeDTO);
            this.codeRepository.save(code);
            logger.info("Added new code {} of type {}", code.getDescription(), code.getType());
            return this.messageSource.getMessage("code.add.success", (Object[])null, locale);
        } catch (Exception var3) {
            throw new SyncBaseException(this.messageSource.getMessage("code.add.failure", (Object[])null, locale), var3);
        }
    }

    public Code getByTypeAndCode(String type, String code) {
        return this.codeRepository.findFirstByTypeAndCode(type, code);
    }

    public Page<CodeType> getCodeTypes(Pageable pageDetails) {
        Page<String> allTypes = this.codeRepository.findAllTypes(pageDetails);
        long t = allTypes.getTotalElements();
        List<CodeType> list = new ArrayList();
        Iterator var6 = allTypes.iterator();

        while(var6.hasNext()) {
            String s = (String)var6.next();
            list.add(new CodeType(s));
        }

        return new PageImpl(list, pageDetails, t);
    }

    public Page<CodeType> findByCodeType(String codeType, Pageable pageDetails) {
        Page<String> allTypes = this.codeRepository.findByCodeType(codeType, pageDetails);
        long t = allTypes.getTotalElements();
        List<CodeType> list = new ArrayList();
        Iterator var7 = allTypes.iterator();

        while(var7.hasNext()) {
            String s = (String)var7.next();
            list.add(new CodeType(s));
        }

        return new PageImpl(list, pageDetails, t);
    }

    public String deleteCode(Long codeId) throws SyncBaseException {
        try {
            Code code = (Code)this.codeRepository.findById(codeId).orElse(null);
            this.codeRepository.delete(code);
            logger.info("Code {} has been deleted", codeId.toString());
            return this.messageSource.getMessage("code.delete.success", (Object[])null, locale);
        } catch (Exception var3) {
            throw new SyncBaseException(this.messageSource.getMessage("code.delete.failure", (Object[])null, locale), var3);
        }
    }
}