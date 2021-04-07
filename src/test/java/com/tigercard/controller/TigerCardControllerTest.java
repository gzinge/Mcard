package com.tigercard.controller;

import com.tigercard.model.CardTxDetails;
import com.tigercard.model.ChargeAndDescriptonBean;
import com.tigercard.model.Station;
import com.tigercard.model.TransactionDayReportBean;
import com.tigercard.service.DailyCapPaymentService;
import com.tigercard.service.WeeklyCapPaymentService;
import com.tigercard.strategy.FareStrategy;
import com.tigercard.strategy.enums.FareZoneEnum;
import com.tigercard.utils.DateUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
class TigerCardControllerTest {

    private DailyCapPaymentService daiyCapPaymentService;
    private WeeklyCapPaymentService weeklyCapPaymentService;
    private TigerCardController tigerCardControllerUnderTest;

    @BeforeEach
    void setUp() {
        daiyCapPaymentService = new DailyCapPaymentService();
        weeklyCapPaymentService = new WeeklyCapPaymentService();
        tigerCardControllerUnderTest = new TigerCardController(daiyCapPaymentService,weeklyCapPaymentService);
    }

    @Test
    void testProcessDayTransaction() throws Exception {

        List<CardTxDetails> cardTxDetailsList = Arrays.asList(
                getTx("18-01-2021", "10:20:00",1, 1),
                getTx("18-01-2021", "16:15:00",1, 1),
                getTx("18-01-2021", "18:15:00",1, 1),
                getTx("18-01-2021", "19:00:00",1, 2),
                getTx("18-01-2021", "21:50:00",2, 2)
        );
        final Map<String, List<CardTxDetails>> expectedResult = new HashMap<>();
        expectedResult.put("18-01-2021",cardTxDetailsList);

        // Setup
        String path = ResourceUtils.getFile("classpath:day.txt").getPath();

        // Run the test
        final List<TransactionDayReportBean> result = tigerCardControllerUnderTest.processDayTransaction(Paths.get(path).toAbsolutePath());

        // Verify the results
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.get(0).getFare()).isEqualTo(120);
    }



    @Test
    void testProcessWeeklyTransaction() throws Exception {
        // Setup
        String path = ResourceUtils.getFile("classpath:weekly.txt").getPath();
        // Run the test
        double weeklyTotalFare = tigerCardControllerUnderTest.processWeeklyTransaction(Paths.get(path).toAbsolutePath());
        // Verify the results
        Assertions.assertThat(weeklyTotalFare).isEqualTo(700);
    }

    @Test
    void testRunAllTransaction() throws Exception {
        // Setup
        String path = ResourceUtils.getFile("classpath:data.txt").getPath();

        // Run the test
        double totalFare = tigerCardControllerUnderTest.runAllTransaction(Paths.get(path).toAbsolutePath());

        // Verify the results
        Assertions.assertThat(totalFare).isEqualTo(685);
    }

    private CardTxDetails getTx(String date, String time,int from, int to) throws ParseException {
        ChargeAndDescriptonBean chargeAndDescriptonBean =
                FareStrategy.getInstance().getJourneyFare(DateUtils.toDate(date,time),
                        Station.getById(from),Station.getById(to));
        return new CardTxDetails(
                (long)Math.random(), date,time,Station.getById(from),Station.getById(to),
                chargeAndDescriptonBean.getFare(),chargeAndDescriptonBean.getMessage(),"1001"
        );
    }

}
