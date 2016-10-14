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
        List<String> caseNumberList = new ArrayList<String>();
        for (int i = 1; i <= myExcelSheet.getLastRowNum(); i++) {
            HSSFRow row = myExcelSheet.getRow(i);
            if (row.getCell(1).getCellType() == HSSFCell.CELL_TYPE_STRING) {
                String caseNumber = row.getCell(1).getStringCellValue();
                rowInit(row);
                caseNumberList.add(caseNumber);
            } else {
                throw new IOException("Wrong cell type in a table!");
            }
        }
        myExcelBook.close();
        return caseNumberList;
    }

    public List<CourtCase> readCurrentListOfCases (){
        List<CourtCase> caseList = new ArrayList<>();
        for (int i = 1; i <= myExcelSheet.getLastRowNum(); i++) {
            HSSFRow row = myExcelSheet.getRow(i);
            CourtCase courtCase = new CourtCase(
                    row.getCell(0).getStringCellValue(),
                    row.getCell(1).getStringCellValue(),
                    row.getCell(2).getStringCellValue(),
                    row.getCell(3).getStringCellValue(),
                    row.getCell(4).getStringCellValue(),
                    row.getCell(5).getStringCellValue(),
                    row.getCell(6).getStringCellValue()
            );
            caseList.add(courtCase);
        }
        return caseList;
    }

    //Private methods...
    public void writeAllTheInfo(List<CourtCase> listOfRows) throws IOException {

        for (CourtCase courtCase : listOfRows) {
            writeOneRow(courtCase);
        }
        for (int i = 0; i < 5; i++) {
            myExcelSheet.autoSizeColumn(i);
        }
    }

    private void rowInit(HSSFRow row) {
        for (int i = 0; i < 7; i++) {
            if (row.getCell(i) == null) {
                row.createCell(i);
            }
        }
    }

    private void writeOneRow(CourtCase courtCase) throws IOException { //TODO method for writting the case to the table by court and involved
        for (int i = 1; i <= myExcelSheet.getLastRowNum(); i++) {
            HSSFRow row = myExcelSheet.getRow(i);
            if (row.getCell(1).toString().equals(courtCase.getNumber())) {
                row.getCell(0).setCellValue(courtCase.getDate());
                row.getCell(1).setCellValue(courtCase.getNumber());
                row.getCell(2).setCellValue(courtCase.getInvolved());
                row.getCell(3).setCellValue(courtCase.getDescription());
                row.getCell(4).setCellValue(courtCase.getJudge());
                row.getCell(5).setCellValue(courtCase.getForma());
                row.getCell(6).setCellValue(courtCase.getAdd_address());
            }
        }
        myExcelBook.write(new FileOutputStream(file));
        myExcelBook.close();
    }

}
