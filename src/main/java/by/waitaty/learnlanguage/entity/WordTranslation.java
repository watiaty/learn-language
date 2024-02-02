package by.waitaty.learnlanguage.entity;

import lombok.Data;

@Data
public class WordTranslation {
    private Word word;
    private Language lang;
    private String Translation;
    private User user;
}
