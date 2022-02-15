package com.itemis.challenge.service;

import com.itemis.challenge.constants.NoteTypeEnum;
import com.itemis.challenge.interfaces.NotesServiceInterface;
import com.itemis.challenge.model.Note;

import java.util.ArrayList;
import java.util.HashMap;

public class NotesService implements NotesServiceInterface {
    private ArrayList<Note> notes;
    public HashMap<String, String> intergalacticUnits;
    public HashMap<String, Double> itemsCatalogue;
    public static final String[] reservedWords = {"credits", "how", "is", "many", "much"};

    public NotesService(ArrayList<Note> notes) {
        this.notes = notes;
        this.intergalacticUnits = new HashMap<String, String>();
        this.itemsCatalogue = new HashMap<String, Double>();
    }

    /**
     * @see NoteTypeEnum
     *
     * processes each note depending on the note type
     * defined in NoteTypeEnum.
     */
    public void processNotes() {
        for (Note n : this.notes) {
            switch (n.parseNote()) {
                case D_INTERGALACTIC_UNIT:
                    addUnit(n.noteTokens[0], n.noteTokens[2].toUpperCase());
                    break;
                /*
                case D_ITEM_PRICE:

                case Q_CONVERT_UNITS:

                case Q_ITEM_PRICE:
                 */
                default:
                    System.out.println("I have no idea what you are talking about");
                    break;
            }
        }
    }

    /**
     * Adds the conversion between intergalactic units and
     * roman numerals to the map of intergalactic units.
     *
     * @param intergalacticUnit The intergalactic unit to be added
     * @param romanRepresentation The roman numeral representation of the intergalactic unit.
     */
    public void addUnit(String intergalacticUnit, String romanRepresentation) {
        if (!isReservedWord(intergalacticUnit)) {
            if (!this.intergalacticUnits.containsKey(intergalacticUnit)) {
                this.intergalacticUnits.put(intergalacticUnit, romanRepresentation);
            }
            else {
                System.out.printf("Intergalactic unit %s already exists\n", intergalacticUnit);
            }
        }
    }

    /**
     * Checks whether a given word is a reserved word.
     *
     * @param word The word to be checked
     * @return {@code true} if it is a reserved word, otherwise {@code false}.
     */
    private boolean isReservedWord(String word) {
        for (String reservedWord : reservedWords) {
            if (word.equalsIgnoreCase(reservedWord)) {
                return true;
            }
        }

        return false;
    }
}
