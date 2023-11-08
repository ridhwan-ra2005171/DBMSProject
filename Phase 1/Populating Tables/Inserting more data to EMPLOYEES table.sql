-- Insert sample data to employee
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('1234', 'Mohammed', 'A', 'Ali', '1990-05-15', '123 Main St', 'Male', '555-123-4567', '2015-03-20', FALSE, NULL),
    ('2345', 'Lea', 'R', 'Garcia', '1988-09-10', '456 Elm St', 'Female', '555-234-5678', '2016-02-12', FALSE, NULL),
	('5678', 'Carlos', 'J', 'Lopez', '1991-03-12', '678 Cedar St', 'Male', '555-567-8901', '2016-09-30', TRUE, NULL),
    ('6789', 'Lina', 'S', 'Martinez', '1995-11-05', '789 Birch St', 'Female', '555-678-9012', '2019-06-22', TRUE, NULL),
    ('3456', 'James', 'M', 'Rodriguez', '1992-12-02', '789 Oak St', 'Male', '555-345-6789', '2017-08-05', FALSE, '6789'),
	('4567', 'Sara', 'L', 'Khan', '1993-07-21', '567 Pine St', 'Female', '555-456-7890', '2018-04-15', FALSE, '6789');


-- Example 7
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('9876', 'Michael', 'D', 'Williams', '1987-02-11', '234 Elm St', 'Male', '555-987-6543', '2019-01-02', TRUE, NULL);

-- Example 8
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('2348', 'Emily', 'R', 'Smith', '1994-04-20', '890 Oak St', 'Female', '555-234-8901', '2020-11-25', FALSE, '1234');

-- Example 9
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('9871', 'Charles', 'E', 'Taylor', '1986-08-15', '123 Cedar St', 'Male', '555-987-9012', '2017-03-14', TRUE, '5678');

-- Example 10
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('2349', 'Catherine', 'A', 'Brown', '1991-05-27', '456 Pine St', 'Female', '555-234-5678', '2018-12-08', FALSE, '1234');

-- Example 11
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('1235', 'Joseph', 'T', 'Lee', '1989-09-16', '567 Oak St', 'Male', '555-123-5678', '2016-07-09', TRUE, '5678');

-- Example 12
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('6780', 'Sarah', 'E', 'Gonzalez', '1995-03-03', '890 Pine St', 'Female', '555-678-8901', '2021-05-21', TRUE, NULL);

-- Example 13
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('1236', 'Benjamin', 'L', 'Smith', '1993-10-11', '123 Elm St', 'Male', '555-123-6789', '2019-03-18', FALSE, '1234');

-- Example 14
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('7891', 'Mia', 'R', 'Davis', '1988-06-14', '456 Oak St', 'Female', '555-789-9012', '2017-09-05', TRUE, '5678');

-- Example 15
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('1237', 'Benjamin', 'L', 'Smith', '1993-10-11', '123 Elm St', 'Male', '555-123-6789', '2019-03-18', FALSE, '1234');

-- Example 16
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('7892', 'Mia', 'R', 'Davis', '1988-06-14', '456 Oak St', 'Female', '555-789-9012', '2017-09-05', TRUE, '5678');

-- Example 17
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('6781', 'Mia', 'R', 'Davis', '1988-06-14', '456 Oak St', 'Female', '555-678-9012', '2017-09-05', FALSE, '1234');

-- Example 18
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('1239', 'Christopher', 'J', 'Garcia', '1990-01-02', '789 Pine St', 'Male', '555-123-5678', '2015-12-03', FALSE, '1234');

-- Example 19
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('7893', 'Ella', 'M', 'Khan', '1992-04-22', '123 Cedar St', 'Female', '555-789-8901', '2018-02-14', TRUE, '5678');

-- Example 20
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('2341', 'Henry', 'R', 'Martinez', '1994-09-30', '234 Pine St', 'Male', '555-234-9012', '2022-04-06', TRUE, '5678');

-- Example 21
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('7894', 'Olivia', 'M', 'Turner', '1985-12-10', '456 Cedar St', 'Female', '555-789-5678', '2017-10-22', TRUE, NULL);

-- Example 22
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('2342', 'Daniel', 'A', 'Walker', '1986-05-21', '567 Elm St', 'Male', '555-234-8901', '2018-06-12', FALSE, '1234');

-- Example 23
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('7895', 'Oliver', 'M', 'King', '1993-01-14', '789 Cedar St', 'Male', '555-789-9012', '2019-03-04', FALSE, '1234');

-- Example 24
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('2343', 'Sophia', 'E', 'Anderson', '1994-05-06', '123 Oak St', 'Female', '555-234-5678', '2020-02-28', TRUE, '5678');

-- Example 25
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('7896', 'Lucas', 'D', 'Thomas', '1988-03-18', '456 Cedar St', 'Male', '555-789-8901', '2018-12-25', FALSE, '1234');

-- Example 26
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('2344', 'Olivia', 'M', 'Wilson', '1990-11-21', '567 Pine St', 'Female', '555-234-9012', '2021-11-08', FALSE, '1234');

-- Example 27
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('7897', 'William', 'T', 'Clark', '1991-06-30', '678 Elm St', 'Male', '555-789-5678', '2020-04-03', TRUE, '5678');

-- Example 28
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('2346', 'Natalie', 'J', 'Harris', '1992-09-12', '123 Birch St', 'Female', '555-234-8901', '2019-03-15', TRUE, '5678');

-- Example 29
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('7898', 'Jonathan', 'A', 'Young', '1987-12-05', '234 Cedar St', 'Male', '555-789-9012', '2018-07-28', TRUE, '5678');

-- Example 30
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('2347', 'Isabella', 'M', 'Lewis', '1993-08-27', '567 Oak St', 'Female', '555-234-5678', '2020-01-19', TRUE, '5678');

-- Example 31
INSERT INTO Employee (SSN, Fname, Minit, Lname, DOB, Address, Gender, PhoneNo, HireDate, Manager, ManagerSSN)
VALUES
    ('7899', 'Samuel', 'R', 'Green', '1989-04-16', '789 Pine St', 'Male', '555-789-8901', '2019-02-11', FALSE, '1234');


