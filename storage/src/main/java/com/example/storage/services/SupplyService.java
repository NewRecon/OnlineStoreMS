package com.example.storage.services;

import com.example.storage.models.Supply;

import java.util.List;

public interface SupplyService {

    List<Supply> getAllSupplies();
    Supply createSupply(Supply supply);
    Supply getSupplyById(Long id);
    Supply updateSupply(Supply supply);
    void deleteSupplyById(Long id);
    Integer getCountFreeSupply(String name);
    Supply getFirstFreeSupplyByName(String name);
}
