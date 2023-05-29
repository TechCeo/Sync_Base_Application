package BOOT-INF.classes.com.arythium.syncbase.application.bankfiles.service;

import com.arythium.syncbase.application.bankfiles.dto.BankBookDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
  List<BankBookDTO> getAllBooks();
  
  List<BankBookDTO> getUnFedBooks();
  
  BankBookDTO getBookByName(String paramString);
  
  BankBookDTO getBookById(Long paramLong);
  
  BankBookDTO addBook(BankBookDTO paramBankBookDTO);
  
  BankBookDTO updateBook(BankBookDTO paramBankBookDTO);
  
  String uploadBooks(MultipartFile paramMultipartFile);
  
  void deleteBook(Long paramLong);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\bankfiles\service\BookService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */