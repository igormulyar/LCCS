import controller.Controller;

import java.io.IOException;

/**
 * Created by TRUST on 12.07.2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        Controller controller = new Controller();
        System.out.println("Current cases: " + controller.showCurrentCases());
        System.out.println("Number list: " + controller.showAllNumbers());
        System.out.println("add some numbers for tracking...");
        controller.addNumber("520/13447/16-п");
        controller.addNumber("520/6302/16-ц");
        controller.addNumber("521/21264/14-ц");
        //controller.addNumber("916/3325/15");

        System.out.println("updating info...");
        System.out.println(controller.updateCaseList().toString());

        System.out.println("deleting one number...");
        controller.deleteNumber("520/13447/16-п");

        //controller.deleteNumber("916/3325/15");

        System.out.println("updating info after deleting one number...");
        System.out.println(controller.updateCaseList().toString());
    }
}
