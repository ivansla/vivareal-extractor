package sk.refactorit.vivareal.extractor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties({ "page" })
public class SearchResponse {
    private Search search;
}
