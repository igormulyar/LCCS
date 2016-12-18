package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import model.CourtCase;
import model.NumberTransferObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by igor on 14.10.16.
 */

@RestController
public class WebController {

    private final SQLiteHandler2 dbHandler ;

    private final HttpExtractor extractor;

    @Autowired
    public WebController(SQLiteHandler2 dbHandler, HttpExtractor extractor) {
        this.dbHandler = dbHandler;
        this.extractor = extractor;
    }

    @GetMapping("/")
    public String getMainPage() {
        return getTextFileForBrowser("index.html");
    }

    @GetMapping("/{file:.+}")
    public String getFile(@PathVariable("file") String filename) {
        return getTextFileForBrowser(filename);
    }

    @GetMapping("/tracked_numbers")
    public List<NumberTransferObject> showAllNumbers() {
        return dbHandler.getAllNumbers();
    }

    @PostMapping("/tracked_numbers")
    public void addNumber(@RequestBody String number) {
        dbHandler.addNumber(number);
    }

    @DeleteMapping("/tracked_numbers/{id}")
    public void deleteNumber(@PathVariable int id) {
        dbHandler.deleteNumberById(id);
    }

    @GetMapping("/hearings")
    public List<CourtCase> showCurrentCases() {
        return updateCaseList();
    }

    private String getTextFileForBrowser(String filename) {
        StringBuilder sb = new StringBuilder();
        InputStream is = getClass().getResourceAsStream(("/webUI/" + filename));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private List<CourtCase> updateCaseList() {
        List<String> allIds = dbHandler.getAllNumbers().stream()
                                       .map(NumberTransferObject::getNumber)
                                       .collect(Collectors.toList());
        List<CourtCase> courtCases = extractor.extractCourtCases(allIds);
        dbHandler.save(courtCases);
        return courtCases;
    }
}
