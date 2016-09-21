import java.time.LocalDate;

/**
 * Created by Yuzer on 01.08.2016.
 */
public class CourtCase {

    private LocalDate date;
    private String judge;
    private String forma;
    private String number;
    private String involved;
    private String description;
    private String add_address;
    private String court;




    //CONSTRUCTORS
    public CourtCase(String number) {
        this.number = number;
    }

    public CourtCase(String number, String description, String court, String judge, LocalDate date) {
        this.number = number;
        this.description = description;
        this.court = court;
        this.judge = judge;
        this.date = date;
    }

    //GETTERS
    public String getNumber() {
        return number;
    }

    public String extractCourtIdByCaseId(){
        char [] courtID = new char[4];
        for (int i=0; i<4; i++){
            courtID[i] = number.toCharArray()[i];
        }
        System.out.println(courtID.toString());
        return courtID.toString();
    }

    public String getDescription() {
        return description;
    }

    public String getCourt() {
        return court;
    }

    public String getJudge() {
        return judge;
    }

    public LocalDate getDate() {
        return date;
    }


    //SETTERS
    public void setCourt(String court) {
        this.court = court;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
