package com.example.storage.controllers;

import com.example.storage.models.Supply;
import com.example.storage.services.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplies")
@RequiredArgsConstructor
public class SupplyController {
    private final SupplyService supplyService;

    @GetMapping
    public ResponseEntity<List<Supply>> getAllSupplies(){
        return new ResponseEntity<>(supplyService.getAllSupplies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supply> getSupply(@PathVariable Long id){
        return new ResponseEntity<>(supplyService.getSupplyById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Supply> createSupply(@RequestBody Supply supply){
        return new ResponseEntity<>(supplyService.createSupply(supply), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Supply> updateSupply(@RequestBody Supply supply){
        return new ResponseEntity<>(supplyService.updateSupply(supply), HttpStatus.OK);
    }

    @GetMapping("/{name}/count")
    public ResponseEntity<Integer> getCountFreeSupply(@PathVariable String name){
        return new ResponseEntity<>(supplyService.getCountFreeSupply(name), HttpStatus.OK);
    }

    @GetMapping("/{name}/free")
    public ResponseEntity<Supply> getFirstFreeSupplyByName(@PathVariable String name){
        return new ResponseEntity<>(supplyService.getFirstFreeSupplyByName(name), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteSupply(@PathVariable Long id){
        supplyService.deleteSupplyById(id);
    }
}
