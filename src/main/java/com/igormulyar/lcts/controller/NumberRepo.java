package com.igormulyar.lcts.controller;

import com.igormulyar.lcts.model.NumberTransferObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by igor on 07.01.17.
 */
@Component
public interface NumberRepo extends CrudRepository<NumberTransferObject, Integer> {

    @Override
    List<NumberTransferObject> findAll();

    NumberTransferObject getNumberTransferObjectByNumber(String number);
}
