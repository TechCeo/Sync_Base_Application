package BOOT-INF.classes.com.arythium.syncbase.application.filesync.service;

import com.arythium.syncbase.application.filesync.dto.BookSyncDashBoardAudit;
import com.arythium.syncbase.application.filesync.dto.BookSyncDto;
import java.util.List;

public interface BookSyncService {
  BookSyncDto addBookSync(BookSyncDto paramBookSyncDto);
  
  BookSyncDto getLatestSyncByBookName(String paramString);
  
  BookSyncDto getBookSyncByProcID(String paramString);
  
  boolean isSyncPaused(BookSyncDto paramBookSyncDto);
  
  List<BookSyncDto> getSyncAudit();
  
  BookSyncDashBoardAudit getSyncDashBoardData();
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\filesync\service\BookSyncService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */