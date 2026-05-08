package se.yrgo.client;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.exceptions.LeagueNotFoundException;
import se.yrgo.exceptions.PlayerNotFoundException;
import se.yrgo.exceptions.TeamNotFoundException;


public class SimpleClient {
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext container =
                     new ClassPathXmlApplicationContext("application.xml")) {

            Menu menu = container.getBean(Menu.class);
            menu.start();
        } catch (LeagueNotFoundException | TeamNotFoundException | PlayerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}