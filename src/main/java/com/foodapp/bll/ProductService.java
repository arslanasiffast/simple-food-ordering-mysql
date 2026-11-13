package com.foodapp.bll;

import com.foodapp.dal.ProductDAO;
import com.foodapp.dto.ProductDTO;
import com.foodapp.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final ProductDAO productDAO = new ProductDAO();

    public void addProduct(ProductDTO dto) {
        validate(dto);
        Product product = new Product(dto.getName().trim(), dto.getPrice());
        productDAO.save(product);
    }

    public void updateProduct(ProductDTO dto) {
        if (dto.getId() <= 0) {
            throw new IllegalArgumentException("Please select a product from the table to update.");
        }
        validate(dto);
        Product product = new Product(dto.getName().trim(), dto.getPrice());
        product.setId(dto.getId());
        productDAO.update(product);
    }

    public void deleteProduct(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Please select a product from the table to delete.");
        }
        productDAO.delete(id);
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> list = new ArrayList<>();
        for (Product p : productDAO.findAll()) {
            list.add(new ProductDTO(p.getId(), p.getName(), p.getPrice()));
        }
        return list;
    }

    private void validate(ProductDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        if (dto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
    }
}
