import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TRUST on 12.07.2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        String unixPath = "/home/igor/Development/exceltable/table1.xls";
        String winPath = "C:\\MyTable.xls";

        FileIOHandler fileIOHandler = new ExcelHandler(unixPath, "Лист1");
        List<String> IDList = fileIOHandler.getIDList(); // получаем список номеров дел из таблицы


        List<CourtCase> resultList = new ArrayList<CourtCase>();
        for (String caseID : IDList){  // итерируемся по списку номеров дел
            resultList.add(new SeleniumCaseExtractor().getCase(caseID));  // при каждом проходе цикла запрашиваем дело по его номеру из списка
                                                                            // и добавляем в список полученных дел
        }

        fileIOHandler.writeAllTheInfo(resultList);
        System.out.println("Done!");


    }
}
