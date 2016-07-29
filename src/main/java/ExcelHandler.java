import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by TRUST on 29.07.2016.
 */
public class ExcelHandler {

    private String filePath = "C:\\MyTable.xls";


    public ExcelHandler() throws IOException {
        File file = new File(filePath);
        HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet myExcelSheet = myExcelBook.getSheet("Лист3");
        HSSFRow row = myExcelSheet.getRow(1);

        if(row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING){
            String name = row.getCell(0).getStringCellValue();
            System.out.println("name : " + name);
        } else {
            System.out.println("Something wrong with cell type.");
        }
        myExcelBook.close();
    }


}
