package gas_suppy;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class Delivery extends JFrame {

    Connection con;

    JTextField txtOrderID, txtRetailerID, txtGasType, txtQuantity;
    JButton btnCreateDelivery;

    public Delivery() {
        setTitle("Create Delivery");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        JLabel lblOrderID = new JLabel("Order ID:");
        lblOrderID.setBounds(50, 40, 100, 25);
        add(lblOrderID);

        txtOrderID = new JTextField();
        txtOrderID.setBounds(150, 40, 180, 25);
        add(txtOrderID);

        JLabel lblRetailerID = new JLabel("Retailer ID:");
        lblRetailerID.setBounds(50, 80, 100, 25);
        add(lblRetailerID);

        txtRetailerID = new JTextField();
        txtRetailerID.setBounds(150, 80, 180, 25);
        add(txtRetailerID);

        JLabel lblGasType = new JLabel("Gas Type:");
        lblGasType.setBounds(50, 120, 100, 25);
        add(lblGasType);

        txtGasType = new JTextField();
        txtGasType.setBounds(150, 120, 180, 25);
        add(txtGasType);

        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(50, 160, 100, 25);
        add(lblQuantity);

        txtQuantity = new JTextField();
        txtQuantity.setBounds(150, 160, 180, 25);
        add(txtQuantity);

        btnCreateDelivery = new JButton("Create Delivery");
        btnCreateDelivery.setBounds(120, 220, 150, 30);
        add(btnCreateDelivery);

        btnCreateDelivery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDelivery();
            }
        });
    }

    private void createDelivery() {
        String orderId = txtOrderID.getText().trim();
        String retailerId = txtRetailerID.getText().trim();
        String gasType = txtGasType.getText().trim();
        String quantityStr = txtQuantity.getText().trim();

        if (orderId.isEmpty() || retailerId.isEmpty() || gasType.isEmpty() || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            String deliveryId = "D" + (int)(Math.random() * 10000);
            java.sql.Date deliveryDate = new java.sql.Date(new Date().getTime());

            // Insert into Delivery1 table
            PreparedStatement pst = con.prepareStatement("INSERT INTO Delivery1 (DeliveryID, OrderID, DeliveryDate, GasType, Quantity) VALUES (?, ?, ?, ?, ?)");
            pst.setString(1, deliveryId);
            pst.setString(2, orderId);
            pst.setDate(3, deliveryDate);
            pst.setString(4, gasType);
            pst.setInt(5, quantity);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                // Update order status
                PreparedStatement pst2 = con.prepareStatement("UPDATE Orders1 SET Status = 'Delivered' WHERE OrderID = ?");
                pst2.setString(1, orderId);
                pst2.executeUpdate();

                // Reduce stock
                PreparedStatement pst3 = con.prepareStatement("UPDATE Stock SET Quantity = Quantity - ? WHERE GasType = ? AND LocationID = ?");
                pst3.setInt(1, quantity);
                pst3.setString(2, gasType);
                pst3.setString(3, retailerId);
                pst3.executeUpdate();

                JOptionPane.showMessageDialog(this, "Delivery created successfully!");
                dispose(); // close current window
                new RecordPayment().setVisible(true); // optional next step
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create delivery.");
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Delivery().setVisible(true));
    }
}
