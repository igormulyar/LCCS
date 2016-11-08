package controller;

import model.CourtCase;

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
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
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
            String sql = "PRAGMA FOREIGN_KEYS=ON;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            sql = "CREATE TABLE IF NOT EXISTS \"numbers\" (\n" +
                    "    \"num_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "    \"number\" TEXT UNIQUE NOT NULL);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            sql = "CREATE TABLE IF NOT EXISTS \"hearings\" (\n" +
                    "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "    \"date\" TEXT,\n" +
                    "    \"num_id\" TEXT,\n" +
                    "    \"involved\" TEXT,\n" +
                    "    \"description\" TEXT,\n" +
                    "    \"judge\" TEXT,\n" +
                    "    \"form\" TEXT,\n" +
                    "    \"address\" TEXT,\n" +
                    "    FOREIGN KEY (num_id) REFERENCES numbers (num_id) ON DELETE CASCADE);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("DB init finished.");
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Crashed when tried to run SQL-query for initializing the tables in DB.\n" + e);
        }
    }

    @Override
    public List<String> getAllNumbers() {
        List<String> ids = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT number FROM numbers");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getString("number"));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ids;
    }

    @Override
    public List<CourtCase> getCurrentListOfCases() {
        List<CourtCase> caseList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT h.date, n.number, h.involved, h.description, h.judge, h.form, h.address\n" +
                            "FROM hearings h\n" +
                            "JOIN numbers n ON h.num_id=n.num_id;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return caseList;
    }

    @Override
    public void save(List<CourtCase> listOfRows) {
        if (listOfRows.size()>0) {
            try {
                PreparedStatement statement = connection.prepareStatement("BEGIN TRANSACTION;");
                statement.executeUpdate();
                statement = connection.prepareStatement("DELETE FROM hearings;");
                statement.executeUpdate();
                for (CourtCase courtCase : listOfRows) {
                    int numId = getNumId(courtCase.getNumber());
                    String sql = "INSERT INTO hearings (date, num_id, involved, description, judge, form, address) VALUES " +
                            "('" + courtCase.getDate() + "', '" +
                            numId + "', '" +
                            courtCase.getInvolved() + "', '" +
                            courtCase.getDescription() + "', '" +
                            courtCase.getJudge() + "', '" +
                            courtCase.getForm() + "', '" +
                            courtCase.getAdd_address() + "');\n";
                    statement = connection.prepareStatement(sql);
                    statement.executeUpdate();
                }
                statement = connection.prepareStatement("COMMIT;");
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void addNumber(String number) {
        try {
            String sql = "INSERT OR IGNORE INTO numbers (number) VALUES (?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, number);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteNumber(String number) {
        try {
            String sql = "DELETE FROM numbers WHERE number = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, number);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private int getNumId(String number) {
        int numId;
        try {
            String sql = "SELECT num_id FROM numbers WHERE number=?";
            PreparedStatement stmnt = connection.prepareStatement(sql);
            stmnt.setString(1, number);
            ResultSet resultSet = stmnt.executeQuery();
            resultSet.next();
            numId = resultSet.getInt("num_id");
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return numId;
    }
}
