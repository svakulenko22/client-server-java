package com.myserver.api.service.impl;

import com.myserver.api.dao.ProductDAO;
import com.myserver.api.dto.SaveProductDTO;
import com.myserver.api.exception.NoSuchEntityException;
import com.myserver.api.model.Product;
import com.myserver.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public Integer save(SaveProductDTO productDTO) {
        if (productDTO.getPrice() < 0.0) {
            throw new RuntimeException("Price must be more than 0");
        }
        return productDAO.save(productDTO);
    }

    @Override
    public void update(Product product) {
        if (product.getPrice() < 0.0) {
            throw new RuntimeException("Price must be more than 0");
        }
        productDAO.update(product);
    }

    @Override
    public Product findById(Integer id) {
        final Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchEntityException("Product not found with id " + id);
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public void delete(Integer id) {
        productDAO.delete(id);
    }
}
