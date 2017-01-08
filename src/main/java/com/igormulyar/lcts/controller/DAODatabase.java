package com.igormulyar.lcts.controller;

import com.igormulyar.lcts.model.CourtCase;
import com.igormulyar.lcts.model.ExtendedCourtCase;
import com.igormulyar.lcts.model.NumberTransferObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by igor on 07.01.17.
 */

@Component
public class DAODatabase {

    private CourtCasesRepo courtCasesRepo;
    private NumberRepo numberRepo;

    @Autowired
    public DAODatabase(CourtCasesRepo courtCasesRepo, NumberRepo numberRepo) {
        this.courtCasesRepo = courtCasesRepo;
        this.numberRepo = numberRepo;
    }


    //Numbers
    public List<NumberTransferObject> getAllNumbers (){
        return numberRepo.findAll();
    }

    public void addNumber(String number){
        List<String> stringNumbers = numberRepo.findAll().stream()
                .map(NumberTransferObject::getNumber)
                .collect(Collectors.toList());
        if(!stringNumbers.contains(number)){
            numberRepo.save(new NumberTransferObject(number));
        }
    }

    public void deleteNumberById(int id){
        numberRepo.delete(id);
    }


    //Cases
    public List<ExtendedCourtCase> getAllCases(){
        List<ExtendedCourtCase> extendedCases = new ArrayList<>();
        for(CourtCase courtCase: courtCasesRepo.findAll()){
            extendedCases.add(new ExtendedCourtCase(courtCase, numberRepo.getNumberTransferObjectByNumber(courtCase.getNumber())));
        }
        return extendedCases;
    }

    @Transactional
    public void saveCases(List<CourtCase> courtCases){
        courtCasesRepo.deleteAll();
        courtCasesRepo.save(courtCases);
    }


}
