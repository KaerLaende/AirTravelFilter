package com.gridnine.testing;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("Общий список: ");
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            List<Segment> segments = flight.getSegments();
            System.out.println("Полёт " + i + " состоит из  - " + segments.size() + " сегментов");
            for (int i1 = 0; i1 < segments.size(); i1++) {
                System.out.print("|" + segments.get(i1));
            }
            System.out.println();
        }

        List<Flight> filteredFlights1 = FlightFilterToNegative.filterByAlreadyDeparted(flights);
        List<Flight> filteredFlights2 = FlightFilterToNegative.filterByNotRealisticDate(flights);
        List<Flight> filteredFlights3 = FlightFilterToNegative.filterFlightsWithLongGroundTimes(flights);

        System.out.println("Полёты которые не прошли фильтрацию:");
        System.out.println("1.Уже вылетели");
        System.out.println(filteredFlights1);
        System.out.println("2.Прилет раньше вылета");
        System.out.println(filteredFlights2);
        System.out.println("3.Полёт, где больше 2 часов на земле");
        System.out.println(filteredFlights3);

        System.out.println("Отфильтрованный список полётов");
        List<Flight> filteredFlights = FlightFilter.filterFlights(flights);
        System.out.println(filteredFlights);

    }
}