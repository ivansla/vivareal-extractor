package sk.refactorit.vivareal.extractor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class VivaRealLastUpdateService {
    private static final Logger log = LoggerFactory.getLogger(VivaRealLastUpdateService.class);
    private static final String LAST_UPDATE_AT = "lastUpdateAt";
    private static final String LAST_UPDATE_AT_SOURCE_ID = "lastUpdateSourceId";
    @Value("${vivareal-extractor.lastUpdatePath}")
    private String lastUpdatePath;
    public void saveLastUpdate(VivaRealLastUpdate lastUpdate) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(lastUpdatePath)))) {
            pw.println(LAST_UPDATE_AT + "=" + lastUpdate.getUpdatedAt());
            pw.println(LAST_UPDATE_AT_SOURCE_ID + "=" + lastUpdate.getUpdatedAtSourceId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Failed to save last update.", e);
        }
    }

    public VivaRealLastUpdate loadLastUpdate() {
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(new FileInputStream(lastUpdatePath)));
        } catch (FileNotFoundException e) {
            log.warn("Couldn't find lastUpdate.cfg at path: {}. Creating default VivaRealLastUpdate object", lastUpdatePath);
            return VivaRealLastUpdate.builder()
                    .updatedAt(LocalDateTime.MIN.format(DateTimeFormatter.ISO_DATE_TIME))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Failed to load last update.", e);
        }
        return VivaRealLastUpdate.builder()
                .updatedAt(props.getProperty(LAST_UPDATE_AT))
                .build();
    }
}
