package controller;

import model.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by igor on 14.10.16.
 */
public class Controller {
    private FileIOHandler ioHandler = new SQLiteHandler();
    private List<CourtCase> caseList = ioHandler.getCurrentListOfCases();
    private Extractor extractor = new HttpExtractor();

    public List<String> showAllNumbers (){
        return ioHandler.getAllNumbers();
    }

    public void addNumber (String number){
        ioHandler.addNumber(number);
    }

    public void deleteNumber(String number){
        ioHandler.deleteNumber(number);
    }

    public List<CourtCase> showCurrentCases() {
        return caseList;
    }

    public String updateCaseList() {
        try {
            List<String> allIds = ioHandler.getAllNumbers();
            List<CourtCase> courtCases = extractor.extractCourtCases(allIds);
            ioHandler.save(courtCases);
            caseList = courtCases;
        } catch (IOException e) {
            throw new RuntimeException("XLS-file handling failed");
        }
        return caseList.toString();
    }

}
