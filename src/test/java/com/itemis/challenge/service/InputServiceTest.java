package com.itemis.challenge.service;

import com.itemis.challenge.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class InputServiceTest {
    @Test
    public void givenStdIn_whenReadNotes_thenReturnNoteList() {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("glob is I".getBytes()));

        InputService inputService = new InputService("");

        String expectedNote = new Note("glob is I").toString();
        String actualNote = inputService.readNotes().get(0).toString();

        Assertions.assertEquals(expectedNote, actualNote);
    }
}
