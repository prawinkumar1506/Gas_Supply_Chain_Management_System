package gas_suppy;

import javax.swing.*;
import java.sql.*;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Customer1 extends javax.swing.JFrame {

    Connection con;

    public Customer1() {
        initComponents();
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            JOptionPane.showMessageDialog(this, "Driver Loaded!");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
            JOptionPane.showMessageDialog(this, "Connected to Oracle database!");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Customer1.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void initComponents() {
        setTitle("Customer Login & Order");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Labels & Fields
        JLabel lblCustomerId = new JLabel("Customer ID:");
        lblCustomerId.setBounds(50, 50, 100, 25);
        add(lblCustomerId);

        JTextField txtCustomerId = new JTextField();
        txtCustomerId.setBounds(160, 50, 200, 25);
        add(txtCustomerId);

        JLabel lblGasType = new JLabel("Gas Type:");
        lblGasType.setBounds(50, 90, 100, 25);
        add(lblGasType);

        String[] gasTypes = {"LPG", "CNG"};
        JComboBox<String> cmbGasType = new JComboBox<>(gasTypes);
        cmbGasType.setBounds(160, 90, 200, 25);
        add(cmbGasType);

        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(50, 130, 100, 25);
        add(lblQuantity);

        JTextField txtQuantity = new JTextField();
        txtQuantity.setBounds(160, 130, 200, 25);
        add(txtQuantity);

        JLabel lblRetailerId = new JLabel("Retailer ID:");
        lblRetailerId.setBounds(50, 170, 100, 25);
        add(lblRetailerId);

        JTextField txtRetailerId = new JTextField();
        txtRetailerId.setBounds(160, 170, 200, 25);
        add(txtRetailerId);

        JButton btnOrder = new JButton("Place Order");
        btnOrder.setBounds(160, 220, 120, 30);
        add(btnOrder);

        // Button Action
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerId = txtCustomerId.getText().trim();
                String gasType = cmbGasType.getSelectedItem().toString();
                String quantityStr = txtQuantity.getText().trim();
                String retailerId = txtRetailerId.getText().trim();

                if (customerId.isEmpty() || quantityStr.isEmpty() || retailerId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields");
                    return;
                }

                try {
                    int quantity = Integer.parseInt(quantityStr);
                    String orderId = "ORD" + (int)(Math.random() * 1000);
                    Date orderDate = new Date(System.currentTimeMillis());

                    PreparedStatement pst = con.prepareStatement(
                        "INSERT INTO Orders1 (OrderID, CustomerID, RetailerID, GasType, Quantity, OrderDate, Status) VALUES (?, ?, ?, ?, ?, ?, ?)"
                    );
                    pst.setString(1, orderId);
                    pst.setString(2, customerId);
                    pst.setString(3, retailerId);
                    pst.setString(4, gasType);
                    pst.setInt(5, quantity);
                    pst.setDate(6, orderDate);
                    pst.setString(7, "Pending");

                    int rows = pst.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(null, "Order Placed Successfully!");

                        int choice = JOptionPane.showConfirmDialog(null, "Go to Retailer Dashboard for Verification?", "Redirect", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            new VerifyOrder().setVisible(true);
                            dispose(); // Close current window
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Order Failed!");
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Quantity must be a number.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        new Customer1().setVisible(true);
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
/*
    /*@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
*/
    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

