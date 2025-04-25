/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package gas_suppy;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class RecordPayment extends JFrame {

    Connection con;

    JTextField txtOrderID, txtRetailerID, txtAmount;
    JComboBox<String> cmbPaymentMode;
    JButton btnSubmitPayment;

    public RecordPayment() {
        setTitle("Record Payment");
        setSize(400, 300);
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

        JLabel lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(50, 120, 100, 25);
        add(lblAmount);

        txtAmount = new JTextField();
        txtAmount.setBounds(150, 120, 180, 25);
        add(txtAmount);

        JLabel lblMode = new JLabel("Payment Mode:");
        lblMode.setBounds(50, 160, 100, 25);
        add(lblMode);

        cmbPaymentMode = new JComboBox<>(new String[]{"Cash", "Card", "Online", "UPI"});
        cmbPaymentMode.setBounds(150, 160, 180, 25);
        add(cmbPaymentMode);

        btnSubmitPayment = new JButton("Submit Payment");
        btnSubmitPayment.setBounds(120, 210, 150, 30);
        add(btnSubmitPayment);

        btnSubmitPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordPayment();
            }
        });
    }

    private void recordPayment() {
        String orderId = txtOrderID.getText().trim();
        String retailerId = txtRetailerID.getText().trim();
        String amountStr = txtAmount.getText().trim();
        String paymentMode = cmbPaymentMode.getSelectedItem().toString();

        if (orderId.isEmpty() || retailerId.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            String paymentId = "P" + (int)(Math.random() * 10000);
            java.sql.Date paymentDate = new java.sql.Date(new Date().getTime());

            // Insert payment record
            PreparedStatement pst = con.prepareStatement("INSERT INTO Payment1 (PaymentID, OrderID, RetailerID, Amount, PaymentDate, PaymentMode) VALUES (?, ?, ?, ?, ?, ?)");
            pst.setString(1, paymentId);
            pst.setString(2, orderId);
            pst.setString(3, retailerId);
            pst.setDouble(4, amount);
            pst.setDate(5, paymentDate);
            pst.setString(6, paymentMode);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Payment recorded successfully!");
                dispose(); // optionally return to dashboard or clear form
            } else {
                JOptionPane.showMessageDialog(this, "Failed to record payment.");
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecordPayment().setVisible(true));
    }
}