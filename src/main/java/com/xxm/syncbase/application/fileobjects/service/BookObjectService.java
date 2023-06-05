package com.xxm.syncbase.application.fileobjects.service;


import com.xxm.syncbase.application.fileobjects.dto.BookObjectDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BookObjectService {
  List<BookObjectDTO> getFeedsByBookName(String bookName);

  List<BookObjectDTO> getFeedsByBookId(Long bankBookId);

  List<BookObjectDTO> getPagedFeedsByBookId(Long bankBookId, Pageable pageable);

  List<BookObjectDTO> getAllFeeds();

  BookObjectDTO addBookObject(BookObjectDTO BookObjectDTO);

  BookObjectDTO getFeedByNameAndBookname(String name, String bookName);

  BookObjectDTO getBookObjectById(Long feedObjectId);

  BookObjectDTO updateBookObject(BookObjectDTO BookObjectDTO);

  String uploadBookObjects(MultipartFile file, Long bookId);

  void deleteBookObject(Long id);
}
