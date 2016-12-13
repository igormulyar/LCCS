package controller;

import model.CourtCase;
import model.NumberTransferObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TRUST on 27.10.2016.
 */
public class SQLiteHandler2 {

    private JdbcTemplate jdbcTemplate;

    public SQLiteHandler2() {
        System.out.println("Starting connection to DB");
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.sqlite.JDBC.class);
        dataSource.setUrl("jdbc:sqlite:caseStorage.db");
        jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println("Connection established.");
        initDB(); // Temporary solving of DB initialization. Need to decide where to move this. Would it be nice to use Flyway or any similar stuff?
    }


    public List<NumberTransferObject> getAllNumbers() {
        return jdbcTemplate.query("SELECT * FROM numbers", (resultSet, i) -> {
            NumberTransferObject nto = new NumberTransferObject();
            nto.setId(resultSet.getInt("num_id"));
            nto.setNumber(resultSet.getString("number"));
            return nto;
        });
    }


    public List<CourtCase> getCurrentListOfCases() {
        return jdbcTemplate.query("SELECT h.date, n.number, h.involved, h.description, h.judge, h.form, h.address\n" +
                "FROM hearings h\n" +
                "JOIN numbers n ON h.num_id=n.num_id;", (rs, i) -> {
            CourtCase courtCase = new CourtCase();
            courtCase.setDate(rs.getString("date"));
            courtCase.setNumber(rs.getString("number"));
            courtCase.setInvolved(rs.getString("involved"));
            courtCase.setDescription(rs.getString("description"));
            courtCase.setJudge(rs.getString("judge"));
            courtCase.setForm(rs.getString("form"));
            courtCase.setAddress(rs.getString("address"));
            return courtCase;
        });
    }

    @Transactional // hope this stuff will make operations within this method atomic
    public void save(List<CourtCase> listOfRows) {
        //jdbcTemplate.execute("BEGIN TRANSACTION;");
        jdbcTemplate.update("DELETE FROM hearings;");
        for (CourtCase courtCase : listOfRows) {
            int numId = getNumId(courtCase.getNumber());
            String sql = "INSERT INTO hearings (date, num_id, involved, description, judge, form, address) VALUES " +
                    "('" + courtCase.getDate() + "', '" +
                    numId + "', '" +
                    courtCase.getInvolved() + "', '" +
                    courtCase.getDescription() + "', '" +
                    courtCase.getJudge() + "', '" +
                    courtCase.getForm() + "', '" +
                    courtCase.getAddress() + "');\n";
            jdbcTemplate.update(sql);
        }
        //jdbcTemplate.execute("COMMIT");
    }

    public void addNumber(String number) {
        jdbcTemplate.update("INSERT OR IGNORE INTO numbers (number) VALUES (?);", number);
    }

    public void deleteNumberById(int numberId) {
        jdbcTemplate.update("DELETE FROM numbers WHERE num_id = ?;", numberId);
    }


    //---------------
    //PRIVATE METHODS
    private void initDB() {
        jdbcTemplate.execute("PRAGMA FOREIGN_KEYS=ON;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS \"numbers\" (\n" +
                "    \"num_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    \"number\" TEXT UNIQUE NOT NULL);");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS \"hearings\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    \"date\" TEXT,\n" +
                "    \"num_id\" TEXT,\n" +
                "    \"involved\" TEXT,\n" +
                "    \"description\" TEXT,\n" +
                "    \"judge\" TEXT,\n" +
                "    \"form\" TEXT,\n" +
                "    \"address\" TEXT,\n" +
                "    FOREIGN KEY (num_id) REFERENCES numbers (num_id) ON DELETE CASCADE);");
        System.out.println("DB init finished.");
    }

    private int getNumId(String number) {
        return jdbcTemplate.queryForObject("SELECT num_id FROM numbers WHERE number=?", new String[]{number}, Integer.class);
    }
}
