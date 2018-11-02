package ru.yusdm.training.springboottest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

public class BaseWebIntegrationTest extends BaseIntegrationTest{

    @Autowired
    protected WebApplicationContext context;

}
