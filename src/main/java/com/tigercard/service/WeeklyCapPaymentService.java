package com.tigercard.service;

import com.tigercard.model.Station;
import com.tigercard.model.TransactionDayReportBean;
import com.tigercard.strategy.enums.FareMessage;
import com.tigercard.strategy.enums.FareZoneEnum;
import com.tigercard.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class WeeklyCapPaymentService {

    public List<TransactionDayReportBean> initWeekJourney(Path filePath) throws IOException, ParseException {
        Scanner fileReader = new Scanner(filePath);
        List<TransactionDayReportBean> transactionDayReportBeans = new ArrayList<>();
        while(fileReader.hasNext()) {
            String record = fileReader.nextLine();
            if(record.isEmpty()) continue;

            if("Stop".equalsIgnoreCase(record)) break;

            transactionDayReportBeans.add(buildTransactionDayReportBean(record));
        }
        fileReader.close();
        return transactionDayReportBeans;
    }

    private TransactionDayReportBean buildTransactionDayReportBean(String record) {
        Scanner dataReader = new Scanner(record);

        String day = dataReader.next();
        FareZoneEnum fromZone = Station.getById(dataReader.nextInt()).getZone();
        dataReader.next();
        FareZoneEnum toZone   = Station.getById(dataReader.nextInt()).getZone();

        double  fare = dataReader.nextDouble();
        dataReader.close();
        boolean isSame = fromZone.equals(toZone);
        return new TransactionDayReportBean(fare,day,isSame,fromZone);
    }

    public double transactionDayCalAndReport(List<TransactionDayReportBean> transactionReportBeans ) throws ParseException {

        String title = "From " +
        transactionReportBeans.get(0).getDay() + " To " +
                transactionReportBeans.get(transactionReportBeans.size()-1).getDay();

        double amount = calTotalFare(transactionReportBeans);
        System.out.println("***** Weekly Report Date : " +title+ "********");
        System.out.println("|----------------------------------------------------------------------------------------------------------|");
        System.out.println("| " +
                String.format("%-10s","Day") +
                "| " +
                String.format("%-12s","Zone") +
                "| " +
                String.format("%-5s","Fare") +
                "| " +
                String.format("%-72s","Description") +
                "|");
        System.out.println("|----------------------------------------------------------------------------------------------------------|");
        transactionReportBeans.stream().forEach(System.out::println);
        System.out.println("|----------------------------------------------------------------------------------------------------------|");
        System.out.println("Output : " +amount);
        return amount;

    }

    private double calTotalFare(List<TransactionDayReportBean> transactionReportBeans){

        double transactionAmount=0.0;
        double totalWeeklyFare=0.0;
        boolean isSameZone = transactionReportBeans.stream().allMatch(
                txDetails -> txDetails.isSame()
        );

        for (TransactionDayReportBean txDetails : transactionReportBeans){
            if(DateUtils.isMonday(txDetails.getDay())){
                totalWeeklyFare=0.0;
            }

            totalWeeklyFare += txDetails.getFare();

            if(isSameZone){
                totalWeeklyFare = getTotalFareOfSameZone(totalWeeklyFare, txDetails);
            }else {
                if(txDetails.getFare() < FareZoneEnum.EAST_WEST_CROSS_ZONE_DAILY_CAP.getFare()) {
                    txDetails.setMessage(FareMessage.DAILY_CAP_NOT_REACHED.getMessage());
                }
                else{
                    txDetails.setMessage(FareMessage.DAILY_CAP_REACHED.getMessage());
                }
                totalWeeklyFare = dailyCapFare(totalWeeklyFare, txDetails,
                        FareZoneEnum.EAST_WEST_CROSS_ZONE_WEEKLY_CAP.getFare());
            }
            transactionAmount += txDetails.getFare();
        }
        return transactionAmount;
    }

    private double getTotalFareOfSameZone(double totalFare,
                                          TransactionDayReportBean txDetails) {
        switch (txDetails.getFareZoneEnum()){
            case EAST:
                if(txDetails.getFare() < FareZoneEnum.EAST_DAILY_CAP.getFare()) {
                    txDetails.setMessage(FareMessage.DAILY_CAP_NOT_REACHED.getMessage());
                }
                else{
                    txDetails.setMessage(FareMessage.DAILY_CAP_REACHED.getMessage());
                }

                totalFare = dailyCapFare(totalFare, txDetails,
                        FareZoneEnum.EAST_WEEKLY_CAP.getFare());

                break;
            case WEST:
                if(txDetails.getFare() < FareZoneEnum.WEST_DAILY_CAP.getFare()) {
                    txDetails.setMessage(FareMessage.DAILY_CAP_NOT_REACHED.getMessage());
                }
                else{
                    txDetails.setMessage(FareMessage.DAILY_CAP_REACHED.getMessage());
                }
                totalFare = dailyCapFare(totalFare, txDetails,
                        FareZoneEnum.WEST_WEEKLY_CAP.getFare());
                break;
            default:
                break;
        }
        return totalFare;

    }

    private double dailyCapFare(double totalFare, TransactionDayReportBean transactionReportBean,
                                double dayFare) {
        if (totalFare >= dayFare){
            totalFare = totalFare - transactionReportBean.getFare();
            double calFare = dayFare - totalFare;
            transactionReportBean.updateFare(calFare);
            updateTxMessage(transactionReportBean, dayFare, transactionReportBean.getFare());
            totalFare += transactionReportBean.getFare();
        }
        return totalFare;
    }

    private void updateTxMessage(TransactionDayReportBean txDetails, double dayFare, double weekFare) {
        if(weekFare == 0){
            txDetails.setMessage(
                    String.format(FareMessage.WEEKLY_CAP_REACHED.getMessage(),
                            dayFare)
            );
        }else {
            txDetails.setMessage(
                    String.format(FareMessage.WEEKLY_CAP.getMessage(),
                            dayFare, weekFare)
            );
        }
    }


}
