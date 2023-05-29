package BOOT-INF.classes.com.arythium.syncbase.application.filesync.service;

import com.arythium.syncbase.application.bankfiles.dto.BankBookDTO;
import com.arythium.syncbase.application.filesync.dto.FeedQueryDTO;

public interface DBUtilService {
  FeedQueryDTO generateQuery(String[] paramArrayOfString, BankBookDTO paramBankBookDTO);
  
  void saveRecord(String[] paramArrayOfString, FeedQueryDTO paramFeedQueryDTO);
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\filesync\service\DBUtilService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */