package ru.yusdm.training.springboottest.user.service.impl.springboottest;

import org.springframework.boot.SpringBootConfiguration;

import javax.annotation.PostConstruct;

/**
 * We stop scan components in upp direction
 */
//@SpringBootConfiguration
public class UserServiceImplBootTestStopScanConfig {

    @PostConstruct
    public void postConstruct(){
        System.out.println("Stopper " + UserServiceImplBootTestStopScanConfig.class.getSimpleName());
    }
}
