package com.myserver.api.dao.impl;

import com.myserver.api.config.DatabaseConfig;
import com.myserver.api.dao.ProductDAO;
import com.myserver.api.dto.SaveProductDTO;
import com.myserver.api.model.Product;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductDAOImpl implements ProductDAO {

    @Override
    public Integer save(SaveProductDTO productDTO) {
        try {
            final Connection connection = DatabaseConfig.connection;
            final PreparedStatement preparedStatement = connection.prepareStatement("insert into products (name, count, price) values (?,?,?)");
            preparedStatement.setString(1, productDTO.getName());
            preparedStatement.setInt(2, productDTO.getCount());
            preparedStatement.setDouble(3, productDTO.getPrice());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (!resultSet.isClosed()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Product product) {
        try {
            final Connection connection = DatabaseConfig.connection;
            final PreparedStatement preparedStatement = connection.prepareStatement("update products set name = ?, count = ?, price = ? where id = ?");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getCount());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product findById(Integer id) {
        try {
            final Connection connection = DatabaseConfig.connection;
            final PreparedStatement preparedStatement = connection.prepareStatement("select * from products p where p.id = ?");
            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isClosed()) {
                return getProduct(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new LinkedList<>();
        try {
            final Connection connection = DatabaseConfig.connection;
            final PreparedStatement preparedStatement = connection.prepareStatement("select * from products");
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Product product = getProduct(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void delete(Integer id) {
        try {
            final Connection connection = DatabaseConfig.connection;
            final PreparedStatement preparedStatement = connection.prepareStatement("delete from products where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product getProduct(ResultSet resultSet) throws SQLException {
        final int productId = resultSet.getInt("id");
        final String name = resultSet.getString("name");
        final int count = resultSet.getInt("count");
        final double price = resultSet.getDouble("price");
        return new Product(productId, name, count, price);
    }
}
