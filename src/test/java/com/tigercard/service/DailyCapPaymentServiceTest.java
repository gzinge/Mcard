package com.tigercard.service;

import com.tigercard.model.CardTxDetails;
import com.tigercard.model.ChargeAndDescriptonBean;
import com.tigercard.model.Station;
import com.tigercard.model.TransactionDayReportBean;
import com.tigercard.strategy.FareStrategy;
import com.tigercard.utils.DateUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
class DailyCapPaymentServiceTest {

    private DailyCapPaymentService dailyCapPaymentServiceUnderTest;

    @BeforeEach
    void setUp() {
        dailyCapPaymentServiceUnderTest = new DailyCapPaymentService();

    }

    @Test
    void testInitDayJourney() throws Exception {
        // Setup
        List<CardTxDetails> cardTxDetailsList = Arrays.asList(
                getTx("18-01-2021", "10:20:00",2, 1),
                getTx("18-01-2021", "10:45:00",1, 1),
                getTx("18-01-2021", "16:15:00",1, 1),
                getTx("18-01-2021", "18:15:00",1, 1),
                getTx("18-01-2021", "19:00:00",1, 2),
                getTx("18-01-2021", "21:50:00",2, 2)
        );
        final Map<String, List<CardTxDetails>> expectedResult = new LinkedHashMap<>();
        expectedResult.put("18-01-2021",cardTxDetailsList);

        String path = ResourceUtils.getFile("classpath:day.txt").getPath();
        // Run the test
        final Map<String, List<CardTxDetails>> result = dailyCapPaymentServiceUnderTest.initDayJourney(Paths.get(path).toAbsolutePath());

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testInitDayJourney_ThrowsParseException() throws Exception {
        // Setup
        String path = ResourceUtils.getFile("classpath:day_exception.txt").getPath();
        // Run the test
        assertThrows(ParseException.class, () -> dailyCapPaymentServiceUnderTest.initDayJourney(Paths.get(path).toAbsolutePath()));
    }

    @Test
    void testTransactionDayCalAndReport() throws Exception {
        // Setup
        List<CardTxDetails> cardTxDetailsList = Arrays.asList(
                getTx("18-01-2021", "10:20:00",2, 1),
                getTx("18-01-2021", "10:45:00",1, 1),
                getTx("18-01-2021", "16:15:00",1, 1),
                getTx("18-01-2021", "18:15:00",1, 1),
                getTx("18-01-2021", "19:00:00",1, 2),
                getTx("18-01-2021", "21:50:00",2, 2)
        );

        // Run the test
        final TransactionDayReportBean result = dailyCapPaymentServiceUnderTest.transactionDayCalAndReport(cardTxDetailsList);

        // Verify the results
        Assertions.assertThat(120.00).isEqualTo(result.getFare());
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
