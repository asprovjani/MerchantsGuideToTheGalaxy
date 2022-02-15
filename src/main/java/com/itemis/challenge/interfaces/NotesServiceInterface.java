package com.itemis.challenge.interfaces;

import com.itemis.challenge.constants.NoteTypeEnum;

public interface NotesServiceInterface {
    /**
     * @see NoteTypeEnum
     *
     * processes each note depending on the note type
     * defined in NoteTypeEnum.
     */
    public void processNotes();
}
