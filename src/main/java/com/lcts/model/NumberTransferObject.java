package com.lcts.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by igor on 13.12.16.
 */
@Entity
public class NumberTransferObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String number;

    public NumberTransferObject() {
    }

    public NumberTransferObject(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "NumberTransferObject{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
