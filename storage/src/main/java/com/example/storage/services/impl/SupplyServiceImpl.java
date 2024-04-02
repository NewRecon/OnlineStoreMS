package com.example.storage.services.impl;

import com.example.storage.models.Supply;
import com.example.storage.repositories.SupplyRepository;
import com.example.storage.services.SupplyService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyServiceImpl implements SupplyService {
    private final SupplyRepository repository;

    @Override
    public List<Supply> getAllSupplies() {
        return repository.findAll();
    }

    @Override
    public Supply createSupply(Supply supply) {
        return repository.save(supply);
    }

    @Override
    public Supply getSupplyById(Long id) {
        return repository.findById(id).orElseThrow(()->new NotFoundException("supply not found"));
    }

    @Override
    public Supply updateSupply(Supply supply) {
        Supply owner = getSupplyById(supply.getId());
        if (supply.getName()!=null) owner.setName(supply.getName());
        owner.setUsername(supply.getUsername());
        return repository.save(owner);
    }

    @Override
    public void deleteSupplyById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Integer getCountFreeSupply(String name) {
        return repository.countAllByNameAndUsernameIsNull(name);
    }

    @Override
    public Supply getFirstFreeSupplyByName(String name) {
        return repository.getFirstByNameAndUsernameIsNull(name);
    }
}
