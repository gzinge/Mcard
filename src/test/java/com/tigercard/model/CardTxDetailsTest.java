package com.tigercard.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class CardTxDetailsTest {

    private CardTxDetails cardTxDetailsUnderTest;

    @BeforeEach
    void setUp() {
        cardTxDetailsUnderTest = new CardTxDetails
                (101L, "18-01-2021", "10:20:00",
                        Station.STATION_201,
                        Station.STATION_201, 35.0,
                        "description",
                        "111225245878001");
    }

    @Test
    void testUpdateFare() {
        // Setup

        // Run the test
        cardTxDetailsUnderTest.updateFare(20.0);

        // Verify the results
        Assertions.assertThat(20.0).isEqualTo(cardTxDetailsUnderTest.getFare());
    }

    @Test
    void testToString() {
        // Setup

        // Run the test
        final String result = cardTxDetailsUnderTest.toString();

        // Verify the results
        assertEquals("| 111225245878001 | MONDAY     | 10:20:00  | WEST | WEST | 35.0 | description                                                             |",
                result);
    }

    @Test
    void testCompareTo() {
        // Setup

        // Run the test
        final int result = cardTxDetailsUnderTest.compareTo(new CardTxDetails
                (101L, "18-01-2021", "10:20:00",
                        Station.STATION_201,
                        Station.STATION_201, 35.0,
                        "description",
                        "111225245878001"));

        // Verify the results
        assertEquals(0, result);
    }

    @Test
    void testCompareTo_ThrowsNullPointerException() {
        // Setup

        // Run the test
        assertThrows(NullPointerException.class, () -> cardTxDetailsUnderTest.compareTo(null));
    }

    @Test
    void testCompareTo_ThrowsClassCastException() {
        // Setup

        // Run the test
        assertThrows(ClassCastException.class, () -> cardTxDetailsUnderTest.compareTo(new Object()));
    }

    @Test
    void testEquals() {
        // Setup

        // Run the test
        final boolean result = cardTxDetailsUnderTest.equals(new CardTxDetails
                (101L, "18-01-2021", "10:20:00",
                        Station.STATION_201,
                        Station.STATION_201, 35.0,
                        "description",
                        "111225245878001"));

        // Verify the results
        assertTrue(result);
    }
}
