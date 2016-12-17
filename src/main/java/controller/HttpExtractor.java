package controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import model.Court;
import model.CourtCase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpExtractor {

    private final ObjectMapper mapper;

    @Autowired
    public HttpExtractor(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<CourtCase> extractCourtCases(List<String> allIds) {
        return allIds.stream()
                     .map(this::getCourtForRequest)
                     .map(this::getCourtCases)
                     .flatMap(List::stream)
                     .filter(courtCase -> allIds.contains(courtCase.getNumber()))
                     .collect(Collectors.toList());
    }

    /*
    This method makes a http POST-Request to the URL with headers and request body
    url, referer-header and courtId are parameters used in order to make a correct request
    returns a list of court cases fetched from server
    */
    private List<CourtCase> getCourtCases(Court court) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        ObjectMapper mapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Host", court.getHost());
        headers.set("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.set("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
        headers.set("Accept-Encoding", "gzip, deflate");
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("X-Requested-With", "XMLHttpRequest");
        headers.set("Referer", court.getReferer());
        String body = "q_court_id=" + court.getCourtId();

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(court.getUrl(), HttpMethod.POST, httpEntity, String.class);
        try {
            return Arrays.asList(mapper.readValue(response.getBody(), CourtCase[].class));
        } catch (IOException e) {
            throw new RuntimeJsonMappingException(e.getMessage());
        }
    }

    private Court getCourtForRequest(String caseNumber) {
        try {
            File file = new File(getClass().getResource("/props/courts.json").toURI());
            String courtId = caseNumber.substring(0, 3);
            return Arrays.stream(mapper.readValue(file, Court[].class))
                         .filter(c -> c.getIdInNumber().equals(courtId))
                         .findAny()
                         .get();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeJsonMappingException(e.getMessage());
        }
    }
}
