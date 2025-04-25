-- customer1
INSERT INTO customer1 VALUES
('CUST001', 'Arjun Reddy', 'arjun123', 'Chennai', '9876543210');
INSERT INTO customer1 VALUES
('CUST002', 'Sanya Mehra', 'sanya456', 'Coimbatore', '9765432109');
INSERT INTO customer1 VALUES
('CUST003', 'Rahul Das', 'rahul789', 'Hyderabad', '9123456780');
INSERT INTO customer1 VALUES
('CUST004', 'Priya K', 'priya234', 'Madurai', '9789012345');
INSERT INTO customer1 VALUES
('CUST005', 'Vikram Singh', 'vikram999', 'Bangalore', '9654321098');

-- retailer1
INSERT INTO retailer1 VALUES
('RET001', 'Gas Express', 'Chennai');
INSERT INTO retailer1 VALUES
('RET002', 'Flame Supply', 'Coimbatore');
INSERT INTO retailer1 VALUES
('RET003', 'SafeGas', 'Hyderabad');
INSERT INTO retailer1 VALUES
('RET004', 'QuickFuel', 'Madurai');
INSERT INTO retailer1 VALUES
('RET005', 'BrightFlame', 'Bangalore');

-- manufacturer1
INSERT INTO manufacturer1 VALUES
('MAN001', 'Indane Corp', 'Chennai');
INSERT INTO manufacturer1 VALUES
('MAN002', 'Bharat Petro', 'Mumbai');
INSERT INTO manufacturer1 VALUES
('MAN003', 'HP Gas Ltd', 'Hyderabad');
INSERT INTO manufacturer1 VALUES
('MAN004', 'EcoGas Inc', 'Coimbatore');
INSERT INTO manufacturer1 VALUES
('MAN005', 'GreenFlame', 'Bangalore');

INSERT INTO orders1 VALUES
('ORD001', 'CUST001', 'RET001', 'LPG', 2, TO_DATE('2025-04-09', 'YYYY-MM-DD'), 'Pending');
INSERT INTO orders1 VALUES
('ORD002', 'CUST002', 'RET002', 'CNG', 3, TO_DATE('2025-04-08', 'YYYY-MM-DD'), 'Delivered');
INSERT INTO orders1 VALUES
('ORD003', 'CUST003', 'RET003', 'LPG', 1, TO_DATE('2025-04-07', 'YYYY-MM-DD'), 'Notified');
INSERT INTO orders1 VALUES
('ORD004', 'CUST004', 'RET004', 'CNG', 4, TO_DATE('2025-04-06', 'YYYY-MM-DD'), 'Delivered');
INSERT INTO orders1 VALUES
('ORD005', 'CUST005', 'RET005', 'LPG', 5, TO_DATE('2025-04-05', 'YYYY-MM-DD'), 'Pending');

INSERT INTO Production VALUES
('PROD001', 'MAN001', 'LPG', 100, TO_DATE('2025-04-04', 'YYYY-MM-DD'));
INSERT INTO Production VALUES
('PROD002', 'MAN002', 'CNG', 80, TO_DATE('2025-04-03', 'YYYY-MM-DD'));
INSERT INTO Production VALUES
('PROD003', 'MAN003', 'LPG', 60, TO_DATE('2025-04-03', 'YYYY-MM-DD'));
INSERT INTO Production VALUES
('PROD004', 'MAN004', 'CNG', 120, TO_DATE('2025-04-02', 'YYYY-MM-DD'));
INSERT INTO Production VALUES
('PROD005', 'MAN005', 'LPG', 90, TO_DATE('2025-04-01', 'YYYY-MM-DD'));

-- Stock (unchanged)
INSERT INTO Stock VALUES
('STK001', 'Manufacturer', 'MAN001', 'LPG', 100);
INSERT INTO Stock VALUES
('STK002', 'Manufacturer', 'MAN002', 'CNG', 80);
INSERT INTO Stock VALUES
('STK003', 'Retailer', 'RET001', 'LPG', 20);
INSERT INTO Stock VALUES
('STK004', 'Retailer', 'RET002', 'CNG', 15);
INSERT INTO Stock VALUES
('STK005', 'Retailer', 'RET005', 'LPG', 30);


INSERT INTO Distribution VALUES
('DIST001', 'MAN001', 'RET001', 'LPG', 20, TO_DATE('2025-04-06', 'YYYY-MM-DD'));
INSERT INTO Distribution VALUES
('DIST002', 'MAN002', 'RET002', 'CNG', 15, TO_DATE('2025-04-05', 'YYYY-MM-DD'));
INSERT INTO Distribution VALUES
('DIST003', 'MAN003', 'RET003', 'LPG', 10, TO_DATE('2025-04-04', 'YYYY-MM-DD'));
INSERT INTO Distribution VALUES
('DIST004', 'MAN004', 'RET004', 'CNG', 25, TO_DATE('2025-04-04', 'YYYY-MM-DD'));
INSERT INTO Distribution VALUES
('DIST005', 'MAN005', 'RET005', 'LPG', 30, TO_DATE('2025-04-03', 'YYYY-MM-DD'));

INSERT INTO notification1 VALUES
('NOTF001', 'ORD003', 'LPG is currently out of stock, please wait for 2 days.', TO_DATE('2025-04-08', 'YYYY-MM-DD'));
INSERT INTO notification1 VALUES
('NOTF002', 'ORD005', 'High demand, delivery may take 3 more days.', TO_DATE('2025-04-09', 'YYYY-MM-DD'));
INSERT INTO notification1 VALUES
('NOTF003', 'ORD001', 'Your order is being processed.', TO_DATE('2025-04-09', 'YYYY-MM-DD'));
INSERT INTO notification1 VALUES
('NOTF004', 'ORD005', 'Insufficient stock, awaiting next distribution.', TO_DATE('2025-04-10', 'YYYY-MM-DD'));
INSERT INTO notification1 VALUES
('NOTF005', 'ORD003', 'Your delivery has been postponed.', TO_DATE('2025-04-08', 'YYYY-MM-DD'));

