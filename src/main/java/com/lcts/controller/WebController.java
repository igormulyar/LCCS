package com.lcts.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import com.lcts.model.CourtCase;
import com.lcts.model.ExtendedCourtCase;
import com.lcts.model.NumberTransferObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by igor on 14.10.16.
 */

@RestController
public class WebController {

    private final DAODatabase daoDatabase;
    private final HttpExtractor extractor;

    @Autowired
    public WebController(DAODatabase daoDatabase, HttpExtractor extractor) {
        this.daoDatabase = daoDatabase;
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

    @PostMapping("/tracked_numbers")
    public void addNumber(@RequestBody String number) {
        daoDatabase.addNumber(number);
    }

    @DeleteMapping("/tracked_numbers/{id}")
    public void deleteNumber(@PathVariable int id) {
        daoDatabase.deleteNumberById(id);
    }

    @GetMapping("/hearings")
    public List<ExtendedCourtCase> showCurrentCases() {
        return updateCaseList();
    }


    private String getTextFileForBrowser(String filename) {
        StringBuilder sb = new StringBuilder();
        InputStream is = getClass().getResourceAsStream(("/webUI/" + filename));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
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

    private List<ExtendedCourtCase> updateCaseList() {
        //retrieving cases from internet and saving them to the database
        List<NumberTransferObject> numberDTOList = daoDatabase.getAllNumbers();
        List<String> allIds = numberDTOList.stream()
                .map(NumberTransferObject::getNumber)
                .collect(Collectors.toList());
        List<CourtCase> courtCases = extractor.fetchCourtCases(allIds);
        daoDatabase.saveCases(courtCases);
        return getExtendedCourtCases(numberDTOList);
    }

    private List<ExtendedCourtCase> getExtendedCourtCases(List<NumberTransferObject> numberDTOList) {
        //creating the list of extendedCourtCases with nullable fields in order to present them for the frontend
        List<ExtendedCourtCase> extendedCourtCases = daoDatabase.getAllCases();
        List<NumberTransferObject> numbersFromCourtCases = extendedCourtCases.stream()
                .map(ExtendedCourtCase::getNumber)
                .collect(Collectors.toList());
        for (NumberTransferObject nto : numberDTOList) {
            if (!numbersFromCourtCases.contains(nto)) {
                extendedCourtCases.add(new ExtendedCourtCase(nto));
            }
        }
        return extendedCourtCases;
    }
}
