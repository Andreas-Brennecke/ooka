package org.hbrs.ooka.uebung1.productManagement;

import org.hbrs.ooka.uebung1.cache.CacheImpl;
import org.hbrs.ooka.uebung1.cache.CacheInt;
import org.hbrs.ooka.uebung1.entities.Product;

import java.util.List;

public class ProductController implements ProductManagementInt {
    final private ProductRepository productRepository;
    private ProductManagementInt productManagementPort;

    private CacheInt cache = new CacheImpl();

    public ProductController() {
        productRepository = new ProductRepository();
    }


    public void inject(ProductManagementInt productManagementPort) {
        this.productManagementPort = productManagementPort;
    }

    @Override
    public void openSession() {
        productRepository.openConnection();
        productRepository.createTableIfNotExists();
    }

    @Override
    public void closeSession() {
        productRepository.closeConnection();
    }

    @Override
    public List<Product> getProductByName(String name) {
        List<Product> products = cache.getProductByName(name);
        if (products != null) {

            // Achtung, sobald ein Item mit dem Namen im Cache vorhanden ist,
            // wird die Datenbank nicht mehr nach neuen Produkten abgefragt!
            System.out.println("Product was in cache");
            return products;
        } else {
            return productRepository.getProductByName(name);
        }
    }

    @Override
    public Product addProduct(Product product) {
        Product newProduct = productRepository.save(product);
        System.out.println(newProduct);
        cache.put(newProduct.getId(), product);
        return newProduct;
    }

    @Override
    public Product getProductById(int productId) {
        Product product = cache.get(productId);
        if(product != null) {
            System.out.println("Product was in cache");
            return product;
        } else {
            return productRepository.getProductById(productId);
        }
    }

    @Override
    public void deleteProduct(int productId) {
        productRepository.deleteProductById(productId);
        cache.remove(productId);
    }

    @Override
    public void updateProduct(Product product) {
        cache.remove(product.getId());
        productRepository.updateProduct(product);
        cache.put(product.getId(), product);
    }
}
