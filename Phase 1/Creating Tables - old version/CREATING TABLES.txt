# Creating Employee Table
CREATE TABLE Employee (
    SSN VARCHAR(9) PRIMARY KEY,
    Fname VARCHAR(50) NOT NULL,
    Minit CHAR(1),
    Lname VARCHAR(50) NOT NULL,
    DOB DATE NOT NULL,
    Address VARCHAR(255),
    Gender ENUM('Male', 'Female'),
    PhoneNo VARCHAR(15),
    HireDate DATE,
    Manager BOOLEAN,
    ManagerSSN VARCHAR(9),
    FOREIGN KEY (ManagerSSN) REFERENCES Employee(SSN)
);

# Creating Secretary Table
CREATE TABLE Secretary (
    SSN VARCHAR(9) PRIMARY KEY,
    TypingSpeed INT,
    FOREIGN KEY (SSN) REFERENCES Employee(SSN)
);

# Creating Technician Table
CREATE TABLE Technician (
    SSN VARCHAR(9) PRIMARY KEY,
    Tgrade ENUM('1', '2', '3'),
    FOREIGN KEY (SSN) REFERENCES Employee(SSN)
);

# Creating Engineer Table
CREATE TABLE Engineer (
    SSN VARCHAR(9) PRIMARY KEY,
    EngType VARCHAR(50),
    FOREIGN KEY (SSN) REFERENCES Employee(SSN)
);

# Creating Salaried_Employee Table
CREATE TABLE Salaried_Employee (
    SSN VARCHAR(9) PRIMARY KEY,
    Salary DECIMAL(10, 2),
    FOREIGN KEY (SSN) REFERENCES Employee(SSN)
);



# Creating Trade_Union Table
CREATE TABLE Trade_Union (
    UnionID VARCHAR(10) PRIMARY KEY,
    UnionName VARCHAR(255) NOT NULL,
    UnionAddress VARCHAR(255)
);

# Creating Hourly_Employee Table
CREATE TABLE Hourly_Employee (
    SSN VARCHAR(9) PRIMARY KEY,
    PayScale DECIMAL(8, 2) NOT NULL,
    HoursWorked INT,
    UnionID VARCHAR(10),
    FOREIGN KEY (SSN) REFERENCES Employee(SSN),
    FOREIGN KEY (UnionID) REFERENCES Trade_Union(UnionID)
);

# Creating Project Table
CREATE TABLE Project (
    ProjectNo INT PRIMARY KEY,
    ProjectName VARCHAR(255) NOT NULL,
    Description TEXT,
    ProjectLoc VARCHAR(255) NOT NULL,
    ManagedBy VARCHAR(9),
    FOREIGN KEY (ManagedBy) REFERENCES Employee(SSN)
);
