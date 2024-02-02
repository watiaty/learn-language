package by.waitaty.learnlanguage.entity;

import lombok.Data;

@Data
public class Word {
    private String word;
    private String transcription;
    private Language lang;
}
