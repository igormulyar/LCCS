import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TRUST on 12.07.2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        FileIOHandler fileIOHandler = new ExcelHandler("/home/igor/Development/exceltable/table1.xls", "Лист1");
        List<String> IDList = fileIOHandler.getIDList();

        List<CourtCase> resultList = new ArrayList<CourtCase>();
        for (String caseID : IDList){
            resultList.add(new SeleniumCaseExtractor().getCase(caseID));
        }

        fileIOHandler.writeAllTheInfo(resultList);
        System.out.println("Done!");


    }
}
