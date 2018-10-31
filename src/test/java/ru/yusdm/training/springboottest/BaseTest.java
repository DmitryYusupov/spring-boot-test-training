package ru.yusdm.training.springboottest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles(value = "test")
public class BaseTest {

    @Test
    public void runnableMethodTest(){
    }
}
