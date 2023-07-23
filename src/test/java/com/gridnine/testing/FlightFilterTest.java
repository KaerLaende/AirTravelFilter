package com.gridnine.testing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
class FlightFilterTest {
    private List<Flight> flights;
    @BeforeEach
    void setUp() {
        flights = new ArrayList<>();
        // "Нормальный" полёт
        Segment segment = new Segment(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(4));
        Flight flight = new Flight(Collections.singletonList(segment));
        flights.add(flight);

        // Полет, который уже состоялся
        Segment segment1 = new Segment(LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1));
        Flight flight1 = new Flight(Collections.singletonList(segment1));
        flights.add(flight1);
        // Не реалистичный полет
        Segment segment2 = new Segment(LocalDateTime.now().plusHours(1), LocalDateTime.now().minusHours(1));
        Flight flight2 = new Flight(Collections.singletonList(segment2));
        flights.add(flight2);
        // Полет с длительным временем ожидания на земле
        Segment segment3 = new Segment(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        Segment segment4 = new Segment(LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(6));
        List<Segment> segmentsList = new ArrayList<>(Arrays.asList(segment3, segment4));
        Flight flight3 = new Flight(segmentsList);
        flights.add(flight3);
    }
    @Test
    void testFilterFlights() {
        List<Flight> filteredFlights = FlightFilter.filterFlights(flights);
        // Проверка, что отфильтрован только один полет
        Assertions.assertEquals(1, filteredFlights.size());
        // Проверка, что отфильтрован правильный полет
        Assertions.assertEquals(flights.get(0), filteredFlights.get(0));
    }
}