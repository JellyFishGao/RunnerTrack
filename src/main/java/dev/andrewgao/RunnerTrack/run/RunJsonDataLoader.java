package dev.andrewgao.RunnerTrack.run;

import com.agapsys.exception.RuntimeException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class RunJsonDataLoader implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);
    private final JdbcRunRepository runRepository;
    private final ObjectMapper objectMapper;

    public RunJsonDataLoader(JdbcRunRepository runRepository, ObjectMapper objectMapper) {
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    @Override 
    public void run(String... args) throws Exception {
        if(runRepository.count() == 0){
            try (InputStream InputStream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
                Runs allRuns = objectMapper.readValue(InputStream, Runs.class);
                log.info("Reading {} runs from JSON file", allRuns.runs().size());
                runRepository.saveAll(allRuns.runs());
            } catch (Exception e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Runs from JSON data because the collection already contains some data.");
        }
    }

}
