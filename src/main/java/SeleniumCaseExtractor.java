import java.time.LocalDate;
import java.time.Month;

/**
 * Created by TRUST on 02.08.2016.
 */
public class SeleniumCaseExtractor implements Extractor {

    public CourtCase getCaseInfo(String caseID) {
        //temporary implementation. just for things work
        return new CourtCase(caseID, "Петров О.П. к Сидорову Л. В. при участии 3-го лица - Психиатрический диспансер № 3",
        "Овидиопольский районный суд", "судья Колядун", LocalDate.of(2016, Month.SEPTEMBER, 24));
    }
}
