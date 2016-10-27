package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by TRUST on 27.10.2016.
 */
public class SQLiteHandler implements FileIOHandler {

    private Connection connection;

    public SQLiteHandler() {
        String url = "jdbc:sqlite:courtCases.db";
        try(Connection conn = DriverManager.getConnection(url)){
            this.connection = conn;
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e){
            throw new RuntimeException("Initializing the connection to SQLite was failed.");
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
