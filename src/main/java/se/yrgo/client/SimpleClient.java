package se.yrgo.client;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.exceptions.LeagueNotFoundException;
import se.yrgo.exceptions.PlayerNotFoundException;
import se.yrgo.exceptions.TeamNotFoundException;

import java.sql.*;


public class SimpleClient {
    public static void main(String[] args) {
        printAllTables();
        try (ClassPathXmlApplicationContext container =
                     new ClassPathXmlApplicationContext("application.xml")) {

            Menu menu = container.getBean(Menu.class);
            menu.start();
        } catch (LeagueNotFoundException | TeamNotFoundException | PlayerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printAllTables() {

        String url = "jdbc:hsqldb:file:database.dat";
        String user = "sa";
        String password = "";

        try (
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement()
        ) {

            ResultSet tables = stmt.executeQuery(
                    "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                            "WHERE TABLE_SCHEMA = 'PUBLIC'"
            );

            while (tables.next()) {

                String tableName = tables.getString("TABLE_NAME");

                System.out.println("\n===== " + tableName + " =====");

                ResultSet rows = stmt.executeQuery("SELECT * FROM " + tableName);

                ResultSetMetaData meta = rows.getMetaData();
                int columns = meta.getColumnCount();

                while (rows.next()) {

                    for (int i = 1; i <= columns; i++) {
                        System.out.print(
                                meta.getColumnName(i)
                                        + ": "
                                        + rows.getString(i)
                                        + "   "
                        );
                    }

                    System.out.println();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}