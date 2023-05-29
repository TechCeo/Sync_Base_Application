package BOOT-INF.classes.com.arythium.syncbase.application.fileobjects.repository;

import com.arythium.syncbase.application.fileobjects.entity.BookObject;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookObjectRepository extends JpaRepository<BookObject, Long> {
  BookObject findByNameAndBankBook_BookName(String paramString1, String paramString2);
  
  List<BookObject> findByBankBook_BookName(String paramString);
  
  List<BookObject> findByBankBook_Id(Long paramLong);
  
  Page<BookObject> findByBankBook_Id(Long paramLong, Pageable paramPageable);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\fileobjects\repository\BookObjectRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */