package com.xxm.syncbase.application.fileobjects.repository;


import com.xxm.syncbase.application.fileobjects.entity.BookObject;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookObjectRepository extends JpaRepository<BookObject, Long> {
  BookObject findByNameAndBankBook_BookName(String name, String bankBookName);

  List<BookObject> findByBankBook_BookName(String bankBookName);

  List<BookObject> findByBankBook_Id(Long bankBookId);

  Page<BookObject> findByBankBook_Id(Long bankBookId, Pageable pageable);
}
