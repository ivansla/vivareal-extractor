package sk.refactorit.vivareal.extractor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Data {
    private String streetNumber;
    private String state;
    private String city;
    private String zone;
    private String neighborhood;
    private String street;
}
