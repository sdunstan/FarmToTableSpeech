package sf.hackday.farmtotable.voicepump;

import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FarmToTableServerPoster {

    @Async
    public void sendPost(final String resultString) {
        if (resultString != null && resultString.trim().length() > 1) {
            System.out.println("Sending " + resultString);
            HttpEntity<String> result = new HttpEntity<>(resultString);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForLocation("http://192.168.1.102:8090/message", result);
        }
    }
}
