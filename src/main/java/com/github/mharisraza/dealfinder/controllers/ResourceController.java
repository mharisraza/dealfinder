package com.github.mharisraza.dealfinder.controllers;

import com.github.mharisraza.dealfinder.models.Product;
import com.github.mharisraza.dealfinder.models.requestmodels.DealFinderForm;
import com.github.mharisraza.dealfinder.services.DealFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ResourceController {

    @Autowired private DealFinderService dealFinderService;

    @PostMapping(value = "/get-deal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBestDeals(@RequestBody DealFinderForm dealFinderForm) {
         List<Product> scrapedDeals = dealFinderService.getBestDeals(dealFinderForm);
        return ResponseEntity.status(HttpStatus.OK).body(scrapedDeals);
    }

}
