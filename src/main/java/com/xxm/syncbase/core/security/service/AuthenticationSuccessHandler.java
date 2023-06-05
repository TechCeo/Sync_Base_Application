package com.xxm.syncbase.core.security.service;


import com.xxm.syncbase.core.usermgt.model.SystemUser;
import com.xxm.syncbase.core.usermgt.repository.ApplicationUserRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);
    @Autowired
    private LoginService loginService;
    @Autowired
    private ApplicationUserRepository userRepository;

    public AuthenticationSuccessHandler() {
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("Authentication successful");
        SystemUser user = this.userRepository.findByUserName(authentication.getName());
        this.loginService.updateLastLogin(user);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}