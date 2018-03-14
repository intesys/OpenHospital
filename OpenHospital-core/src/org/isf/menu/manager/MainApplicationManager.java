package org.isf.menu.manager;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class MainApplicationManager {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {

        if(applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            PropertyConfigurator.configure(new File("./rsc/log4j.properties").getAbsolutePath());
        }
        return  applicationContext;
    }
}
