-- Drop child tables first (order matters due to FK constraints)
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Notification1';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Distribution';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Production';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Delivery1';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Payment1';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Orders1';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Stock';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

-- Now drop parent tables
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Customer1';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Retailer1';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Manufacturer1';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

-- Customer Table
CREATE TABLE  Customer1 (
    CustomerID VARCHAR2(10) PRIMARY KEY,
    CustomerName VARCHAR2(100),
    Password VARCHAR2(50),
    Address VARCHAR2(200),
    Phone VARCHAR2(15)
);

-- Retailer Table
CREATE TABLE Retailer1 (
    RetailerID VARCHAR2(10) PRIMARY KEY,
    RetailerName VARCHAR2(100),
    Location VARCHAR2(100)
);

-- Manufacturer Table
CREATE TABLE Manufacturer1 (
    ManufacturerID VARCHAR2(10) PRIMARY KEY,
    ManufacturerName VARCHAR2(100),
    Location VARCHAR2(100)
);

-- Orders Table
CREATE TABLE Orders1 (
    OrderID VARCHAR2(10) PRIMARY KEY,
    CustomerID VARCHAR2(10),
    RetailerID VARCHAR2(10),
    GasType VARCHAR2(20),
    Quantity NUMBER,
    OrderDate DATE,
    Status VARCHAR2(20),
    CONSTRAINT fk_customer FOREIGN KEY (CustomerID) REFERENCES Customer1(CustomerID),
    CONSTRAINT fk_retailer FOREIGN KEY (RetailerID) REFERENCES Retailer1(RetailerID)
);

CREATE TABLE Payment1 (
    PaymentID VARCHAR2(20) PRIMARY KEY,
    OrderID VARCHAR2(20),
    RetailerID VARCHAR2(20),
    Amount NUMBER(10, 2),
    PaymentDate DATE,
    PaymentMode VARCHAR2(20),
    FOREIGN KEY (OrderID) REFERENCES Orders1(OrderID),
    FOREIGN KEY (RetailerID) REFERENCES Retailer1(RetailerID)
);
CREATE TABLE Delivery1 (
    DeliveryID VARCHAR2(20) PRIMARY KEY,
    OrderID VARCHAR2(20),
    DeliveryDate DATE,
    GasType VARCHAR2(20),
    Quantity NUMBER,
    FOREIGN KEY (OrderID) REFERENCES Orders1(OrderID)
);

-- Production Table
CREATE TABLE Production (
    ProductionID VARCHAR2(10) PRIMARY KEY,
    ManufacturerID VARCHAR2(10),
    GasType VARCHAR2(20),
    Quantity NUMBER,
    ProductionDate DATE,
    CONSTRAINT fk_manufacturer_prod FOREIGN KEY (ManufacturerID) REFERENCES Manufacturer1(ManufacturerID)
);

-- Stock Table
CREATE TABLE Stock (
    StockID VARCHAR2(10) PRIMARY KEY,
    LocationType VARCHAR2(20), -- Manufacturer / Retailer
    LocationID VARCHAR2(10),
    GasType VARCHAR2(20),
    Quantity NUMBER
);

-- Distribution Table
CREATE TABLE Distribution (
    DistributionID VARCHAR2(10) PRIMARY KEY,
    ManufacturerID VARCHAR2(10),
    RetailerID VARCHAR2(10),
    GasType VARCHAR2(20),
    Quantity NUMBER,
    DistributionDate DATE,
    CONSTRAINT fk_manufacturer_dist FOREIGN KEY (ManufacturerID) REFERENCES Manufacturer1(ManufacturerID),
    CONSTRAINT fk_retailer_dist FOREIGN KEY (RetailerID) REFERENCES Retailer1(RetailerID)
);

-- Notification Table
CREATE TABLE Notification1 (
    NotificationID VARCHAR2(10) PRIMARY KEY,
    OrderID VARCHAR2(10),
    Message VARCHAR2(255),
    NotifiedDate DATE,
    CONSTRAINT fk_order_notif FOREIGN KEY (OrderID) REFERENCES Orders1(OrderID)
);
