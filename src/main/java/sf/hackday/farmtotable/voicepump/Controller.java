package sf.hackday.farmtotable.voicepump;

import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
       for(int i = 0; i < 3; i++) {
           SpeechResult result = recognizer.getResult();
           buf.append(result.getHypothesis()).append(" ");
       }
       recognizer.stopRecognition();

       return buf.toString();
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
}
