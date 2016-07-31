import org.apache.poi.hssf.record.pivottable.StreamIDRecord;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TRUST on 29.07.2016.
 */
public class ExcelHandler {

    private String filePath = "C:\\MyTable.xls";
    private String sheetName = "Лист3";


    public ExcelHandler() throws IOException {

        HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
        HSSFSheet myExcelSheet = myExcelBook.getSheet(sheetName);

         System.out.println("Last column number:"+myExcelSheet.getLastRowNum());
        List<String> IDList = new ArrayList<String>();

        for (int i=1; i<=myExcelSheet.getLastRowNum(); i++){
            HSSFRow row = myExcelSheet.getRow(i);
            if(row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING){
                String ID = row.getCell(0).getStringCellValue();
                System.out.println("ID #\""+i+"\" : " + ID);
                IDList.add(ID);
            } else {
                System.out.println("Something wrong with cell type. Uncorrect cell type.");
            }
        }

        myExcelBook.close();
    }


}
