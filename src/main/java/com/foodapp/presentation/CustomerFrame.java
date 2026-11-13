package com.foodapp.presentation;

import com.foodapp.bll.OrderService;
import com.foodapp.bll.ProductService;
import com.foodapp.dto.OrderDTO;
import com.foodapp.dto.ProductDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerFrame extends JFrame {
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();

    private final JTextField txtCustomerName = new JTextField();
    private final JComboBox<ProductDTO> cmbProducts = new JComboBox<>();
    private final JTextField txtQuantity = new JTextField("1");
    private final DefaultTableModel orderTableModel = new DefaultTableModel(
            new String[]{"Order ID", "Customer", "Product", "Qty", "Total ($)"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public CustomerFrame() {
        setTitle("Customer Module - Place Order");
        setSize(540, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Order Form
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 4, 12));

        formPanel.add(new JLabel("Customer Name:"));
        formPanel.add(txtCustomerName);
        formPanel.add(new JLabel("Select Food Item:"));
        formPanel.add(cmbProducts);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(txtQuantity);

        JButton btnRefresh = new JButton("Refresh Menu");
        JButton btnOrder = new JButton("Place Order");
        formPanel.add(btnRefresh);
        formPanel.add(btnOrder);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(orderTableModel)), BorderLayout.CENTER);

        // Button Actions
        btnRefresh.addActionListener(e -> loadMenu());

        btnOrder.addActionListener(e -> {
            try {
                ProductDTO selected = (ProductDTO) cmbProducts.getSelectedItem();
                if (selected == null) {
                    throw new IllegalArgumentException("No products available. Add products in the Manager module first.");
                }
                int qty = parseQuantity(txtQuantity.getText());
                OrderDTO saved = orderService.placeOrder(new OrderDTO(txtCustomerName.getText(), selected.getId(), qty));

                JOptionPane.showMessageDialog(this,
                        "Order #" + saved.getId() + " placed! Total: $" + String.format("%.2f", saved.getTotalAmount()));
                txtCustomerName.setText("");
                txtQuantity.setText("1");
                loadOrders();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadMenu();
        loadOrders();
    }

    private int parseQuantity(String raw) {
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid whole number for quantity.");
        }
    }

    private void loadMenu() {
        cmbProducts.removeAllItems();
        for (ProductDTO p : productService.getAllProducts()) {
            cmbProducts.addItem(p);
        }
    }

    private void loadOrders() {
        orderTableModel.setRowCount(0);
        for (OrderDTO o : orderService.getAllOrders()) {
            orderTableModel.addRow(new Object[]{
                    o.getId(),
                    o.getCustomerName(),
                    o.getProductName(),
                    o.getQuantity(),
                    String.format("%.2f", o.getTotalAmount())
            });
        }
    }
}
