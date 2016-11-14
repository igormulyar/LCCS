package controller;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import model.Court;
import model.CourtCase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
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
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        ObjectMapper mapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Host", host);
        headers.set("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.set("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
        headers.set("Accept-Encoding", "gzip, deflate");
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("X-Requested-With", "XMLHttpRequest");
        headers.set("Referer", referer);
        String body = "q_court_id=" + courtId;

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        List<CourtCase> cases;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        try {
            cases = Arrays.asList(mapper.readValue(response.getBody(), CourtCase[].class));
        } catch (IOException e) {
            throw new RuntimeJsonMappingException(e.getMessage());
        }
        return cases;
    }


    // find out the information about court which is needed for making correct http-request.
    // returns controller.HttpExtractor.Court that consist url, referer, court_id and others
    private Court getCourtForRequest(String caseNumber) {
        //TODO: I do not understand why you have picked an XML for this. JSON is more convenient, and Jackson can do all this job automatically.
        //or even simpler way: use java serialization
        ObjectMapper mapper = new ObjectMapper();
        List<Court> courts = null;
        try {
            courts = Arrays.asList(mapper.readValue(new File("src/courts.json"), Court[].class));
        } catch (IOException e) {
            throw new RuntimeJsonMappingException(e.getMessage());
        }
        //TODO Filtering STREAM !!!
        for (Court court : courts) {
            if (court.getIdInNumber().equals(caseNumber.substring(0, 3))) {
                return court;
            }
        }
        return null;// TODO: really bad thing. If this is valid case use Optional<Court> as return type. If this is exceptional situation - throw an exception.
    }
}