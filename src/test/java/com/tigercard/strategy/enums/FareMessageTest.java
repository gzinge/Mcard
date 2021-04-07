package com.tigercard.strategy.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FareMessageTest {

    @Test
    void testGetMessage() {
        assertEquals("Peak hours Single fare.", FareMessage.PEAK_HOURS_SINGLE_FARE.getMessage());
        assertEquals("Off-peak single fare.", FareMessage.OFF_PEAK_SINGLE_FARE.getMessage());
        assertEquals("The Daily cap reached %s for zone 1 - 2. Charged %s instead of %s.", FareMessage.DAILY_CAP_REACHED_CROSS_ZONE.getMessage());
        assertEquals("Daily cap reached.", FareMessage.DAILY_CAP_REACHED.getMessage());
        assertEquals("Daily cap not reached.", FareMessage.DAILY_CAP_NOT_REACHED.getMessage());
        assertEquals("A weekly cap of %s reached before the daily cap of %s.", FareMessage.WEEKLY_CAP.getMessage());
        assertEquals("A weekly cap of %s reached.", FareMessage.WEEKLY_CAP_REACHED.getMessage());
    }
}
