package BOOT-INF.classes.com.arythium.syncbase.application.filesync.service;

import com.arythium.syncbase.application.bankfiles.dto.BankBookDTO;
import java.util.List;

public interface HandShakeService {
  List<String> getPendingFiles(String paramString);
  
  void generateFeed(String paramString, BankBookDTO paramBankBookDTO);
  
  void scheduledFeedGeneration();
}


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\application\filesync\service\HandShakeService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */