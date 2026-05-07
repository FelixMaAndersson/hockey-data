package se.yrgo.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleClient {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");

        Menu menu = ctx.getBean(Menu.class);
        menu.start();
    }
}