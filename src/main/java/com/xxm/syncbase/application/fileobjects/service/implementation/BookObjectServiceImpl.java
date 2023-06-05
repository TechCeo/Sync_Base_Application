package com.xxm.syncbase.application.fileobjects.service.implementation;

import com.xxm.syncbase.application.bankfiles.dto.BankBookDTO;
import com.xxm.syncbase.application.bankfiles.service.BookService;
import com.xxm.syncbase.application.fileobjects.dto.BookObjectDTO;
import com.xxm.syncbase.application.fileobjects.entity.BookObject;
import com.xxm.syncbase.application.fileobjects.repository.BookObjectRepository;
import com.xxm.syncbase.application.fileobjects.service.BookObjectService;
import com.xxm.syncbase.core.exceptions.SyncBaseException;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookObjectServiceImpl implements BookObjectService {
    private static final Logger logger = LoggerFactory.getLogger(BookObjectServiceImpl.class);
    @Value("${book_objects.config.header_columns}")
    private String[] configureHeaders;
    @Autowired
    BookObjectRepository bookObjectRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    BookService bookService;

    public BookObjectServiceImpl() {
    }

    public List<BookObjectDTO> getFeedsByBookName(String bookName) {
        List<BookObjectDTO> BookObjectDTOS = new ArrayList();
        List<BookObject> feedObjects = this.bookObjectRepository.findByBankBook_BookName(bookName);
        Iterator var4 = feedObjects.iterator();

        while(var4.hasNext()) {
            BookObject feedObject = (BookObject)var4.next();
            BookObjectDTO BookObjectDTO = (BookObjectDTO)this.modelMapper.map(feedObject, BookObjectDTO.class);
            BookObjectDTOS.add(BookObjectDTO);
        }

        return BookObjectDTOS;
    }

    public List<BookObjectDTO> getFeedsByBookId(Long bankBookId) {
        List<BookObjectDTO> BookObjectDTOS = new ArrayList();
        List<BookObject> feedObjects = this.bookObjectRepository.findByBankBook_Id(bankBookId);
        Iterator var4 = feedObjects.iterator();

        while(var4.hasNext()) {
            BookObject feedObject = (BookObject)var4.next();
            BookObjectDTO BookObjectDTO = (BookObjectDTO)this.modelMapper.map(feedObject, BookObjectDTO.class);
            BookObjectDTOS.add(BookObjectDTO);
        }

        return BookObjectDTOS;
    }

    public List<BookObjectDTO> getPagedFeedsByBookId(Long bankBookId, Pageable pageable) {
        List<BookObjectDTO> BookObjectDTOS = new ArrayList();
        Page<BookObject> feedObjects = this.bookObjectRepository.findByBankBook_Id(bankBookId, pageable);
        Iterator var5 = feedObjects.getContent().iterator();

        while(var5.hasNext()) {
            BookObject feedObject = (BookObject)var5.next();
            BookObjectDTO BookObjectDTO = (BookObjectDTO)this.modelMapper.map(feedObject, BookObjectDTO.class);
            BookObjectDTOS.add(BookObjectDTO);
        }

        return BookObjectDTOS;
    }

    public List<BookObjectDTO> getAllFeeds() {
        return null;
    }

    public BookObjectDTO addBookObject(BookObjectDTO BookObjectDTO) {
        BookObject feedObject = (BookObject)this.modelMapper.map(BookObjectDTO, BookObject.class);
        if (feedObject != null && feedObject.getBankBook() != null) {
            BookObjectDTO check = this.getFeedByNameAndBookname(feedObject.getName(), feedObject.getBankBook().getBookName());
            if (check != null) {
                throw new SyncBaseException("BookObject Already Exists");
            } else {
                feedObject.setDelFlag("N");
                feedObject.setEnabled(true);
                feedObject = (BookObject)this.bookObjectRepository.save(feedObject);
                logger.info("File Object {}  has been Added", feedObject.getName());
                BookObjectDTO.setId(feedObject.getId());
                return BookObjectDTO;
            }
        } else {
            throw new SyncBaseException("Invalid FileObject passed");
        }
    }

    public BookObjectDTO getFeedByNameAndBookname(String ObjectName, String bookName) {
        System.out.println(ObjectName + " " + bookName);
        BankBookDTO bankBookDTO = this.bookService.getBookByName(bookName);
        if (bankBookDTO == null) {
            throw new SyncBaseException("Book Not found :: " + bookName);
        } else {
            System.out.println(bankBookDTO);
            BookObject feedObject = this.bookObjectRepository.findByNameAndBankBook_BookName(ObjectName, bankBookDTO.getBookName());
            BookObjectDTO BookObjectDTO = null;
            if (feedObject != null) {
                BookObjectDTO = (BookObjectDTO)this.modelMapper.map(feedObject, BookObjectDTO.class);
            }

            return BookObjectDTO;
        }
    }

    public BookObjectDTO getBookObjectById(Long feedObjectId) {
        BookObject feedObject = (BookObject)this.bookObjectRepository.findById(feedObjectId).orElse(null);
        BookObjectDTO BookObjectDTO = null;
        if (feedObject != null) {
            BookObjectDTO = (BookObjectDTO)this.modelMapper.map(feedObject, BookObjectDTO.class);
        }

        return BookObjectDTO;
    }

    public BookObjectDTO updateBookObject(BookObjectDTO bookObjectDTO) {
        if (bookObjectDTO != null && bookObjectDTO.getBankBookDTO() != null) {
            BankBookDTO bankBookDTO = this.bookService.getBookByName(bookObjectDTO.getBankBookDTO().getBookName());
            if (bankBookDTO == null) {
                throw new SyncBaseException("File Object Not found :: " + bookObjectDTO.getBankBookDTO().getBookName());
            } else {
                BookObject feedObject = this.bookObjectRepository.findByNameAndBankBook_BookName(bookObjectDTO.getName(), bookObjectDTO.getBankBookDTO().getBookName());
                if (feedObject == null) {
                    throw new SyncBaseException("File Object Does not Exist");
                } else {
                    feedObject.setDefaultValue(bookObjectDTO.getDefaultValue());
                    feedObject.setDescription(bookObjectDTO.getDescription());
                    feedObject.setMappedColumn(bookObjectDTO.getMappedColumn());
                    feedObject.setEnabled(bookObjectDTO.getEnabled());
                    feedObject.setMappedColumnType(bookObjectDTO.getMappedColumnType());
                    this.bookObjectRepository.save(feedObject);
                    logger.info("File Object {} has been updated", feedObject.getName());
                    return bookObjectDTO;
                }
            }
        } else {
            throw new SyncBaseException("Invalid FileObject DTO");
        }
    }

    public String uploadBookObjects(MultipartFile multipartFile, Long bookId) {
        String fileName = multipartFile.getOriginalFilename();
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        System.out.println(file.getPath());

        try {
            multipartFile.transferTo(file);
            CSVReader reader = new CSVReader(new FileReader(file));

            String[] line;
            for(long cnt = 0L; (line = reader.readNext()) != null; ++cnt) {
                List datas;
                if (cnt == 0L) {
                    datas = Arrays.asList(this.configureHeaders);
                    if (!datas.equals(Arrays.asList(line))) {
                        throw new SyncBaseException("Invalid Header Found in Upload");
                    }
                } else {
                    datas = Arrays.asList(line);
                    BookObjectDTO feedObjectDTO = new BookObjectDTO();
                    BankBookDTO bankBookDTO = this.bookService.getBookByName((String)datas.get(0));
                    if (bankBookDTO == null) {
                        throw new SyncBaseException("Invalid Book Name at row : " + cnt);
                    }

                    feedObjectDTO.setBankBookDTO(bankBookDTO);
                    feedObjectDTO.setMappedColumnType((String)datas.get(4));
                    feedObjectDTO.setName((String)datas.get(1));
                    feedObjectDTO.setDescription((String)datas.get(2));
                    feedObjectDTO.setDefaultValue((String)datas.get(5));
                    feedObjectDTO.setMappedColumn((String)datas.get(3));
                    feedObjectDTO.setDelFlag("N");
                    feedObjectDTO.setEnabled(true);
                    BookObjectDTO check = this.getFeedByNameAndBookname(feedObjectDTO.getName(), feedObjectDTO.getBankBookDTO().getBookName());
                    if (check == null) {
                        this.addBookObject(feedObjectDTO);
                    } else {
                        this.updateBookObject(feedObjectDTO);
                    }
                }
            }

            reader.close();
            return null;
        } catch (IOException var21) {
            file.delete();
            var21.printStackTrace();
            throw new SyncBaseException(var21.getMessage());
        } catch (Exception var22) {
            var22.printStackTrace();
            throw new SyncBaseException(var22.getMessage());
        } finally {
            try {
                file.delete();
            } catch (Exception var20) {
                var20.printStackTrace();
            }

        }
    }

    public void deleteBookObject(Long id) {
        BookObject bookObject = (BookObject)this.bookObjectRepository.findById(id).orElse(null);
        if (bookObject == null) {
            throw new SyncBaseException("Book Not Found");
        } else {
            bookObject.setDelFlag("Y");
            bookObject.setDeletedOn(new Date());
            this.bookObjectRepository.save(bookObject);
        }
    }
}
