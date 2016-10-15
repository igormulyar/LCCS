package model;

/**
 * Created by igor on 21.09.16.
 */

//TODO: this class is only the model. Rest classes should be moved out of model package
public class CourtCase {

    private String date; //TODO: why not LocalDate?
    private String number;
    private String involved;
    private String description;
    private String judge;
    private String forma; //TODO: form? template?
    private String add_address;


    public CourtCase(String date, String number, String involved, String description, String judge, String forma, String add_address) {
        this.date = date;
        this.number = number;
        this.involved = involved;
        this.description = description;
        this.judge = judge;
        this.forma = forma;
        this.add_address = add_address; //TODO: snake_case is not allowed. camelCase only.
    }

    /*public model.CourtCase (String number){ //TODO: remove
        this.number = number;
    }*/

    public String getDate() {
        return date;
    }

    public String getJudge() {
        return judge;
    }

    public String getForma() {
        return forma;
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

    public String getAdd_address() {
        return add_address;
    }

    public String extractCourtIdFromNumber() {
        return number.substring(0, 2);
    }

    //TODO: I would add equals and hashcode

    @Override
    public String toString() {
        return "model.CourtCase{" + "\n" +
                "date  =" + date + "\n" +
                "number = " + number + "\n" +
                "involved = " + involved + "\n" +
                "description = " + description + "\n" +
                "judge = " + judge + "\n" +
                "forma = " + forma + "\n" +
                "add_address = " + add_address + "\n" +
                '}' + "\n";
    }
}
