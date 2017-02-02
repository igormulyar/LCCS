package com.lcts.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.lcts.model.Court;
import com.lcts.model.CourtCase;
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
        List<CourtCase> resultCaseList = new ArrayList<>();
        for (String caseNumber : allIds) {
            Court court = null;
            try {
                court = getCourtForRequest(caseNumber); //fetch required headers for http request (collected in controller.HttpExtractor.Court) using case number
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                System.out.println("MESSAGE: Didn't find coincidence for number " + caseNumber + " when looking for court with court ID: " + caseNumber.substring(0, 3));      // to avoid application crashing when getCourtCases
            }
            if (court == null) {
                continue;
            }
            List<CourtCase> caseList = getCourtCases(court);
            resultCaseList.addAll(caseList.stream()
                    .filter(currentCase -> currentCase.getNumber().equals(caseNumber))
                    .collect(Collectors.toList()));
        }
        // this stream doesn't work correctly // TODO: Try to fix that stream
        /*List<CourtCase> list = allIds.stream()
                .map(this::getCourtForRequest)
                .map(this::getCourtCases)
                .flatMap(List::stream)
                .filter(courtCase -> allIds.contains(courtCase.getNumber()))
                .collect(Collectors.toList());*/
        return resultCaseList;
    }

    /**
     * This method makes a http POST-Request to the URL with headers and request body
     * url, referer-header and courtId are parameters used in order to make a correct request
     * returns a list of court cases fetched from server
     */
    private List<CourtCase> getCourtCases(Court court) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        //ObjectMapper mapper = new ObjectMapper();

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
            List<CourtCase> courtCases = Arrays.asList(mapper.readValue(response.getBody(), CourtCase[].class));
            courtCases.forEach(c -> {
                c.setCourtName(court.getName());
                c.setJudge(c.getJudge().trim());
            });
            return courtCases;
        } catch (IOException e) {
            throw new RuntimeJsonMappingException(e.getMessage());
        }
    }

    private Court getCourtForRequest(String caseNumber) {
        InputStream is = getClass().getResourceAsStream("/props/courts.json");
        try {
            return Arrays.stream(mapper.readValue(is, Court[].class))
                    .filter(c -> c.getIdInNumber().equals(caseNumber.substring(0, 3)))
                    .findAny()
                    .get();
        } catch (IOException e) {
            throw new RuntimeJsonMappingException(e.getMessage());
        }
    }
}

