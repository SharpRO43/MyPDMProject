-- Stored procedure to generate random trips
-- Usage:
--   CALL generate_random_trips(50, 20);  -- 50 trips from today through next 20 days
-- Assumptions:
--   Trip table columns: TripID, TripDate (DATE), DepartureTime (TIME), ArrivalTime (TIME), BusID (FK)
--   Bus_info has existing rows (BusID).
--   MySQL/MariaDB syntax.

DELIMITER //
CREATE PROCEDURE generate_random_trips(IN trip_count INT, IN days_forward INT)
BEGIN
  DECLARE i INT DEFAULT 0;
  DECLARE rand_day INT;
  DECLARE rand_dep_minutes INT;
  DECLARE rand_duration_minutes INT;
  DECLARE dep_time TIME;
  DECLARE arr_time TIME;
  DECLARE trip_date DATE;
  DECLARE b INT;

  IF (SELECT COUNT(*) FROM Bus_info) = 0 THEN
    SELECT 'No buses available; aborting generation.' AS Message; 
    LEAVE BEGIN; 
  END IF;

  WHILE i < trip_count DO
    SELECT BusID INTO b FROM Bus_info ORDER BY RAND() LIMIT 1;
    SET rand_day = FLOOR(RAND() * (days_forward + 1));
    SET trip_date = CURDATE() + INTERVAL rand_day DAY;

    SET rand_dep_minutes = 360 + FLOOR(RAND() * (14 * 60)); -- 06:00 (360) to 20:00 (1200) exclusive upper bound
    SET rand_duration_minutes = 30 + FLOOR(RAND() * 211);  -- 30 to 240-ish minutes

    SET dep_time = SEC_TO_TIME(rand_dep_minutes * 60);
    SET arr_time = SEC_TO_TIME(LEAST((rand_dep_minutes + rand_duration_minutes) * 60, 86340)); -- cap before midnight

    INSERT INTO Trip (TripDate, DepartureTime, ArrivalTime, BusID)
    VALUES (trip_date, dep_time, arr_time, b);

    SET i = i + 1;
  END WHILE;
END //
DELIMITER ;

-- To clean up after use (optional): DROP PROCEDURE IF EXISTS generate_random_trips;
