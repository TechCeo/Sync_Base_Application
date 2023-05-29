/*    */ package BOOT-INF.classes.com.arythium.syncbase.web.settings;
/*    */ import com.arythium.syncbase.core.exceptions.SyncBaseException;
/*    */ import com.arythium.syncbase.core.setting.dto.SettingDto;
/*    */ import com.arythium.syncbase.core.setting.service.SettingService;
/*    */ import java.util.List;
/*    */ import javax.validation.Valid;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.web.bind.annotation.GetMapping;
/*    */ import org.springframework.web.bind.annotation.ModelAttribute;
/*    */ import org.springframework.web.bind.annotation.PathVariable;
/*    */ import org.springframework.web.bind.annotation.PostMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ @RequestMapping({"/settings"})
/*    */ public class SettingController {
/* 21 */   private static final Logger logger = LoggerFactory.getLogger(com.arythium.syncbase.web.settings.SettingController.class);
/*    */ 
/*    */   
/*    */   @Autowired
/*    */   private SettingService settingService;
/*    */ 
/*    */   
/*    */   @GetMapping({""})
/*    */   public String index(Model model) {
/* 30 */     List<SettingDto> allSettings = this.settingService.getSettings();
/* 31 */     model.addAttribute("settings", allSettings);
/* 32 */     return "settings/list";
/*    */   }
/*    */ 
/*    */   
/*    */   @GetMapping({"/add"})
/*    */   public String addSettingPage(Model model) {
/* 38 */     model.addAttribute("setting", new SettingDto());
/* 39 */     return "settings/add";
/*    */   }
/*    */ 
/*    */   
/*    */   @PostMapping({"/add"})
/*    */   public String addSetting(@Valid @ModelAttribute("setting") SettingDto settingDto, Model model) {
/*    */     try {
/* 46 */       this.settingService.addSetting(settingDto);
/* 47 */       model.addAttribute("successMessage", "Successfully Created Setting");
/* 48 */     } catch (SyncBaseException e) {
/* 49 */       model.addAttribute("errorMessage", e.getMessage());
/* 50 */     } catch (Exception e) {
/* 51 */       model.addAttribute("errorMessage", e.getMessage());
/*    */     } 
/*    */     
/* 54 */     List<SettingDto> allSettings = this.settingService.getSettings();
/* 55 */     model.addAttribute("settings", allSettings);
/* 56 */     return "settings/list";
/*    */   }
/*    */ 
/*    */   
/*    */   @GetMapping({"/{id}/edit"})
/*    */   public String editPage(@PathVariable("id") Long id, Model model) {
/* 62 */     SettingDto settingDto = this.settingService.getSettingById(id);
/* 63 */     model.addAttribute("setting", settingDto);
/* 64 */     return "settings/edit";
/*    */   }
/*    */ 
/*    */   
/*    */   @PostMapping({"/{id}/edit"})
/*    */   public String editPage(@PathVariable("id") Long id, @Valid @ModelAttribute("setting") SettingDto settingDto, Model model) {
/*    */     try {
/* 71 */       this.settingService.updateSetting(settingDto);
/* 72 */       model.addAttribute("successMessage", "Successfully Updated Setting");
/* 73 */     } catch (SyncBaseException e) {
/* 74 */       model.addAttribute("errorMessage", e.getMessage());
/* 75 */     } catch (Exception e) {
/* 76 */       model.addAttribute("errorMessage", e.getMessage());
/*    */     } 
/* 78 */     model.addAttribute("setting", settingDto);
/* 79 */     return "settings/edit";
/*    */   }
/*    */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\BOOT-INF\classes\com\arythium\syncbase\web\settings\SettingController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */