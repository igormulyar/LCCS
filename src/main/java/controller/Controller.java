package controller;

import model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by igor on 14.10.16.
 */
public class Controller {

    private List<CourtCase> caseList; //TODO: can be replaced by ioHandler.readCurrentListOfCases();
    // how? If i write "private List<CourtCase> caseList = ioHandler.readCurrentListOfCases()", there will be
    // checked exception and i'll should write ugly "{ } - blocks" here. Is it necessary?
    private FileIOHandler ioHandler;//done //TODO: lower case for fields ioHandler
    private Extractor extractor = new HttpExtractor();

    public Controller(File file) {
        try {
            ioHandler = new ExcelHandler(file.getPath(), "Лист1");
            caseList = ioHandler.readCurrentListOfCases();// i initialized ioHandler in constructor because handling exception here is convenient to me.
        } catch (IOException e) {
            e.printStackTrace();// TODO: I think it is not efficient handling. Runtime exception should be thrown (or existed rethrown)
        }
    }

    //methods to use

    public List<CourtCase> showCurrentCases() {
        return caseList;
    }

    public void updateCaseList() {
        try {
            List<String> allIds = ioHandler.getAllIds();//done//TODO: lower case idList
            List<CourtCase> courtCases = extractor.extractCourtCases(allIds);
            ioHandler.save(courtCases);
            caseList = courtCases;
        } catch (IOException e) {
            e.printStackTrace(); //TODO: same thing: throw runtime exception
        }
        //done
        /* I would refactor this method in such way:
        List<String> allIds = ioHandler.getAllIds();
        List<CourtCase> courtCases = extractor.extractCourtCases(allIds);
        ioHandler.save(courtCases);
        */
    }

    public void addCase(String caseNumber) {
        //will be implemented later
        //"later" almost always is never
    }

    public void deleteCase(String caseNumber) {
        // will be implemented later
    }


}
