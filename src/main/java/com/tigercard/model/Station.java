package com.tigercard.model;

import com.tigercard.strategy.enums.FareZoneEnum;

public enum Station {

    STATION_201(2, "STATION_201", FareZoneEnum.WEST),
    STATION_202(20, "STATION_202", FareZoneEnum.WEST),
    STATION_203(202,"STATION_203", FareZoneEnum.WEST),
    STATION_204(2002, "STATION_204", FareZoneEnum.WEST),

    STATION_101(1, "STATION_101", FareZoneEnum.EAST),
    STATION_102(10, "STATION_102", FareZoneEnum.EAST),
    STATION_103(101, "STATION_103", FareZoneEnum.EAST),
    STATION_104(1001, "STATION_104", FareZoneEnum.EAST);

    private final long id;
    private final String name;
    private final FareZoneEnum zone;

    private static final Station[] ALL = {
            STATION_201, STATION_202, STATION_203, STATION_204,
            STATION_101, STATION_102, STATION_103, STATION_104
    };

    Station(long id, String name, FareZoneEnum zone) {
        this.id = id;
        this.name = name;
        this.zone = zone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FareZoneEnum getZone() {
        return zone;
    }

    public static Station getById(long id) {
        return getStationById(id);
    }


    private static Station getStationById(long id) {
        for (Station station : ALL) {
            if (station.getId() == id)
                return station;
        }
        return null;
    }

}
