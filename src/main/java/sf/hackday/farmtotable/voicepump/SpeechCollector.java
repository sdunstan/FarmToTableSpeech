package sf.hackday.farmtotable.voicepump;

import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Component
public class SpeechCollector {

    @Autowired
    private LiveSpeechRecognizer recognizer;

    @Autowired
    private FarmToTableServerPoster poster;

    public void start() {
        recognizer.startRecognition(false);

        StringBuffer buf = new StringBuffer();
        Instant startTime = Instant.now();
        while (true) {
            try {
                buf.append(recognizer.getResult().getHypothesis()).append(" ");
                Instant endTime = Instant.now();
                Duration duration = Duration.between(startTime, endTime);
                if(duration.getSeconds() >= 5.0) {
                    poster.sendPost(buf.toString());
                    buf.setLength(0);
                    startTime = Instant.now();
                }
            } catch (Exception exc) {
                System.out.println("Error sending data: " + exc.getMessage());
            }
        }
    }
}
