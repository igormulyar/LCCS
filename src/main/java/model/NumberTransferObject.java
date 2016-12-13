package model;


/**
 * Created by igor on 13.12.16.
 */
public class NumberTransferObject {

    private int id;
    private String number;

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
