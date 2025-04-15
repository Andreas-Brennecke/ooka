package org.hbrs.ooka.uebung1.productManagement;

import org.hbrs.ooka.uebung1.entities.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductManagementPort implements ProductManagementInt {
    private ProductController productController = null;

    public static void main(String[] args) {}

    public ProductManagementPort() {
        productController = new ProductController();
        productController.inject(this);
    }

    @Override
    public void openSession() {
        System.out.println(timestamp() + ": " + currentMethodName());
        productController.openSession();
    }

    @Override
    public void closeSession() {
        System.out.println(timestamp() + ": " + currentMethodName());
        productController.closeSession();
    }

    @Override
    public List<Product> getProductByName(String name) {
        System.out.println(timestamp() + ": " + currentMethodName() + " Suchwort: " + name);
        return productController.getProductByName(name);
    }

    @Override
    public Product addProduct(Product product) {
        System.out.println(timestamp() + ": " + currentMethodName() + " Parameter: " + product);
        return productController.addProduct(product);
    }

    @Override
    public Product getProductById(int productId) {
        System.out.println(timestamp() + ": " + currentMethodName() + " Suchwort: " + productId);
        return productController.getProductById(productId);
    }

    @Override
    public void deleteProduct(int productId) {
        System.out.println(timestamp() + ": " + currentMethodName() + " Suchwort: " + productId);
        productController.deleteProduct(productId);
    }

    @Override
    public void updateProduct(Product product) {
        System.out.println(timestamp() + ": " + currentMethodName() + " Parameter: " + product);
        productController.updateProduct(product);
    }

    private String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    private String currentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
