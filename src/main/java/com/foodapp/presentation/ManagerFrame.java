package com.foodapp.presentation;

import com.foodapp.bll.ProductService;
import com.foodapp.dto.ProductDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManagerFrame extends JFrame {
    private final ProductService productService = new ProductService();
    private final JTextField txtId = new JTextField();
    private final JTextField txtName = new JTextField();
    private final JTextField txtPrice = new JTextField();
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Price ($)"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);

    public ManagerFrame() {
        setTitle("Manager Module - Manage Products");
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 4, 12));
        txtId.setEditable(false);

        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Price ($):"));
        formPanel.add(txtPrice);

        // Buttons Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Product Table
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Populate fields when a table row is selected
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtPrice.setText(tableModel.getValueAt(row, 2).toString());
            }
        });

        // Button Actions
        btnAdd.addActionListener(e -> {
            try {
                double price = parsePrice(txtPrice.getText());
                productService.addProduct(new ProductDTO(0, txtName.getText(), price));
                clearFields();
                loadProducts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
                double price = parsePrice(txtPrice.getText());
                productService.updateProduct(new ProductDTO(id, txtName.getText(), price));
                clearFields();
                loadProducts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
                productService.deleteProduct(id);
                clearFields();
                loadProducts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnClear.addActionListener(e -> clearFields());

        loadProducts();
    }

    private double parsePrice(String raw) {
        try {
            return Double.parseDouble(raw.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid numeric price.");
        }
    }

    private void loadProducts() {
        tableModel.setRowCount(0);
        for (ProductDTO p : productService.getAllProducts()) {
            tableModel.addRow(new Object[]{p.getId(), p.getName(), String.format("%.2f", p.getPrice())});
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        table.clearSelection();
    }
}
