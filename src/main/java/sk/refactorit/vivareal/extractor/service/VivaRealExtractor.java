package sk.refactorit.vivareal.extractor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import sk.refactorit.vivareal.extractor.model.SearchResponse;

public class VivaRealExtractor {
    private static final Logger log = LoggerFactory.getLogger(VivaRealExtractor.class);
    private static final String BUSINESS = "SALE";
    private static final String FACETS = "amenities";
    private static final String LISTING_TYPE = "USED";
    private static final String CATEGORY_PAGE = "RESULT";
    private static final String DEVELOPMENTS_SIZE = "5";
    private static final String ADDRESS_CITY = "Uberlândia";
    private static final String ADDRESS_STATE = "Minas Gerais";
    private static final String ADDRESS_NEIGHBORHOOD = "Brasil";
    private static final String ADDRESS_COUNTRY = "";
    private static final String UNIT_TYPES = "";
    private static final String UNIT_SUB_TYPES = "";
    private static final String UNIT_TYPES_V3 = "";
    private static final String USAGE_TYPES = "";
    private static final String SORT = "updatedAt DESC sortFilter:pricingInfos.businessType='SALE'";
    private static final String LEVELS = "CITY";
    private static final String INCLUDE_FIELDS = "search(totalCount,result(listings(listing(sourceId,displayAddressType,contractType,usableAreas,id,createdAt,updatedAt,pricingInfos,unitTypes,parkingSpaces,suites,bathrooms,usageTypes,bedrooms,buildings,status,amenities,constructionStatus,description,title,floors,propertyType,unitSubTypes,ceilingHeight,legacyId,stamps,address,totalAreas,searchableAmenities,capacityLimit),link))),page";

    public SearchResponse extract(String addressNeighborhood, String size, String from) {
        WebClient client = WebClient.create("https://glue-api.vivareal.com");
        WebClient.ResponseSpec responseSpec = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/listings")
                        .queryParam("addressCity", ADDRESS_CITY)
                        .queryParam("addressState", ADDRESS_STATE)
                        .queryParam("addressCountry", ADDRESS_COUNTRY)
                        .queryParam("addressNeighborhood", addressNeighborhood)
                        .queryParam("business", BUSINESS)
                        .queryParam("facets", FACETS)
                        .queryParam("unitTypes", UNIT_TYPES)
                        .queryParam("unitSubTypes", UNIT_SUB_TYPES)
                        .queryParam("unitTypesV3", UNIT_TYPES_V3)
                        .queryParam("usageTypes", USAGE_TYPES)
                        .queryParam("listingType", LISTING_TYPE)
                        .queryParam("categoryPage", CATEGORY_PAGE)
                        .queryParam("size", size)
                        .queryParam("from", from)
                        .queryParam("sort", SORT)
                        .queryParam("developmentsSize", DEVELOPMENTS_SIZE)
                        .queryParam("levels", LEVELS)
                        .queryParam("includeFields", INCLUDE_FIELDS)
                        .build())
                .header("x-domain", "www.vivareal.com.br")
                .retrieve();
        return responseSpec.bodyToMono(SearchResponse.class).block();
    }
}
