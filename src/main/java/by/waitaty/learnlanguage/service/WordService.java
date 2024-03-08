package by.waitaty.learnlanguage.service;

import by.waitaty.learnlanguage.entity.Language;
import by.waitaty.learnlanguage.entity.Word;

import java.util.List;
import java.util.Optional;

public interface WordService {
    List<Word> findAllByLang(Language lang);

    List<Word> findAll();

    Word addWord(Word word);

    Optional<Word> findWordByNameAndLang(String name, Language lang);

    Word findWordById(Long id);

    Word findOrCreateWord(String name, Language lang, String transcription);
}
