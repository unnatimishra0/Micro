// DealItemRepository.java
package com.nagarro.repository;

import com.nagarro.model.DealItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealItemRepository extends JpaRepository<DealItem, String> {
    // You can define custom query methods here if needed
}
