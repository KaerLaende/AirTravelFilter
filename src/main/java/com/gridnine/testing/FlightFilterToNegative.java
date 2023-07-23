package com.gridnine.testing;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-фильтр с 3 разными фильтрами, которые используют Stream API и лямбда-выражения для выполнения
 * операции фильтрации и возвращают отфильтрованный список полетов.
 */

public class FlightFilterToNegative {

    /**
     * фильтрует полеты, у которых дата вылета раньше текущей даты и времени.
     *
     * @param flights список полетов из класса FlightBuilder
     * @return отфильтрованный список полетов
     */
    public static List<Flight> filterByAlreadyDeparted(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    /**
     * фильтрует полеты, у которых имеются сегменты с датой прилёта раньше даты вылета.
     *
     * @param flights список полетов из класса FlightBuilder
     * @return отфильтрованный список полетов
     */
    public static List<Flight> filterByNotRealisticDate(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(segment.getArrivalDate())))
                .collect(Collectors.toList());
    }

    /**
     * фильтрует полеты, у которых суммарное время на земле больше 2х часов.
     *
     * @param flights список полетов из класса FlightBuilder
     * @return отфильтрованный список полетов
     */
    public static List<Flight> filterFlightsWithLongGroundTimes(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    long totalGroundTime = 0;
                    for (int i = 1; i < segments.size(); i++) {
                        long groundTime = Duration.between(segments.get(i - 1).getArrivalDate(), segments.get(i).getDepartureDate()).toHours();
                        totalGroundTime += groundTime;
                    }
                    return totalGroundTime > 2;
                })
                .collect(Collectors.toList());
    }
}

