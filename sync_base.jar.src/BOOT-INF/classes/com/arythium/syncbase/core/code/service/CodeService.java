package BOOT-INF.classes.com.arythium.syncbase.core.code.service;

import com.arythium.syncbase.core.code.dto.CodeDTO;
import com.arythium.syncbase.core.code.model.Code;
import com.arythium.syncbase.core.code.model.CodeType;
import com.arythium.syncbase.core.exceptions.SyncBaseException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeService {
  String addCode(CodeDTO paramCodeDTO) throws SyncBaseException;
  
  Page<CodeType> findByCodeType(String paramString, Pageable paramPageable);
  
  String deleteCode(Long paramLong) throws SyncBaseException;
  
  CodeDTO getCode(Long paramLong);
  
  Code getCodeById(Long paramLong);
  
  List<CodeDTO> getCodesByType(String paramString);
  
  String updateCode(CodeDTO paramCodeDTO) throws SyncBaseException;
  
  Page<CodeDTO> getCodesByType(String paramString, Pageable paramPageable);
  
  Page<CodeType> getCodeTypes(Pageable paramPageable);
  
  Code getByTypeAndCode(String paramString1, String paramString2);
  
  Page<CodeDTO> findByTypeAndValue(String paramString1, String paramString2, Pageable paramPageable);
  
  Page<CodeDTO> getCodes(Pageable paramPageable);
  
  Iterable<CodeDTO> getCodes();
  
  CodeDTO convertEntityToDTO(Code paramCode);
  
  Code convertDTOToEntity(CodeDTO paramCodeDTO);
  
  List<CodeDTO> convertEntitiesToDTOs(Iterable<Code> paramIterable);
  
  CodeDTO getCodeByCode(String paramString);
  
  CodeDTO getCodeByDescription(String paramString);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\code\service\CodeService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */