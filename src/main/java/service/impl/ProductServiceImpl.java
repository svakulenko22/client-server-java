package service.impl;

import dao.ProductDAO;
import dao.impl.ProductDAOImpl;
import entity.Product;
import service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    final ProductDAO productDAO;

    {
        productDAO = new ProductDAOImpl();
    }

    @Override
    public Integer save(Product product) {
        // send email
        return productDAO.save(product);
    }

    @Override
    public void update(Product product) {
        productDAO.update(product);
    }

    @Override
    public Product findById(Integer id) {
        return productDAO.findById(id);
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

