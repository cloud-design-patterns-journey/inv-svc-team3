package com.ibm.inventory_management.services;

import java.util.List;

import com.ibm.inventory_management.models.StockItem;

public interface StockItemApi {
    List<StockItem> listStockItems() throws Exception;

    void addStockItem(String name, Double price, Integer stock, String manufacturer) throws Exception;

    void updateStockItem(String id, String name, Double price, Integer stock, String manufacturer) throws Exception;

    void deleteStockItem(String id) throws Exception;
}
