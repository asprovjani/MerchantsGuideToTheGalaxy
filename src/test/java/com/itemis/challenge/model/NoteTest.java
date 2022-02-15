package com.itemis.challenge.model;

import com.itemis.challenge.constants.NoteTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NoteTest {
    @Test
    public void givenNote_whenParseNote_thenReturnNoteType() {
        NoteTypeEnum[] expectedNoteTypes = {
                NoteTypeEnum.D_INTERGALACTIC_UNIT,
                NoteTypeEnum.D_ITEM_PRICE,
                NoteTypeEnum.Q_CONVERT_UNITS,
                NoteTypeEnum.Q_ITEM_PRICE,
                NoteTypeEnum.NOT_VALID
        };

        NoteTypeEnum[] actualNoteTypes = {
                new Note("glob is I").parseNote(),
                new Note("glob glob Silver is 34 Credits").parseNote(),
                new Note("how much is pish tegj glob glob ?").parseNote(),
                new Note("how many Credits is glob prok Silver ?").parseNote(),
                new Note("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?").parseNote()
        };

        Assertions.assertArrayEquals(expectedNoteTypes, actualNoteTypes);
    }
}
