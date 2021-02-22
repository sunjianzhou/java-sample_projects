package com.example.demoproject.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 应用上下文监听器。
 *
 * @author sunjianzhou
 * @date 2021/1/27 18:36
 */

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("上下文监听器，contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        System.out.println("上下文监听器，contextDestroyed");
    }
}
