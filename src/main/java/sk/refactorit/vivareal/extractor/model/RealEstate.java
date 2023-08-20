package sk.refactorit.vivareal.extractor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class RealEstate {
    private String sourceId;
    private String displayAddressType;
    private String contractType;
    private String[] usableAreas;
    private LocalDateTime createdAt;
    private String[] unitTypes;
    private String id;
    private String[] parkingSpaces;
    private LocalDateTime updatedAt;
    private String[] suites;
    private String[] bathrooms;
    private String[] usageTypes;
    private String[] bedrooms;
    private PricingInfo[] pricingInfos;
    private int buildings;
    private String status;
    private String[] amenities;
    private String constructionStatus;
    private String description;
    private String title;
    private String[] floors;
    private String propertyType;
    private String[] unitSubTypes;
    private String[] ceilingHeight;
    private String legacyId;
    private String[] stamps;
    private Address address;
    private String[] totalAreas;
    private String searchableAmenities;
    private String[] capacityLimit;
}
