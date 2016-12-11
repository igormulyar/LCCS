package controller;

import model.*;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by igor on 14.10.16.
 */

@RestController
public class WebController {

    private FileIOHandler ioHandler = new SQLiteHandler2();
    private List<CourtCase> caseList = ioHandler.getCurrentListOfCases();
    private HttpExtractor extractor = new HttpExtractor();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showPge() {
        return getPage();
    }


    @RequestMapping(value = "/tracked_numbers", method = RequestMethod.GET)
    public List<String> showAllNumbers (){
        return ioHandler.getAllNumbers();
    }


    @RequestMapping(value = "/tracked_numbers", method = RequestMethod.POST)
    public void addNumber (@RequestBody String number){
        ioHandler.addNumber(number);
    }


    @RequestMapping(value = "/tracked_numbers/{num}", method = RequestMethod.DELETE)
    public void deleteNumber(@PathVariable("num") String number){
        ioHandler.deleteNumber(number);
    }


    @RequestMapping(value = "/hearings", method = RequestMethod.GET)
    public List<CourtCase> showCurrentCases() {
        updateCaseList();
        return caseList;
    }


    private String getPage() {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader("/home/igor/IdeaProjects/LCCS/src/main/java/view/html/index.html"))) {
            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = reader.readLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void updateCaseList() {
            List<String> allIds = ioHandler.getAllNumbers();
            List<CourtCase> courtCases = extractor.extractCourtCases(allIds);
            ioHandler.save(courtCases);
            caseList = courtCases;
    }

}
