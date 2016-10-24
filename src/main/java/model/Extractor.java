package model;

import java.util.List;

/**
 * Created by TRUST on 02.08.2016.
 */

//TODO: I would not use interfaces here (and in FileIOHanler too)
public interface Extractor {

    List<CourtCase> extractCourtCases(List<String> allIds);
}
