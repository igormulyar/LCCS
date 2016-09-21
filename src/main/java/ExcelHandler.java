import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TRUST on 29.07.2016.
 */
public class ExcelHandler implements FileIOHandler {

    private HSSFWorkbook myExcelBook;
    private HSSFSheet myExcelSheet;
    private File file;

    public ExcelHandler(String filePath, String sheetName) throws IOException {
        this.file = new File(filePath);
        myExcelBook = new HSSFWorkbook(new FileInputStream(file));
        myExcelSheet = myExcelBook.getSheet(sheetName);

    }


    public List<String> getIDList() throws IOException {
        List<String> IDList = new ArrayList<String>();
        for (int i=1; i<=myExcelSheet.getLastRowNum(); i++){
            HSSFRow row = myExcelSheet.getRow(i);
            if(row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING){
                String ID = row.getCell(0).getStringCellValue();
                rowInit(row);
                IDList.add(ID);
            } else {
                throw new IOException("Wrong cell type in a table!");
            }
        }
        myExcelBook.close();
        return IDList;
    }

    private void rowInit(HSSFRow row) {
        for (int i=1; i<5; i++){
            if(row.getCell(i)==null){row.createCell(i);}
        }
        //TODO implement the feature with fucking Excel data type - after its implementation in writeOneRow method
    }

    public void writeAllTheInfo(List<CourtCase> listOfRows) throws IOException {

        for (CourtCase courtCase : listOfRows){
            writeOneRow(courtCase);
        }
        for (int i=0; i<5;i++) {
            myExcelSheet.autoSizeColumn(i);
        }
    }

    private void writeOneRow(CourtCase courtCase) throws IOException {
        courtCase.getNumber();

        for (int i=1; i<=myExcelSheet.getLastRowNum(); i++){
            HSSFRow row = myExcelSheet.getRow(i);
            if (row.getCell(0).toString().equals(courtCase.getNumber())){
                row.getCell(1).setCellValue(courtCase.getDescription());
                row.getCell(2).setCellValue(courtCase.getCourt());
                row.getCell(3).setCellValue(courtCase.getJudge());
                row.getCell(4).setCellValue(courtCase.getDate().toString()); //TODO implement compatibility with Excel date
            }
        }
        myExcelBook.write(new FileOutputStream(file));
        myExcelBook.close();
    }

}
