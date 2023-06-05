package com.xxm.syncbase.application.filesync.service;

import com.xxm.syncbase.application.bankfiles.dto.BankBookDTO;
import com.xxm.syncbase.application.filesync.dto.FeedQueryDTO;

public interface DBUtilService {
  FeedQueryDTO generateQuery(String[] csvHeaders, BankBookDTO bookDTO);

  void saveRecord(String[] csvHeaders, FeedQueryDTO feedQueryDTO);
}