package com.xxm.syncbase.application.filesync.service;

import com.xxm.syncbase.application.bankfiles.dto.BankBookDTO;
import java.util.List;

public interface HandShakeService {
  List<String> getPendingFiles(String pattern);

  void generateFeed(String FileName, BankBookDTO bankBookDTO);

  void scheduledFeedGeneration();
}