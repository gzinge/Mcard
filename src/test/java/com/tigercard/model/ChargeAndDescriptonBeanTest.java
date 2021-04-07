package com.tigercard.model;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class ChargeAndDescriptonBeanTest {

    private ChargeAndDescriptonBean chargeAndDescriptonBeanUnderTest;

    @BeforeEach
    void setUp() {
        chargeAndDescriptonBeanUnderTest = new ChargeAndDescriptonBean(10.0, "message");
    }

    @Test
    public void testGetFare(){
        Assertions.assertThat(10.0).isEqualTo(chargeAndDescriptonBeanUnderTest.getFare());
    }

    @Test
    public void testGetMessage(){
        Assertions.assertThat("message").isEqualTo(chargeAndDescriptonBeanUnderTest.getMessage());
    }
}
