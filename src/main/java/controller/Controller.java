package controller;

import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 14.10.16.
 */
public class Controller {

    private List<CourtCase> caseList;
    private FileIOHandler IOHandler;
    private Extractor extractor = new HttpExtractor();

    public Controller(String filePath) {
        try {
            IOHandler = new ExcelHandler(filePath, "Лист1");
            caseList = IOHandler.readCurrentListOfCases();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //methods to use

    public List<CourtCase> showCurrentCases() {
        return caseList;
    }

    public void updateCaseList() {
        List<String> IDList;
        try {
            IDList = IOHandler.getIDList();
            List<CourtCase> resultList = new ArrayList<CourtCase>();

            for (String caseID : IDList) {
                resultList.add(extractor.getCase(caseID));
            }

            IOHandler.writeAllTheInfo(resultList);
            caseList = resultList;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCase(String caseNumber) {
        //will be implemented later
    }

    public void deleteCase(String caseNumber) {
        // will be implemented later
    }


}
