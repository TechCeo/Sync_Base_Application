package com.xxm.syncbase.application.filesync.service.implementation;

import com.xxm.syncbase.application.bankfiles.dto.BankBookDTO;
import com.xxm.syncbase.application.bankfiles.dto.BookSyncStatus;
import com.xxm.syncbase.application.bankfiles.service.BookService;
import com.xxm.syncbase.application.filesync.dto.BookSyncDto;
import com.xxm.syncbase.application.filesync.dto.FeedQueryDTO;
import com.xxm.syncbase.application.filesync.service.BookSyncService;
import com.xxm.syncbase.application.filesync.service.DBUtilService;
import com.xxm.syncbase.application.filesync.service.HandShakeService;
import com.xxm.syncbase.core.exceptions.SyncBaseException;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HandShakeServiceImpl implements HandShakeService {
    private static final Logger logger = LoggerFactory.getLogger(HandShakeServiceImpl.class);
    @Value("${syncbase.files.directory}")
    private String fileDirectory;
    @Value("${syncbase.files.search_pattern}")
    private String searchPattern;

    @Value("${syncbase.files.seperator}")
    private String fileSeperator;

    @Autowired
    BookService bookService;
    @Autowired
    BookSyncService bookSyncService;
    @Autowired
    DBUtilService dbUtilService;

    public HandShakeServiceImpl() {
    }

    public List<String> getPendingFiles(String filePrefix) {
        List<String> pendingFiles = new ArrayList();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File folder = new File(this.fileDirectory);
        if (!folder.exists()) {
            folder = new File(classLoader.getResource("").getFile());
        }

        File[] folderItems = folder.listFiles();
        File[] var6 = folderItems;
        int var7 = folderItems.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            File f = var6[var8];
            if (f.isFile() && f.getName().matches(filePrefix + this.searchPattern)) {
                pendingFiles.add(f.getPath());
            }
        }

        return pendingFiles;
    }

    public void generateFeed(String fileName, BankBookDTO bankBookDTO) {
        long cnt = 0L;
        BookSyncDto bookSyncDto = new BookSyncDto();
        bookSyncDto.setBookName(bankBookDTO.getBookName());
        bookSyncDto.setStatus(String.valueOf(BookSyncStatus.STARTED));
        bookSyncDto.setLastCount(cnt);
        this.bookSyncService.addBookSync(bookSyncDto);
        logger.info("Data sync started using :: {}", fileName);
        File file = new File(fileName);
        FeedQueryDTO feedQueryDTO = null;
        CSVReader reader = null;
        boolean containsHeader = true;

        try {
            bookSyncDto.setStatus(String.valueOf(BookSyncStatus.PROCESSING));
            bookSyncDto.setLastCount(Long.valueOf((cnt > 0L) ? (cnt - 1L) : cnt));
            this.bookSyncService.addBookSync(bookSyncDto);
            char csvDelimitter = (this.fileSeperator == null) ? ',' : this.fileSeperator.charAt(0);
            reader = new CSVReader(new FileReader(file), csvDelimitter);
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (containsHeader && cnt == 0L) {
                    feedQueryDTO = this.dbUtilService.generateQuery(line, bankBookDTO);
                    logger.info("Query Properties {} ", feedQueryDTO);

                }
                else {

                    logger.info("{}. {}", Long.valueOf(cnt), feedQueryDTO.getQueryString());
                    try {
                        this.dbUtilService.saveRecord(line, feedQueryDTO);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (reader != null) {
                            reader.close();
                        }
                        throw new SyncBaseException("Error occurred inserting record at row: " + cnt + " Cause : " + e.getCause().getCause());
                    }
                }

                boolean isPaused = this.bookSyncService.isSyncPaused(bookSyncDto);
                if (isPaused) {
                    logger.error("Sync was paused at {} ", Long.valueOf(cnt));
                    break;
                }
                cnt++;
            }
            reader.close();
            logger.info("Data sync ended using :: {}", fileName);
            backOutFile(fileName, "success");
            bookSyncDto.setStatus(String.valueOf(BookSyncStatus.COMPLETED));
            bookSyncDto.setLastCount(Long.valueOf((cnt > 0L) ? (cnt - 1L) : cnt));
            this.bookSyncService.addBookSync(bookSyncDto);
        } catch (IOException var14) {
            var14.printStackTrace();
            bookSyncDto.setStatus(String.valueOf(BookSyncStatus.STOPPED));
            bookSyncDto.setLastCount(cnt);
            bookSyncDto.setMessage(var14.getMessage());
            this.bookSyncService.addBookSync(bookSyncDto);

            backOutFile(fileName, "failed");

            throw new SyncBaseException("Error occurred inserting record ");
        } catch (Exception var15) {
            var15.printStackTrace();
            bookSyncDto.setStatus(String.valueOf(BookSyncStatus.STOPPED));
            bookSyncDto.setLastCount(cnt);
            bookSyncDto.setMessage(var15.getMessage());
            this.bookSyncService.addBookSync(bookSyncDto);

            backOutFile(fileName, "failed");

            throw new SyncBaseException("Error occurred inserting record ");
        }

        logger.info("");
    }

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    public void scheduledFeedGeneration() {
        logger.info("**** Scheduled JOB for data handshake has begun ***");
        List<BankBookDTO> bankBookDTOS = this.bookService.getUnFedBooks();
        logger.info("Number of books to process :: {}", bankBookDTOS.size());
        Iterator var2 = bankBookDTOS.iterator();

        while(var2.hasNext()) {
            BankBookDTO bankBookDTO = (BankBookDTO)var2.next();
            logger.info("[ Generating feeds for  {} ]", bankBookDTO.getBookName());
            List<String> csvFiles = this.getPendingFiles(bankBookDTO.getBookPrefix());
            if (csvFiles.size() > 0) {
                logger.info("[ Processing file seen for {} data sync ]", bankBookDTO.getBookName());
                this.generateFeed(csvFiles.get(0), bankBookDTO);
            } else {
                logger.info("[ No File seen for {} data sync ]", bankBookDTO.getBookName());
            }
        }

    }

    private void backOutFile(String fileName, String status) {
      Path source = Paths.get(fileName, new String[0]);
      String backoutFileName = fileName + "." + status;
      try {
           Files.move(source, source.resolveSibling(backoutFileName), new java.nio.file.CopyOption[0]);
         } catch (IOException e) {
           e.printStackTrace();
         }
      logger.info("{} file has been backed out to {}", fileName, backoutFileName);
    }
}