package com.xxm.syncbase.application.filesync.service.implementation;

import com.xxm.syncbase.application.filesync.dto.BookSyncDashBoardAudit;
import com.xxm.syncbase.application.filesync.dto.BookSyncDto;
import com.xxm.syncbase.application.filesync.dto.BookSyncStatus;
import com.xxm.syncbase.application.filesync.entity.BookSync;
import com.xxm.syncbase.application.filesync.repository.BookSyncRepository;
import com.xxm.syncbase.application.filesync.service.BookSyncService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookSyncServiceImpl implements BookSyncService {
    private static final Logger logger = LoggerFactory.getLogger(BookSyncServiceImpl.class);
    @Autowired
    BookSyncRepository bookSyncRepository;

    @Autowired
    ModelMapper modelMapper;

    public BookSyncServiceImpl() {
    }

    public BookSyncDto addBookSync(BookSyncDto bookSyncStatus) {
        BookSync bookSync = null;
        if (bookSyncStatus.getStatus().equals("STARTED")) {
            bookSync = new BookSync();
            bookSync.setBookName(bookSyncStatus.getBookName());
            bookSync.setProcessId("PR" + (new Date()).getTime());
        } else {
            bookSync = this.bookSyncRepository.findFirstByBookNameOrderByDateCreatedDesc(bookSyncStatus.getBookName());
            if (bookSyncStatus.getStatus().equals("COMPLETED")) {
                bookSync.setCompletedOn(new Date());
            }

            bookSync.setLastCount(bookSyncStatus.getLastCount());
            bookSync.setMessage(bookSyncStatus.getMessage());
        }


        bookSync.setStatus(BookSyncStatus.valueOf(bookSyncStatus.getStatus()));
        bookSync = this.bookSyncRepository.save(bookSync);
        bookSyncStatus.setId(bookSync.getId());

        return bookSyncStatus;
    }

    public BookSyncDto getLatestSyncByBookName(String bookName) {
        BookSync bookSync = this.bookSyncRepository.findFirstByBookNameOrderByDateCreatedDesc(bookName);
        return bookSync == null ? null : this.modelMapper.map(bookSync, BookSyncDto.class);
    }

    public BookSyncDto getBookSyncByProcID(String procID) {
        BookSync bookSync = this.bookSyncRepository.findByProcessId(procID);
        return bookSync == null ? null : this.modelMapper.map(bookSync, BookSyncDto.class);
    }

    public boolean isSyncPaused(BookSyncDto bookSyncDto) {
        BookSync syncAudit = this.bookSyncRepository.findFirstByBookNameOrderByDateCreatedDesc(bookSyncDto.getBookName());
        return syncAudit != null && syncAudit.getStatus().equals(BookSyncStatus.PAUSED);
    }

    public List<BookSyncDto> getSyncAudit() {
        List<BookSync> bookSyncs = this.bookSyncRepository.findAllByOrderByDateCreatedDesc();
        List<BookSyncDto> bookSyncDtos = new ArrayList();
        bookSyncs.forEach((bookSync) -> {
            BookSyncDto bookSyncDto = (BookSyncDto)this.modelMapper.map(bookSync, BookSyncDto.class);
            bookSyncDtos.add(bookSyncDto);
        });
        return bookSyncDtos;
    }

    public BookSyncDashBoardAudit getSyncDashBoardData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todaysDate = simpleDateFormat.format(new Date());
        Date todaysStartTime = null;

        try {
            todaysStartTime = simpleDateFormat.parse(todaysDate);
        } catch (ParseException var9) {
            var9.printStackTrace();
        }

        BookSyncDashBoardAudit bookSyncDashBoardAudit = new BookSyncDashBoardAudit();
        int cnt1 = this.bookSyncRepository.countByStatusAndStartedOnGreaterThan(BookSyncStatus.COMPLETED, todaysStartTime);
        bookSyncDashBoardAudit.setCOMPLETED_BOOKS_COUNT(String.valueOf(cnt1));
        int cnt2 = this.bookSyncRepository.countByStatusAndStartedOnGreaterThan(BookSyncStatus.PROCESSING, todaysStartTime);
        bookSyncDashBoardAudit.setPROCESSED_BOOKS_COUNT(String.valueOf(cnt2));
        int cnt3 = this.bookSyncRepository.countByStatusAndStartedOnGreaterThan(BookSyncStatus.PAUSED, todaysStartTime);
        bookSyncDashBoardAudit.setPAUSED_BOOKS_COUNT(String.valueOf(cnt3));
        int cnt4 = this.bookSyncRepository.countByStatusAndStartedOnGreaterThan(BookSyncStatus.STOPPED, todaysStartTime);
        bookSyncDashBoardAudit.setSTOPPED_BOOKS_COUNT(String.valueOf(cnt4));
        return bookSyncDashBoardAudit;
    }
}
