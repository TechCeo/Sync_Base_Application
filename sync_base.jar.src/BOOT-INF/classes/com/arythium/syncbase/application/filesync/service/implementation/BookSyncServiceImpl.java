/*     */ package BOOT-INF.classes.com.arythium.syncbase.application.filesync.service.implementation;
/*     */ 
/*     */ import com.arythium.syncbase.application.filesync.dto.BookSyncDashBoardAudit;
/*     */ import com.arythium.syncbase.application.filesync.dto.BookSyncDto;
/*     */ import com.arythium.syncbase.application.filesync.dto.BookSyncStatus;
/*     */ import com.arythium.syncbase.application.filesync.entity.BookSync;
/*     */ import com.arythium.syncbase.application.filesync.repository.BookSyncRepository;
/*     */ import com.arythium.syncbase.application.filesync.service.BookSyncService;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class BookSyncServiceImpl
/*     */   implements BookSyncService
/*     */ {
/*  27 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.application.filesync.service.implementation.BookSyncServiceImpl.class);
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   BookSyncRepository bookSyncRepository;
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   ModelMapper modelMapper;
/*     */ 
/*     */ 
/*     */   
/*     */   public BookSyncDto addBookSync(BookSyncDto bookSyncStatus) {
/*  42 */     BookSync bookSync = null;
/*  43 */     if (bookSyncStatus.getStatus().equals("PROCESSING")) {
/*  44 */       bookSync = new BookSync();
/*  45 */       bookSync.setBookName(bookSyncStatus.getBookName());
/*     */     } else {
/*  47 */       bookSync = this.bookSyncRepository.findFirstByBookNameOrderByDateCreatedDesc(bookSyncStatus.getBookName());
/*  48 */       if (bookSyncStatus.getStatus().equals("COMPLETED")) {
/*  49 */         bookSync.setCompletedOn(new Date());
/*     */       }
/*  51 */       bookSync.setLastCount(bookSyncStatus.getLastCount());
/*  52 */       bookSync.setMessage(bookSyncStatus.getMessage());
/*     */     } 
/*  54 */     bookSync.setProcessId("PR" + (new Date()).getTime());
/*  55 */     bookSync.setStatus(BookSyncStatus.valueOf(bookSyncStatus.getStatus()));
/*  56 */     bookSync = (BookSync)this.bookSyncRepository.save(bookSync);
/*  57 */     bookSyncStatus.setId(bookSync.getId());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     return bookSyncStatus;
/*     */   }
/*     */ 
/*     */   
/*     */   public BookSyncDto getLatestSyncByBookName(String bookName) {
/*  72 */     BookSync bookSync = this.bookSyncRepository.findFirstByBookNameOrderByDateCreatedDesc(bookName);
/*  73 */     if (bookSync == null) {
/*  74 */       return null;
/*     */     }
/*  76 */     return (BookSyncDto)this.modelMapper.map(bookSync, BookSyncDto.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BookSyncDto getBookSyncByProcID(String procID) {
/*  82 */     BookSync bookSync = this.bookSyncRepository.findByProcessId(procID);
/*  83 */     if (bookSync == null) {
/*  84 */       return null;
/*     */     }
/*  86 */     return (BookSyncDto)this.modelMapper.map(bookSync, BookSyncDto.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSyncPaused(BookSyncDto bookSyncDto) {
/*  91 */     BookSync syncAudit = this.bookSyncRepository.findFirstByBookNameOrderByDateCreatedDesc(bookSyncDto.getBookName());
/*  92 */     if (syncAudit != null && syncAudit.getStatus().equals(BookSyncStatus.PAUSED)) {
/*  93 */       return true;
/*     */     }
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BookSyncDto> getSyncAudit() {
/* 100 */     List<BookSync> bookSyncs = this.bookSyncRepository.findAllByOrderByDateCreatedDesc();
/* 101 */     List<BookSyncDto> bookSyncDtos = new ArrayList<>();
/* 102 */     bookSyncs.forEach(bookSync -> {
/*     */           BookSyncDto bookSyncDto = (BookSyncDto)this.modelMapper.map(bookSync, BookSyncDto.class);
/*     */           bookSyncDtos.add(bookSyncDto);
/*     */         });
/* 106 */     return bookSyncDtos;
/*     */   }
/*     */ 
/*     */   
/*     */   public BookSyncDashBoardAudit getSyncDashBoardData() {
/* 111 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 112 */     String todaysDate = simpleDateFormat.format(new Date());
/* 113 */     Date todaysStartTime = null;
/*     */     try {
/* 115 */       todaysStartTime = simpleDateFormat.parse(todaysDate);
/* 116 */     } catch (ParseException e) {
/* 117 */       e.printStackTrace();
/*     */     } 
/* 119 */     BookSyncDashBoardAudit bookSyncDashBoardAudit = new BookSyncDashBoardAudit();
/*     */ 
/*     */     
/* 122 */     int cnt1 = this.bookSyncRepository.countByStatusAndStartedOnGreaterThan(BookSyncStatus.COMPLETED, todaysStartTime);
/*     */     
/* 124 */     bookSyncDashBoardAudit.setCOMPLETED_BOOKS_COUNT(String.valueOf(cnt1));
/*     */     
/* 126 */     int cnt2 = this.bookSyncRepository.countByStatusAndStartedOnGreaterThan(BookSyncStatus.PROCESSING, todaysStartTime);
/* 127 */     bookSyncDashBoardAudit.setPROCESSED_BOOKS_COUNT(String.valueOf(cnt2));
/*     */     
/* 129 */     int cnt3 = this.bookSyncRepository.countByStatusAndStartedOnGreaterThan(BookSyncStatus.PAUSED, todaysStartTime);
/* 130 */     bookSyncDashBoardAudit.setPAUSED_BOOKS_COUNT(String.valueOf(cnt3));
/*     */     
/* 132 */     int cnt4 = this.bookSyncRepository.countByStatusAndStartedOnGreaterThan(BookSyncStatus.STOPPED, todaysStartTime);
/* 133 */     bookSyncDashBoardAudit.setSTOPPED_BOOKS_COUNT(String.valueOf(cnt4));
/*     */     
/* 135 */     return bookSyncDashBoardAudit;
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\filesync\service\implementation\BookSyncServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */