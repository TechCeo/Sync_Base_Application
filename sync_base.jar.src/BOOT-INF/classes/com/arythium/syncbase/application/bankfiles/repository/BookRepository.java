package BOOT-INF.classes.com.arythium.syncbase.application.bankfiles.repository;

import com.arythium.syncbase.application.bankfiles.entity.BankBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BankBook, Long> {
  BankBook findByBookName(String paramString);
  
  List<BankBook> findByEnabledTrue();
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\bankfiles\repository\BookRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */