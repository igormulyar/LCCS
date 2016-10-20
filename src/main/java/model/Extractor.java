package model;

/**
 * Created by TRUST on 02.08.2016.
 */

//TODO: I would not use interfaces here (and in FileIOHanler too)
public interface Extractor {

    CourtCase extractCourtCases(String caseNumber);
}
