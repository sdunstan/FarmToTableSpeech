package sf.hackday.farmtotable.voicepump;

import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class Controller {

    @Autowired
    private LiveSpeechRecognizer recognizer;

    private boolean cache = false;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/start")
    public String start() {
       recognizer.startRecognition(cache);
       cache = false;
       StringBuffer buf = new StringBuffer();
       for(int i = 0; i < 2; i++) {
           SpeechResult result = recognizer.getResult();
           buf.append(result.getHypothesis()).append(" ");
       }
       recognizer.stopRecognition();

       sendPost(buf.toString());

       return buf.toString();
    }

    @Async
    private void sendPost(String resultString) {
        HttpEntity<String> result = new HttpEntity<>(resultString);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForLocation("http://localhost:8080/data", result);
    }

    @RequestMapping("/pause")
    public String pause() {
        recognizer.stopRecognition();
        return "Recognizer paused.";
    }

    @RequestMapping("/resume")
    public String resume() {
        recognizer.startRecognition(false);
        return "Recognizer paused.";
    }

    @PostMapping("/data")
    public String acceptData(@RequestBody String result) throws UnsupportedEncodingException {
       System.out.println("Thanks for sending " + URLDecoder.decode(result, "utf-8"));
       return "ok";
    }
}
