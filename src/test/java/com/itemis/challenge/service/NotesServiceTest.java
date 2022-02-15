package com.itemis.challenge.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;


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

    @Test
    public void givenQuery_whenConvertIntergalacticUnitsQuery_thenConvertIntergalacticUnitsToRomanNotation() {
        this.notesService.intergalacticUnits = new HashMap<String, String>() {
            {
                put("glob", "I");
                put("prok", "V");
                put("pish", "X");
                put("tegj", "L");
            }
        };

        this.notesService.convertIntergalacticUnitsQuery(new String[] {"pish", "tegj", "glob", "glob"});

        String expectedQueryResult = "pish tegj glob glob is 42\n";
        String actualQueryResult = out.toString();

        Assertions.assertEquals(expectedQueryResult, actualQueryResult);
    }

    @Test
    public void givenNotValidQuery_whenConvertIntergalacticUnitsQuery_thenPrintErrorMessage() {
        this.notesService.intergalacticUnits = new HashMap<String, String>() {
            {
                put("prok", "V");
                put("pish", "X");
                put("tegj", "L");
            }
        };

        this.notesService.convertIntergalacticUnitsQuery(new String[] {"glob"});
        this.notesService.convertIntergalacticUnitsQuery(new String[] {"prok", "pish"});

        String expectedQueryResult = "glob is not a valid intergalactic unit\n"
                + "The specified amount is not valid\n";
        String actualQueryResult = out.toString();

        Assertions.assertEquals(expectedQueryResult, actualQueryResult);
    }

    @Test
    public void givenItem_whenAddItem_thenUpdateItemCatalogue() {
        this.notesService.intergalacticUnits = new HashMap<String, String>() {
            {
                put("glob", "I");
            }
        };

        this.notesService.addItem(new String[] {"glob", "glob", "silver","is", "34", "credits"});

        double expectedCataloguePrice = 17.0;
        double actualCataloguePrice = this.notesService.itemsCatalogue.get("silver");

        Assertions.assertEquals(expectedCataloguePrice, actualCataloguePrice);
    }

    @Test
    public void givenNotValidNote_whenAddItem_thenPrintErrorMessage() {
        this.notesService.intergalacticUnits = new HashMap<String, String>() {
            {
                put("glob", "I");
            }
        };

        this.notesService.addItem(new String[] {"glob", "pish", "silver","is", "34", "credits"});

        String expectedOutput = "pish is not a valid intergalactic unit\n";
        String actualOutput = out.toString();

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @AfterAll
    static void cleanUp() {
        System.setOut(null);
    }
}
