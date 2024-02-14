package com.github.mharisraza.dealfinder.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String linkToProduct;
    private Double priceOfProduct;
    private Double rating;
    private Integer discount;
    private Integer numberOfReviews;
    private String platform;


}
