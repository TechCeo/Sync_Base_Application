package com.xxm.syncbase.application.filesync.service.implementation;

import com.xxm.syncbase.application.bankfiles.dto.BankBookDTO;
import com.xxm.syncbase.application.fileobjects.dto.BookObjectDTO;
import com.xxm.syncbase.application.fileobjects.service.BookObjectService;
import com.xxm.syncbase.application.filesync.dto.FeedQueryDTO;
import com.xxm.syncbase.application.filesync.repository.DBUtilsRepository;
import com.xxm.syncbase.application.filesync.service.DBUtilService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.xxm.syncbase.core.exceptions.SyncBaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBUtilServiceImpl implements DBUtilService {
    @Autowired
    DBUtilsRepository dbUtilsRepository;
    @Autowired
    BookObjectService bookObjectService;

    public DBUtilServiceImpl() {
    }
    public FeedQueryDTO generateQuery(String[] csvHeaders, BankBookDTO bookDTO) {
        FeedQueryDTO feedQueryDTO = new FeedQueryDTO();
        ArrayList<Integer> cellsToIgnore = new ArrayList<>();
        ArrayList<Integer> dateCells = new ArrayList<>();
        ArrayList<Integer> timeStampCells = new ArrayList<>();
        ArrayList<Integer> yearCells = new ArrayList<>();
        Map<Integer, String> patternCells = new HashMap<>();
        ArrayList<Integer> numberCells = new ArrayList<>();
        ArrayList<String> defaultValues = new ArrayList<>();
        String objectTable = bookDTO.getMappedTableName();
        String finalQuery = "";
        int columnLength = csvHeaders.length;
        boolean firstColumnSeen = false;



        for (int i = 0; i < csvHeaders.length; i++) {

            String columnHeader = csvHeaders[i];

            BookObjectDTO feedObjectDTO = this.bookObjectService.getFeedByNameAndBookname(columnHeader, bookDTO.getBookName());

            if (feedObjectDTO == null) {
                System.out.println("column header is not found, so corresponding data would be skipped " + columnHeader);
                columnLength--;
                cellsToIgnore.add(Integer.valueOf(i));
                if (i == csvHeaders.length - 1) {
                    finalQuery = finalQuery + ") values (" + StringUtils.repeat("?", ",", columnLength) + "}";
                }
            } else {

                columnHeader = feedObjectDTO.getMappedColumn();


                defaultValues.add(feedObjectDTO.getDefaultValue());

                if (feedObjectDTO.getMappedColumnType().equalsIgnoreCase("DATE")) {
                    dateCells.add(Integer.valueOf(i));
                }
                if (feedObjectDTO.getMappedColumnType().equalsIgnoreCase("NUMBER")) {
                    numberCells.add(Integer.valueOf(i));
                }
                if (feedObjectDTO.getMappedColumnType().equalsIgnoreCase("TIMESTAMP")) {
                    timeStampCells.add(Integer.valueOf(i));
                }
                if (feedObjectDTO.getMappedColumnType().equalsIgnoreCase("YEAR")) {
                    yearCells.add(Integer.valueOf(i));
                }
                if (feedObjectDTO.getMappedColumnType().contains("PATTERN")) {
                    patternCells.put(Integer.valueOf(i), feedObjectDTO.getMappedColumnType().split("_")[1]);
                }

                if (!firstColumnSeen) {
                    finalQuery = finalQuery + "insert into " + objectTable + "(" + columnHeader;
                    firstColumnSeen = true;
                } else if (i == csvHeaders.length - 1) {
                    finalQuery = finalQuery + "," + columnHeader + ") values (" + StringUtils.repeat("?", ",", columnLength) + ")";
                } else {
                    finalQuery = finalQuery + "," + columnHeader;
                }
            }
        }

        feedQueryDTO.setIgnoredCells(cellsToIgnore);
        feedQueryDTO.setDateCells(dateCells);
        feedQueryDTO.setTimeStampCells(timeStampCells);
        feedQueryDTO.setNumberCells(numberCells);
        feedQueryDTO.setQueryString(finalQuery);
        feedQueryDTO.setNumOfValues(columnLength);
        feedQueryDTO.setCellsDefaultValue(defaultValues);
        feedQueryDTO.setYearCells(yearCells);
        feedQueryDTO.setPatternCells(patternCells);
        return feedQueryDTO;
    }

    public void saveRecord(String[] csvRows, FeedQueryDTO feedQueryDTO) {
        try {
            this.dbUtilsRepository.insertWithQuery(csvRows, feedQueryDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SyncBaseException("Error occurred saving record" + e.getMessage());
        }
    }
}