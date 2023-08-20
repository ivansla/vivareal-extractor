package sk.refactorit.vivareal.extractor.service;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class VivaRealLastUpdate {
    private String updatedAt;
    private String updatedAtSourceId;
}
