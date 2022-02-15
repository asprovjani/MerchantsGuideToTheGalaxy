package com.itemis.challenge.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class NotesServiceTest {
    public NotesService notesService;
    public ByteArrayOutputStream out = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        this.notesService = new NotesService(null);
        System.setOut(new PrintStream(this.out));
    }

    @Test
    public void givenIntergalacticUnitDefinition_whenAddUnit_thenUpdateIntergalacticUnitsMap() {
        this.notesService.addUnit("glob", "I");

        String expectedRomanNumber = "I";
        String actualRomanNumber = this.notesService.intergalacticUnits.get("glob");

        Assertions.assertEquals(expectedRomanNumber, actualRomanNumber);
    }

    @Test
    public void givenDuplicateIntergalacticUnit_whenAddUnit_thenKeepOriginalUnit() {
        this.notesService.addUnit("glob", "I");
        this.notesService.addUnit("glob", "I");

        String expectedOutput = "Intergalactic unit glob already exists\n";
        String actualOutput = out.toString();

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @AfterAll
    static void cleanUp() {
        System.setOut(null);
    }
}
