package com.igormulyar.lcts.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import com.igormulyar.lcts.model.CourtCase;
import com.igormulyar.lcts.model.ExtendedCourtCase;
import com.igormulyar.lcts.model.NumberTransferObject;
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

    /*@GetMapping("/tracked_numbers")
    public List<NumberTransferObject> showAllNumbers() {
        return daoDatabase.getAllNumbers();
    }*/

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
        List<CourtCase> courtCases = extractor.extractCourtCases(allIds);
        daoDatabase.saveCases(courtCases);

        //creating the list of extendedCourtCases with nullable fields in order to present them for the frontend
        List<ExtendedCourtCase> extendedCourtCases = daoDatabase.getAllCases();
        ListIterator<ExtendedCourtCase> it = extendedCourtCases.listIterator();
        for (NumberTransferObject nto : numberDTOList) {
            while (it.hasNext()) {
                if (!it.next().getNumber().equals(nto)) {
                    it.add(new ExtendedCourtCase(nto));
                }
            }
        }
        return extendedCourtCases;
    }
}
