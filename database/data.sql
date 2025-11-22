INSERT INTO UserRoles (RoleDescription)
VALUES
("Passenger"),
("Admin");

INSERT INTO Payment_Methods (Method)
VALUES
("Cash"),
("Card"),
("Online Banking"),
("Free");

INSERT INTO `User` (Username, UserPassword, FullName, Age, Phone, UserAddress, UserRoleID)
VALUES
('Admin', "admin", 'I AM BETTER', 67, '113', 'An India Telecom Center', 2),
('UserA', NULL, 'Nguyen Van A', 24, '0912345678', '81 LA', 1),
('UserB', NULL, 'Le Van B', 30, '0987654321', '35 WA', 1),
('UserC', NULL, 'Nguyen Van C', 52, '0965432987', '56 TA', 2),
('UserD', NULL, 'Tran Yen D', 35, '0924681357', '75 WE', 1),
('UserE', NULL, 'Nam Cong E', 65, '0913579246', '20 VN', 2),
('UserF', NULL, 'Pham Thi F', 19, '0901112233', '12 QB', 1),
('UserG', NULL, 'Hoang Minh G', 41, '0978765432', '99 XT', 1),
('UserH', NULL, 'Vo Quoc H', 28, '0934567890', '40 KN', 1),
('UserI', NULL, 'Dinh Thi I', 58, '0955667788', '10 CD', 2),
('UserJ', NULL, 'Doan Van J', 22, '0941234567', '21 GH', 1);

INSERT INTO Driver (DriverName, Age, License, Phone)
VALUES
('Le Driver', 45, 'L12345', '0902345678'),
('Nguyen Driver', 30, 'L54321', '0907654321'),
('Tran Driver', 50, 'L13579', '0998765432'),
('Vu Driver', 38, 'L98765', '0911223344'),
('Bui Driver', 55, 'L24680', '0909887766');

INSERT INTO `Route` (RouteName, StartLocation, EndLocation, Fare, Distance, Duration)
VALUES
('Route A', 'Station A', 'Station B', 1.50, 12.3, '00:45:00'),
('Route B', 'Station B', 'Station C', 1.50, 12.3, '00:45:00'),
('Route C', 'Station C', 'Station D', 1.50, 10.3, '00:35:00'),
('Route D', 'Station D', 'Station E', 2.00, 15.0, '01:00:00'),
('Route E', 'Station E', 'Station F', 1.00, 8.5, '00:25:00');

INSERT INTO Bus_Info (PlateNumber, Capacity, DriverID, RouteID)
VALUES
('30A-12345', 40, 1, 1),
('30A-54321', 40, 2, 2),
('30A-13579', 40, 3, 3),
('29B-99887', 50, 4, 4),
('51C-10203', 35, 5, 5);

INSERT INTO Trip (TripDate, DepartureTime, ArrivalTime, BusID)
VALUES
('2025-06-01', '08:00:00', '08:45:00', 1),
('2025-06-01', '09:00:00', '09:45:00', 2),
('2025-06-01', '10:00:00', '10:35:00', 3),
('2025-06-01', '09:00:00', '09:45:00', 1),
('2025-06-01', '10:00:00', '10:45:00', 2),
('2025-06-01', '11:00:00', '11:35:00', 3),
('2025-06-02', '07:30:00', '08:30:00', 4),
('2025-06-02', '12:00:00', '12:25:00', 5),
('2025-06-02', '14:00:00', '14:45:00', 1),
('2025-06-03', '06:00:00', '06:45:00', 2),
('2025-06-03', '18:00:00', '18:35:00', 3),
('2025-06-03', '17:00:00', '18:00:00', 4);

INSERT INTO Payment (UserID, Amount, PaymentDate, Payment_Method_ID)
VALUES
(1, 10000.00, '2025-06-01 09:15:00', 1),
(2, 12000.50, '2025-06-01 10:30:00', 2),
(4, 8000.00,  '2025-06-01 11:00:00', 1),
(6, 15000.00, '2025-06-02 08:45:00', 3),
(7, 0.00, '2025-06-02 12:20:00', 4),
(8, 20000.00, '2025-06-03 14:05:00', 1),
(10, 10000.00, '2025-06-03 18:30:00', 2);

INSERT INTO Ticket (TicketDateTime, UserID, TripID, PaymentID)
VALUES
('2025-06-01 09:15:00', 1, 1, 1),
('2025-06-01 10:30:00', 2, 5, 2),
('2025-06-01 11:00:00', 4, 3, 3),
('2025-06-02 08:45:00', 6, 7, 4),
('2025-06-02 12:20:00', 7, 9, 5),
('2025-06-03 14:05:00', 8, 12, 6),
('2025-06-03 18:30:00', 10, 10, 7);