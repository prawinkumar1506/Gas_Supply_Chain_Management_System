package gas_suppy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class VerifyOrder extends JFrame {
    Connection con;

    JTable orderTable;
    DefaultTableModel tableModel;

    public VerifyOrder() {
        setTitle("Verify Orders");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        connectToDatabase();

        JLabel lblTitle = new JLabel("Pending Orders for Verification");
        lblTitle.setBounds(200, 20, 300, 25);
        add(lblTitle);

        // Table setup
        tableModel = new DefaultTableModel();
        orderTable = new JTable(tableModel);
        tableModel.addColumn("Order ID");
        tableModel.addColumn("Customer ID");
        tableModel.addColumn("Retailer ID");
        tableModel.addColumn("Gas Type");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Status");

        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBounds(50, 60, 580, 200);
        add(scrollPane);

        JButton btnVerify = new JButton("Verify");
        btnVerify.setBounds(150, 280, 100, 30);
        add(btnVerify);

        JButton btnReject = new JButton("Reject");
        btnReject.setBounds(280, 280, 100, 30);
        add(btnReject);

        JButton btnNext = new JButton("Next ➜");
        btnNext.setBounds(410, 280, 100, 30);
        add(btnNext);

        loadPendingOrders();

        btnVerify.addActionListener(e -> updateOrderStatus("Verified"));
        btnReject.addActionListener(e -> updateOrderStatus("Rejected"));

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CheckStock().setVisible(true);
                dispose(); // close current window
            }
        });
    }

    private void connectToDatabase() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
            JOptionPane.showMessageDialog(this, "Connected to Oracle Database!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed: " + ex.getMessage());
        }
    }

    private void loadPendingOrders() {
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM Orders1 WHERE Status = 'Pending'");
            ResultSet rs = pst.executeQuery();

            tableModel.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("OrderID"),
                    rs.getString("CustomerID"),
                    rs.getString("RetailerID"),
                    rs.getString("GasType"),
                    rs.getInt("Quantity"),
                    rs.getString("Status")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load orders: " + ex.getMessage());
        }
    }

    private void updateOrderStatus(String newStatus) {
        int selectedRow = orderTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order.");
            return;
        }

        String orderId = tableModel.getValueAt(selectedRow, 0).toString();

        try {
            PreparedStatement pst = con.prepareStatement("UPDATE Orders1 SET Status = ? WHERE OrderID = ?");
            pst.setString(1, newStatus);
            pst.setString(2, orderId);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Order " + newStatus + " successfully.");
                loadPendingOrders();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to update status: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new VerifyOrder().setVisible(true);
    }
}