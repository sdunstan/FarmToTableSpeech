package sf.hackday.farmtotable.voicepump;

import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SpeechPoster {

    @Autowired
    private LiveSpeechRecognizer recognizer;

    public void start() {
        recognizer.startRecognition(false);

        while(true) {
            try {
                sendPost(recognizer.getResult().getHypothesis());
            }
            catch (Exception exc) {
                System.out.println("Error sending data: " + exc.getMessage());
            }
        }
        //recognizer.stopRecognition();
    }

    private void sendPost(String resultString) {
        System.out.println("Sending " + resultString);
        HttpEntity<String> result = new HttpEntity<>(resultString);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForLocation("http://192.168.1.104:8090/message", result);
    }

}
