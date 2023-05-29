package BOOT-INF.classes.com.arythium.syncbase.core.code.repository;

import com.arythium.syncbase.core.code.model.Code;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
  Iterable<Code> findByType(String paramString);
  
  Page<Code> findByTypeIgnoreCase(String paramString, Pageable paramPageable);
  
  Page<Code> findByType(String paramString, Pageable paramPageable);
  
  Code findByTypeAndCode(String paramString1, String paramString2);
  
  Code findFirstByTypeAndCode(String paramString1, String paramString2);
  
  @Query("select distinct c.type from Code c")
  Iterable<String> findAllTypes();
  
  @Query("select distinct c.type from Code c")
  Page<String> findAllTypes(Pageable paramPageable);
  
  @Query("select distinct c.type from Code c where c.type LIKE CONCAT('%',:type,'%')")
  Page<String> findByCodeType(@Param("type") String paramString, Pageable paramPageable);
  
  @Query("select c from Code c where c.type =:type and c.code LIKE CONCAT('%',:value,'%') or c.description LIKE CONCAT('%',:value,'%')")
  Page<Code> findByTypeAndValue(@Param("type") String paramString1, @Param("value") String paramString2, Pageable paramPageable);
  
  Code findByCode(String paramString);
  
  Code findByDescription(String paramString);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\core\code\repository\CodeRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */