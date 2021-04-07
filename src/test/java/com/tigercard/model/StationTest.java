package com.tigercard.model;

import com.tigercard.strategy.enums.FareZoneEnum;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
class StationTest {

    @Test
    void testGetId() {
        assertEquals(2L, Station.STATION_201.getId());
        assertEquals(20L, Station.STATION_202.getId());
        assertEquals(202L, Station.STATION_203.getId());
        assertEquals(2002L, Station.STATION_204.getId());
        assertEquals(1L, Station.STATION_101.getId());
        assertEquals(10L, Station.STATION_102.getId());
        assertEquals(101L, Station.STATION_103.getId());
        assertEquals(1001L, Station.STATION_104.getId());
    }

    @Test
    void testGetName() {
        assertEquals("STATION_201", Station.STATION_201.getName());
        assertEquals("STATION_202", Station.STATION_202.getName());
        assertEquals("STATION_203", Station.STATION_203.getName());
        assertEquals("STATION_204", Station.STATION_204.getName());
        assertEquals("STATION_101", Station.STATION_101.getName());
        assertEquals("STATION_102", Station.STATION_102.getName());
        assertEquals("STATION_103", Station.STATION_103.getName());
        assertEquals("STATION_104", Station.STATION_104.getName());
    }

    @Test
    void testGetZone() {
        assertEquals(FareZoneEnum.WEST, Station.STATION_201.getZone());
        assertEquals(FareZoneEnum.WEST, Station.STATION_202.getZone());
        assertEquals(FareZoneEnum.WEST, Station.STATION_203.getZone());
        assertEquals(FareZoneEnum.WEST, Station.STATION_204.getZone());
        assertEquals(FareZoneEnum.EAST, Station.STATION_101.getZone());
        assertEquals(FareZoneEnum.EAST, Station.STATION_102.getZone());
        assertEquals(FareZoneEnum.EAST, Station.STATION_103.getZone());
        assertEquals(FareZoneEnum.EAST, Station.STATION_104.getZone());
    }

    @Test
    void testGetById() {
        assertEquals(Station.STATION_201, Station.getById(2L));
    }
}
