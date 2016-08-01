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

        WebDriver driver = new FirefoxDriver();
        driver.get("https://github.com/Vedenin/useful-java-links/tree/master/link-rus#%D0%91%D0%B5%D0%B7%D0%BE%D0%BF%D0%B0%D1%81%D0%BD%D0%BE%D1%81%D1%82%D1%8C-%D0%B8-%D0%B0%D0%B2%D1%82%D0%BE%D1%80%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F");
        System.out.println(driver.getTitle());
        driver.close();
        driver.quit();

    }
}
