package sf.hackday.farmtotable.voicepump;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class Application {

    @Bean
    public Configuration getConfiguration() {
        Configuration config = new Configuration();

        config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        return config;
    }

    @Bean
    public LiveSpeechRecognizer getRecognizer(Configuration config) throws IOException {
        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(config);
        return recognizer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
