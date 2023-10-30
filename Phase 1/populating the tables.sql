-- Insert sample data to employee
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('1234', 'Mohammed', 'A', 'Ali', '1990-05-15', '123 Main St', 'Male', '555-123-4567', '2015-03-20', FALSE, NULL),
    ('2345', 'Lea', 'R', 'Garcia', '1988-09-10', '456 Elm St', 'Female', '555-234-5678', '2016-02-12', FALSE, NULL),
	('5678', 'Carlos', 'J', 'Lopez', '1991-03-12', '678 Cedar St', 'Male', '555-567-8901', '2016-09-30', TRUE, NULL),
    ('6789', 'Lina', 'S', 'Martinez', '1995-11-05', '789 Birch St', 'Female', '555-678-9012', '2019-06-22', TRUE, NULL),
    ('3456', 'James', 'M', 'Rodriguez', '1992-12-02', '789 Oak St', 'Male', '555-345-6789', '2017-08-05', FALSE, '6789'),
	('4567', 'Sara', 'L', 'Khan', '1993-07-21', '567 Pine St', 'Female', '555-456-7890', '2018-04-15', FALSE, '6789');

-- Insert sample data into Secretary table
INSERT INTO Secretary (SSN, TypingSpeed)
VALUES
    ('1234', 60),
    ('2345', 70);

-- Insert sample data into Technician table
INSERT INTO Technician (SSN, Tgrade)
VALUES
    ('3456', 2),
	('4567', 3);

-- Insert sample data into Engineer table
INSERT INTO Engineer (SSN, EngType)
VALUES
    ('5678', 'Software'),
    ('6789', 'Civil');

-- Insert sample data into Salaried_Employee table
INSERT INTO Salaried_Employee (SSN, Salary)
VALUES
    ('1234', 60000.00),
    ('2345', 55000.00);

-- Insert sample data into Trade_Union table
INSERT INTO Trade_Union (UnionID, UnionName, UnionAddress)
VALUES
    ('UNION001', 'Engineering Union', '123 Union St'),
    ('UNION002', 'Labor Union', '456 Labor Ave'),
    ('UNION003', 'Technical Workers Union', '789 Tech Rd'),
	('UNION004', 'Healthcare Workers Union', '456 Health Rd'),
    ('UNION005', 'Teachers Union', '789 Education Ave'),
    ('UNION006', 'Transport Workers Union', '123 Transport Blvd');

-- Insert sample data into Hourly_Employee table
INSERT INTO Hourly_Employee (SSN, PayScale, HoursWorked, UnionID)
VALUES
    ('3456', 16.00, 155, 'UNION001'),
('4567', 13.50, 175, 'UNION004'),
    ('5678', 14.25, 160, 'UNION005'),
    ('6789', 14.75, 155, 'UNION006');

-- Insert sample data into Project table
INSERT INTO Project (ProjectNo, ProjectName, Description, ProjectLoc, ManagedBy)
VALUES
    (101, 'Building Construction', 'Construction of a new office building', 'City Center', '6789'),
    (102, 'Software Development', 'Development of a new software application', 'Tech Park', '5678');
