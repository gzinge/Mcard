package com.tigercard.service;

import com.tigercard.model.TransactionDayReportBean;
import com.tigercard.strategy.enums.FareZoneEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
class WeeklyCapPaymentServiceTest {

    private WeeklyCapPaymentService weeklyCapPaymentServiceUnderTest;

    @BeforeEach
    void setUp() {
        weeklyCapPaymentServiceUnderTest = new WeeklyCapPaymentService();
    }

    @Test
    void testInitWeekJourney() throws Exception {
        // Setup
        List<TransactionDayReportBean> expectedResult =
                Arrays.asList(
                        new TransactionDayReportBean(120.0,"18-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(120.0,"19-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(120.0,"20-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(120.0,"21-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(80.00,"22-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(40.00,"23-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(00.00,"24-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(100.0,"25-01-2021",false,FareZoneEnum.EAST)
                );
        String path = ResourceUtils.getFile("classpath:weekly.txt").getPath();
        // Run the test
        final List<TransactionDayReportBean> result = weeklyCapPaymentServiceUnderTest.initWeekJourney(Paths.get(path).toAbsolutePath());

        // Verify the results
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(expectedResult.size());
        Assertions.assertThat(result.equals(expectedResult)).isTrue();
    }

    @Test
    void testInitWeekJourney_ThrowsIOException()throws Exception {
        // Setup
        String path = ResourceUtils.getFile("classpath:weekly.txt").getPath();
        // Run the test
        assertThrows(IOException.class, () -> weeklyCapPaymentServiceUnderTest.initWeekJourney(Paths.get(path+"_data.txt")));
    }

    @Test
    void testTransactionDayCalAndReport() throws Exception {
        List<TransactionDayReportBean> expectedResult =
                Arrays.asList(
                        new TransactionDayReportBean(120.0,"18-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(120.0,"19-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(120.0,"20-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(120.0,"21-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(80.00,"22-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(40.00,"23-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(00.00,"24-01-2021",false,FareZoneEnum.EAST),
                        new TransactionDayReportBean(100.0,"25-01-2021",false,FareZoneEnum.EAST)
                );

        // Run the test
        double weeklyFare = weeklyCapPaymentServiceUnderTest.transactionDayCalAndReport(expectedResult);

        // Verify the results
        Assertions.assertThat(weeklyFare).isEqualTo(700);
    }
}
