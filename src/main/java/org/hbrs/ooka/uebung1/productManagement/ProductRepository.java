package org.hbrs.ooka.uebung1.productManagement;

import org.hbrs.ooka.uebung1.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private boolean session = false;

    // Weitere CRUD-Methoden können hinzugefügt werden
    public Product save(Product product) {
        if(session) {
            Connection connection;
            try {
                connection = DatabaseConnection.getConnection();
                String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
                try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, product.getName());
                    ps.setDouble(2, product.getPrice());
                    ps.executeUpdate();

                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        System.out.println("Product " + product.getName() + " inserted successfully");
                        connection.close();
                        return new Product(generatedId, product.getName(), product.getPrice());
                    } else {
                        throw new SQLException("Could not insert new product");
                    }
                } catch (SQLException e) {
                    connection.close();
                    throw new RuntimeException("Error while inserting product: " + e.getMessage());
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error while inserting product: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("There is no session started. Start a session first.");
        }
        // Siehe auch Beispiel in ConnectionTest.java
    }

    public Product getProductById(int productId) {
        if(session) {
            Connection connection;
            try {
                connection = DatabaseConnection.getConnection();

                String sql = "SELECT * FROM products WHERE id = ? LIMIT 1";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, productId);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        connection.close();
                        return new Product(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getDouble("price")
                        );
                    } else {
                        connection.close();
                        return null;
                    }
                } catch (SQLException e) {
                    connection.close();
                    throw new RuntimeException("Error while getting product by id: " + e.getMessage());
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("There is no session started. Start a session first.");
        }
    }

    public List<Product> getProductByName(String name) {
        if(session) {
            Connection connection;
            try {
                connection = DatabaseConnection.getConnection();
                List<Product> products = new ArrayList<>();

                String sql = "SELECT * FROM products WHERE name = ?";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, name);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        Product product = new Product(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getDouble("price")
                        );
                        products.add(product);
                    }
                    connection.close();
                    return products;
                } catch (SQLException e) {
                    connection.close();
                    throw new RuntimeException("Error while getting product by name: " + e.getMessage());
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("There is no session started. Start a session first.");
        }
    }

    public void updateProduct(Product product) {
        if(session) {
            Connection connection;
            try {
                connection = DatabaseConnection.getConnection();
                String sql = "UPDATE products SET name = ?, price = ? WHERE id = ?;";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, product.getName());
                    ps.setDouble(2, product.getPrice());
                    ps.setInt(3, product.getId());
                    ps.execute();
                    System.out.println("Product " + product.getName() + " updated successfully");
                    connection.close();
                } catch (SQLException e) {
                    connection.close();
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("There is no session started. Start a session first.");
        }
    }

    public void deleteProductById(int productId) {
        if(session) {
            Connection connection;
            try {
                connection = DatabaseConnection.getConnection();
                String sql = "DELETE FROM products WHERE id = ?";

                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, productId);
                    ps.execute();
                    System.out.println("Product " + productId + " deleted successfully");
                    connection.close();
                } catch (SQLException e) {
                    connection.close();
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("There is no session started. Start a session first.");
        }
    }


    public void openConnection() {
        if(!session) {
            session = true;
        } else {
            throw new RuntimeException("There is already a session started. Use this session first.");
        }
    }

    public void closeConnection() {
        if(session) {
            session = false;
        } else {
            throw new RuntimeException("There is no session started. Start a session first.");
        }
    }

    public void createTableIfNotExists() {
        if(session) {
            Connection connection;
            try {
                connection = DatabaseConnection.getConnection();
                String sql = "CREATE TABLE IF NOT EXISTS products ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT, "
                        + "name VARCHAR(255) NOT NULL, "
                        + "price DOUBLE NOT NULL)";

                try {
                    PreparedStatement pstmt = connection.prepareStatement(sql);
                    pstmt.execute();
                    System.out.println("Check if Table exists successfully");
                    connection.close();
                } catch (SQLException e) {
                    connection.close();
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("There is no session started. Start a session first.");
        }
    }
}
