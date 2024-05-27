package com.ibm.inventory_management.services;

import java.util.List;

import com.ibm.inventory_management.repositories.StockItemRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.ibm.inventory_management.models.StockItem;

@Service
public class StockItemService implements StockItemApi {
    private final StockItemRepository stockItemRepository;

    public StockItemService(StockItemRepository stockItemRepository) {
        this.stockItemRepository = stockItemRepository;
    }

    @Override
    public List<StockItem> listStockItems() {
        return stockItemRepository.findAll();
    }

    @Override
    public void addStockItem(String name, Double price, Integer stock, String manufacturer) throws Exception {
        try {
            stockItemRepository.save(
                    new StockItem(ObjectId.get().toString())
                            .withName(name)
                            .withManufacturer(manufacturer)
                            .withStock(stock)
                            .withPrice(price)
            );
        } catch (Exception e) {
            throw new Exception("",e);
        }
    }

    @Override
    public void updateStockItem(String id, String name, Double price, Integer stock, String manufacturer) throws Exception {
        try {
            StockItem itemToUpdate = stockItemRepository.findById(id).get();

            itemToUpdate.setName(name !=null ? name : itemToUpdate.getName());
            itemToUpdate.setManufacturer(manufacturer != null ? manufacturer : itemToUpdate.getManufacturer());
            itemToUpdate.setPrice(price != null ? price : itemToUpdate.getPrice());
            itemToUpdate.setStock(stock != null ? stock : itemToUpdate.getStock());

            stockItemRepository.save(itemToUpdate);
        } catch (Exception e) {
            throw new Exception("",e);
        }
    }

    @Override
    public void deleteStockItem(String id) throws Exception {
        try {
            stockItemRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception("",e);
        }
    }
}
