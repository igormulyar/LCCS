import controller.Controller;
import model.SQLiteHandler;

import java.io.File;
import java.io.IOException;

/**
 * Created by TRUST on 12.07.2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        File unixPath = new File("/home/igor/Development/exceltable/table1.xls");//done //TODO: use Path (or File) instead of String
        File winPath = new File("C:\\MyTable.xls");//done

        new SQLiteHandler();


        Controller controller = new Controller(unixPath);
        System.out.println(controller.showCurrentCases());

        /*OUTPUT:
        [model.CourtCase{
        date  =хз
        number = 520/1553/16-ц
        involved = Позивач: Малигін Олександр Геннадійович, відповідач: Малигіна Єлизавета Вікторівна
        description = усунення від права спадкування за правом представлення
        judge = Луняченко
        forma = цивільна
        add_address = хз
        }
        , model.CourtCase{
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
        System.out.println(controller.showCurrentCases()); //TODO refactor: System.out.println(controller.updateCaseList());


        /*OUTPUT AFTER UPDATING THE CASE LIST:
        [model.CourtCase{
        date  =01.12.2016 10:00
        number = 520/1553/16-ц
        involved = Позивач: Малигін Олександр Геннадійович, відповідач: Малигіна Єлизавета Вікторівна
        description = усунення від права спадкування за правом представлення
        judge =  Луняченко В.О.
        forma = Цивільні справи
        add_address = 65080, Одеська, м. Одеса, вул.Варненська, 3б
        }
        , model.CourtCase{
        date  =14.12.2016 14:30
        number = 522/11272/16-ц
        involved = Позивач: Зофірі Наталя Григорівна, позивач: Яськова Валентина Григорівна, позивач: Трунько Тетяна Григорівна, позивач: Чегурко Григорій Артемович, відповідач: Чегурко Віталій Васильович, третя особа: Шоста Одеська державна нотаріальна контора
        description = про визнання заповіту недійсним
        judge =  Чернявська Л.М.
        forma = Цивільні справи
        add_address = 65029, Одеська, м. Одеса, вул. Балківська, 33
        }
        ....
        ....
        * */
    }
}
