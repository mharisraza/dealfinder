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
    private double priceRange;
    private List<String> selectedPlatforms;
}
