
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpExtractor implements Extractor {

    public CourtCase getCase(String caseNumber) {
        Court court = getCourtForRequest(caseNumber); //fetch required headers for http request (collected in HttpExtractor.Court) using case number
        List<CourtCase> caseList = null;
        try {
            caseList = getCourtCases(court.getUrl(), court.getHost(), court.getReferer(), court.getCourtId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (CourtCase c : caseList) {
            if (c.getNumber().equals(caseNumber)) {
                return c;
            }
        }

        return null;
    }


    /*
    This method makes a http POST-Request to the URL with headers and request body
    url, referer-header and courtId are parameters used in order to make a correct request
    returns a list of court cases fetched from server
    */
    private List<CourtCase> getCourtCases(String url, String host, String referer, String courtId) throws IOException {
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.post(url)
                    .header("Host", host)
                    .header("Accept", "application/json, text/javascript, */*; q=0.01")
                    .header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .header("Referer", referer)
                    .body(("q_court_id="+courtId))
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = jsonResponse.getBody().getArray();

        List<CourtCase> caseList = new ArrayList<CourtCase>();

        for (int i = 0; i < jsonArray.length(); i++) {
            caseList.add(parseCourtCaseFromJson(new JsonNode(jsonArray.get(i).toString()).getObject()));
        }

        System.out.println(caseList.get(7).toString());

        return caseList;
    }

    // convert json-object to court case
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
    // returns HttpExtractor.Court that consist url, referer, court_id and others
    private Court getCourtForRequest(String caseNumber) {
        DocumentBuilderFactory domFactory;
        DocumentBuilder documentBuilder;
        Document document = null;
        try {
            domFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = domFactory.newDocumentBuilder();
            document = documentBuilder.parse(new File("src/courts.xml"));
        } catch (IOException | ParserConfigurationException | SAXException e){
            e.printStackTrace();
        }

        NodeList nodeList = document.getElementsByTagName("court");
        List<Court> courtList = new ArrayList<>();
        for (int i = 0; i<nodeList.getLength(); i++){
            Element element = (Element)nodeList.item(i);
            Court court = new Court (element.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue(),
                    element.getElementsByTagName("idInNumber").item(0).getChildNodes().item(0).getNodeValue(),
                    element.getElementsByTagName("courtId").item(0).getChildNodes().item(0).getNodeValue(),
                    element.getElementsByTagName("url").item(0).getChildNodes().item(0).getNodeValue(),
                    element.getElementsByTagName("host").item(0).getChildNodes().item(0).getNodeValue(),
                    element.getElementsByTagName("referer").item(0).getChildNodes().item(0).getNodeValue());
            courtList.add(court);
        }
        //TODO Filtering STREAM !!!
        for (Court court : courtList){
            if (court.getIdInNumber().equals(caseNumber.substring(0,2))){
                return court;
            }
        }
        return null;
    }

    private class Court {

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