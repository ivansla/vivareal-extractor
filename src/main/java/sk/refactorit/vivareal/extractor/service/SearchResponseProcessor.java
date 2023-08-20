package sk.refactorit.vivareal.extractor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import sk.refactorit.vivareal.extractor.model.Listing;
import sk.refactorit.vivareal.extractor.model.SearchResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class SearchResponseProcessor {
    private static final Logger log = LoggerFactory.getLogger(SearchResponseProcessor.class);
    @Value("${vivareal-extractor.retrievalSize}")
    private String retrievalSize;
    @Autowired
    private VivaRealExtractor vivaRealExtractor;
    @Autowired
    private VivaRealLoader vivaRealLoader;

    public void processAllListings(SearchResponse searchResponse,
                                   String from,
                                   LocalDateTime lastUpdateAt,
                                   String neighborhood) {
        List<Listing> listings = extractListingFromSearchResponse(searchResponse).stream()
                .filter(listing -> listing.getListing().getUpdatedAt().isAfter(lastUpdateAt))
                .collect(Collectors.toList());
        log.debug("Processing items from {}, quantity {}", from, listings.size());

//        vivaRealLoader.load(listings);

        if(!listings.isEmpty()) {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            searchResponse = vivaRealExtractor.extract(neighborhood, retrievalSize, getNextFromValue(from));
            log.debug("Total Count: {}", searchResponse.getSearch().getTotalCount());
            processAllListings(searchResponse, getNextFromValue(from), lastUpdateAt, neighborhood);
        }
    }

    private String getNextFromValue(String from) {
        return String.valueOf(Integer.parseInt(from) + Integer.parseInt(retrievalSize));
    }

    private List<Listing> extractListingFromSearchResponse(SearchResponse searchResponse) {
        return Optional.ofNullable(searchResponse)
                .map(sr -> sr.getSearch().getResult().getListings())
                .orElseThrow(() -> new IllegalArgumentException(
                        format("Failed to retrieve listings from searchResponse. %s", searchResponse)));
    }
}
