package controller;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.CourtCase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HttpExtractor {

    public List<CourtCase> extractCourtCases(List<String> allIds) {

        List<CourtCase> resultCaseList = new ArrayList<>();
        for (String caseNumber : allIds) {
            Court court = getCourtForRequest(caseNumber); //fetch required headers for http request (collected in controller.HttpExtractor.Court) using case number
            List<CourtCase> caseList = null;
            if (court != null) {
                    caseList = getCourtCases(court.getUrl(), court.getHost(), court.getReferer(), court.getCourtId());
            }
            if (caseList != null) {
                resultCaseList.addAll(caseList.stream()
                        .filter(currentCase -> currentCase.getNumber().equals(caseNumber))
                        .collect(Collectors.toList()));
            }
        }
        return resultCaseList;
        // done? please, check// TODO avoid this (null values)
    }


    /*
    This method makes a http POST-Request to the URL with headers and request body
    url, referer-header and courtId are parameters used in order to make a correct request
    returns a list of court cases fetched from server
    */
    private List<CourtCase> getCourtCases(String url, String host, String referer, String courtId) { //I've just removed throwing that IOException from method signature//TODO: catch this exception
        List<CourtCase> caseList = new ArrayList<CourtCase>();
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.post(url)// TODO spring RestTemplate could be used insted
                    .header("Host", host)
                    .header("Accept", "application/json, text/javascript, */*; q=0.01")
                    .header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .header("Referer", referer)
                    .body(("q_court_id=" + courtId))
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (jsonResponse != null) {
            JSONArray jsonArray = jsonResponse.getBody().getArray(); //done? //TODO: IDEA points that here null pointer exception possible
            for (int i = 0; i < jsonArray.length(); i++) {
                caseList.add(parseCourtCaseFromJson(new JsonNode(jsonArray.get(i).toString()).getObject()));
            }
        }
        return caseList;
    }

    // convert json-object to court case
    // TODO: jackson ObjectMapper can do this automatically
    private static CourtCase parseCourtCaseFromJson(JSONObject jsonCase) {
        return new CourtCase(
                jsonCase.getString("date"),
                jsonCase.getString("number"),
                jsonCase.getString("involved"),
                jsonCase.getString("description"),
                jsonCase.getString("judge"),
                jsonCase.getString("forma"),
                jsonCase.getString("add_address")
        );
    }

    // find out the information about court which is needed for making correct http-request.
    // returns controller.HttpExtractor.Court that consist url, referer, court_id and others
    private Court getCourtForRequest(String caseNumber) {
        //TODO: I do not understand why you have picked an XML for this. JSON is more convenient, and Jackson can do all this job automatically.
        //or even simpler way: use java serialization
        DocumentBuilderFactory domFactory;
        DocumentBuilder documentBuilder;
        Document document = null;
        try {
            domFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = domFactory.newDocumentBuilder();
            document = documentBuilder.parse(new File("src/courts.xml"));
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        NodeList nodeList = document.getElementsByTagName("court");
        List<Court> courtList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            Court court = new Court(element.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue(),
                    element.getElementsByTagName("idInNumber").item(0).getChildNodes().item(0).getNodeValue(),
                    element.getElementsByTagName("courtId").item(0).getChildNodes().item(0).getNodeValue(),
                    element.getElementsByTagName("url").item(0).getChildNodes().item(0).getNodeValue(),
                    element.getElementsByTagName("host").item(0).getChildNodes().item(0).getNodeValue(),
                    element.getElementsByTagName("referer").item(0).getChildNodes().item(0).getNodeValue());
            courtList.add(court);
        }
        //TODO Filtering STREAM !!!
        for (Court court : courtList) {
            if (court.getIdInNumber().equals(caseNumber.substring(0, 3))) {
                return court;
            }
        }
        return null;// TODO: really bad thing. If this is valid case use Optional<Court> as return type. If this is exceptional situation - throw an exception.
    }

    private class Court {// TODO: extract to separate file (in model package)

        private String name;
        private String idInNumber;
        private String courtId;
        private String url;
        private String host;
        private String referer;

        public Court(String name, String idInNumber, String courtId, String url, String host, String referer) {
            this.name = name;
            this.idInNumber = idInNumber;
            this.courtId = courtId;
            this.url = url;
            this.host = host;
            this.referer = referer;
        }

        public String getName() {
            return name;
        }

        public String getIdInNumber() {
            return idInNumber;
        }

        public String getCourtId() {
            return courtId;
        }

        public String getUrl() {
            return url;
        }

        public String getHost() {
            return host;
        }

        public String getReferer() {
            return referer;
        }
    }
}