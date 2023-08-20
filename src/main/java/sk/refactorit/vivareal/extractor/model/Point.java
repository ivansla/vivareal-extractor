package sk.refactorit.vivareal.extractor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Point {
    private double lon;
    private double lat;
    private String source;
}
