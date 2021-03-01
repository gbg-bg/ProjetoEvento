package main.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseScript {
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";

    private static Connection conn;

    public static void migrateDatabase() throws SQLException {
        conn = connect();
        createDatabaseScript();
        createParticipantTableScript();
        createTrainningRoomTableScript();
        createCoffeeRoomTableScript();
        createTrainningRoomParticipantTableScript();
        createTrainningRoomParticipantFKScript();
        createTrainningRoomFKScript();
        createCoffeeRoomParticipantFKScript();
        conn.close();
    }

    private static Connection connect() throws SQLException {
        try {
            Class.forName(DATABASE_DRIVER);
            return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }

    private static void execScript(String sql) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    private static void createDatabaseScript() throws SQLException {
        execScript( "CREATE DATABASE IF NOT EXISTS `eventdatabase`;");
        execScript( "USE `eventdatabase`;");
    }

    private static void createParticipantTableScript() throws SQLException {
        execScript( "CREATE TABLE IF NOT EXISTS `participant` (" +
                "   `id` INT NOT NULL PRIMARY KEY," +
                "   `name` VARCHAR(150) NOT NULL," +
                "   `surname` VARCHAR(150) NOT NULL," +
                "   `coffeeroom_id` INT NOT NULL" +
                ");");
    }

    private static void createTrainningRoomTableScript() throws SQLException {
        execScript( "CREATE TABLE IF NOT EXISTS `trainningroom` (" +
                "   `id` INT NOT NULL PRIMARY KEY," +
                "   `name` VARCHAR(150) NOT NULL," +
                "   `capacity` INT(8) NOT NULL" +
                ");");
    }

    private static void createCoffeeRoomTableScript() throws SQLException {
        execScript( "CREATE TABLE IF NOT EXISTS `coffeeroom` (" +
                "   `id` INT NOT NULL PRIMARY KEY," +
                "   `name` VARCHAR(150) NOT NULL," +
                "   `capacity` INT(8) NOT NULL" +
                ");");
    }

    private static void createTrainningRoomParticipantTableScript() throws SQLException {
        execScript( "CREATE TABLE IF NOT EXISTS `trainningroom_participant` (" +
                "   `participant_id` INT NOT NULL," +
                "   `trainningroom_id` INT NOT NULL," +
                "   `stage` INT NOT NULL" +
                ");");
    }

    private static void createTrainningRoomParticipantFKScript() throws SQLException {
        execScript( "ALTER TABLE trainningroom_participant ADD FOREIGN KEY (participant_id) REFERENCES participant(id);");
    }

    private static void createTrainningRoomFKScript() throws SQLException {
        execScript( "ALTER TABLE trainningroom_participant ADD FOREIGN KEY (trainningroom_id) REFERENCES trainningroom(id);");
    }

    private static void createCoffeeRoomParticipantFKScript() throws SQLException {
        execScript( "ALTER TABLE participant ADD FOREIGN KEY (coffeeroom_id) REFERENCES coffeeroom(id);");
    }
}
