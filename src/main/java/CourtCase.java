import java.time.LocalDate;

/**
 * Created by Yuzer on 01.08.2016.
 */
public class CourtCase {
    private String caseID;
    private String description;
    private String court;
    private String judge;
    private LocalDate date;


    //CONSTRUCTORS
    public CourtCase(String caseID) {
        this.caseID = caseID;
    }

    public CourtCase(String caseID, String description, String court, String judge, LocalDate date) {
        this.caseID = caseID;
        this.description = description;
        this.court = court;
        this.judge = judge;
        this.date = date;
    }

    //GETTERS
    public String getCaseID() {
        return caseID;
    }

    public String extractCourtIdByCaseId(){
        char [] courtID = new char[4];
        for (int i=0; i<4; i++){
            courtID[i] = caseID.toCharArray()[i];
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
