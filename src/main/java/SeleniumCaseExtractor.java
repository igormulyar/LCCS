
/**
 * Created by TRUST on 02.08.2016.
 */
public class SeleniumCaseExtractor implements Extractor {

    public CourtCase getCase(String caseNumber) {
        String link = "";
        return searchByLink(caseNumber, link);
    }

    private CourtCase searchByLink(String caseID,String link){
        //temporary implementation. just for things work
        return new CourtCase("12/12/12 12:00", "522/0000/16", "Иванов к Сидорову", "визнання недійсним договору", "суддя Хабарник М.В.", "цивільна",
                "Приморський, Балківська, 33");
    }
}
