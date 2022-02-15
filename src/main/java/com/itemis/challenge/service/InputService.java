package com.itemis.challenge.service;

import com.itemis.challenge.interfaces.InputServiceInterface;
import com.itemis.challenge.model.Note;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputService implements InputServiceInterface {
    private String filePath;

    public InputService(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the program input from the standard input or from a file.
     * If the file can not be found, the program exits with status 1.
     * @return
     */
    public ArrayList<Note> readNotes() {
        Scanner sc = null;

        if (this.filePath.equals("")) {
            sc = new Scanner(System.in);
        }
        else {
            try {
                sc = new Scanner(new File(this.filePath));
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage().concat(". The program will now terminate."));
                System.exit(1);
            }
        }

        ArrayList<Note> notes = new ArrayList<Note>();
        while (sc.hasNextLine()) {
            notes.add(
                    new Note(sc.nextLine().trim().replaceAll("\\s{2,}", " "))
            );
        }

        sc.close();

        return notes;
    }

    public String getFilePath() {
        return filePath;
    }
}
