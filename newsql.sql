CREATE DATABASE IF NOT EXISTS EVENTMANAGEMENT;
USE EVENTMANAGEMENT;

CREATE TABLE IF NOT EXISTS CLIENTS (
    Client_ID INT AUTO_INCREMENT PRIMARY KEY,
    Client_Name VARCHAR(100) NOT NULL,
    Contact_Number VARCHAR(15) NOT NULL UNIQUE
);



CREATE TABLE IF NOT EXISTS EVENTDETAILS (
    Event_ID INT AUTO_INCREMENT PRIMARY KEY,
    Client_ID INT NOT NULL,
    Customer_Name VARCHAR(100) NOT NULL,
    Contact_Number VARCHAR(15),
    Event_Date DATE NOT NULL,
    Event_Time TIME,
    Event_Type VARCHAR(50) NOT NULL,
    ExpectedPeople INT CHECK (ExpectedPeople > 0),
    Event_Venue VARCHAR(50),
    Event_Theme VARCHAR(50),
    Extra_Programs VARCHAR(50),
    Dress_Code_Host VARCHAR(50),
    Dress_Code_Guest VARCHAR(50),
    Food_Type VARCHAR(50) NOT NULL,
    Cuisine_Type VARCHAR(50),
    Transportation_For_Event VARCHAR(50),
    Transportation_for_Guests VARCHAR(50),
    Accommodation_for_Guests VARCHAR(50),
    Payment_Method VARCHAR(50) NOT NULL,
    Event_Status VARCHAR(20) DEFAULT 'Scheduled',
    Total_Cost DECIMAL(10, 2) CHECK (Total_Cost >= 0),
    FOREIGN KEY (Client_ID) REFERENCES CLIENTS(Client_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PAYMENTS (
    Payment_ID INT AUTO_INCREMENT PRIMARY KEY,
    Event_ID INT NOT NULL,
    Payment_Date DATE NOT NULL,
    Amount DECIMAL(10, 2) CHECK (Amount > 0),
    Payment_Method VARCHAR(50) NOT NULL,
    Status VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (Event_ID) REFERENCES EVENTDETAILS(Event_ID) ON DELETE CASCADE
);

CREATE TABLE PRICES (
    Service_Type VARCHAR(50) NOT NULL,
    Description VARCHAR(100) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL CHECK (Price >= 0),
    PRIMARY KEY (Service_Type, Description)
);

INSERT INTO PRICES (Service_Type, Description, Price) VALUES 

    ('Venue', 'Banquet Hall', 25000.00),
    ('Venue', 'Park', 5000.00),
    ('Venue', 'Beach', 12000.00),
    ('Venue', 'RoofTop', 60000),
    ('Theme', 'Elegent', 2000),
    ('Theme', 'Rustic', 3500),
    ('Theme', 'Modern', 4000),
    ('Theme', 'Traditional', 5000),
    ('Extra Program', 'DJ', 500.00),
    ('Extra Program', 'Band', 1000.00),
    ('Extra Program', 'Dance', 15000),
    ('Extra Program', 'Photo Booth', 300.00),
    ('Dress Code', 'Suit', 5000),
    ('Dress Code', 'Traditional', 3000),
    ('Dress Code', 'Casual', 2000),
    ('Dress Code', 'Formal', 4000),           
    ('Food', 'Veg', 100.00),
    ('Food', 'Non-Veg', 150.00),
    ('Food', 'Both', 200.00),
    ('Cuisine', 'Indian', 200),         
    ('Cuisine', 'Continental', 300),
    ('Cuisine', 'Italian', 350),
    ('Cuisine', 'Chinese', 250),
    ('Cuisine', 'Mexican', 400),
    ('Transportation', 'Standard', 5000),
    ('Transportation', 'Luxury', 10000),        
    ('Transportation', 'Economy', 3000),
    ('Accommodation', 'Guest House', 20000),         
    ('Accommodation', 'Hotel', 40000),
    ('Accommodation', 'Resort', 60000);