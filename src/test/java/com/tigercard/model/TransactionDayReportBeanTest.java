package com.tigercard.model;

import com.tigercard.strategy.enums.FareZoneEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionDayReportBeanTest {

    private TransactionDayReportBean transactionDayReportBeanUnderTest;

    @BeforeEach
    void setUp() {
        transactionDayReportBeanUnderTest = new TransactionDayReportBean
                (100.0, "18-02-2021", false, FareZoneEnum.EAST);
    }

    @Test
    void testUpdateFare() {
        // Setup

        // Run the test
        transactionDayReportBeanUnderTest.updateFare(20.0);

        // Verify the results
        Assertions.assertThat(20.0).isEqualTo(transactionDayReportBeanUnderTest.getFare());
    }

    @Test
    void testGetZoneName() {
        // Setup

        // Run the test
        final String result = transactionDayReportBeanUnderTest.getZoneName();

        // Verify the results
        assertEquals(FareZoneEnum.EAST + " - " + FareZoneEnum.WEST, result);
    }
}
