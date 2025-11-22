package com.buspass;

import com.buspass.queries.*;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        BusQuery busQuery = new BusQuery();
        RouteQuery routeQuery = new RouteQuery();
        PaymentQuery paymentQuery = new PaymentQuery();
        DriverQuery driverQuery = new DriverQuery();
        TripQuery tripQuery = new TripQuery();

        // userService.registerUser("hello", "there" ,69, "09", "08", 2);
        // System.out.println(userService.getUserById(10));
        // System.out.println(userService.getAllUsers());

        // userService.updateUsername(1, "YourMom");
        // userService.updateRoleID(1, 1);

        // System.out.println(userService.getUserById(1));
        // userService.deleteUser(11);

        // System.out.println(busQuery.getBusInfoById(2));
        // System.out.println(routeQuery.getAllRoutes());

        
        // System.out.println(userService.getAllUsers());
        // System.out.println(driverQuery.getDriverById(1));

        // System.out.println(driverQuery.getAllDrivers());
        // System.out.println(driverQuery.updatePhoneNumber(8, "6999999"));

        // System.out.println(driverQuery.getTripsTraveledById(1));
        // System.out.println(tripQuery.getTripsWithJoinAndDrivers());
        // System.out.println(busQuery.getTripsTraveledById(1));
        // System.out.println(userService.getTicketsByUserId(1));
        // tripQuery.generateRandomTrips(30, 5);
    }
}
