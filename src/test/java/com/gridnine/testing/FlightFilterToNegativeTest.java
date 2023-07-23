package com.gridnine.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


class FlightFilterToNegativeTest {
    private List<Flight> flights;

    @BeforeEach
    void setUp() {
        flights = new ArrayList<>();

        //Вылетевший раньше текущего времени
        Segment segment1 = new Segment(LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1));
        Flight flight1 = new Flight(Collections.singletonList(segment1));
        flights.add(flight1);

        // Не реалистичный полёт
        Segment segment2 = new Segment(LocalDateTime.now().plusHours(1), LocalDateTime.now().minusHours(1));
        Flight flight2 = new Flight(Collections.singletonList(segment2));
        flights.add(flight2);

        // длительный полёт
        Segment segment3 = new Segment(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        Segment segment4 = new Segment(LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(6));
        List<Segment> segmentsList = new ArrayList<>(Arrays.asList(segment3, segment4));
        Flight flight3 = new Flight(segmentsList);
        flights.add(flight3);
    }

    /**
     * Проверка на положительный результат:
     */
    @Test
    void testFilterByAlreadyDeparted() {
        List<Flight> filteredFlights = FlightFilterToNegative.filterByAlreadyDeparted(flights);
        Assertions.assertEquals(1, filteredFlights.size());
        Assertions.assertEquals(flights.get(0), filteredFlights.get(0));
    }

    @Test
    void testFilterByNotRealisticDate() {
        List<Flight> filteredFlights = FlightFilterToNegative.filterByNotRealisticDate(flights);
        Assertions.assertEquals(1, filteredFlights.size());
        Assertions.assertEquals(flights.get(1), filteredFlights.get(0));
    }

    @Test
    void testFilterForLongFlights() {
        List<Flight> filteredFlights = FlightFilterToNegative.filterFlightsWithLongGroundTimes(flights);
        Assertions.assertEquals(1, filteredFlights.size());
        Assertions.assertEquals(flights.get(2), filteredFlights.get(0));
    }

    /**
     * Проверка на отрецательный результат:
     */

    @Test
    public void testFilterByAlreadyDepartedNeg() {
        List<Flight> result = FlightFilterToNegative.filterByAlreadyDeparted(flights);
        assertNotEquals(2, result.size());
    }

    @Test
    public void testFilterByNotRealisticDateNeg() {
        List<Flight> result = FlightFilterToNegative.filterByNotRealisticDate(flights);
        assertNotEquals(2, result.size());
    }

    @Test
    public void testFilterForLongFlightsNeg() {
        List<Flight> result = FlightFilterToNegative.filterFlightsWithLongGroundTimes(flights);
        assertNotEquals(2, result.size());
    }
}