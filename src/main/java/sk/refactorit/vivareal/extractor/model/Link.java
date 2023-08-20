package sk.refactorit.vivareal.extractor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Link {
    private Data data;
    private String name;
    private String href;
    private String rel;
}
