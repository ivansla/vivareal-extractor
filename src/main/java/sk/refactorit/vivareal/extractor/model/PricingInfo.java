package sk.refactorit.vivareal.extractor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PricingInfo {
    private String yearlyIptu;
    private String price;
    private String businessType;
    private String monthlyCondoFee;
}
