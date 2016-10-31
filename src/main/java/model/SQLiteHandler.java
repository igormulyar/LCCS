package model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
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
            throw new  RuntimeException("Crashed when tried to run SQL-query for initializing the tables in DB.\n"+e);
        }
    }

    @Override
    public List<String> getAllIds() throws IOException {
        List<String> ids = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT number FROM numbers");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ids.add(resultSet.getString("number"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ids;
    }

    @Override
    public List<CourtCase> readCurrentListOfCases() {
        List<CourtCase> caseList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT h.date, n.number, h.involved, h.description, h.judge, h.form, h.address\n" +
                    "FROM hearings h\n" +
                    "JOIN numbers n ON h.num_id=n.num_id;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                CourtCase courtCase = new CourtCase(
                        resultSet.getString("date"),
                        resultSet.getString("number"),
                        resultSet.getString("involved"),
                        resultSet.getString("description"),
                        resultSet.getString("judge"),
                        resultSet.getString("form"),
                        resultSet.getString("address")
                );
                caseList.add(courtCase);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return caseList;
    }

    @Override
    public void save(List<CourtCase> listOfRows) throws IOException {
        try {
            PreparedStatement statement = connection.prepareStatement(""
                    );
            ResultSet resultSet = statement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
