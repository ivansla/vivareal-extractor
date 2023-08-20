package sk.refactorit.vivareal.extractor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Search {
    private int totalCount;
    private Result result;
}
