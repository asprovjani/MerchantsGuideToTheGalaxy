package com.itemis.challenge.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RomanNumberEnumTest {
    @Test
    public void givenValidRomanNumber_whenGetDecimalValue_thenDecimalValue() {
        int[] actualNumbers = {
                RomanNumberEnum.getDecimalValue('I'),
                RomanNumberEnum.getDecimalValue('V'),
                RomanNumberEnum.getDecimalValue('X'),
                RomanNumberEnum.getDecimalValue('L'),
                RomanNumberEnum.getDecimalValue('C'),
                RomanNumberEnum.getDecimalValue('D'),
                RomanNumberEnum.getDecimalValue('M'),
        };

        int[] expectedNumbers = {1, 5, 10, 50, 100, 500, 1000};

        Assertions.assertArrayEquals(expectedNumbers, actualNumbers);
    }

    @Test
    public void givenNotValidRomanNumber_whenGetDecimalValue_thenMinusOne() {
        int actualNumber = RomanNumberEnum.getDecimalValue('A');
        int expectedNumber = -1;

        Assertions.assertEquals(expectedNumber, actualNumber);
    }
}
