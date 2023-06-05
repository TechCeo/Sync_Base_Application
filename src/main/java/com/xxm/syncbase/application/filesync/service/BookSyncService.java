package com.xxm.syncbase.application.filesync.service;

import com.xxm.syncbase.application.filesync.dto.BookSyncDashBoardAudit;
import com.xxm.syncbase.application.filesync.dto.BookSyncDto;
import java.util.List;

public interface BookSyncService {
  BookSyncDto addBookSync(BookSyncDto bookSyncStatus);

  BookSyncDto getLatestSyncByBookName(String bookName);

  BookSyncDto getBookSyncByProcID(String procID);

  boolean isSyncPaused(BookSyncDto bookSyncDto);

  List<BookSyncDto> getSyncAudit();

  BookSyncDashBoardAudit getSyncDashBoardData();
}
