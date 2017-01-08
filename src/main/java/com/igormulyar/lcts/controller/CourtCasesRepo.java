package com.igormulyar.lcts.controller;

import com.igormulyar.lcts.model.CourtCase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by igor on 07.01.17.
 */
@Component
public interface CourtCasesRepo extends CrudRepository<CourtCase, Long> {

    @Override
    List<CourtCase> findAll();


}
