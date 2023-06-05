package com.xxm.syncbase.web.codes;

import com.xxm.syncbase.application.filesync.dto.BookSyncDto;
import com.xxm.syncbase.application.filesync.service.BookSyncService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/codes"})
public class CodeController {
    private static final Logger logger = LoggerFactory.getLogger(CodeController.class);
    @Autowired
    private BookSyncService bookSyncService;

    public CodeController() {
    }

    @GetMapping({""})
    public String index(Model model) {
        List<BookSyncDto> bookSyncDtoList = this.bookSyncService.getSyncAudit();
        model.addAttribute("syncAudits", bookSyncDtoList);
        return "codes/list";
    }

    @GetMapping({"/add"})
    public String addPage() {
        return "codes/add";
    }

    @GetMapping({"/{id}/edit"})
    public String editPage() {
        return "codes/edit";
    }
}
