package com.xxm.syncbase.core.security.service;


import com.xxm.syncbase.core.usermgt.model.SystemUser;
import com.xxm.syncbase.core.usermgt.repository.ApplicationUserRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandler.class);
    @Autowired
    private ApplicationUserRepository userRepository;
    @Autowired
    private LoginService loginService;

    public AuthenticationFailureHandler() {
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        this.setDefaultFailureUrl("/login?failed=true");
        String usernameAndToken = request.getParameter("username");
        String[] strings = StringUtils.split(usernameAndToken, ":");
        if (strings != null && strings.length == 2) {
            String username = strings[0];
            this.logger.info("user {}", username);
            SystemUser systemUser = this.userRepository.findByUserName(username);
            if (systemUser != null) {
                this.loginService.updateUserLoginAttempt(systemUser);
                this.logger.error("Failed authentication for user [{}] ", username);
            }
        }

        this.logger.error("Failed authentication using credentials: {}  {}", usernameAndToken, exception.getMessage());
        super.onAuthenticationFailure(request, response, exception);
        request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception.getMessage());
    }
}