package com.myserver.api.dao;

import com.myserver.api.dto.SaveProductDTO;
import com.myserver.api.model.Product;

import java.util.List;

public interface ProductDAO {

    Integer save(SaveProductDTO productDTO);

    void update(Product product);

    Product findById(Integer id);

    List<Product> findAll();

    void delete(Integer id);
}
