package com.foodapp.presentation;

import com.foodapp.dal.HibernateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Food Ordering System (MySQL + Hibernate)");
        setSize(360, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Select Module", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));

        JButton btnManager = new JButton("Manager Module (Manage Products)");
        JButton btnCustomer = new JButton("Customer Module (Place Order)");

        btnManager.addActionListener(e -> openModule(() -> new ManagerFrame().setVisible(true)));
        btnCustomer.addActionListener(e -> openModule(() -> new CustomerFrame().setVisible(true)));

        panel.add(title);
        panel.add(btnManager);
        panel.add(btnCustomer);
        add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                HibernateUtil.shutdown();
            }
        });
    }

    private void openModule(Runnable launcher) {
        try {
            launcher.run();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage() +
                            "\n\nMake sure MySQL is running on localhost:3306 and check credentials in hibernate.cfg.xml.",
                    "MySQL Connection Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
