package model;

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
    private String form; // done //TODO: form? template?
    private String add_address;


    public CourtCase(String date, String number, String involved, String description, String judge, String form, String add_address) {
        this.date = date;
        this.number = number;
        this.involved = involved;
        this.description = description;
        this.judge = judge;
        this.form = form;
        this.add_address = add_address; //TODO: snake_case is not allowed. camelCase only.
    }

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

    public String getAdd_address() {
        return add_address;
    }

    public String extractCourtIdFromNumber() {
        return number.substring(0, 2);
    }

    //TODO: I would add equals and hashcode
    //done

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
        return add_address.equals(courtCase.add_address);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + involved.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + judge.hashCode();
        result = 31 * result + form.hashCode();
        result = 31 * result + add_address.hashCode();
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
                "add_address = " + add_address + "\n" +
                '}' + "\n";
    }
}
