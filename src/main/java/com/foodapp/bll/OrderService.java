package com.foodapp.bll;

import com.foodapp.dal.OrderDAO;
import com.foodapp.dal.ProductDAO;
import com.foodapp.dto.OrderDTO;
import com.foodapp.entity.FoodOrder;
import com.foodapp.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();
    private final ProductDAO productDAO = new ProductDAO();

    public OrderDTO placeOrder(OrderDTO dto) {
        if (dto.getCustomerName() == null || dto.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required.");
        }
        if (dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }

        Product product = productDAO.findById(dto.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("Selected product does not exist.");
        }

        double total = product.getPrice() * dto.getQuantity();

        FoodOrder order = new FoodOrder();
        order.setCustomerName(dto.getCustomerName().trim());
        order.setProduct(product);
        order.setQuantity(dto.getQuantity());
        order.setTotalAmount(total);

        orderDAO.save(order);

        dto.setId(order.getId());
        dto.setProductName(product.getName());
        dto.setTotalAmount(total);
        return dto;
    }

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> list = new ArrayList<>();
        for (FoodOrder o : orderDAO.findAll()) {
            OrderDTO dto = new OrderDTO();
            dto.setId(o.getId());
            dto.setCustomerName(o.getCustomerName());
            dto.setProductId(o.getProduct().getId());
            dto.setProductName(o.getProduct().getName());
            dto.setQuantity(o.getQuantity());
            dto.setTotalAmount(o.getTotalAmount());
            list.add(dto);
        }
        return list;
    }
}
