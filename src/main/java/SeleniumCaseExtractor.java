
/**
 * Created by TRUST on 02.08.2016.
 */
public class SeleniumCaseExtractor implements Extractor {

    public CourtCase getCaseInfo(String caseID) {
        String link = "";
        new CourtCase("522/1516/16").extractCourtIdFromNumber();
        return searchByLink(caseID, link);
    }

    private CourtCase searchByLink(String caseID,String link){
        //temporary implementation. just for things work
        return new CourtCase("12/12/12 12:00", "522/0000/16", "Иванов к Сидорову", "визнання недійсним договору", "суддя Хабарник М.В.", "цивільна",
                "Приморський, Балківська, 33");
    }
}
