package BOOT-INF.classes.com.arythium.syncbase.application.filesync.repository;

import com.arythium.syncbase.application.filesync.dto.BookSyncStatus;
import com.arythium.syncbase.application.filesync.entity.BookSync;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSyncRepository extends JpaRepository<BookSync, Long> {
  BookSync findFirstByBookNameOrderByDateCreatedDesc(String paramString);
  
  BookSync findByProcessId(String paramString);
  
  List<BookSync> findAllByOrderByDateCreatedDesc();
  
  int countByStatusAndStartedOnGreaterThan(BookSyncStatus paramBookSyncStatus, Date paramDate);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\filesync\repository\BookSyncRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */