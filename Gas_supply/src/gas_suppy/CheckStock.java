/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gas_suppy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CheckStock extends JFrame {
    Connection con;

    JTable ordersTable;
    JButton btnCheckStock;

    public CheckStock() {
        setTitle("Check Stock for Verified Orders");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        JLabel title = new JLabel("Verified Orders", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        ordersTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        add(scrollPane, BorderLayout.CENTER);

        btnCheckStock = new JButton("Check Stock & Proceed to Delivery");
        add(btnCheckStock, BorderLayout.SOUTH);

        populateVerifiedOrders();

        btnCheckStock.addActionListener(e -> checkStockAndRedirect());
    }

    private void populateVerifiedOrders() {
        try {
            String sql = "SELECT * FROM Orders1 WHERE Status = 'Verified'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[]{"OrderID", "CustomerID", "RetailerID", "GasType", "Quantity", "OrderDate"}, 0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("OrderID"),
                    rs.getString("CustomerID"),
                    rs.getString("RetailerID"),
                    rs.getString("GasType"),
                    rs.getInt("Quantity"),
                    rs.getDate("OrderDate")
                });
            }
            ordersTable.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void checkStockAndRedirect() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a verified order.");
            return;
        }

        String gasType = ordersTable.getValueAt(selectedRow, 3).toString();
        int quantityNeeded = Integer.parseInt(ordersTable.getValueAt(selectedRow, 4).toString());
        String retailerId = ordersTable.getValueAt(selectedRow, 2).toString();

        try {
            PreparedStatement pst = con.prepareStatement(
                "SELECT * FROM Stock WHERE GasType = ? AND LOCATIONID = ?"
            );
            pst.setString(1, gasType);
            pst.setString(2, retailerId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int availableStock = rs.getInt("Quantity");
                if (availableStock >= quantityNeeded) {
                    JOptionPane.showMessageDialog(this, "Sufficient stock available!");

                    // Proceed to delivery creation
                    new Delivery().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient stock for this order.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No stock record found for this Retailer and Gas Type.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CheckStock().setVisible(true));
    }
}
