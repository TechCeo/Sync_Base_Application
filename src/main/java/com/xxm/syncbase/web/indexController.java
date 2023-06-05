package com.xxm.syncbase.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {
    private static final Logger logger = LoggerFactory.getLogger(indexController.class);

    public indexController() {
    }

    @GetMapping({"/login"})
    public String loginPage(@RequestParam(value = "failed",required = false) boolean failed, @RequestParam(value = "expired",required = false) boolean expired, Model model) {
        if (failed) {
            model.addAttribute("failed", failed);
        }

        if (expired) {
            model.addAttribute("expired", expired);
        }

        return "login";
    }

    @GetMapping({"/log"})
    public String log() {
        logger.trace("This is a TRACE level message");
        logger.debug("This is a DEBUG level message");
        logger.info("This is an INFO level message");
        logger.warn("This is a WARN level message");
        logger.error("This is an ERROR level message");
        return "See the log for details";
    }
}
