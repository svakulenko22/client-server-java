package dao;

import entity.Product;

import java.util.List;

public interface ProductDAO {

    Integer save(Product product);

    void update(Product product);

    Product findById(Integer id);

    List<Product> findAll();

    void delete(Integer id);
}