import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by TRUST on 12.07.2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        ExcelHandler excelHandler = new ExcelHandler("C:\\MyTable.xls", "Лист3");
        List<String> IDList = excelHandler.getIDList();
        for (String s : IDList){
            System.out.println(s);
        }


    }
}
