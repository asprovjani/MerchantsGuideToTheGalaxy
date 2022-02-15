package com.itemis.challenge;

import com.itemis.challenge.service.InputService;
import com.itemis.challenge.service.NotesService;

public class Solution {
    public static void main(String[] args) {
        InputService inputService = new InputService((args.length != 0) ? args[0] : "");
        NotesService notesService = new NotesService(inputService.readNotes());
        notesService.processNotes();
    }
}
