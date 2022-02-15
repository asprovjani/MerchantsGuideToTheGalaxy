package com.itemis.challenge.model;

import com.itemis.challenge.constants.NoteTypeEnum;

import java.util.Arrays;

public class Note {
    public String[] noteTokens;

    public Note(String note) {
        this.noteTokens = note.toLowerCase().split("\\s");
    }

    public NoteTypeEnum parseNote() {
        // check if note defines an intergalactic unit
        if (this.noteTokens.length == 3) {
            // check if intergalactic unit is a valid roman numeral
            if (this.noteTokens[2]
                    .toUpperCase().matches("I|V|X|L|C|D|M")) {
                return NoteTypeEnum.D_INTERGALACTIC_UNIT;
            }
        }
        // check if note defines an item price
        else if (this.noteTokens.length >= 5 &&
                this.noteTokens[noteTokens.length - 1].equals("credits") &&
                this.noteTokens[noteTokens.length - 2].matches("^[1-9]\\d*$")) {
            return NoteTypeEnum.D_ITEM_PRICE;
        }
        //check if note is a query
        else if (this.noteTokens[this.noteTokens.length - 1].equals("?")) {
            String queryStart = String.join(" ", this.noteTokens[0], this.noteTokens[1], this.noteTokens[2]);

            // check if query type is "how much is"
            if(queryStart.equals("how much is") && this.noteTokens.length >= 5) {
                return NoteTypeEnum.Q_CONVERT_UNITS;
            }

            // check if query type is "how many credits is"
            queryStart = queryStart.concat(" ".concat(this.noteTokens[3]));
            if(queryStart.equals("how many credits is") && this.noteTokens.length >= 7) {
                return NoteTypeEnum.Q_ITEM_PRICE;
            }
        }

        return NoteTypeEnum.NOT_VALID;
    }

    public String[] getNoteTokens() {
        return noteTokens;
    }

    public void setNoteTokens(String[] noteTokens) {
        this.noteTokens = noteTokens;
    }

    @Override
    public String toString() {
        return Arrays.toString(noteTokens);
    }
}
