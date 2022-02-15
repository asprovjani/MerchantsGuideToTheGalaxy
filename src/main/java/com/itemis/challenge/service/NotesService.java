package com.itemis.challenge.service;

import com.itemis.challenge.constants.NoteTypeEnum;
import com.itemis.challenge.constants.RomanNumberEnum;
import com.itemis.challenge.interfaces.NotesServiceInterface;
import com.itemis.challenge.model.Note;

import java.util.ArrayList;
import java.util.Arrays;
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

                case D_ITEM_PRICE:
                    addItem(n.noteTokens);
                    break;

                case Q_CONVERT_UNITS:
                    convertIntergalacticUnitsQuery(
                        Arrays.copyOfRange(n.noteTokens, 3, n.noteTokens.length - 1)
                    );
                    break;

                //case Q_ITEM_PRICE:

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
     * Prints the conversion of intergalactic units to decimal notation.
     *
     * @param intergalacticUnits The array containing the intergalactic units to be converted.
     */
    public void convertIntergalacticUnitsQuery(String[] intergalacticUnits) {
        for (String unit : intergalacticUnits) {
            if (isReservedWord(unit) || !this.intergalacticUnits.containsKey(unit)) {
                System.out.printf("%s is not a valid intergalactic unit\n", unit);
                return;
            }
        }

        int queryResult = convertIntergalacticUnits(intergalacticUnits);

        if (queryResult != -1) {
            StringBuilder str = new StringBuilder();
            for (String unit : intergalacticUnits) {
                str.append(unit.concat(" "));
            }
            System.out.printf("%s is %d\n", str.toString().trim(), queryResult);
        }
        else {
            System.out.printf("The specified amount is not valid\n");
        }
    }

    /**
     * Converts the intergalactic units to decimal notation.
     *
     * @param intergalacticUnits The array containing the intergalactic units to be converted
     * @return the intergalactic units in decimal notation.
     */
    private int convertIntergalacticUnits(String[] intergalacticUnits) {
        String romanNumber = getRomanNotation(intergalacticUnits);
        if (romanNumber != null) {
            return romanToDecimal(romanNumber);
        }

        return -1;
    }

    /**
     * Extracts the contents of the note tokens and adds
     * the price for a single unit of an item to the item catalogue map.
     *
     * @param noteTokens The tokenized note.
     */
    public void addItem(String[] noteTokens) {
        String item = noteTokens[noteTokens.length - 4];
        double price = Double.parseDouble(noteTokens[noteTokens.length - 2]);

        if (!this.itemsCatalogue.containsKey(item)) {
            for (int i = 0; i < noteTokens.length - 4; i++) {
                if (isReservedWord(noteTokens[i]) ||
                        !this.intergalacticUnits.containsKey(noteTokens[i])) {
                    System.out.printf("%s is not a valid intergalactic unit\n", noteTokens[i]);
                    return;
                }
            }

            String romanNumber = getRomanNotation(
                    Arrays.copyOfRange(noteTokens, 0, noteTokens.length - 4)
            );

            if(romanNumber != null) {
                int itemQuantity = romanToDecimal(romanNumber);
                this.itemsCatalogue.put(item, price / itemQuantity);
            }
            else {
                System.out.printf("The specified amount is not valid\n");
            }
        }
    }

    /**
     * Converts the intergalactic units to roman notation.
     *
     * @param intergalacticUnits The intergalactic units to be converted to roman notation
     * @return The intergalactic units in roman notation.
     */
    private String getRomanNotation(String[] intergalacticUnits) {
        StringBuilder romanNotation = new StringBuilder();

        for (String intergalacticUnit : intergalacticUnits) {
            romanNotation.append(this.intergalacticUnits.get(intergalacticUnit));
        }

        if (romanNotation.toString()
                .matches("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")) {
            return romanNotation.toString();
        }

        return null;
    }

    /**
     * Converts a number from roman to decimal notation.
     *
     * @param number The number to be converted from roman to decimal notation
     * @return  The number in decimal notation.
     */
    private int romanToDecimal(String number) {
        int decimalNumber = 0;

        for (int i = 0; i < number.length(); i++) {
            if (i != number.length() - 1 && RomanNumberEnum.getDecimalValue(number.charAt(i)) <
                    RomanNumberEnum.getDecimalValue(number.charAt(i + 1))) {
                decimalNumber += RomanNumberEnum.getDecimalValue(number.charAt(i + 1)) -
                        RomanNumberEnum.getDecimalValue(number.charAt(i));
                i++;
            }
            else {
                decimalNumber += RomanNumberEnum.getDecimalValue(number.charAt(i));
            }
        }

        return decimalNumber;
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
