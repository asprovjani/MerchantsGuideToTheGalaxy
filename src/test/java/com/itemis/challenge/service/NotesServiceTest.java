package com.itemis.challenge.service;

import com.itemis.challenge.model.Note;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class NotesServiceTest {
    public NotesService notesService;
    public ByteArrayOutputStream out = new ByteArrayOutputStream();

    public static final ArrayList<Note> NOTES_1 = new ArrayList<Note>(
        Arrays.asList(
            new Note("glob is I"),
            new Note("prok is V"),
            new Note("pish is X"),
            new Note("tegj is L"),
            new Note("glob glob Silver is 34 Credits"),
            new Note("glob prok Gold is 57800 Credits"),
            new Note("pish pish Iron is 3910 Credits"),
            new Note("how much is pish tegj glob glob ?"),
            new Note("how many Credits is glob prok Silver ?"),
            new Note("how many Credits is glob prok Gold ?"),
            new Note("how many Credits is glob prok Iron ?"),
            new Note("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?")
        )
    );

    public static final String NOTES_1_OUT = "pish tegj glob glob is 42\n"
            + "glob prok Silver is 68 Credits\n"
            + "glob prok Gold is 57800 Credits\n"
            + "glob prok Iron is 782 Credits\n"
            + "I have no idea what you are talking about\r\n";

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

    @Test
    public void givenQuery_whenCalculateItemPriceQuery_thenCalculateItemPriceInDecimalNotation() {
        this.notesService.intergalacticUnits = new HashMap<String, String>() {
            {
                put("glob", "I");
                put("prok", "V");
            }
        };
        this.notesService.itemsCatalogue = new HashMap<String, Double>() {
            {
                put("silver", 17.0);
            }
        };

        this.notesService.calculateItemPriceQuery(new String[] {"glob", "prok"}, "silver");

        String expectedQueryResult = "glob prok Silver is 68 Credits\n";
        String actualQueryResult = out.toString();

        Assertions.assertEquals(expectedQueryResult, actualQueryResult);
    }

    @Test
    public void givenNotValidQuery_whenCalculateItemPrice_thenPrintErrorMessage() {
        this.notesService.intergalacticUnits = new HashMap<String, String>() {
            {
                put("glob", "I");
                put("prok", "V");
                put("pish", "X");
                put("tegj", "L");
            }
        };
        this.notesService.itemsCatalogue = new HashMap<String, Double>() {
            {
                put("silver", 17.0);
            }
        };

        this.notesService.calculateItemPriceQuery(new String[] {"glob", "prok"}, "iron");
        this.notesService.calculateItemPriceQuery(new String[] {"prok", "pish"}, "silver");

        String expectedQueryResult = "There is no known price information about iron\n"
                + "The specified amount is not valid\n";
        String actualQueryResult = out.toString();

        Assertions.assertEquals(expectedQueryResult, actualQueryResult);
    }

    @Test
    public void givenInputNotes_whenProcessNotes_thenHandleNotesProcessing() {
        this.notesService = new NotesService(NOTES_1);
        this.notesService.processNotes();

        String expectedResult = NOTES_1_OUT;
        String actualResult = out.toString();

        Assertions.assertEquals("I", this.notesService.intergalacticUnits.get("glob"));
        Assertions.assertEquals("V", this.notesService.intergalacticUnits.get("prok"));
        Assertions.assertEquals("X", this.notesService.intergalacticUnits.get("pish"));
        Assertions.assertEquals("L", this.notesService.intergalacticUnits.get("tegj"));

        Assertions.assertEquals(17.0, this.notesService.itemsCatalogue.get("silver"));
        Assertions.assertEquals(14450.0, this.notesService.itemsCatalogue.get("gold"));
        Assertions.assertEquals(195.5, this.notesService.itemsCatalogue.get("iron"));

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @AfterAll
    static void cleanUp() {
        System.setOut(null);
    }
}
