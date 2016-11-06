package model;

import java.io.IOException;
import java.util.List;

/**
 * Created by Yuzer on 31.07.2016.
 */
public interface FileIOHandler {

    List<String> getAllNumbers();

    List<CourtCase> getCurrentListOfCases();

    void save(List<CourtCase> listOfRows) throws IOException;

    void addNumber(String number);

    void deleteNumber(String number);

}
