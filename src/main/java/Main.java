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

        Controller controller = new Controller(unixPath);
        System.out.println(controller.showCurrentCases());

        /*OUTPUT:
        [CourtCase{
        date  =хз
        number = 520/1553/16-ц
        involved = Позивач: Малигін Олександр Геннадійович, відповідач: Малигіна Єлизавета Вікторівна
        description = усунення від права спадкування за правом представлення
        judge = Луняченко
        forma = цивільна
        add_address = хз
        }
        , CourtCase{
        date  =хз
        number = 522/11272/16-ц
        involved = хз
        description = хз
        judge = хз
        forma = хз
        add_address = хз
        }
        ............
        */

        controller.updateCaseList();
        System.out.println(controller.showCurrentCases());

    }
}
