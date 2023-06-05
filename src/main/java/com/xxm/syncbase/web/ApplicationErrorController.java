package com.xxm.syncbase.web;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ApplicationErrorController implements ErrorController {
    private static final String PATH = "/error";
    @Autowired
    private ErrorAttributes errorAttributes;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${dev.members.mail}")
    private String devMembersMails;

    public ApplicationErrorController() {
    }

    @RequestMapping({"/error"})
    public String handleError(WebRequest request) {
        Map<String, Object> errorDetails = this.errorAttributes.getErrorAttributes(request, true);
        String errorPath = (String)errorDetails.get("path");
        String statusCode = errorDetails.get("status").toString();
        Object exception = errorDetails.get("exception");
        this.logger.error("Page Error Detail: {}", errorDetails.get("error"));
        if (exception != null) {
            this.sendNotification(errorDetails);
        }

        if ("403".equals(statusCode)) {
            return "error403";
        } else if ("404".equals(statusCode)) {
            return "error404";
        } else {
            String subPath = StringUtils.substringAfter(errorPath, "/");
            return subPath != null ? "error" : "redirect:/login";
        }
    }

    public String getErrorPath() {
        return "/error";
    }

    private void sendNotification(Map errorDetails) {
    }
}
