package com.test.demo.Controller;

import com.test.demo.model.CurrentPrice;

import org.springframework.http.MediaType;
import com.test.demo.service.CurrentPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final List<CurrentPrice> currentPriceDB = new ArrayList<>();

    @Autowired
    private CurrentPriceService currentPriceService;

    @GetMapping("/{id}")
    public ResponseEntity<CurrentPrice> getProduct(@PathVariable("id") Integer id) {

    	CurrentPrice product = currentPriceService.getCurrentPrice(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<CurrentPrice>> getAll() {
    	
    	List<CurrentPrice> products = currentPriceService.getAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<CurrentPrice> createOne(@RequestBody CurrentPrice request) {
    	
    	CurrentPrice currentPrice = currentPriceService.createOne(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(currentPrice.getId())
                .toUri();

        return ResponseEntity.created(location).body(currentPrice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrentPrice> updateOne(@PathVariable("id") Integer id, @RequestBody CurrentPrice request) {
    	
    	CurrentPrice product = currentPriceService.updCurrentPrice(id, request);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
    	
    	currentPriceService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }
}
