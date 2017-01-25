package com.lcts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by igor on 21.09.16.
 */

@Entity
@JsonIgnoreProperties
public class CourtCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String date;
    private String number;
    @Column(length = 1200)
    private String involved;
    @Lob
    private String description;
    private String judge;
    @JsonProperty("forma")
    private String form;
    private String courtName;
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

    public String getCourtName() {
        return courtName;
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

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourtCase courtCase = (CourtCase) o;

        if (date != null ? !date.equals(courtCase.date) : courtCase.date != null) return false;
        if (number != null ? !number.equals(courtCase.number) : courtCase.number != null) return false;
        if (involved != null ? !involved.equals(courtCase.involved) : courtCase.involved != null) return false;
        if (description != null ? !description.equals(courtCase.description) : courtCase.description != null)
            return false;
        if (judge != null ? !judge.equals(courtCase.judge) : courtCase.judge != null) return false;
        if (form != null ? !form.equals(courtCase.form) : courtCase.form != null) return false;
        if (courtName != null ? !courtName.equals(courtCase.courtName) : courtCase.courtName != null) return false;
        return address != null ? address.equals(courtCase.address) : courtCase.address == null;
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
        return "CourtCase{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", number='" + number + '\'' +
                ", involved='" + involved + '\'' +
                ", description='" + description + '\'' +
                ", judge='" + judge + '\'' +
                ", form='" + form + '\'' +
                ", courtName='" + courtName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
