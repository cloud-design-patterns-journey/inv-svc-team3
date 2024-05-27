package com.ibm.inventory_management.repositories;

import com.ibm.inventory_management.models.StockItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockItemRepository extends MongoRepository<StockItem, String> {
    Optional<StockItem> findById(String id);
}
