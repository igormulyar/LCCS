package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by TRUST on 27.10.2016.
 */
public class SQLiteHandler implements FileIOHandler {

    private Connection connection;

    public SQLiteHandler() {
        String url = "jdbc:sqlite:caseStorage.db";
        try{
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Initializing the connection to SQLite was failed.");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));

        //Temporary solving of DB initialization. Need to decide where to move this
        try {
            String sql = "CREATE TABLE IF NOT EXISTS \"numbers\" (\n" +
                    "    \"num_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "    \"number\" TEXT NOT NULL\n" +
                    ");\n" +
                    "CREATE UNIQUE INDEX unq_number ON numbers(number);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "CREATE TABLE IF NOT EXISTS \"hearings\" (\n" +
                    "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "    \"date\" TEXT,\n" +
                    "    \"num_id\" TEXT,\n" +
                    "    \"involved\" TEXT,\n" +
                    "    \"description\" TEXT,\n" +
                    "    \"judge\" TEXT,\n" +
                    "    \"form\" TEXT,\n" +
                    "    \"address\" TEXT\n" +
                    ");";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("DB init finished.");
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
            throw new  RuntimeException("Crashed when tried to run SQL-query for initializing the tables in DB.\n");
        }


    }

    @Override
    public List<String> getAllIds() throws IOException {
        return null;
    }

    @Override
    public List<CourtCase> readCurrentListOfCases() {
        return null;
    }

    @Override
    public void save(List<CourtCase> listOfRows) throws IOException {

    }
}
