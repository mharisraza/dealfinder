package com.github.mharisraza.dealfinder.models.requestmodels;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DealFinderForm {
    private String productTitle;
    private Double priceRange;
    private Integer numberOfTotalBestDealsToGet;
    private List<String> selectedPlatforms;
}
