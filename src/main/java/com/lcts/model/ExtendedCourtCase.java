package com.lcts.model;

/**
 * Created by igor on 07.01.17.
 */
public class ExtendedCourtCase {

    private String date;
    private NumberTransferObject number;
    private String involved;
    private String description;
    private String judge;
    private String form;
    private String courtName;
    private String address;



    public ExtendedCourtCase(CourtCase courtCase, NumberTransferObject numberDTO) {
        this.date = courtCase.getDate();
        this.involved = courtCase.getInvolved();
        this.description = courtCase.getDescription();
        this.judge = courtCase.getJudge();
        this.form = courtCase.getForm();
        this.address = courtCase.getAddress();
        this.courtName = courtCase.getCourtName();
        this.number = numberDTO;
    }

    public ExtendedCourtCase(NumberTransferObject number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NumberTransferObject getNumber() {
        return number;
    }

    public void setNumber(NumberTransferObject number) {
        this.number = number;
    }

    public String getInvolved() {
        return involved;
    }

    public void setInvolved(String involved) {
        this.involved = involved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtendedCourtCase that = (ExtendedCourtCase) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (involved != null ? !involved.equals(that.involved) : that.involved != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (judge != null ? !judge.equals(that.judge) : that.judge != null) return false;
        if (form != null ? !form.equals(that.form) : that.form != null) return false;
        if (courtName != null ? !courtName.equals(that.courtName) : that.courtName != null) return false;
        return address != null ? address.equals(that.address) : that.address == null;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (involved != null ? involved.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (judge != null ? judge.hashCode() : 0);
        result = 31 * result + (form != null ? form.hashCode() : 0);
        result = 31 * result + (courtName != null ? courtName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExtendedCourtCase{" +
                "date='" + date + '\'' +
                ", number=" + number +
                ", involved='" + involved + '\'' +
                ", description='" + description + '\'' +
                ", judge='" + judge + '\'' +
                ", form='" + form + '\'' +
                ", courtName='" + courtName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
