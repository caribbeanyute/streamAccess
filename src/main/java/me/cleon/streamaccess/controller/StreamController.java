package me.cleon.streamaccess.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.cleon.streamaccess.domain.Stream;
import me.cleon.streamaccess.repository.StreamRepository;
import me.cleon.streamaccess.service.SrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/stream")
public class StreamController {

    @Autowired
    SrsService srsService;

    @Autowired
    StreamRepository streamRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/all")
    public @ResponseBody
    Iterable<Stream> getAllStreams() {
        return streamRepository.findAll();
    }

    @GetMapping("/stream/{streamCode}")
    public Stream getStreamByCode(@PathVariable String streamCode) {
        return streamRepository.findByStreamCode(streamCode);
    }

    static String strippedStreamCode(String streamCode) {
        String trim = streamCode.substring(streamCode.lastIndexOf('/') + 1).trim();
        return trim;
    }

    public LinkedHashMap<String, Object> matchLiveStreamsToDBs(ArrayList<LinkedHashMap<String, Object>> streams, String streamCode, String streamKey) {


        return streams.stream()
                .filter(x ->
                        x.get("name").equals(streamKey) && strippedStreamCode((String) x.get("app")).equals(streamCode)
                ).findFirst()
                .orElse(null);

    }


    @GetMapping("/streams")
    @ResponseBody
    public ResponseEntity<Object> getStreams() {

        Map<String, Object> response = srsService.getStreams();
        ArrayList<LinkedHashMap<String, Object>> streams =
                (ArrayList) response.get("streams");

        List<Stream> dbstreams = streamRepository.findAll();


        List<Map<String, Object>> liveStreamMap = new ArrayList<>();
        for (Stream x : dbstreams) {
            Map <String, Object> mapObj = objectMapper.convertValue(x, Map.class);
            mapObj.put("live", matchLiveStreamsToDBs(streams, x.getStreamCode(), x.getStreamKey()));
            liveStreamMap.add(mapObj);
        }


        if (liveStreamMap.isEmpty()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(liveStreamMap, HttpStatus.OK);
    }

    @PostMapping("/stream")
    public ResponseEntity<Object> addStreams(@RequestBody Stream streamBody) {

        //String streamsResp = srsService.getStreams();


        return new ResponseEntity<Object>(null, HttpStatus.OK);

    }

}
