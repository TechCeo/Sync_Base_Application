package com.xxm.syncbase.web.dashboard;

import com.xxm.syncbase.application.filesync.dto.BookSyncDashBoardAudit;
import com.xxm.syncbase.application.filesync.service.BookSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({""})
public class DashBoardController {
    private static final Logger logger = LoggerFactory.getLogger(DashBoardController.class);
    @Autowired
    BookSyncService bookSyncService;

    public DashBoardController() {
    }

    @RequestMapping({"/"})
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping({"/dashboard"})
    public String dashBoardIndex(Model model) {
        BookSyncDashBoardAudit bookSyncDashBoardAudit = this.bookSyncService.getSyncDashBoardData();
        model.addAttribute("dashBoardData", bookSyncDashBoardAudit);
        return "dashboard/index";
    }
}
