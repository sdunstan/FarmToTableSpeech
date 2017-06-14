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
        recognizer.startRecognition(false);

        while (true) {
            try {
                poster.sendPost(recognizer.getResult().getHypothesis());
            } catch (Exception exc) {
                System.out.println("Error sending data: " + exc.getMessage());
            }
        }
    }
}
