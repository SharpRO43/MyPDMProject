package com.buspass.queries;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import com.buspass.db.QueryExecutionModule;

public class TripQuery {

    public List<Map<String, Object>> getUpcomingTrips() {
        String sql = "SELECT TripID, TripDate, DepartureTime, ArrivalTime, Capacity, \r\n" + //
                        "    RouteName, StartLocation, EndLocation, Duration\r\n" + //
                        "FROM Trip tr JOIN Bus_Info b ON tr.BusID = b.BusID\r\n" + //
                        "    JOIN Route r ON b.RouteID = r.RouteID\r\n" + //
                        "WHERE TripDate > CURDATE()\r\n" + //
                        "    OR (TripDate = CURDATE()\r\n" + //
                        "       AND DepartureTime >= NOW())\r\n" +
                        "ORDER BY TripDate, DepartureTime";
        return QueryExecutionModule.executeQuery(sql);
    }

    //#region ADMIN PRIVILEDGES
    
    public Map<String, Object> getTripById(int tripId) {
        String sql = "SELECT * FROM Trip WHERE TripID = ? ";
        List<Map<String, Object>> trips = QueryExecutionModule.executeQuery(sql, tripId);
        if (!trips.isEmpty()) return trips.get(0);
        return null;
    }

    public List<Map<String, Object>> getAllTrips() {
        String sql = "SELECT * FROM Trip\r\n" +
                     "ORDER BY TripDate, DepartureTime";
        return QueryExecutionModule.executeQuery(sql);
    }

    /** Trips joined with Buses and Routes */
    public List<Map<String, Object>> getTripsWithJoin() {
        String sql = "SELECT t.TripID, t.TripDate, t.DepartureTime, t.ArrivalTime, b.PlateNumber, r.RouteName "
                   + "FROM Trip t "
                   + "    LEFT JOIN Bus_info b ON t.BusID = b.BusID "
                   + "    LEFT JOIN Route r ON b.RouteID = r.RouteID "
                   + "ORDER BY TripDate, DepartureTime";
        return QueryExecutionModule.executeQuery(sql);
    }

    /** Trips joined with Buses and Routes */
    public List<Map<String, Object>> getTripsWithJoinAndDrivers() {
        String sql = 
                "SELECT t.TripID, t.TripDate, t.DepartureTime, t.ArrivalTime, b.PlateNumber, r.RouteName, DriverName "
                + "FROM Trip t "
                + "LEFT JOIN Bus_info b ON t.BusID = b.BusID "
                + "LEFT JOIN Route r ON b.RouteID = r.RouteID "
                + "LEFT JOIN Driver d ON b.DriverID = d.DriverID "
                + "ORDER BY TripDate, DepartureTime";
        return QueryExecutionModule.executeQuery(sql);
    }

    public boolean createTrip(String tripDate, String departureTime, String arrivalTime, int busId) {
        // Schema Trip: TripDate, DepartureTime, ArrivalTime, BusID (no RouteID column)
        String sql = "INSERT INTO Trip (TripDate, DepartureTime, ArrivalTime, BusID) VALUES (?, ?, ?, ?);";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, tripDate, departureTime, arrivalTime, busId);
        return rowsAffected > 0;
    }

    public boolean removeTrip(int tripId) {
        String sql = "DELETE FROM Trip WHERE TripID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, tripId);
        return rowsAffected > 0;
    }

    /* Update operations */
    public boolean updateTripDate(int tripId, String newDate) {
        String sql = "UPDATE Trip SET TripDate = ? WHERE TripID = ?";
        return QueryExecutionModule.executeUpdate(sql, newDate, tripId) > 0;
    }

    public boolean updateTimes(int tripId, String departureTime, String arrivalTime) {
        String sql = "UPDATE Trip SET DepartureTime = ?, ArrivalTime = ? WHERE TripID = ?";
        return QueryExecutionModule.executeUpdate(sql, departureTime, arrivalTime, tripId) > 0;
    }

    public boolean updateBusId(int tripId, int busId) {
        String sql = "UPDATE Trip SET BusID = ? WHERE TripID = ?";
        return QueryExecutionModule.executeUpdate(sql, busId, tripId) > 0;
    }

    /**
     * Generate random Trips between today and today + daysAhead.
     * Departure time random between 06:00 and 20:00, duration 30 - 240 minutes.
     * Picks random BusID from existing buses.
     * @param count number of trips to generate
     * @param daysAhead inclusive upper bound of day offset from today
     * @return number of trips successfully inserted
     */
    public int generateRandomTrips(int count, int daysAhead) {
        List<Map<String,Object>> buses = QueryExecutionModule.executeQuery("SELECT BusID FROM Bus_info");

        if (buses == null || buses.isEmpty()) return 0;

        LocalDate today = LocalDate.now();
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        int inserted = 0;
        for (int i = 0; i < count; i++) {
            int busIndex = rng.nextInt(buses.size());
            int busId = Integer.parseInt(buses.get(busIndex).get("BusID").toString());
            int dayOffset = rng.nextInt(daysAhead + 1); // 0..daysAhead
            LocalDate tripDate = today.plusDays(dayOffset);
            int depMinutes = rng.nextInt(6 * 60, 20 * 60); // 06:00 to <20:00
            int durationMinutes = rng.nextInt(30, 241); // 30..240
            LocalTime departure = LocalTime.of(depMinutes / 60, depMinutes % 60);
            LocalTime arrival = departure.plusMinutes(durationMinutes);

            if (arrival.isBefore(departure)) arrival = departure.plusHours(1); // safety
            String dateStr = tripDate.toString();
            String depStr = departure.format(timeFmt);
            String arrStr = arrival.format(timeFmt);

            int rows = QueryExecutionModule.executeUpdate(
                "INSERT INTO Trip (TripDate, DepartureTime, ArrivalTime, BusID) VALUES (?, ?, ?, ?)",
                dateStr, depStr, arrStr, busId
            );
            if (rows > 0) inserted += 1;
        }
        return inserted;
    }

    /** Convenience method to generate 50 trips over next 20 days */
    public int generateRandomTripsDefault() { return generateRandomTrips(50, 20); }

    //#endregion
}
