package se.yrgo.client;


import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SimpleClient {
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext container =
                     new ClassPathXmlApplicationContext("application.xml")) {

            Menu menu = container.getBean(Menu.class);
            menu.start();
        }
    }
}