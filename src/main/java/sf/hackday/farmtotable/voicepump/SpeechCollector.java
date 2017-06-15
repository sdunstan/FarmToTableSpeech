package sf.hackday.farmtotable.voicepump;

import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpeechCollector {

    @Autowired
    private LiveSpeechRecognizer recognizer;

    @Autowired
    private FarmToTableServerPoster poster;

    public void start() {
        recognizer.startRecognition(true);
        while (true) {
            try {
                String result = recognizer.getResult().getHypothesis();
                if(result != null && result.length() > 18) {
                    System.out.println("SENDING: " + result);
                }
                //recognizer.stopRecognition();
                //recognizer.startRecognition(true);
            } catch (Exception exc) {
                System.out.println("Error sending data: " + exc.getMessage());
            }
        }
    }
}
