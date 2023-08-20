package sk.refactorit.vivareal.extractor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import sk.refactorit.vivareal.extractor.model.SearchResponse;
import sk.refactorit.vivareal.extractor.service.SearchResponseProcessor;
import sk.refactorit.vivareal.extractor.service.VivaRealExtractor;
import sk.refactorit.vivareal.extractor.service.VivaRealLastUpdate;
import sk.refactorit.vivareal.extractor.service.VivaRealLastUpdateService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

@EnableScheduling
@SpringBootApplication
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static final String ADDRESS_NEIGHBORHOOD_BRASIL = "Brasil";
    private static final String FROM = "";
    @Autowired
    private VivaRealExtractor vivaRealExtractor;
    @Autowired
    private SearchResponseProcessor searchResponseProcessor;
    @Autowired
    private VivaRealLastUpdateService vivaRealLastUpdateService;
    @Value("${vivareal-extractor.retrievalSize}")
    private String retrievalSize;

    private LocalDateTime lastUpdatedAt;
    private String sourceId;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = null;
        try {
            ctx = SpringApplication.run(App.class, args);
            log.info("starting the application");
            App myApp = ctx.getBean(App.class);
//            myApp.run();
//            log.info("application finished");
//            ctx.close();
        } catch (Exception e) {
            log.error("Application failed.", e);
            if (ctx != null) {
                ctx.close();
            }
            System.exit(1);
        }
    }

    @Scheduled(fixedRate = 60000)
    private void run() {
        SearchResponse resultJson = vivaRealExtractor.extract(ADDRESS_NEIGHBORHOOD_BRASIL, retrievalSize, FROM);
        VivaRealLastUpdate vivaRealLastUpdate = vivaRealLastUpdateService.loadLastUpdate();
        LocalDateTime lastUpdatedAt = extractLastUpdatedAt(vivaRealLastUpdate);

        if (!hasNewUpdates(extractUpdatedAt(resultJson), lastUpdatedAt)) {
            log.debug("No new updates.");
            return;
        }

        searchResponseProcessor.processAllListings(resultJson, "0", lastUpdatedAt, ADDRESS_NEIGHBORHOOD_BRASIL);

        log.debug("Last Update At before save: {}", extractUpdatedAt(resultJson));
        vivaRealLastUpdate = VivaRealLastUpdate.builder()
                .updatedAt(extractUpdatedAt(resultJson).format(DateTimeFormatter.ISO_DATE_TIME))
                .updatedAtSourceId(extractSourceId(resultJson))
                .build();

        vivaRealLastUpdateService.saveLastUpdate(vivaRealLastUpdate);
    }

    private boolean hasNewUpdates(LocalDateTime updatedAt, LocalDateTime lastUpdatedAt) {
        if (lastUpdatedAt == null) {
            return true;
        }
        log.debug("Last Update At loaded: {}", lastUpdatedAt);
        return updatedAt.isAfter(lastUpdatedAt);
    }

    private LocalDateTime extractUpdatedAt(SearchResponse searchResponse) {
        return searchResponse.getSearch().getResult().getListings().stream()
                .findFirst()
                .map(listing -> listing.getListing().getUpdatedAt())
                .orElseThrow(() -> new IllegalArgumentException(
                        format("Failed to retrieve UpdateAt from result. %s", searchResponse)));
    }

    private String extractSourceId(SearchResponse searchResponse) {
        return searchResponse.getSearch().getResult().getListings().stream()
                .findFirst()
                .map(listing -> listing.getListing().getSourceId())
                .orElseThrow(() -> new IllegalArgumentException(
                        format("Failed to retrieve SourceId from result. %s", searchResponse)));
    }

    private LocalDateTime extractLastUpdatedAt(VivaRealLastUpdate vivaRealLastUpdate) {
        if (StringUtils.isBlank(vivaRealLastUpdate.getUpdatedAt())) {
            return LocalDateTime.MIN;
        }
        return LocalDateTime.parse(vivaRealLastUpdate.getUpdatedAt(), DateTimeFormatter.ISO_DATE_TIME);
    }
}
