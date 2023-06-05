package com.xxm.syncbase.application.bankfiles.service.implementation;

import com.xxm.syncbase.application.bankfiles.dto.BankBookDTO;
import com.xxm.syncbase.application.bankfiles.entity.BankBook;
import com.xxm.syncbase.application.bankfiles.repository.BookRepository;
import com.xxm.syncbase.application.bankfiles.service.BookService;
import com.xxm.syncbase.application.fileobjects.dto.BookObjectDTO;
import com.xxm.syncbase.application.fileobjects.service.BookObjectService;
import com.xxm.syncbase.application.filesync.dto.BookSyncDto;
import com.xxm.syncbase.application.filesync.service.BookSyncService;
import com.xxm.syncbase.application.filesync.service.implementation.DBUtilServiceImpl;
import com.xxm.syncbase.core.code.service.CodeService;
import com.xxm.syncbase.core.exceptions.SyncBaseException;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CodeService codeService;
    @Autowired
    DBUtilServiceImpl dbUtilService;
    @Autowired
    BookSyncService bookSyncService;
    @Autowired
    BookObjectService bookObjectService;
    @Value("${book.config.header_columns}")
    private String[] configureHeaders;

    public BookServiceImpl() {
    }

    public List<BankBookDTO> getAllBooks() {
        List<BankBookDTO> bankBookDTOS = new ArrayList();
        this.bookRepository.findAll().forEach((bankBook) -> {
            bankBookDTOS.add(this.modelMapper.map(bankBook, BankBookDTO.class));
        });
        return bankBookDTOS;
    }

    public List<BankBookDTO> getUnFedBooks() {
        List<BankBookDTO> bankBookDTOS = new ArrayList();
        this.bookRepository.findByEnabledTrue().forEach((bankBook) -> {
            BookSyncDto syncAudit = this.bookSyncService.getLatestSyncByBookName(bankBook.getBookName());
            if (syncAudit != null) {
                if (!syncAudit.getStatus().equals("COMPLETED") && !syncAudit.getStatus().equals("STOPPED")) {
                    logger.error("{} is currently {}, started at :{} ", new Object[]{bankBook.getBookName(), syncAudit.getStatus(), syncAudit.getDateCreated()});
                } else {
                    bankBookDTOS.add(this.modelMapper.map(bankBook, BankBookDTO.class));
                }
            } else {
                bankBookDTOS.add(this.modelMapper.map(bankBook, BankBookDTO.class));
            }

        });
        return bankBookDTOS;
    }

    public BankBookDTO getBookByName(String bookName) {
        BankBook book = this.bookRepository.findByBookName(bookName);
        if (book == null) {
            return null;
        } else {
            BankBookDTO bankBookDTO = (BankBookDTO)this.modelMapper.map(book, BankBookDTO.class);
            return bankBookDTO;
        }
    }

    public BankBookDTO getBookById(Long bookId) {
        BankBook book = (BankBook)this.bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return null;
        } else {
            BankBookDTO bankBookDTO = (BankBookDTO)this.modelMapper.map(book, BankBookDTO.class);
            return bankBookDTO;
        }
    }

    public BankBookDTO addBook(BankBookDTO bankBookDTO) {
        if (bankBookDTO == null) {
            throw new SyncBaseException("Book Cannot be null");
        } else {
            BankBook check = this.bookRepository.findByBookName(bankBookDTO.getBookName());
            if (check != null) {
                throw new SyncBaseException("File Already exist");
            } else {
                BankBook book = (BankBook)this.modelMapper.map(bankBookDTO, BankBook.class);
                book.setFeedObjects((List)null);
                book.setDelFlag("N");
                book = (BankBook)this.bookRepository.save(book);
                logger.info("Book Added :: {} ", book.getBookName());
                bankBookDTO.setId(book.getId());
                return bankBookDTO;
            }
        }
    }

    public BankBookDTO updateBook(BankBookDTO bankBookDTO) {
        BankBook check = this.bookRepository.findByBookName(bankBookDTO.getBookName());
        if (check == null) {
            throw new SyncBaseException("File Does not exist");
        } else {
            check.setBookPrefix(bankBookDTO.getBookPrefix());
            check.setMappedTableName(bankBookDTO.getMappedTableName());
            check.setDescription(bankBookDTO.getDescription());
            check.setEnabled(bankBookDTO.getEnabled());
            check = (BankBook)this.bookRepository.save(check);
            logger.info("File Config {} has been Updated", check.getBookName());
            bankBookDTO.setId(check.getId());
            return bankBookDTO;
        }
    }

    public String uploadBooks(MultipartFile multipartFile) {
        System.out.println("conn :: " + this.configureHeaders);
        String fileName = multipartFile.getOriginalFilename();
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);

        try {
            multipartFile.transferTo(file);
            CSVReader reader = new CSVReader(new FileReader(file));

            String[] line;
            for(long cnt = 0L; (line = reader.readNext()) != null; ++cnt) {
                List csvHeader;
                if (cnt == 0L) {
                    csvHeader = Arrays.asList(this.configureHeaders);
                    if (!csvHeader.equals(Arrays.asList(line))) {
                        throw new SyncBaseException("Invalid Header Found in Upload");
                    }
                } else {
                    csvHeader = Arrays.asList(line);
                    BankBookDTO bankBookDTO = new BankBookDTO();
                    bankBookDTO.setBookName((String)csvHeader.get(0));
                    bankBookDTO.setMappedTableName((String)csvHeader.get(2));
                    bankBookDTO.setBookPrefix((String)csvHeader.get(1));
                    bankBookDTO.setEnabled(true);
                    bankBookDTO.setDelFlag("N");
                    BankBookDTO check = this.getBookByName(bankBookDTO.getBookName());
                    if (check == null) {
                        this.addBook(bankBookDTO);
                    } else {
                        this.updateBook(bankBookDTO);
                    }
                }
            }

            reader.close();
            return null;
        } catch (IOException var11) {
            var11.printStackTrace();
            throw new SyncBaseException("Error Occurred configuring book " + var11.getMessage());
        } catch (Exception var12) {
            var12.printStackTrace();
            throw new SyncBaseException("Error Occurred configuring book " + var12.getMessage());
        }
    }

    public void deleteBook(Long id) {
        BankBook bankBook = (BankBook)this.bookRepository.findById(id).orElse(null);
        if (bankBook == null) {
            throw new SyncBaseException("Book Not Found");
        } else {
            List<BookObjectDTO> bookObjectDTOS = this.bookObjectService.getFeedsByBookId(bankBook.getId());

            for(int i = 0; i < bookObjectDTOS.size(); ++i) {
                BookObjectDTO bookObjectDTO = (BookObjectDTO)bookObjectDTOS.get(i);
                this.bookObjectService.deleteBookObject(bookObjectDTO.getId());
            }

            bankBook.setDelFlag("Y");
            bankBook.setDeletedOn(new Date());
            this.bookRepository.save(bankBook);
        }
    }
}
