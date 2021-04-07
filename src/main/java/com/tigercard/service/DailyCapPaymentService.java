package com.tigercard.service;

import com.tigercard.model.TransactionDayReportBean;
import com.tigercard.model.CardTxDetails;
import com.tigercard.model.ChargeAndDescriptonBean;
import com.tigercard.model.Station;
import com.tigercard.strategy.FareStrategy;
import com.tigercard.strategy.enums.FareMessage;
import com.tigercard.strategy.enums.FareZoneEnum;
import com.tigercard.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;

@Service
public class DailyCapPaymentService {

    /**
     *
     * @param filePath file Path of Day transaction records.
     * @return Map : Key transactionDate & Vaule List of transactions.
     * @throws IOException
     * @throws ParseException
     */
    public Map<String,List<CardTxDetails>> initDayJourney(Path filePath) throws IOException, ParseException {
        Scanner fileReader = new Scanner(filePath);
        List<CardTxDetails> dayTxDetailsList = new ArrayList<>();
        Map<String,List<CardTxDetails>> aDayTransationByDate = new LinkedHashMap<>();
        String dateOfTx="";
        while(fileReader.hasNext()){
            String record = fileReader.nextLine();
            if(record.isEmpty()) {
                aDayTransationByDate.put(dateOfTx,dayTxDetailsList);
                dayTxDetailsList = new ArrayList<>();
                continue;
            }
            if("Stop".equalsIgnoreCase(record)) break;
            Scanner readline = new Scanner(record);
            dateOfTx = readline.next();
            dayTxDetailsList.add(buildCardTxDetails(record));
            readline.close();
        }
        fileReader.close();
        return aDayTransationByDate;
    }

    private CardTxDetails buildCardTxDetails( String record) throws ParseException {
        Scanner dataReader = new Scanner(record);

        String cardNumber =  "1001";
        String date = dataReader.next();
        String time = dataReader.next();
        Station from = Station.getById(dataReader.nextLong());
        Station to = Station.getById(dataReader.nextLong());
        dataReader.close();
        ChargeAndDescriptonBean chargeAndDescriptonBean =
                FareStrategy.getInstance().getJourneyFare(DateUtils.toDate(date,time),from,to);
        return new CardTxDetails(
                (long)Math.random(), date,time,from,to,
                chargeAndDescriptonBean.getFare(),chargeAndDescriptonBean.getMessage(),cardNumber
        );
    }

    public TransactionDayReportBean transactionDayCalAndReport(List<CardTxDetails> cardTxDetailsList){
        System.out.println("\nTravle Date : "+cardTxDetailsList.get(0).getDate());
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("| " +
                String.format("%-5s","Card") +
                " | " +
                String.format("%-10s","Day") +
                " | " +
                String.format("%-10s","Time") +
                "| " +
                String.format("%-5s","From") +
                "| " +
                String.format("%-5s","To") +
                "| " +
                String.format("%-5s","Fare") +
                "| " +
                String.format("%-72s","Description") +
                "|");
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------|");
        TransactionDayReportBean dayTransactionReportBean = getTotalFare(cardTxDetailsList);
        cardTxDetailsList.forEach(System.out::println);
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("Output: "+dayTransactionReportBean.getFare()+"\n");
        return dayTransactionReportBean;
    }

    private TransactionDayReportBean getTotalFare(List<CardTxDetails> cardTxDetailsList) {
        double totalFare=0.0;
        boolean isSameZone = cardTxDetailsList.stream().allMatch(
                cardTxDetails -> cardTxDetails.isSameZone()
        );

        Optional<FareZoneEnum> fareZoneEnum =
                cardTxDetailsList.stream().map(cardTxDetails ->
                        cardTxDetails.getFrom().getZone()).findFirst();
        String day="";
        for (CardTxDetails cardTxDetails : cardTxDetailsList){
            day = day.isEmpty() ? cardTxDetails.getDate() : day;
            totalFare += cardTxDetails.getFare();
            if(isSameZone){
                totalFare = getTotalFareOfSameZone(totalFare, fareZoneEnum, cardTxDetails);
            }else {
                totalFare = dailyCapFare(totalFare, cardTxDetails,
                            FareZoneEnum.EAST_WEST_CROSS_ZONE_DAILY_CAP.getFare());
            }
        }
        return new TransactionDayReportBean(totalFare,day,isSameZone , fareZoneEnum.get());
    }

    private double getTotalFareOfSameZone(double totalFare, Optional<FareZoneEnum> fareZoneEnum,
                                          CardTxDetails cardTxDetails) {
        switch (fareZoneEnum.get()){
            case EAST:
                totalFare = dailyCapFare(totalFare, cardTxDetails,
                        FareZoneEnum.EAST_DAILY_CAP.getFare());
                break;
            case WEST:
                totalFare = dailyCapFare(totalFare, cardTxDetails,
                        FareZoneEnum.WEST_DAILY_CAP.getFare());
                break;
            default:
                break;
        }
        return totalFare;
    }

    private double dailyCapFare(double totalFare, CardTxDetails cardTxDetails,
                                double zonefare) {
        if (totalFare > zonefare){
            totalFare = totalFare - cardTxDetails.getFare();
            double calFare = zonefare - totalFare;
            updateTxMessage(cardTxDetails, zonefare, calFare);
            cardTxDetails.updateFare(calFare);
            totalFare += cardTxDetails.getFare();
        }
        return totalFare;
    }

    private void updateTxMessage(CardTxDetails cardTxDetails, double zonefare, double calFare) {
        cardTxDetails.setDescription(
                String.format(FareMessage.DAILY_CAP_REACHED_CROSS_ZONE.getMessage(),
                        zonefare, calFare, cardTxDetails.getFare())
        );
    }
}
