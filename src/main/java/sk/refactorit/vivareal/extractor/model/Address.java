package sk.refactorit.vivareal.extractor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Address {
    private String country;
    private String zipCode;
    private String geoJson;
    private String city;
    private String level;
    private String precision;
    private String confidence;
    private String stateAcronym;
    private String source;
    private String ibgeCityId;
    private String zone;
    private String street;
    private String locationId;
    private String district;
    private String name;
    private String state;
    private String streetNumber;
    private String neighborhood;
    private String[] poisList;
    private String[] pois;
    private String[] valuableZones;
    private Point point;
}
