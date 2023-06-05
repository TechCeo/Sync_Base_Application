package com.xxm.syncbase.core.code.service;

import com.xxm.syncbase.core.code.dto.CodeDTO;
import com.xxm.syncbase.core.code.model.Code;
import com.xxm.syncbase.core.code.model.CodeType;
import com.xxm.syncbase.core.exceptions.SyncBaseException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeService {
  String addCode(CodeDTO code) throws SyncBaseException;

  Page<CodeType> findByCodeType(String codeType, Pageable pageDetails);

  String deleteCode(Long codeId) throws SyncBaseException;

  CodeDTO getCode(Long codeId);

  Code getCodeById(Long codeId);

  List<CodeDTO> getCodesByType(String codeType);

  String updateCode(CodeDTO codeDTO) throws SyncBaseException;

  Page<CodeDTO> getCodesByType(String codeType, Pageable pageDetails);

  Page<CodeType> getCodeTypes(Pageable pageDetails);

  Code getByTypeAndCode(String type, String code);

  Page<CodeDTO> findByTypeAndValue(String codeType, String value, Pageable pageDetails);

  Page<CodeDTO> getCodes(Pageable pageDetails);

  Iterable<CodeDTO> getCodes();

  CodeDTO convertEntityToDTO(Code code);

  Code convertDTOToEntity(CodeDTO codeDTO);

  List<CodeDTO> convertEntitiesToDTOs(Iterable<Code> codes);

  CodeDTO getCodeByCode(String code);

  CodeDTO getCodeByDescription(String description);
}