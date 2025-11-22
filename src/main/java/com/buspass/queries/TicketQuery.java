package com.buspass.queries;

import java.util.List;
import java.util.Map;

import com.buspass.db.QueryExecutionModule;

public class TicketQuery {
    
    /**
     * Obtain a Ticket Free of Charge 
     * 
     * @param userId UserID of the User who paid for the Ticket
     * @param tripId TripID of the Trip the Ticket is paid for
     */
    public boolean purchaseTicket(int userId, int tripId) {
        PaymentQuery paymentQuery = new PaymentQuery();

        // PaymentMethodID = 4 ==> Method = 'Free'
		boolean isPurchaseSuccessful = paymentQuery.createTransactionNow(userId, 0, 4);

        if (!isPurchaseSuccessful)
            return false;
        
        String ticketSQL = "INSERT INTO Ticket (TicketDateTime, UserID, TripID, PaymentID)\r\n" +
                           "VALUES (NOW(), ?, ?, LAST_INSERT_ID())";
        int rowsAffected = QueryExecutionModule.executeUpdate(ticketSQL, userId, tripId);
        
        return rowsAffected > 0;
    }
    
    /**
     * Purchase a Ticket with a cost
     * 
     * @param userId UserID of the User who paid for the Ticket
     * @param tripId TripID of the Trip the Ticket is paid for
     * @param paymentAmount amount of money paid for the ticket (in VND)
     * @param paymentMethodId PaymentMethodID corresponding to the method of payment (`Method` column). 
     *                        <p> (1) 1 for "Cash" 
     *                        <p> (2) 2 for "Card"
     *                        <p> (3) 3 for "Online Banking"
     *                        <p> (4) 4 for "Free"
     * @return true if the Ticket has been created successfully. Otherwise, false. If false is the case, it could imply 
     *         the payment for the Ticket has failed
     */
    public boolean purchaseTicket(int userId, int tripId, float paymentAmount, int paymentMethodId) {
        PaymentQuery paymentQuery = new PaymentQuery();

		boolean isPurchaseSuccessful = paymentQuery.createTransactionNow(userId, paymentAmount, paymentMethodId);

        if (!isPurchaseSuccessful)
            return false;
        
        String ticketSQL = "INSERT INTO Ticket (TicketDateTime, UserID, TripID, PaymentID)\r\n" +
                           "VALUES (NOW(), ?, ?, LAST_INSERT_ID())";
        int rowsAffected = QueryExecutionModule.executeUpdate(ticketSQL, userId, tripId);

        return rowsAffected > 0;
    }
    
    //#region ADMIN PRIVILEDGES

    public List<Map<String, Object>> getTicketsOfUser(int userId) {
        String sql = "SELECT TicketID, TicketDateTime, t.TripID, RouteName, StartLocation, EndLocation, TripDate, DepartureTime, ArrivalTime\r\n" + //
                        "FROM User u JOIN Ticket t ON u.UserID = t.TicketID\r\n" + //
                        "    JOIN Trip tr ON tr.TripID = t.TripID\r\n" + //
                        "    JOIN Bus_Info b ON tr.BusID = b.BusID\r\n" + //
                        "    JOIN Route r ON b.RouteID = r.RouteID\r\n" + //
                        "WHERE u.UserID = ?";
            
        List<Map<String, Object>> users = QueryExecutionModule.executeQuery(sql, userId);
        
        return users;
    }

    public boolean changeTripTo(int ticketId, int tripId) {
        String sql = "UPDATE Ticket SET TripID = ? WHERE TicketID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, tripId, ticketId);
        return rowsAffected > 0;
    }

    public boolean deleteTicket(int ticketId) {
        String sql = "DELETE FROM Ticket WHERE TicketID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, ticketId);
        return rowsAffected > 0;
    }

    //#endregion
}
