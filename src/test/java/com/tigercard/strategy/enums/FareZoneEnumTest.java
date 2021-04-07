package com.tigercard.strategy.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FareZoneEnumTest {

    @Test
    void testGetFare() {
        assertEquals(30.0, FareZoneEnum.EAST.getFare(), 0.0001);
        assertEquals(25.0, FareZoneEnum.WEST.getFare(), 0.0001);
        assertEquals(35.0, FareZoneEnum.EAST_WEST_CROSS_ZONE.getFare(), 0.0001);
        assertEquals(100.0, FareZoneEnum.EAST_DAILY_CAP.getFare(), 0.0001);
        assertEquals(80.0, FareZoneEnum.WEST_DAILY_CAP.getFare(), 0.0001);
        assertEquals(120.0, FareZoneEnum.EAST_WEST_CROSS_ZONE_DAILY_CAP.getFare(), 0.0001);
        assertEquals(500.0, FareZoneEnum.EAST_WEEKLY_CAP.getFare(), 0.0001);
        assertEquals(400.0, FareZoneEnum.WEST_WEEKLY_CAP.getFare(), 0.0001);
        assertEquals(600.0, FareZoneEnum.EAST_WEST_CROSS_ZONE_WEEKLY_CAP.getFare(), 0.0001);
    }
}
