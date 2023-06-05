package com.xxm.syncbase.application.bankfiles.repository;

import com.xxm.syncbase.application.bankfiles.entity.BankBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BankBook, Long> {
  BankBook findByBookName(String bookName);

  List<BankBook> findByEnabledTrue();
}