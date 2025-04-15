package org.hbrs.ooka.uebung1.cache;

import org.hbrs.ooka.uebung1.entities.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheImpl implements CacheInt {
    final private Map<Integer, Product> cache = new HashMap<Integer, Product>();

    @Override
    public void put(int key, Product product) {
        cache.put(key, product);
    }

    @Override
    public Product get(int key) {
        return cache.get(key);
    }

    @Override
    public List<Product> getProductByName(String name) {
        List<Product> products = new ArrayList<Product>();

        for (Product product : cache.values()) {
            if (product.getName().equals(name)) {
                products.add(product);
            }

            return products;
        }
        return null;
    }

    @Override
    public void remove(int key) {
        cache.remove(key);
    }

    @Override
    public boolean contains(int key) {
        return cache.containsKey(key);
    }
}
