package com.xxm.syncbase.web.settings;

import com.xxm.syncbase.core.exceptions.SyncBaseException;
import com.xxm.syncbase.core.setting.dto.SettingDto;
import com.xxm.syncbase.core.setting.service.SettingService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/settings"})
public class SettingController {
    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);
    @Autowired
    private SettingService settingService;

    public SettingController() {
    }

    @GetMapping({""})
    public String index(Model model) {
        List<SettingDto> allSettings = this.settingService.getSettings();
        model.addAttribute("settings", allSettings);
        return "settings/list";
    }

    @GetMapping({"/add"})
    public String addSettingPage(Model model) {
        model.addAttribute("setting", new SettingDto());
        return "settings/add";
    }

    @PostMapping({"/add"})
    public String addSetting(@ModelAttribute("setting") @Valid SettingDto SettingDto, Model model) {
        try {
            this.settingService.addSetting(SettingDto);
            model.addAttribute("successMessage", "Successfully Created Setting");
        } catch (SyncBaseException var4) {
            model.addAttribute("errorMessage", var4.getMessage());
        } catch (Exception var5) {
            model.addAttribute("errorMessage", var5.getMessage());
        }

        List<SettingDto> allSettings = this.settingService.getSettings();
        model.addAttribute("settings", allSettings);
        return "settings/list";
    }

    @GetMapping({"/{id}/edit"})
    public String editPage(@PathVariable("id") Long id, Model model) {
        SettingDto settingDto = this.settingService.getSettingById(id);
        model.addAttribute("setting", settingDto);
        return "settings/edit";
    }

    @PostMapping({"/{id}/edit"})
    public String editPage(@PathVariable("id") Long id, @ModelAttribute("setting") @Valid SettingDto SettingDto, Model model) {
        try {
            this.settingService.updateSetting(SettingDto);
            model.addAttribute("successMessage", "Successfully Updated Setting");
        } catch (SyncBaseException var5) {
            model.addAttribute("errorMessage", var5.getMessage());
        } catch (Exception var6) {
            model.addAttribute("errorMessage", var6.getMessage());
        }

        model.addAttribute("setting", SettingDto);
        return "settings/edit";
    }
}
