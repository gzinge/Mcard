package com.tigercard.controller;

import com.tigercard.model.CardTxDetails;
import com.tigercard.model.TransactionDayReportBean;
import com.tigercard.service.DailyCapPaymentService;
import com.tigercard.service.WeeklyCapPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TigerCardController {

    @Autowired
    private DailyCapPaymentService daiyCapPaymentService;

    @Autowired
    private WeeklyCapPaymentService weeklyCapPaymentService;


    @Autowired
    public TigerCardController(DailyCapPaymentService daiyCapPaymentService,WeeklyCapPaymentService weeklyCapPaymentService){
        this.daiyCapPaymentService = daiyCapPaymentService;
        this.weeklyCapPaymentService = weeklyCapPaymentService;
    }

    public List<TransactionDayReportBean> processDayTransaction(Path path){
        try {
            Map<String, List<CardTxDetails>> data  = daiyCapPaymentService.initDayJourney(path);
            Collection<List<CardTxDetails>> cardTxDetails = data.values();
            List<TransactionDayReportBean>  dayTransactionReportBeans =
                    cardTxDetails.stream()
                            .map(daiyCapPaymentService::transactionDayCalAndReport).
                            collect(Collectors.toList());
            return dayTransactionReportBeans;
        } catch (IOException e) {
            System.err.println("Error Message : " +e.getMessage() +" " +
                    " Cause : "+ e.getCause().toString());
        } catch (ParseException e) {
            System.err.println("Error Message : " +e.getMessage() +" " +
                    " Cause : "+ e.getCause().toString());
        }
        return null;
    }

    public double processWeeklyTransaction(Path path) {
        try {
            List<TransactionDayReportBean> transactionDayReportBeans =
                    weeklyCapPaymentService.initWeekJourney(path);
            return weeklyCapPaymentService.transactionDayCalAndReport(transactionDayReportBeans);
        }catch (IOException e) {
            System.err.println("Error Message : " +e.getMessage() +" " +
                    " Cause : "+ e.getCause().toString());
        } catch (ParseException e) {
            System.err.println("Error Message : " +e.getMessage() +" " +
                    " Cause : "+ e.getCause().toString());
        }
        return 0;
    }

    public double runAllTransaction(Path dayPath) {

        try {
            Map<String, List<CardTxDetails>> data = daiyCapPaymentService.initDayJourney(dayPath);

            Collection<List<CardTxDetails>> cardTxDetails = data.values();
            List<TransactionDayReportBean> dayTransactionReportBeans =
                    cardTxDetails.stream()
                            .map(daiyCapPaymentService::transactionDayCalAndReport).
                            collect(Collectors.toList());
            return weeklyCapPaymentService.transactionDayCalAndReport(dayTransactionReportBeans);

        }catch (IOException e) {
                System.err.println("Error Message : " +e.getMessage() +" " +
                        " Cause : "+ e.getCause().toString());
        } catch (ParseException e) {
            System.err.println("Error Message : " +e.getMessage() +" " +
                    " Cause : "+ e.getCause().toString());
        }
        return 0;
    }
}
