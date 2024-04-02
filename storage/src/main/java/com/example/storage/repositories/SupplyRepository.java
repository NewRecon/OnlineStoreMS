package com.example.storage.repositories;

import com.example.storage.models.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
    Supply getFirstByNameAndUsernameIsNull(String name);
    Integer countAllByNameAndUsernameIsNull(String name);
}
