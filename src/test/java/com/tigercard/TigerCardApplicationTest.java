package com.tigercard;

import com.tigercard.controller.TigerCardController;
import com.tigercard.model.TransactionDayReportBean;
import com.tigercard.strategy.enums.FareZoneEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TigerCardApplicationTest {

    @Mock
    private TigerCardController mockTigerCardController;

    @InjectMocks
    private TigerCardApplication tigerCardApplicationUnderTest;

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void testRun() throws Exception {

        String input = "1" +System.lineSeparator() +"4";
        provideInput(input);
        // Run the test
        tigerCardApplicationUnderTest.run();

        // Verify the results
    }
}
