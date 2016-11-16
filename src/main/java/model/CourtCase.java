package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by igor on 21.09.16.
 */

//TODO: this class is only the model. Rest classes should be moved out of model package
    // I moved them to controller package
public class CourtCase {

    private String date; //TODO: why not LocalDate?
    // i think now it's not necessary. where to use it?
    private String number;
    private String involved;
    private String description;
    private String judge;
    @JsonProperty("forma")
    private String form; // done //TODO: form? template?
    @JsonProperty("add_address")
    private String address;


    public String getDate() {
        return date;
    }

    public String getJudge() {
        return judge;
    }

    public String getForm() {
        return form;
    }

    public String getNumber() {
        return number;
    }

    public String getInvolved() {
        return involved;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setInvolved(String involved) {
        this.involved = involved;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //TODO: I would add equals and hashcode
    //done/automatically generated

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourtCase courtCase = (CourtCase) o;
        if (!date.equals(courtCase.date)) return false;
        if (!number.equals(courtCase.number)) return false;
        if (!involved.equals(courtCase.involved)) return false;
        if (!description.equals(courtCase.description)) return false;
        if (!judge.equals(courtCase.judge)) return false;
        if (!form.equals(courtCase.form)) return false;
        return address.equals(courtCase.address);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + involved.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + judge.hashCode();
        result = 31 * result + form.hashCode();
        result = 31 * result + address.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "model.CourtCase{" + "\n" +
                "date  =" + date + "\n" +
                "number = " + number + "\n" +
                "involved = " + involved + "\n" +
                "description = " + description + "\n" +
                "judge = " + judge + "\n" +
                "form = " + form + "\n" +
                "address = " + address + "\n" +
                '}' + "\n";
    }
}
