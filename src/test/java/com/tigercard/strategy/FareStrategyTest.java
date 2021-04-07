package com.tigercard.strategy;

import com.tigercard.model.ChargeAndDescriptonBean;
import com.tigercard.model.Station;
import com.tigercard.strategy.enums.FareMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

class FareStrategyTest {

    private FareStrategy fareStrategyUnderTest;

    @BeforeEach
    void setUp() {
        fareStrategyUnderTest = FareStrategy.getInstance();
    }

    @Test
    void testGetJourneyFare() {
        // Setup

        // Run the test
        final ChargeAndDescriptonBean result = fareStrategyUnderTest.
                getJourneyFare(
                        new GregorianCalendar(2021,
                                    Calendar.JANUARY,
                                18 ,
                                10,
                                15,
                                00).getTime(),
                        Station.STATION_101, Station.STATION_201);

        // Verify the results
        Assertions.assertThat(35.00).isEqualTo(result.getFare());
        Assertions.assertThat(FareMessage.PEAK_HOURS_SINGLE_FARE.getMessage()).isEqualTo(result.getMessage());
    }

    @Test
    void testGetInstance() {
        // Setup

        // Run the test
        final FareStrategy result = FareStrategy.getInstance();

        // Verify the results
        Assertions.assertThat(result).isNotNull();
    }
}
