package main;

import main.config.DatabaseScript;
import main.exception.DatabaseScriptException;
import main.view.MainFrame;

import java.sql.SQLException;

import static main.util.MessageUtil.DATABASE_ERROR;

public class EventApplication {

    public static void main(String[] args) {
        try {
            DatabaseScript.migrateDatabase();
            MainFrame.runApp();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DatabaseScriptException(DATABASE_ERROR);
        }
    }
}
