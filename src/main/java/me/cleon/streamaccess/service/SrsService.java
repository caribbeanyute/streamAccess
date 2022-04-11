package me.cleon.streamaccess.service;

import lombok.extern.slf4j.Slf4j;
import me.cleon.streamaccess.config.AppProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class SrsService {

    WebClient webClient;

    @Autowired
    public SrsService(AppProperties appProps) {

        this.webClient = WebClient
                .builder()
                .baseUrl(appProps.getSrsServerUrl())
                .build();

    }

    public HashMap<String, Object> getStreams() {

        ObjectMapper objectMapper = new ObjectMapper();

        HashMap responseMap = new HashMap<String, Object>();

        String response = webClient.get()
                .uri("/streams/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    log.error("Failure to fetch streams from SRS server: EC: SRSF4");
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
                }).bodyToMono(String.class)
                .block();

        try {

            responseMap = objectMapper.readValue(response, HashMap.class);

        } catch (Exception e) {
            log.error("Response from SRS is not valid json:" + response);
        }

        return responseMap;

    }

}
