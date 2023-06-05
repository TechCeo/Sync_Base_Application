package com.xxm.syncbase.core.security.service;

import com.xxm.syncbase.core.usermgt.model.SystemUser;
import com.xxm.syncbase.core.usermgt.repository.ApplicationUserRepository;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private ApplicationUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginService() {
    }

    public void updateUserLoginAttempt(SystemUser user) {
        int numOfLoginAttempts = user.getNoOfWrongLoginCount();
        ++numOfLoginAttempts;
        user.setNoOfWrongLoginCount(numOfLoginAttempts);
        this.userRepository.save(user);
        logger.info("Updated failed login count for user [{}]", user.getUserName());
    }

    public void updateLastLogin(SystemUser user) {
        user.setLastLoginDate(new Date());
        this.userRepository.save(user);
        logger.info("Updated last login date for user [{}]", user.getUserName());
    }
}
