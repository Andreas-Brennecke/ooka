import org.hbrs.ooka.uebung1.productManagement.ProductManagementInt;
import org.hbrs.ooka.uebung1.entities.Product;
import org.hbrs.ooka.uebung1.productManagement.ProductManagementPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProductManagementTest {
    private ProductManagementInt productInt;

    @BeforeEach
    public void setUp() {
        productInt = new ProductManagementPort();
    }

    @Test
    public void testOpenSession() {
        productInt.openSession();
    }

    // @Test
    // public void testCloseSession() {
    //    productInt.closeSession();
    // }

    @Test
    public void openAndCloseSession() {
        productInt.openSession();
        productInt.closeSession();
    }

    @Test
    public void insertProduct() {
        Product product = new Product(1, "My Motor 1.0", 100.0);
        productInt.openSession();
        productInt.addProduct(product);
        productInt.closeSession();
    }

    @Test
    public void getProductByName() {
        String productName =  "My Motor 1.0";
        productInt.openSession();
        List<Product> searchedProducts = productInt.getProductByName(productName);
        for (Product product : searchedProducts) {
            System.out.println(product);
        }
        productInt.closeSession();
    }

    @Test
    public void getProductById() {
        int productId =  1;
        productInt.openSession();
        Product searchedProduct = productInt.getProductById(productId);
        System.out.println(searchedProduct);
        productInt.closeSession();
    }

    @Test
    public void updateProductById() {
        Product product = new Product(8, "Updated Motor", 100.0);
        productInt.openSession();
        productInt.updateProduct(product);
        productInt.closeSession();
    }

    @Test
    public void deleteProductById() {
        int productId =  4;
        productInt.openSession();
        productInt.deleteProduct(productId);
        productInt.closeSession();
    }

    @Test void roundTripProduct() {
        Product newProduct = new Product(1, "TestObjectRoundTrip", 100.0);

        // open Session
        productInt.openSession();

        // create Product
        Product addedProduct = productInt.addProduct(newProduct);

        // read Product
        List<Product> foundProducts = productInt.getProductByName(newProduct.getName());
        for (Product product : foundProducts) {
            System.out.println("Found product: " + product);
        }

        // update Product
        Product updatedProduct = new Product(addedProduct.getId(), "UpdatedTestObjectRoundTrip", 100.0);
        productInt.updateProduct(updatedProduct);

        // delete Product
        productInt.deleteProduct(updatedProduct.getId());

        productInt.closeSession();

    }
}