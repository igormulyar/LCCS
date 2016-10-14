import java.io.IOException;
import java.util.List;

/**
 * Created by igor on 14.10.16.
 */
public class Controller {

    private List<CourtCase> caseList;
    {
        try {
            caseList = new ExcelHandler().readCurrentListOfCases();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<CourtCase> showCurrentCases(){
        return caseList;
    }

    public void updateCaseList(){

    }


}
