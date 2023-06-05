package com.xxm.syncbase.application.filesync.repository;

import com.xxm.syncbase.application.filesync.dto.FeedQueryDTO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class DBUtilsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Value("${syncbase.date.format}")
    private String dateFormat;
    @Value("${syncbase.timestamp.format}")
    private String timeStampFormat;
    private SimpleDateFormat simpleDateFormater;
    private SimpleDateFormat simpleTimeStampFormatter;
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    private Logger logger = LoggerFactory.getLogger(DBUtilsRepository.class);

    public DBUtilsRepository() {
    }

    @Transactional

    public void insertWithQuery(String[] queryValues, FeedQueryDTO feedQueryDTO) throws ParseException {
        this.simpleDateFormater = new SimpleDateFormat(this.dateFormat);
        this.simpleTimeStampFormatter = new SimpleDateFormat(this.timeStampFormat);
        Query query = this.entityManager.createNativeQuery(feedQueryDTO.getQueryString());
        int currentValue = 0;
        for (int a = 0; a < feedQueryDTO.getNumOfValues(); a++) {
            while (feedQueryDTO.getIgnoredCells().contains(Integer.valueOf(currentValue))) {
                currentValue++;
            }

            String cellValue = queryValues[currentValue];
            if ((cellValue == null || cellValue.equals("")) && feedQueryDTO
                    .getCellsDefaultValue().get(currentValue) != null) {
                cellValue = feedQueryDTO.getCellsDefaultValue().get(currentValue);
            }

            if (feedQueryDTO.getDateCells().contains(Integer.valueOf(currentValue))) {
                Date date = null;
                try {
                    date = this.simpleDateFormater.parse(cellValue);
                } catch (Exception e) {
                    this.logger.error(e.getMessage());
                }
                query.setParameter(a + 1, date, TemporalType.DATE);
            } else if (feedQueryDTO.getTimeStampCells().contains(Integer.valueOf(currentValue))) {
                Date date = null;
                try {
                    date = this.simpleTimeStampFormatter.parse(cellValue);
                } catch (Exception e) {
                    this.logger.error(e.getMessage());
                }
                query.setParameter(a + 1, date, TemporalType.TIMESTAMP);
            } else if (feedQueryDTO.getYearCells().contains(Integer.valueOf(currentValue))) {
                Date date = null;

                try {
                    if (cellValue.trim().length() < 12) {
                        date = this.simpleDateFormater.parse(cellValue);
                    } else {
                        date = this.simpleTimeStampFormatter.parse(cellValue);
                    }
                    cellValue = (new SimpleDateFormat("yyyy")).format(date);
                } catch (Exception e) {
                    this.logger.error(e.getMessage());
                }
                query.setParameter(a + 1, cellValue);
            } else if (feedQueryDTO.getPatternCells().get(Integer.valueOf(currentValue)) != null) {
                String regexMatcher = (String)feedQueryDTO.getPatternCells().get(Integer.valueOf(currentValue));
                Pattern sourceAcctPattern = Pattern.compile(regexMatcher);
                Matcher matcher = sourceAcctPattern.matcher(cellValue);
                if (matcher.find()) {
                    cellValue = matcher.group(0);
                }
                query.setParameter(a + 1, cellValue);
            } else if (feedQueryDTO.getNumberCells().contains(Integer.valueOf(currentValue))) {
                cellValue = cellValue.replaceAll(",", "");
                if (cellValue.trim().equals("") || !this.pattern.matcher(cellValue).matches()) {
                    cellValue = "0";
                }
                query.setParameter(a + 1, cellValue);
            } else {
                query.setParameter(a + 1, cellValue);
            }
            currentValue++;
        }
        query.executeUpdate();
    }
}
