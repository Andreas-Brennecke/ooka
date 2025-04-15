package org.hbrs.ooka.uebung1.cache;

import org.hbrs.ooka.uebung1.entities.Product;

import java.util.List;

public interface CacheInt {
    void put(int key, Product product);
    Product get(int key);
    List<Product> getProductByName(String name);
    void remove(int key);
    boolean contains(int key);
}