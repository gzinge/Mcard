package com.tigercard.strategy;

import com.tigercard.model.Station;
import com.tigercard.strategy.enums.FareDayEnum;
import com.tigercard.strategy.enums.FareZoneEnum;
import com.tigercard.model.ChargeAndDescriptonBean;
import com.tigercard.strategy.enums.FareMessage;
import com.tigercard.utils.DateUtils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class FareStrategy {

    private static final HashMap<FareDayEnum, List<String>> FARE_TIME_CACHE = new HashMap<>();
    private static final FareStrategy FARE_STRATEGY = new FareStrategy();
    private static final int SAME_ZONE_HOUR_WRITE_OFF = 5;

    static{
        FARE_TIME_CACHE.put(FareDayEnum.WEEKDAY_PICK_HOURS, Arrays.asList(
                "07:00 - 10:30", "17:00 - 20:00"
        ));

        FARE_TIME_CACHE.put(FareDayEnum.WEEKEND_PICK_HOURS, Arrays.asList(
                "09:00 - 11:00", "18:00 - 22:00"
        ));

        FARE_TIME_CACHE.put(FareDayEnum.WEEKDAY_OFF_PEAK_HOURS, Arrays.asList(
                "17:00 - 20:00"
        ));

        FARE_TIME_CACHE.put(FareDayEnum.WEEKEND_OFF_PEAK_HOURS, Arrays.asList(
                "18:00 - 22:00"
        ));
    }

    private FareStrategy(){ }

    public ChargeAndDescriptonBean getJourneyFare(Date journey, Station from, Station to){
        boolean isSameZone = from.getZone().equals(to.getZone());

        final LocalDateTime journeyDateTime
                = journey.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

        final boolean isWeekend = isWeekend(journeyDateTime.getDayOfWeek());

        double fare = freeHourFare(from, isSameZone,false);
        ChargeAndDescriptonBean chargeAndDescriptonBean
                = new ChargeAndDescriptonBean(fare, FareMessage.OFF_PEAK_SINGLE_FARE.getMessage());
        if(isWeekend) {
            if(isHourMatch(journey, getFareDayTime(FareDayEnum.WEEKEND_PICK_HOURS))){
                fare = freeHourFare(from, isSameZone,true);
                chargeAndDescriptonBean
                        = new ChargeAndDescriptonBean(fare, FareMessage.PEAK_HOURS_SINGLE_FARE.getMessage());
            }
            if(isHourMatch(journey, getFareDayTime(FareDayEnum.WEEKEND_OFF_PEAK_HOURS))
                   && to.getZone().equals(FareZoneEnum.EAST) && !from.getZone().equals(FareZoneEnum.EAST)){
                fare = freeHourFare(from, isSameZone,false);
                chargeAndDescriptonBean
                        = new ChargeAndDescriptonBean(fare, FareMessage.OFF_PEAK_SINGLE_FARE.getMessage());
            }
        }
        else{
            if(isHourMatch(journey, getFareDayTime(FareDayEnum.WEEKDAY_PICK_HOURS))){
                fare = freeHourFare(from, isSameZone,true);
                chargeAndDescriptonBean
                        = new ChargeAndDescriptonBean(fare, FareMessage.PEAK_HOURS_SINGLE_FARE.getMessage());
            }
            if(isHourMatch(journey, getFareDayTime(FareDayEnum.WEEKDAY_OFF_PEAK_HOURS))
                && to.getZone().equals(FareZoneEnum.EAST) && !from.getZone().equals(FareZoneEnum.EAST)){
                fare = freeHourFare(from, isSameZone,false);
                chargeAndDescriptonBean
                        = new ChargeAndDescriptonBean(fare, FareMessage.OFF_PEAK_SINGLE_FARE.getMessage());
            }
        }
        return chargeAndDescriptonBean;
    }

    private double freeHourFare(Station station, boolean isSameZone,boolean isPick){
        double fare;
        if(isSameZone){
            fare =  isPick ? station.getZone().getFare() : (station.getZone().getFare() - SAME_ZONE_HOUR_WRITE_OFF);
        }else{
            fare = isPick ? FareZoneEnum.EAST_WEST_CROSS_ZONE.getFare() :
                    (FareZoneEnum.EAST_WEST_CROSS_ZONE.getFare() - SAME_ZONE_HOUR_WRITE_OFF);
        }
        return fare;
    }


    private boolean isHourMatch(Date startDateTime, List<String> hours) {
        return hours.stream().anyMatch(hour -> DateUtils.isHourMatch(startDateTime, hour));
    }

    private boolean isWeekend(DayOfWeek date){
        return Stream.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
                .anyMatch((dayOfWeek) -> dayOfWeek.equals(date));
    }

    public static FareStrategy getInstance(){
        return FARE_STRATEGY;
    }

    private List<String> getFareDayTime(FareDayEnum fareDayEnum){
        return FARE_TIME_CACHE.get(fareDayEnum);
    }


}
