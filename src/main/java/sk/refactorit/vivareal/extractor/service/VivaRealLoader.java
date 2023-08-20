package sk.refactorit.vivareal.extractor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.refactorit.vivareal.extractor.model.Listing;

import java.util.List;

public class VivaRealLoader {

    private static final Logger log = LoggerFactory.getLogger(VivaRealLoader.class);

    public void load(List<Listing> listings) {
        listings.stream().forEach(listing -> log.debug(listing.toString()));
    }
}
