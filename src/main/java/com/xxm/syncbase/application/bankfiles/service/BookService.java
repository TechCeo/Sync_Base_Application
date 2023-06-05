package com.xxm.syncbase.application.bankfiles.service;


import com.xxm.syncbase.application.bankfiles.dto.BankBookDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
  List<BankBookDTO> getAllBooks();

  List<BankBookDTO> getUnFedBooks();

  BankBookDTO getBookByName(String bookName);

  BankBookDTO getBookById(Long bookId);

  BankBookDTO addBook(BankBookDTO bankBookDTO);

  BankBookDTO updateBook(BankBookDTO bankBookDTO);

  String uploadBooks(MultipartFile file);

  void deleteBook(Long id);
}
