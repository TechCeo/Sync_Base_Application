package com.xxm.syncbase.application.filesync.repository;

import com.xxm.syncbase.application.filesync.dto.BookSyncStatus;
import com.xxm.syncbase.application.filesync.entity.BookSync;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSyncRepository extends JpaRepository<BookSync, Long> {
  BookSync findFirstByBookNameOrderByDateCreatedDesc(String bookName);

  BookSync findByProcessId(String procID);

  List<BookSync> findAllByOrderByDateCreatedDesc();

  int countByStatusAndStartedOnGreaterThan(BookSyncStatus syncStatus, Date completedOn);
}
