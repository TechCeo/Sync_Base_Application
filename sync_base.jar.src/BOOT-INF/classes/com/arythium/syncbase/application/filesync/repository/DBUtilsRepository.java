/*     */ package BOOT-INF.classes.com.arythium.syncbase.application.filesync.repository;
/*     */ 
/*     */ import com.arythium.syncbase.application.filesync.dto.FeedQueryDTO;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.persistence.EntityManager;
/*     */ import javax.persistence.PersistenceContext;
/*     */ import javax.persistence.Query;
/*     */ import javax.persistence.TemporalType;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.stereotype.Repository;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Repository
/*     */ public class DBUtilsRepository
/*     */ {
/*     */   @PersistenceContext
/*     */   private EntityManager entityManager;
/*     */   @Value("${syncbase.date.format}")
/*     */   private String dateFormat;
/*     */   @Value("${syncbase.timestamp.format}")
/*     */   private String timeStampFormat;
/*     */   private SimpleDateFormat simpleDateFormater;
/*     */   private SimpleDateFormat simpleTimeStampFormatter;
/*  35 */   private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
/*     */   
/*  37 */   private Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.application.filesync.repository.DBUtilsRepository.class);
/*     */   
/*     */   @Transactional
/*     */   public void insertWithQuery(String[] queryValues, FeedQueryDTO feedQueryDTO) throws ParseException {
/*  41 */     this.simpleDateFormater = new SimpleDateFormat(this.dateFormat);
/*  42 */     this.simpleTimeStampFormatter = new SimpleDateFormat(this.timeStampFormat);
/*  43 */     Query query = this.entityManager.createNativeQuery(feedQueryDTO.getQueryString());
/*  44 */     int currentValue = 0;
/*  45 */     for (int a = 0; a < feedQueryDTO.getNumOfValues(); a++) {
/*  46 */       while (feedQueryDTO.getIgnoredCells().contains(Integer.valueOf(currentValue))) {
/*  47 */         currentValue++;
/*     */       }
/*     */       
/*  50 */       String cellValue = queryValues[currentValue];
/*  51 */       if ((cellValue == null || cellValue.equals("")) && feedQueryDTO
/*  52 */         .getCellsDefaultValue().get(currentValue) != null) {
/*  53 */         cellValue = feedQueryDTO.getCellsDefaultValue().get(currentValue);
/*     */       }
/*     */       
/*  56 */       if (feedQueryDTO.getDateCells().contains(Integer.valueOf(currentValue))) {
/*  57 */         Date date = null;
/*     */         try {
/*  59 */           date = this.simpleDateFormater.parse(cellValue);
/*  60 */         } catch (Exception e) {
/*  61 */           this.logger.error(e.getMessage());
/*     */         } 
/*  63 */         query.setParameter(a + 1, date, TemporalType.DATE);
/*  64 */       } else if (feedQueryDTO.getTimeStampCells().contains(Integer.valueOf(currentValue))) {
/*  65 */         Date date = null;
/*     */         try {
/*  67 */           date = this.simpleTimeStampFormatter.parse(cellValue);
/*  68 */         } catch (Exception e) {
/*  69 */           this.logger.error(e.getMessage());
/*     */         } 
/*  71 */         query.setParameter(a + 1, date, TemporalType.TIMESTAMP);
/*  72 */       } else if (feedQueryDTO.getYearCells().contains(Integer.valueOf(currentValue))) {
/*  73 */         Date date = null;
/*     */         
/*     */         try {
/*  76 */           if (cellValue.trim().length() < 12) {
/*  77 */             date = this.simpleDateFormater.parse(cellValue);
/*     */           } else {
/*  79 */             date = this.simpleTimeStampFormatter.parse(cellValue);
/*     */           } 
/*  81 */           cellValue = (new SimpleDateFormat("yyyy")).format(date);
/*  82 */         } catch (Exception e) {
/*  83 */           this.logger.error(e.getMessage());
/*     */         } 
/*  85 */         query.setParameter(a + 1, cellValue);
/*  86 */       } else if (feedQueryDTO.getPatternCells().get(Integer.valueOf(currentValue)) != null) {
/*  87 */         String regexMatcher = (String)feedQueryDTO.getPatternCells().get(Integer.valueOf(currentValue));
/*  88 */         Pattern sourceAcctPattern = Pattern.compile(regexMatcher);
/*  89 */         Matcher matcher = sourceAcctPattern.matcher(cellValue);
/*  90 */         if (matcher.find()) {
/*  91 */           cellValue = matcher.group(0);
/*     */         }
/*  93 */         query.setParameter(a + 1, cellValue);
/*  94 */       } else if (feedQueryDTO.getNumberCells().contains(Integer.valueOf(currentValue))) {
/*  95 */         cellValue = cellValue.replaceAll(",", "");
/*  96 */         if (cellValue.trim().equals("") || !this.pattern.matcher(cellValue).matches()) {
/*  97 */           cellValue = "0";
/*     */         }
/*  99 */         query.setParameter(a + 1, cellValue);
/*     */       } else {
/* 101 */         query.setParameter(a + 1, cellValue);
/*     */       } 
/* 103 */       currentValue++;
/*     */     } 
/* 105 */     query.executeUpdate();
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\filesync\repository\DBUtilsRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */