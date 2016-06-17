package controller;

import core.StreamTaskExecutor;
import model.contactDetails.UserContact;
import org.elasticsearch.common.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ContactBroker;

import java.util.List;

@RestController
public class ElasticFiller {
    public final String CONFIRM_RESPONSE = "Service is preparing to start";
    public final String REJECT_RESPONSE = "Incorrect start time";
    @Value("${response.stram.size}") int responseStreamSize;
    @Autowired private StreamTaskExecutor streamTaskExecutor;
    @Autowired private ContactBroker contactBroker;

    @RequestMapping(value = "/service/start", method = RequestMethod.POST)
    public ResponseEntity<String> startService(@RequestParam DateTime startTime) {
        long startPoint = startTime.getMillis();
        if (startPoint > System.currentTimeMillis()) {
            startTaskExecutionService(startPoint);
            return new ResponseEntity<String>(CONFIRM_RESPONSE, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(REJECT_RESPONSE, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/contacts/streaming/{streamSize}", method = RequestMethod.GET)
    public ResponseEntity<List<UserContact>> getStreamData(@PathVariable int streamSize) {
        ResponseEntity<List<UserContact>> responseEntity;
        if (!streamTaskExecutor.isStreamEnable()) {
            responseEntity = new ResponseEntity<List<UserContact>>(HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            responseEntity = new ResponseEntity<List<UserContact>>(contactBroker.getContacts(responseStreamSize), HttpStatus.OK);
        }
        return responseEntity;
    }


    private void startTaskExecutionService(long startPoint) {
        while (true) {
            if (startPoint <= System.currentTimeMillis()) {
                streamTaskExecutor.setStreamEnable(true);
                break;
            }
        }
    }
}
