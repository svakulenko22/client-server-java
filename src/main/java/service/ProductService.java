package service;

import entity.Product;

import java.util.List;

public interface ProductService {

    Integer save(Product product);

    void update(Product product);

    Product findById(Integer id);

    List<Product> findAll();

    void delete(Integer id);
}
