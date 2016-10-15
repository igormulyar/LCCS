package controller;

import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 14.10.16.
 */
public class Controller {

    private List<CourtCase> caseList; //TODO: can be replaced by IOHandler.readCurrentListOfCases();
    private FileIOHandler IOHandler;//TODO: lower case for fields ioHandler
    private Extractor extractor = new HttpExtractor();

    public Controller(String filePath) {
        try {
            IOHandler = new ExcelHandler(filePath, "Лист1");
            caseList = IOHandler.readCurrentListOfCases();
        } catch (IOException e) {
            e.printStackTrace();// TODO: I think it is not efficient handling. Runtime exception should be thrown (or existed rethrown)
        }
    }

    //methods to use

    public List<CourtCase> showCurrentCases() {
        return caseList;
    }

    public void updateCaseList() {
        List<String> IDList;//TODO: lower case idList
        try {
            IDList = IOHandler.getIDList();
            List<CourtCase> resultList = new ArrayList<CourtCase>();

            for (String caseID : IDList) {
                resultList.add(extractor.getCase(caseID));
            }

            IOHandler.writeAllTheInfo(resultList);
            caseList = resultList;

        } catch (IOException e) {
            e.printStackTrace(); //TODO: same thing: throw runtime exception
        }

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
