package com.xxm.syncbase.web.reports;

import com.xxm.syncbase.application.filesync.dto.BookSyncDto;
import com.xxm.syncbase.application.filesync.service.BookSyncService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/reports"})
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    @Autowired
    private BookSyncService bookSyncService;

    public ReportController() {
    }

    @GetMapping({"/book_sync"})
    public String index(Model model) {
        List<BookSyncDto> bookSyncDtoList = this.bookSyncService.getSyncAudit();
        model.addAttribute("syncAudits", bookSyncDtoList);
        return "reports/book_sync/list";
    }

    @GetMapping({"/book_sync/{procID}/edit"})
    public String viewBookSyncAudit(@PathVariable("procID") String procID, Model model) {
        BookSyncDto bookSyncDto = this.bookSyncService.getBookSyncByProcID(procID);
        model.addAttribute("book_sync", bookSyncDto);
        return "reports/book_sync/edit";
    }
}
