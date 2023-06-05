package com.xxm.syncbase.core.code.repository;


import com.xxm.syncbase.core.code.model.Code;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
  Iterable<Code> findByType(String type);

  Page<Code> findByTypeIgnoreCase(String type, Pageable pageable);

  Page<Code> findByType(String type, Pageable pageable);

  Code findByTypeAndCode(String type, String code);

  Code findFirstByTypeAndCode(String type, String code);

  @Query("select distinct c.type from Code c")
  Iterable<String> findAllTypes();

  @Query("select distinct c.type from Code c")
  Page<String> findAllTypes(Pageable pageable);

  @Query("select distinct c.type from Code c where c.type LIKE CONCAT('%',:type,'%')")
  Page<String> findByCodeType(@Param("type") String codeType, Pageable pageable);

  @Query("select c from Code c where c.type =:type and c.code LIKE CONCAT('%',:value,'%') or c.description LIKE CONCAT('%',:value,'%')")
  Page<Code> findByTypeAndValue(@Param("type") String codeType, @Param("value") String value, Pageable pageable);

  Code findByCode(String code);

  Code findByDescription(String description);
}
