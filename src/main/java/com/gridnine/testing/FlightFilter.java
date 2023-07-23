package com.gridnine.testing;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-фильтр с 3 разными фильтрами одновременно, оставляющий только те полёты которые без длительных пересадок,
 * реалистичные и те что состоятся в будущем.
 */

public class FlightFilter {
    public static List<Flight> filterFlights(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())))
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isBefore(segment.getArrivalDate())))
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    long totalGroundTime = 0;
                    for (int i = 1; i < segments.size(); i++) {
                        long groundTime = Duration.between(segments.get(i - 1).getArrivalDate(), segments.get(i).getDepartureDate()).toHours();
                        totalGroundTime += groundTime;
                    }
                    return totalGroundTime <= 2;
                })
                .collect(Collectors.toList());
    }
}

