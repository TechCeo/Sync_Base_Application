package BOOT-INF.classes.com.arythium.syncbase.application.fileobjects.service;

import com.arythium.syncbase.application.fileobjects.dto.BookObjectDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BookObjectService {
  List<BookObjectDTO> getFeedsByBookName(String paramString);
  
  List<BookObjectDTO> getFeedsByBookId(Long paramLong);
  
  List<BookObjectDTO> getPagedFeedsByBookId(Long paramLong, Pageable paramPageable);
  
  List<BookObjectDTO> getAllFeeds();
  
  BookObjectDTO addBookObject(BookObjectDTO paramBookObjectDTO);
  
  BookObjectDTO getFeedByNameAndBookname(String paramString1, String paramString2);
  
  BookObjectDTO getBookObjectById(Long paramLong);
  
  BookObjectDTO updateBookObject(BookObjectDTO paramBookObjectDTO);
  
  String uploadBookObjects(MultipartFile paramMultipartFile, Long paramLong);
  
  void deleteBookObject(Long paramLong);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\fileobjects\service\BookObjectService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */